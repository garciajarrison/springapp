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
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.Validacion;
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
	private StreamedContent fileDescargar;
	
	private Validacion validacion;
	private List<Validacion> listaValidacion;
	
	
	public UsuarioBB() {
		util = Util.getInstance();
		usuario = new Usuario();
		selectedUsuario = new Usuario();
		listaUsuarios = getUsuarioService().getUsuarios();
		listasGenericas = ListasGenericas.getInstance();
	}
	
	private boolean validar(Usuario usu) {
		boolean permiteGuardar = true;
		
		if(usu.getNumeroDocumento() == null || "".equals(usu.getNumeroDocumento().trim())) {
			util.mostrarError("El campo Número de Documento es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getNombre() == null || "".equals(usu.getNombre().trim())) {
			util.mostrarError("El campo Nombre Completo es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getUsuario() == null || "".equals(usu.getUsuario().trim())) {
			util.mostrarError("El campo Usuario es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getCorreo() == null || "".equals(usu.getCorreo().trim())) {
			util.mostrarError("El campo Correo Electrónico es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getCargo() == null || "".equals(usu.getCargo().trim())) {
			util.mostrarError("El campo Cargo es requerido.");
			permiteGuardar = false;
		}
		
		if(usu.getRol() == null || "".equals(usu.getRol().trim())) {
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
				
				//Validar que no exista un registro duplicado
				for(Usuario usr : listaUsuarios) {
					if((usr.getNumeroDocumento().equals(usuario.getNumeroDocumento().trim())) ||
							(usr.getUsuario().equals(usuario.getUsuario().trim())))	 {
						guardar = false;
						break;
					}
				}
				
				if(guardar) {
					getUsuarioService().addUsuario(usuario);
					listaUsuarios = getUsuarioService().getUsuarios();
					usuario = new Usuario();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe un usuario con el mismo Número de Documento o mismo Usuario");
				}
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}
	
	public void updateUsuario() {
		try {
			boolean actualizar = true;
			if(validar(selectedUsuario)) { 
				
				//Validar que no exista un registro duplicado
				for(Usuario usr : listaUsuarios) {
					if(usr.getId() != selectedUsuario.getId())	 {
						if((usr.getNumeroDocumento().equals(selectedUsuario.getNumeroDocumento())) ||
								(usr.getUsuario().equals(selectedUsuario.getUsuario())))	 {
							actualizar = false;	
							break;
						}					
					}					
				}
				
				if(actualizar) {
					getUsuarioService().updateUsuario(selectedUsuario);
					listaUsuarios = getUsuarioService().getUsuarios();
					selectedUsuario = new Usuario();
					util.mostrarMensaje("Registro actualizado con éxito.");
				}else {
					util.mostrarError("Ya existe un usuario con el mismo Número de Documento o mismo Usuario");
				}
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
			if((e.toString()).contains("ConstraintViolationException")) {
				util.mostrarError("Error eliminando el registro. No puede eliminar un usuario que tenga Centros de Costo asociados o Presupuestos realizados");
			}else {
				util.mostrarError("Error eliminando el registro.");
			}
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
						
			if(validarArchivoPlano(workbook)) {
				getUsuarioService().addUsuariosArchivoPlano(sheet);
				
				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Usuarios", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public StreamedContent getFileDescargar() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Archivo Plano Usuarios.xlsx");
        fileDescargar = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Archivo Plano Usuarios.xlsx");
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
		
		if (!(sheet.getRow(0).getCell(0)).toString().trim().equals("Numero Documento")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la primer columna debe ser Numero Documento");
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

		if (!(sheet.getRow(0).getCell(2).toString()).trim().equals("Usuario")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la tercer columna debe ser Usuario");
			validacion.setFila("1");
			validacion.setColumna("C");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(3).toString()).trim().equals("Correo")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la cuarta columna debe ser Correo");
			validacion.setFila("1");
			validacion.setColumna("D");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(4).toString()).trim().equals("Cargo")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la quinta columna debe ser Cargo");
			validacion.setFila("1");
			validacion.setColumna("E");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(5).toString()).trim().equals("Rol")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la sexta columna debe ser Rol");
			validacion.setFila("1");
			validacion.setColumna("F");
			listaValidacion.add(validacion);
		}
		
		//Validar que no existe un usuario creado con el numero de documento
		Row row;		
		for(Usuario usr : listaUsuarios) {
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);				
				if(usr.getNumeroDocumento().equals(row.getCell(0)+"")) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe un usuario con el número de documento: "+row.getCell(0));
					validacion.setFila((fila+1)+"");
					validacion.setColumna("A");
					listaValidacion.add(validacion);
				}	
				
				if(usr.getUsuario().equals(row.getCell(2)+"")) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe un usuario con el usuario: "+row.getCell(2));
					validacion.setFila((fila+1)+"");
					validacion.setColumna("C");
					listaValidacion.add(validacion);
				}	
			}
		}	
		
		String rol="";
		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);	
			
			rol = row.getCell(5)+"";
			
			if(!rol.equals("Administrador")) {
				if(!rol.equals("Usuario")) {
					validacion = new Validacion();
					validacion.setMensaje("El rol: "+ row.getCell(5)+ " ingresado no es valido");
					validacion.setFila((fila+1)+"");
					validacion.setColumna("F");
					listaValidacion.add(validacion);
				}
				
			}	
		}
		if(listaValidacion.size()>=1) {
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
    
   	public List<Validacion> getListaValidacion() {
		return listaValidacion;
	}

	public void setListaValidacion(List<Validacion> listaValidacion) {
		this.listaValidacion = listaValidacion;
	}

 }