package com.marketingpersonal.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Enumeration;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;
import org.primefaces.util.ComponentUtils;

public class Util {
	
	private static Util instance;
	public static final String SRC_PATH_DOWNLOAD = System.getProperty("file.separator") + "download" + System.getProperty("file.separator");
    public static final String SEPARADOR_CARPETA = System.getProperty("file.separator");
    public static final String SEPARADOR_LINEA = System.getProperty("line.separator");
    public static final String URL_FOTO_PERFIL = System.getProperty("file.separator") + "download" + System.getProperty("file.separator") + "images" + System.getProperty("file.separator");
	
	private Util(){}

	public static Util getInstance() {
		if(instance == null)
			instance = new Util();
		return instance;
	}
	
	public boolean validaNuloVacio(String valor) {
		if(valor == null || "".equals(valor)) {
			return true;
		}
		return false;
	}
	
	public void validarSession() {
		if(this.getSessionAttribute(EnumSessionAttributes.USUARIO) == null) {
			cerrarSesion();
		}
	}
	
	public void cerrarSesion() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext extContext = fc.getExternalContext();
		try {
			extContext.redirect(this.getContextPath() + "/login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cerrarSesionHttp();
	}
	
	public void setSessionAttribute(EnumSessionAttributes property, Object value){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		session.setAttribute(property.toString(), value);
	}
	
	public Object getSessionAttribute(EnumSessionAttributes property) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		return session.getAttribute(property.toString());
	}
	
	public void redirect(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath() 
		            + "/" + page);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	 * Ejejcuta codigo javascript en las paginas
	 * @param codigoJS codigo javascript a ejecutar
	 * @return retorna si la peticion era ajax la ejecucion exitosa
	 */
	public boolean ejecutarPF(String codigoJS){
		PrimeFaces pf = PrimeFaces.current();
		if (pf.isAjaxRequest()) {
			pf.executeScript(codigoJS);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Ejejcuta actualiza componentes en la vista
	 * @param componente componente a actualizar de la vista
	 * @return retorna si la peticion era ajax la actualizacion exitosa
	 */
	public boolean actualizarPF(String componente){
		PrimeFaces pf = PrimeFaces.current();
		if (pf.isAjaxRequest()) {
			pf.ajax().update(componente);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * obtiene el identificador de la sesión
	 * @return devuelve el Id de la sesion
	 */
	public String getIdSesion()	{
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getId();
	}

	/**
	 * Obtiene la ruta relativa del contexto de la aplicacion
	 * @return Url con la ruta relativa
	 */
	public String getContextPath() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getContextPath();
	}

	@SuppressWarnings("deprecation")
	public String findComponentClientIdPF(String... idBotonActualizar) {
		StringBuilder retorno = new StringBuilder();
		if(idBotonActualizar != null) {
			
			int cantidad = idBotonActualizar.length;
			int i = 1;
			for (String id : idBotonActualizar) {
				if(id != null) {
					if(id.contains("@"))
						retorno.append(id);
					else 
						retorno.append(ComponentUtils.findComponentClientId(id));
					if(cantidad > i )retorno.append(" ");
					i++;
				}
			}
		}
		return retorno.toString();
	}

	/**
	 * Método para que invalida la sesion
	 */
	public void cerrarSesionHttp() {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if(sesion != null){
			Enumeration<?> attSess = sesion.getAttributeNames();
			while( attSess.hasMoreElements() ){
				String attName = (String)attSess.nextElement();
				sesion.removeAttribute( attName );
			}
			sesion.invalidate();
		}
	}
	
	/**
	 * Obtiene el valor de un atributo desde el request
	 * @param atributo Atributo de request
	 * @return Valor del atributo o null si no existe
	 */
	public String get(EnumRequestAttributes atributo) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(atributo.toString());
	}
	
	public void mostrarErrorRedirect(String msg, boolean aceptRedirect) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(aceptRedirect);
		mostrarError(msg);
	}
	
	public void mostrarError(String mensaje) {
		this.mostrarMsgGeneral(mensaje, FacesMessage.SEVERITY_ERROR);
	}
	
	public void mostrarMensajeRedirect(String msg, boolean aceptRedirect) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(aceptRedirect);
		mostrarMensaje(msg);
	}
	
	public void mostrarMensaje(String mensaje) {
		this.mostrarMsgGeneral(mensaje, FacesMessage.SEVERITY_INFO);
	}
	
	private void mostrarMsgGeneral(String mensaje, FacesMessage.Severity severidad) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, mensaje, ""));  
	}

	public String encriptarClave(String clave) {
		return BCrypt.hashpw(clave, BCrypt.gensalt());
	}
	
	public boolean verificarContrasenna(String plainPassword, String hashedPassword) {
		if (BCrypt.checkpw(plainPassword, hashedPassword))
			return true;
		else
			return false;
	}
	
	/**
	 * Permite verificar si se encuentra el directorio especificado
	 * @param directorio Ruta del directorio a validar
	 * @return boolean true si existe el directorio; false no existe el directorio
	 * @throws Exception
	 */
	public boolean existeDirectorio(String rutaDirectorio) throws Exception{
		
		boolean retorno = false;
		File directorio = new File(rutaDirectorio);
		if(directorio.exists() && directorio.isDirectory()) { 
			retorno = true;
		}else{
			retorno = false;
		}
		return retorno;
	}
	
	/**
	 * Permite crear el directorio especificado
	 * @param directorio Ruta del directorio
	 * @throws Exception
	 */
	public void crearDirectorio(String directorio) throws Exception{
		
		int j = 0;
		String directorioVer = "";
		File dirCrear = null;
		String [] campos = directorio.split("\\\\");
		String separador = System.getProperty("file.separator");

		while(j < campos.length){
			directorioVer = directorioVer + campos[j] + separador;
			
			if (!existeDirectorio(directorioVer)){
				dirCrear = new File(directorioVer);
				dirCrear.mkdirs();
			}
			j++;
		}
	}
	
	/**
	 * Permite verificar si se encuentra el archivo especificado
	 * @param directorio Ruta del directorio a validar
	 * @return boolean true si existe el archivo; false no existe el archivo
	 * @throws Exception
	 */
	public boolean existeArchivo(String rutaArchivo) throws Exception{
		boolean retorno = false;
		File archivo = new File(rutaArchivo);
		if(archivo.exists() && archivo.isFile()) { 
			retorno = true;
		}else{
			retorno = false;
		}
		return retorno;
	}
	
	public String crearFoto(int fileName, byte[] foto) {
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		StringBuilder realPath = new StringBuilder(((String) servletContext.getRealPath("/")))
				.append(SEPARADOR_CARPETA);
		StringBuilder url;
		
		if(foto == null){
			url = new StringBuilder(servletContext.getContextPath().replace("/", SEPARADOR_CARPETA)) 
				.append(SEPARADOR_CARPETA)
				.append("resources").append(SEPARADOR_CARPETA)
				.append("images").append(SEPARADOR_CARPETA)
				.append("user.png");
		}else {
		
			realPath.append(URL_FOTO_PERFIL);
		
			url = new StringBuilder(servletContext.getContextPath().replace("/", SEPARADOR_CARPETA))
					.append(SEPARADOR_CARPETA)
					.append(URL_FOTO_PERFIL)
					.append(fileName).append(".jpg");
			try {
				this.crearDirectorio(realPath.toString());
				// convert byte array back to BufferedImage
				InputStream in = new ByteArrayInputStream(foto);
				BufferedImage bImageFromConvert = ImageIO.read(in);
	
				ImageIO.write(bImageFromConvert, "jpg", new File(
						realPath.toString() + fileName + ".jpg"));
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return url.toString();
	}
	
	 /**
     * Borrar archivos de un directorio
     */
    public void borrarArchivos(String rutaArchivo, String... archivosBorrar){
    	File archivo = null;
		for (String imagen : archivosBorrar) {
			archivo = new File(rutaArchivo + imagen);
			if (archivo.exists()){
				archivo.delete();
			}
		}
    }
    
	/**
	 * Elimina los archivos del servidor
	 * @param path
	 */
	public void borrarArchivos(String path){
		File file = new File(path);
		try {
			Files.deleteIfExists(file.toPath());
			if(!file.exists() && !file.isDirectory()) 
				Files.createDirectories(file.toPath().getParent());
				file.mkdirs();
				
		} catch (IOException e) {
			//Si falla la eliminacion de la carpeta se elimina archivo por archivo
			String[] filesD = file.list();
			if(filesD != null && filesD.length > 0)
				borrarArchivos(path, filesD);
		}
	}

}

