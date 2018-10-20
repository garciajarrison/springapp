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
import com.marketingpersonal.model.entity.Direccion;
import com.marketingpersonal.model.entity.Gerencia;
import com.marketingpersonal.model.entity.Jefatura;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IDireccionService;
import com.marketingpersonal.service.IGerenciaService;
import com.marketingpersonal.service.IJefaturaService;


@ManagedBean(name = "centroCostoBB")
@ViewScoped
public class CentroCostoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private IGerenciaService gerenciaService;
	@Autowired
	private IDireccionService direccionService;
	@Autowired
	private IJefaturaService jefaturaService;
	private Util util;
	private CentroCosto centroCosto;
	private CentroCosto selectedCentroCosto;
	private List<CentroCosto> listaCentroCostos;
	private UploadedFile file;
	private StreamedContent fileDescargar;
	
	private Gerencia gerencia;
	private Direccion direccion;
	private Jefatura jefatura;
	
	//Listas
	private List<Gerencia> lstGerencias;
	private List<Direccion> lstDireccions;
	private List<Jefatura> lstJefaturas;
	
	public CentroCostoBB() {
		util = Util.getInstance();
		centroCosto = new CentroCosto();
		selectedCentroCosto = new CentroCosto();
		listaCentroCostos = getCentroCostoService().getCentroCostos(false);
		
		lstGerencias = getGerenciaService().getGerencias(true);
		lstDireccions = getDireccionService().getDirecciones(true);
		lstJefaturas = getJefaturaService().getJefaturas(true);
	}
	
	private boolean validar(CentroCosto cc) {
		boolean permiteGuardar = true;
		
		if(cc.getCentroCosto() == null || 
				"".equals(cc.getCentroCosto().trim())) {
			util.mostrarError("El campo Centro de Costo es requerido.");
			permiteGuardar = false;
		}
		
		if(cc.getGerencia().getId() <= 0) {
			util.mostrarError("El campo Gerencia es requerido.");
			permiteGuardar = false;
		}
		
		if(cc.getDireccion().getId() <= 0) {
			util.mostrarError("El campo Dirección es requerido.");
			permiteGuardar = false;
		}
		
		if(cc.getJefatura().getId() <= 0) {
			util.mostrarError("El campo Jefatura es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addCentroCosto() {
		try {
			boolean guardar = true;
			
			if(validar(centroCosto)) {
								
				for(CentroCosto ceco : listaCentroCostos) {
					
					if((ceco.getCentroCosto().equals(centroCosto.getCentroCosto())) 
							&& (ceco.getGerencia().getId()==centroCosto.getGerencia().getId())
							&& (ceco.getDireccion().getId()==centroCosto.getDireccion().getId())
							&& (ceco.getJefatura().getId()==centroCosto.getJefatura().getId())) {
						guardar = false;			
					}						
				}		
								
				if(guardar) {
					getCentroCostoService().addCentroCosto(centroCosto);
					listaCentroCostos = getCentroCostoService().getCentroCostos(false);
					centroCosto = new CentroCosto();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe un Centro de Costo creado con lo datos ingresados");
				}
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateCentroCosto() {
		try {
			if(validar(selectedCentroCosto)) {
				getCentroCostoService().updateCentroCosto(selectedCentroCosto);
				listaCentroCostos = getCentroCostoService().getCentroCostos(false);
				selectedCentroCosto = new CentroCosto();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCentroCosto() {
		try {
			getCentroCostoService().deleteCentroCosto(selectedCentroCosto);
			listaCentroCostos = getCentroCostoService().getCentroCostos(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public void uploadPlanoCentrosCosto(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
						
			if(validarArchivoPlano(sheet)) {
				insertarCentrosCosto(sheet);
				
				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Centros de Costo", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public StreamedContent getFileDescargar() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Archivo Plano Centros de Costo.xlsx");
        fileDescargar = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Archivo Plano Centros de Costo.xlsx");
        return fileDescargar;
    }
	
	private boolean validarArchivoPlano(XSSFSheet sheet) {
		boolean permiteGuardar = true;
		
		//Validar numero de columnas del archvi
		if(sheet.getRow(0).getPhysicalNumberOfCells() != 4) {
			util.mostrarError("El número de columnas que tiene la hoja no es válido");
			permiteGuardar = false;
		}
		
		//Validar que no existe un registro de centro de costo duplicado
		Row row;	
		
		int idGerencia;
		int idDireccion;
		int idJefatura;
		
		for(CentroCosto ceco : listaCentroCostos) {
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);	
				
				// Obtenemos los ids de Gerencia, Direccion y Jefatura a partir de los nombres ingresados en el archivo plano
				// ya que el usuario no conoce los codigos
				idGerencia = getIdGerenciaByNombre(row.getCell(1)+"");
				idDireccion = getIdDireccionByNombre(row.getCell(2)+"");
				idJefatura = getIdJefaturaByNombre(row.getCell(3)+"");
								
				if((ceco.getCentroCosto().equals(row.getCell(0)+"")) 
						&& (ceco.getGerencia().getId()==idGerencia)
						&& (ceco.getDireccion().getId()==idDireccion)
						&& (ceco.getJefatura().getId()==idJefatura)) {
					util.mostrarError("Ya existe un Centro de Costo creado con lo datos ingresados ");
					permiteGuardar = false;				
				}	
			}
		}	
				
		return permiteGuardar;
	}
    
    public void insertarCentrosCosto(XSSFSheet sheet) {
		Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();	
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);
			
			centroCosto = new CentroCosto();
			
			gerencia = new Gerencia();
			gerencia.setId(getIdGerenciaByNombre(row.getCell(1)+""));
			
			direccion = new Direccion();
			direccion.setId(getIdDireccionByNombre(row.getCell(2)+""));
			
			jefatura = new Jefatura();
			jefatura.setId(getIdJefaturaByNombre(row.getCell(3)+""));
			
			centroCosto.setCentroCosto(row.getCell(0)+"");
			centroCosto.setGerencia(gerencia);
			centroCosto.setDireccion(direccion);
			centroCosto.setJefatura(jefatura);
						
			getCentroCostoService().addCentroCosto(centroCosto);		
		}
	}
	
	public int getIdGerenciaByNombre(String nombreGerencia) {
		for(Gerencia ger : lstGerencias) {
			if(ger.getNombre().equals(nombreGerencia)) {
				return ger.getId();
			}	
		}		
		return 9999;
	}
	
	public int getIdDireccionByNombre(String nombreDireccion) {
		for(Direccion dir : lstDireccions) {
			if(dir.getNombre().equals(nombreDireccion)) {
				return dir.getId();
			}	
		}		
		return 9999;
	}
	
	public int getIdJefaturaByNombre(String nombreJefatura) {
		for(Jefatura jef : lstJefaturas) {
			if(jef.getNombre().equals(nombreJefatura)) {
				return jef.getId();
			}	
		}		
		return 9999;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public IGerenciaService getGerenciaService() {
		return gerenciaService;
	}

	public void setGerenciaService(IGerenciaService gerenciaService) {
		this.gerenciaService = gerenciaService;
	}

	public IDireccionService getDireccionService() {
		return direccionService;
	}

	public void setDireccionService(IDireccionService direccionService) {
		this.direccionService = direccionService;
	}

	public IJefaturaService getJefaturaService() {
		return jefaturaService;
	}

	public void setJefaturaService(IJefaturaService jefaturaService) {
		this.jefaturaService = jefaturaService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public CentroCosto getSelectedCentroCosto() {
		return selectedCentroCosto;
	}

	public void setSelectedCentroCosto(CentroCosto selectedCentroCosto) {
		this.selectedCentroCosto = selectedCentroCosto;
	}

	public List<CentroCosto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CentroCosto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Jefatura getJefatura() {
		return jefatura;
	}

	public void setJefatura(Jefatura jefatura) {
		this.jefatura = jefatura;
	}

	public List<Gerencia> getLstGerencias() {
		return lstGerencias;
	}

	public void setLstGerencias(List<Gerencia> lstGerencias) {
		this.lstGerencias = lstGerencias;
	}

	public List<Direccion> getLstDireccions() {
		return lstDireccions;
	}

	public void setLstDireccions(List<Direccion> lstDireccions) {
		this.lstDireccions = lstDireccions;
	}

	public List<Jefatura> getLstJefaturas() {
		return lstJefaturas;
	}

	public void setLstJefaturas(List<Jefatura> lstJefaturas) {
		this.lstJefaturas = lstJefaturas;
	}

	public void setFileDescargar(StreamedContent fileDescargar) {
		this.fileDescargar = fileDescargar;
	}
	
 }