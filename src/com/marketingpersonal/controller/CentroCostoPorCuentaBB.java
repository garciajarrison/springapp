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
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ManagedBean(name = "centroCostoPorCuentaBB")
@ViewScoped
public class CentroCostoPorCuentaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICuentaService cuentaService;
	private Util util;
	private CentroCostoPorCuenta centroCostoPorCuenta;
	private CentroCostoPorCuenta selectedCentroCostoPorCuenta;
	private List<CentroCostoPorCuenta> listaCentroCostoPorCuentas;
	private UploadedFile file;
	private StreamedContent fileDescargar;
	
	private Cuenta cuenta;
	private CentroCosto centroCosto;
	
	private List<Cuenta> lstCuenta;
	private List<CentroCosto> lstCentroCosto;
	
	public CentroCostoPorCuentaBB() {
		util = Util.getInstance();
		centroCostoPorCuenta = new CentroCostoPorCuenta();
		selectedCentroCostoPorCuenta = new CentroCostoPorCuenta();
		listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
		
		lstCuenta = getCuentaService().getCuentas(true);
		lstCentroCosto = getCentroCostoService().getCentroCostos(true);	
	}
	
	private boolean validar(CentroCostoPorCuenta cue) {
		boolean permiteGuardar = true;
		
		if(cue.getCuenta().getId() <= 0) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getCentroCosto().getId() <= 0) {
			util.mostrarError("El campo Centro de Costo es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addCentroCostoPorCuenta() {
		try {
			boolean guardar = true;
			
			if(validar(centroCostoPorCuenta)) {
				
				for(CentroCostoPorCuenta ceco : listaCentroCostoPorCuentas) {					
					if((ceco.getCuenta().getId()==centroCostoPorCuenta.getCuenta().getId()) 
							&& (ceco.getCentroCosto().getId()==centroCostoPorCuenta.getCentroCosto().getId())) {
						guardar = false;			
					}						
				}
				
				if(guardar) {
					getCentroCostoService().addCentroCostoPorCuenta(centroCostoPorCuenta);
					listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
					centroCostoPorCuenta = new CentroCostoPorCuenta();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Cuenta y Centro de Costo creado con los datos ingresados");
				}				
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateCentroCostoPorCuenta() {
		try {
			if(validar(selectedCentroCostoPorCuenta)) {
				getCentroCostoService().updateCentroCostoPorCuenta(selectedCentroCostoPorCuenta);
				listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
				selectedCentroCostoPorCuenta = new CentroCostoPorCuenta();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCentroCostoPorCuenta() {
		try {
			getCentroCostoService().deleteCentroCostoPorCuenta(selectedCentroCostoPorCuenta);
			listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}
	
	public void uploadPlanoCentrosCostoPorCuenta(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
						
			if(validarArchivoPlano(sheet)) {
				insertarCentroCostoPorCuenta(sheet);
				
				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Cuentas por Centros de Costo", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			workbook.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public StreamedContent getFileDescargar() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Archivo Plano Cuentas por Centros de Costo.xlsx");
        fileDescargar = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Archivo Plano Cuentas por Centros de Costo.xlsx");
        return fileDescargar;
    }
	
	private boolean validarArchivoPlano(XSSFSheet sheet) {
		boolean permiteGuardar = true;
		
		//Validar numero de columnas del archvi
		if(sheet.getRow(0).getPhysicalNumberOfCells() != 2) {
			util.mostrarError("El número de columnas que tiene la hoja no es válido");
			permiteGuardar = false;
		}
		
		//Validar que no existe un registro de centro de costo duplicado
		Row row;	
		
		int idCuenta;
		int idCentroCosto;

		for(CentroCostoPorCuenta ceco : listaCentroCostoPorCuentas) {	
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);	
				
				idCuenta = getIdCuentaByCuenta(row.getCell(0)+"");
				idCentroCosto = getIdCentroCostoByCentroCosto(row.getCell(1)+"");
				
				if((ceco.getCuenta().getId()==idCuenta) && (ceco.getCentroCosto().getId()==idCentroCosto)) {
					util.mostrarError("Ya existe un Centro de Costo y Cuenta creado con lo datos ingresados ");
					permiteGuardar = false;				
				}	
			}
		}	
				
		return permiteGuardar;
	}
    
    public void insertarCentroCostoPorCuenta(XSSFSheet sheet) {
    	Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();	
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);
			
			centroCostoPorCuenta = new CentroCostoPorCuenta();
			
			cuenta = new Cuenta();
			cuenta.setId(getIdCuentaByCuenta(row.getCell(0)+""));		
			
			centroCosto = new CentroCosto();
			centroCosto.setId(getIdCentroCostoByCentroCosto(row.getCell(1)+""));	
			
			centroCostoPorCuenta.setCentroCosto(centroCosto);
			centroCostoPorCuenta.setCuenta(cuenta);
									
			getCentroCostoService().addCentroCostoPorCuenta(centroCostoPorCuenta);		
		}    	
	}	
    
    public int getIdCuentaByCuenta(String cuenta) {
		for(Cuenta cue : lstCuenta) {
			if(cue.getCuenta().equals(cuenta)) {
				return cue.getId();
			}	
		}		
		return 9999;
	}
	
	public int getIdCentroCostoByCentroCosto(String centroCosto) {
		for(CentroCosto ceco : lstCentroCosto) {
			if(ceco.getCentroCosto().equals(centroCosto)) {
				return ceco.getId();
			}	
		}		
		return 9999;
	}

 }