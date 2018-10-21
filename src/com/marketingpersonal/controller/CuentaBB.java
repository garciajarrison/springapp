package com.marketingpersonal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.marketingpersonal.model.entity.Validacion;
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
	
	private Validacion validacion;
	private List<Validacion> listaValidacion;
	
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
					if(cue.getCuenta().equals(cuenta.getCuenta().trim())) {
						guardar = false;						
					}
				}
				
				if(guardar) {
					getCuentaService().addCuenta(cuenta);
					listaCuentas = getCuentaService().getCuentas(false);
					cuenta = new Cuenta();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Cuenta con el código ingresado");
				}
			}			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 		
	}

	public void updateCuenta() {
		try {
			boolean actualizar = true;
			if(validar(selectedCuenta)) {
				
				//Validar que no exista un registro duplicado
				for(Cuenta cue : listaCuentas) {
					if(cue.getId() != selectedCuenta.getId())	 {
						if(cue.getCuenta().equals(selectedCuenta.getCuenta().trim()))	 {
							actualizar = false;	
							break;
						}
					}
				}
				
				if(actualizar) {
					getCuentaService().updateCuenta(selectedCuenta);
					listaCuentas = getCuentaService().getCuentas(false);
					selectedCuenta = new Cuenta();
					util.mostrarMensaje("Registro actualizado con éxito.");
				}else {
					util.mostrarError("Ya existe una Cuenta con el código ingresado");
				}
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
			if((e.toString()).contains("ConstraintViolationException")) {
				util.mostrarError("Error eliminando el registro. No puede eliminar una cuenta que tenga centros de costo o presupuestos asociados");
			}else {
				util.mostrarError("Error eliminando el registro.");
			}
		} 	
	}
	
	public void uploadPlanoCuentas(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
						
			if(validarArchivoPlano(workbook)) {
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
	
	private boolean validarArchivoPlano(XSSFWorkbook workbook) {
		boolean permiteGuardar = true;
		
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		listaValidacion = new ArrayList<>();
		
		if (workbook.getNumberOfSheets() > 1) {
			validacion = new Validacion();
			validacion.setMensaje("El archivo de excel a cargar solo puede tener 1 hoja con los datos");
			validacion.setFila("--");
			validacion.setColumna("--");
			listaValidacion.add(validacion);
		}
		
		if (sheet.getPhysicalNumberOfRows() <= 1) {
			validacion = new Validacion();
			validacion.setMensaje("El archivo no contiene registros con datos");
			validacion.setFila("--");
			validacion.setColumna("--");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(0).getCell(0)).toString().trim().equals("Cuenta")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la primer columna debe ser Cuenta");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(1)).toString().trim().equals("Nombre")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la segunda columna debe ser Nombre");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}
		
		//Validar que no existe una cuenta duplicada 
		Row row;		
		for(Cuenta cue : listaCuentas) {
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);				
				if(cue.getCuenta().equals(row.getCell(0)+"".trim())) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe una Cuenta con el código ingresado: "+row.getCell(0));
					validacion.setFila((fila+1)+"");
					validacion.setColumna("A");
					listaValidacion.add(validacion);
				}
			}
		}	
		
		if(listaValidacion.size()>=1) {
			permiteGuardar = false;
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
		listaCuentas = getCuentaService().getCuentas(false);
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

	public void setFileDescargar(StreamedContent fileDescargar) {
		this.fileDescargar = fileDescargar;
	}
	
	public Validacion getValidacion() {
		return validacion;
	}

	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;
	}

	public List<Validacion> getListaValidacion() {
		return listaValidacion;
	}

	public void setListaValidacion(List<Validacion> listaValidacion) {
		this.listaValidacion = listaValidacion;
	}
	
 }