package com.marketingpersonal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.service.ICuentaService;

@ManagedBean(name = "cuentaBB")
@ViewScoped
public class CuentaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICuentaService cuentaService;
	private Util util;
	private Cuenta cuenta;
	private Cuenta selectedCuenta;
	private List<Cuenta> listaCuentas;
	private UploadedFile file;
	private StreamedContent fileDescargar;
	
	public CuentaBB() {
		util = Util.getInstance();
		cuenta = new Cuenta();
		selectedCuenta = new Cuenta();
		listaCuentas = getCuentaService().getCuentas(false);
	}
	
	private boolean validar(Cuenta cue) {
		boolean permiteGuardar = true;		

		if(cue.getCuenta() == null || "".equals(cue.getCuenta().trim())) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getNombre() == null || "".equals(cue.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addCuenta() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(cuenta)) {
				
				//Validar que no exista un registro duplicado
				for(Cuenta cue : listaCuentas) {
					if(cue.getCuenta().equals(cuenta.getCuenta())) {
						guardar = false;						
					}
				}
				
				if(guardar) {
					getCuentaService().addCuenta(cuenta);
					listaCuentas = getCuentaService().getCuentas(false);
					cuenta = new Cuenta();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Cuenta con el mismo código");
				}
			}			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 		
	}

	public void updateCuenta() {
		try {
			if(validar(selectedCuenta)) {
				getCuentaService().updateCuenta(selectedCuenta);
				listaCuentas = getCuentaService().getCuentas(false);
				selectedCuenta = new Cuenta();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCuenta() {
		try {
			getCuentaService().deleteCuenta(selectedCuenta);
			listaCuentas = getCuentaService().getCuentas(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Cuenta getSelectedCuenta() {
		return selectedCuenta;
	}

	public void setSelectedCuenta(Cuenta selectedCuenta) {
		this.selectedCuenta = selectedCuenta;
	}

	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}
	
	public UploadedFile getFile() {
	    return file;
	}

	public void setFile(UploadedFile file) {
	    this.file = file;
	}
	
	public void uploadPlanoCuentas(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
						
			if(validarArchivoPlano(sheet)) {
				insertarCuentas(sheet);
				
				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Cuentas", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public StreamedContent getFileDescargar() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Archivo Plano Cuentas.xlsx");
        fileDescargar = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Archivo Plano Cuentas.xlsx");
        return fileDescargar;
    }
	
	private boolean validarArchivoPlano(XSSFSheet sheet) {
		boolean permiteGuardar = true;
		
		//Validar numero de columnas del archvi
		if(sheet.getRow(0).getPhysicalNumberOfCells() != 2) {
			util.mostrarError("El número de columnas que tiene la hoja no es válido");
			permiteGuardar = false;
		}
		
		//Validar que no existe una cuenta creada con el numero 
		Row row;		
		for(Cuenta cue : listaCuentas) {
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);				
				if(cue.getCuenta().equals(row.getCell(0)+"")) {
					util.mostrarError("Ya existe una cuenta con el número " + row.getCell(0));
					permiteGuardar = false;					
				}	
			}
		}		
		
		return permiteGuardar;
	}
    
    public void insertarCuentas(XSSFSheet sheet) {
		Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();	
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);
			
			cuenta = new Cuenta();

			cuenta.setCuenta(row.getCell(0)+"");
			cuenta.setNombre(row.getCell(1)+"");
						
			getCuentaService().addCuenta(cuenta);		
		}
	}
	
 }