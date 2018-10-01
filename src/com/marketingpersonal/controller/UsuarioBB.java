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
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.IUsuarioService;

@ManagedBean(name = "usuarioBB")
@ViewScoped
public class UsuarioBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUsuarioService usuarioService;
	private Util util;
	private Usuario usuario;
	private Usuario selectedUsuario;
	private List<Usuario> listaUsuarios;
	private ListasGenericas listasGenericas;
	private UploadedFile file;
	
	public UsuarioBB() {
		util = Util.getInstance();
		usuario = new Usuario();
		selectedUsuario = new Usuario();
		listaUsuarios = getUsuarioService().getUsuarios();
		listasGenericas = ListasGenericas.getInstance();
	}
	
	private boolean validar(Usuario usu) {
		boolean permiteGuardar = true;
		
		if(usu.getNumeroDocumento() == null || 
				"".equals(usu.getNumeroDocumento().trim())) {
			util.mostrarError("El campo Número de Documento es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getNombre() == null || 
				"".equals(usu.getNombre().trim())) {
			util.mostrarError("El campo Nombre Completo es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getUsuario() == null || 
				"".equals(usu.getUsuario().trim())) {
			util.mostrarError("El campo Usuario es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getCorreo() == null || 
				"".equals(usu.getCorreo().trim())) {
			util.mostrarError("El campo Correo Electrónico es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getCargo() == null || 
				"".equals(usu.getCargo().trim())) {
			util.mostrarError("El campo Cargo es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getRol() == null || 
				"".equals(usu.getRol().trim())) {
			util.mostrarError("El campo Rol es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addUsuario() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(usuario)) {
				
				//Validar que no existe un usuario creado con el numero de documento
				for(Usuario usr : listaUsuarios) {
					if(usr.getNumeroDocumento().equals(usuario.getNumeroDocumento())) {
						guardar = false;						
					}
				}
				
				if(guardar) {
					getUsuarioService().addUsuario(usuario);
					listaUsuarios = getUsuarioService().getUsuarios();
					usuario = new Usuario();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe un usuario con el mismo número de documento");
				}
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}
	
	public void updateUsuario() {
		try {
			if(validar(selectedUsuario)) {
				getUsuarioService().updateUsuario(selectedUsuario);
				listaUsuarios = getUsuarioService().getUsuarios();
				selectedUsuario = new Usuario();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteUsuario() {
		try {
			getUsuarioService().deleteUsuario(selectedUsuario);
			listaUsuarios = getUsuarioService().getUsuarios();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getSelectedUsuario() {
		return selectedUsuario;
	}

	public void setSelectedUsuario(Usuario selectedUsuario) {
		this.selectedUsuario = selectedUsuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ListasGenericas getListasGenericas() {
		return listasGenericas;
	}

	public void setListasGenericas(ListasGenericas listasGenericas) {
		this.listasGenericas = listasGenericas;
	}
	
	public UploadedFile getFile() {
	    return file;
	}

	public void setFile(UploadedFile file) {
	    this.file = file;
	}
	
	public void uploadPlanoUsuarios(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int numColumnas = sheet.getRow(0).getPhysicalNumberOfCells();
			int numFilas = sheet.getPhysicalNumberOfRows();
			
			validarNumeroColumnas(numColumnas);
			
			insertarUsuarios(sheet, numFilas);
			
			FacesMessage msg = new FacesMessage("Carga Archivo Plano de Usuarios", event.getFile().getFileName() + " fue cargado correctamente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    public void validarNumeroColumnas(int numColumnas) {
		if (numColumnas != 6) {
			util.mostrarError("El numero de columnas que tiene la hoja no es válido");
		}
	}
    
    public void insertarUsuarios(XSSFSheet sheet, int numFilas) {
		Row row;
				
		// Recorrido de filas
		for (int fila = 1; fila < numFilas; fila++) {

			row = sheet.getRow(fila);
			
			usuario = new Usuario();

			usuario.setNumeroDocumento(row.getCell(0)+"");
			usuario.setNombre(row.getCell(1)+"");
			usuario.setUsuario(row.getCell(2)+"");
			usuario.setCorreo(row.getCell(3)+"");
			usuario.setCargo(row.getCell(4)+"");
			usuario.setRol(row.getCell(5)+"");
						
			// insertar.out.println();
			getUsuarioService().addUsuario(usuario);		
		}
	}

 }