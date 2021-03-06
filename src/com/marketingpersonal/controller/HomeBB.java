package com.marketingpersonal.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Home;
import com.marketingpersonal.service.IHomeService;

/**
 * Clase controladora para manejo del home de la aplicaci�n en el cual se visualizan las variables macroeconomicas
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@ManagedBean(name = "homeBB")
@ViewScoped
public class HomeBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//Campos de la clase
	@Autowired
	private IHomeService homeService;
	private Util util;
	private Home home;
	private Home selectedHome;
	private List<Home> listaHomes;
	
	private UploadedFile imagen;
	
	/**
     * Constructor para controlador de Home
     */
	public HomeBB() {
		util = Util.getInstance();
		home = new Home();
		selectedHome = new Home();
		listaHomes = getHomeService().getHomes(false);
	}
	
	/**
     * Metodo para actualizar imagenes de las variables macroeconomicas
     * @param event: variable que contiene la imagen a cargar al perfil del usuario
     */
	public void upload(FileUploadEvent event) {
		imagen = event.getFile();
        if(imagen != null) {
        	home.setUrl(util.crearFoto(home.getNombre(), imagen.getContents()));
        	util.mostrarMensaje("Registro agregado con �xito."); 
        }
    }
	
	/**
     * M�todo que valida la obligatoriedad de los campos
     * @param ho: Variable de tipo Home
     * @param validaImagen: Variable booleana que indica si la imagen es valida o no
     * @return permiteGuardar: variable booleana que indica si es posible guardar o no la nueva imagen
     */
	private boolean validar(Home ho, boolean validaImagen) {
		boolean permiteGuardar = true;
		
		if(ho.getNombre() == null || 
				"".equals(ho.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		if(ho.getFechaInicio() == null) {
			util.mostrarError("El campo Fecha inicio es requerido.");
			permiteGuardar = false;
		}
		
		if(ho.getFechaFin() == null) {
			util.mostrarError("El campo Fecha fin es requerido.");
			permiteGuardar = false;
		}
		
		//Validamos que fecha inicio no sea mayor a la final
		if(ho.getFechaInicio() != null && 
				ho.getFechaFin() != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				int f1 = Integer.parseInt(sdf.format(ho.getFechaInicio()));
				int f2 = Integer.parseInt(sdf.format(ho.getFechaFin()));
			
				if(f1 > f2) {
					util.mostrarError("El campo Fecha inicio no puede ser mayor a Fecha fin.");
					permiteGuardar = false;
				}
			}catch(Exception e){}
		}
		
		if(validaImagen && imagen == null) {
			util.mostrarError("El campo Imagen es requerido.");
		}
		
		return permiteGuardar;
	}
	
	/**
     *Metodo para crear una nueva Imagen de variable Macroeconomica
     */
	public void addHome() {
		try {
			if(validar(home, true)) {
		        home.setUrl(util.crearFoto(home.getNombre(), imagen.getContents()));
				getHomeService().addHome(home);
				listaHomes = getHomeService().getHomes(false);
				home = new Home();
				util.mostrarMensaje("Registro agregado con �xito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	/**
     *Metodo para modificar una Imagen de variable Macroeconomica
     */
	public void updateHome() {
		try {
			if(validar(selectedHome, false)) {
				getHomeService().updateHome(selectedHome);
				listaHomes = getHomeService().getHomes(false);
				selectedHome = new Home();
				util.mostrarMensaje("Registro actualizado con �xito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	/**
     *Metodo para eliminar una Imagen de variable Macroeconomica
     */
	public void deleteHome() {
		try {
			getHomeService().deleteHome(selectedHome);
			listaHomes = getHomeService().getHomes(false);
			util.mostrarMensaje("Registro eliminado con �xito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public IHomeService getHomeService() {
		return homeService;
	}

	public void setHomeService(IHomeService homeService) {
		this.homeService = homeService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public Home getSelectedHome() {
		return selectedHome;
	}

	public void setSelectedHome(Home selectedHome) {
		this.selectedHome = selectedHome;
	}

	public List<Home> getListaHomes() {
		return listaHomes;
	}

	public void setListaHomes(List<Home> listaHomes) {
		this.listaHomes = listaHomes;
	}

	public UploadedFile getImagen() {
		return imagen;
	}

	public void setImagen(UploadedFile imagen) {
		this.imagen = imagen;
	}

 }