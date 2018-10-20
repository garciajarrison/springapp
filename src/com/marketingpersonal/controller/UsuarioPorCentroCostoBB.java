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
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IUsuarioService;

@ManagedBean(name = "usuarioPorCentroCostoBB")
@ViewScoped
public class UsuarioPorCentroCostoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ICentroCostoService centroCostoService;
	private Util util;
	private UsuarioPorCentroCosto usuarioPorCentroCosto;
	private UsuarioPorCentroCosto selectedUsuarioPorCentroCosto;
	private List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos;
	private UploadedFile file;
	private StreamedContent fileDescargar;
	
	private CentroCosto centroCosto;
	private Usuario responsable;
	private Usuario aprobadorInicial;
	private Usuario aprobadorFinal;
	
	private List<CentroCosto> lstCentroCostos;
	private List<Usuario> lstUsuarios;
	
	public UsuarioPorCentroCostoBB() {
		util = Util.getInstance();
		usuarioPorCentroCosto = new UsuarioPorCentroCosto();
		selectedUsuarioPorCentroCosto = new UsuarioPorCentroCosto();
		listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
		lstCentroCostos = getCentroCostoService().getCentroCostos(true);
		lstUsuarios = getUsuarioService().getUsuarios();
	}
	
	private boolean validar(UsuarioPorCentroCosto cue) {
		boolean permiteGuardar = true;
		
		if(cue.getCentroCosto().getId() <= 0) {
			util.mostrarError("El campo Centro de Costo es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioResponsable().getId() <= 0) {
			util.mostrarError("El campo Responsable es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioAprobadorInicial().getId() <= 0) {
			util.mostrarError("El campo Aprobador Inicial es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioAprobadorFinal().getId() <= 0) {
			util.mostrarError("El campo Aprobador Final es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addUsuarioPorCentroCosto() {
		try {
			boolean guardar = true;
			
			if(validar(usuarioPorCentroCosto)) {
				
				for(UsuarioPorCentroCosto usceco : listaUsuarioPorCentroCostos) {					
					if((usceco.getCentroCosto().getId()==usuarioPorCentroCosto.getCentroCosto().getId()) 
							&& (usceco.getUsuarioResponsable().getId()==usuarioPorCentroCosto.getUsuarioResponsable().getId())
							&& (usceco.getUsuarioAprobadorInicial().getId()==usuarioPorCentroCosto.getUsuarioAprobadorInicial().getId())
							&& (usceco.getUsuarioAprobadorFinal().getId()==usuarioPorCentroCosto.getUsuarioAprobadorFinal().getId())) {
						guardar = false;			
					}						
				}
				
				if(guardar) {
					getUsuarioService().addUsuarioPorCentroCosto(usuarioPorCentroCosto);
					listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
					usuarioPorCentroCosto = new UsuarioPorCentroCosto();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe Centro de Costo creado con los datos ingresados");
				}				
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}

	public void updateUsuarioPorCentroCosto() {
		try {
			if(validar(selectedUsuarioPorCentroCosto)) {
				getUsuarioService().updateUsuarioPorCentroCosto(selectedUsuarioPorCentroCosto);
				listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
				selectedUsuarioPorCentroCosto = new UsuarioPorCentroCosto();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteUsuarioPorCentroCosto() {
		try {
			getUsuarioService().deleteUsuarioPorCentroCosto(selectedUsuarioPorCentroCosto);
			listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}
	
	public void uploadPlanoUsuariosPorCentrosCosto(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
						
			if(validarArchivoPlano(sheet)) {
				insertarUsuarioPorCentroCosto(sheet);
				
				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Usuarios por Centros de Costo", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			workbook.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public StreamedContent getFileDescargar() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Archivo Plano Usuarios por Centros de Costo.xlsx");
        fileDescargar = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Archivo Plano Usuarios por Centros de Costo.xlsx");
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
		
		int idCentroCosto;
		int idResponsable;
		int idAprobadorInicial;
		int idAprobadorFinal;

		for(UsuarioPorCentroCosto usceco : listaUsuarioPorCentroCostos) {	
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);	
				
				idCentroCosto = getIdCentroCostoByCentroCosto(row.getCell(0)+"");
				idResponsable = getIdUsuarioByUsuario(row.getCell(1)+"");
				idAprobadorInicial = getIdUsuarioByUsuario(row.getCell(2)+"");
				idAprobadorFinal = getIdUsuarioByUsuario(row.getCell(3)+"");
				
				if((usceco.getCentroCosto().getId()==idCentroCosto) 
						&& (usceco.getUsuarioResponsable().getId()==idResponsable)
						&& (usceco.getUsuarioAprobadorInicial().getId()==idAprobadorInicial)
						&& (usceco.getUsuarioAprobadorFinal().getId()==idAprobadorFinal)) {
					util.mostrarError("Ya existe un Centro de Costo, Responsable, Aprobador Inicial y Aprobador Final creado con lo datos ingresados ");
					permiteGuardar = false;				
				}	
			}
		}	
				
		return permiteGuardar;
	}
    
    public void insertarUsuarioPorCentroCosto(XSSFSheet sheet) {
    	Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();	
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);
			
			usuarioPorCentroCosto = new UsuarioPorCentroCosto();
			
			System.out.println("fila"+fila+ " - "+row.getCell(0)+"");
			
			centroCosto = new CentroCosto();
			centroCosto.setId(getIdCentroCostoByCentroCosto(row.getCell(0)+""));		
			
			responsable = new Usuario();
			responsable.setId(getIdUsuarioByUsuario(row.getCell(1)+""));
			
			aprobadorInicial = new Usuario();
			aprobadorInicial.setId(getIdUsuarioByUsuario(row.getCell(2)+""));
			
			aprobadorFinal = new Usuario();
			aprobadorFinal.setId(getIdUsuarioByUsuario(row.getCell(3)+""));
			
			usuarioPorCentroCosto.setCentroCosto(centroCosto);
			usuarioPorCentroCosto.setUsuarioResponsable(responsable);
			usuarioPorCentroCosto.setUsuarioAprobadorInicial(aprobadorInicial);
			usuarioPorCentroCosto.setUsuarioAprobadorFinal(aprobadorFinal);
			
			getUsuarioService().addUsuarioPorCentroCosto(usuarioPorCentroCosto);		
		}    	
	}	
    
    public int getIdCentroCostoByCentroCosto(String centroCosto) {
		for(CentroCosto ceco : lstCentroCostos) {
			if(ceco.getCentroCosto().equals(centroCosto)) {
				return ceco.getId();
			}	
		}		
		return 9999;
	}
    
    public int getIdUsuarioByUsuario(String usuario) {
		for(Usuario usu : lstUsuarios) {
			if(usu.getUsuario().equals(usuario)) {
				return usu.getId();
			}	
		}		
		return 9999;
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public UsuarioPorCentroCosto getUsuarioPorCentroCosto() {
		return usuarioPorCentroCosto;
	}

	public void setUsuarioPorCentroCosto(UsuarioPorCentroCosto usuarioPorCentroCosto) {
		this.usuarioPorCentroCosto = usuarioPorCentroCosto;
	}

	public UsuarioPorCentroCosto getSelectedUsuarioPorCentroCosto() {
		return selectedUsuarioPorCentroCosto;
	}

	public void setSelectedUsuarioPorCentroCosto(UsuarioPorCentroCosto selectedUsuarioPorCentroCosto) {
		this.selectedUsuarioPorCentroCosto = selectedUsuarioPorCentroCosto;
	}

	public List<UsuarioPorCentroCosto> getListaUsuarioPorCentroCostos() {
		return listaUsuarioPorCentroCostos;
	}

	public void setListaUsuarioPorCentroCostos(List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos) {
		this.listaUsuarioPorCentroCostos = listaUsuarioPorCentroCostos;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Usuario getAprobadorInicial() {
		return aprobadorInicial;
	}

	public void setAprobadorInicial(Usuario aprobadorInicial) {
		this.aprobadorInicial = aprobadorInicial;
	}

	public Usuario getAprobadorFinal() {
		return aprobadorFinal;
	}

	public void setAprobadorFinal(Usuario aprobadorFinal) {
		this.aprobadorFinal = aprobadorFinal;
	}

	public List<CentroCosto> getLstCentroCostos() {
		return lstCentroCostos;
	}

	public void setLstCentroCostos(List<CentroCosto> lstCentroCostos) {
		this.lstCentroCostos = lstCentroCostos;
	}

	public List<Usuario> getLstUsuarios() {
		return lstUsuarios;
	}

	public void setLstUsuarios(List<Usuario> lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
	}

	public void setFileDescargar(StreamedContent fileDescargar) {
		this.fileDescargar = fileDescargar;
	}
        
 }