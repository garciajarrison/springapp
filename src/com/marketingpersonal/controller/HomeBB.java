package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Home;
import com.marketingpersonal.service.IHomeService;

@ManagedBean(name = "homeBB")
@ViewScoped
public class HomeBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IHomeService homeService;
	private Util util;
	private Home home;
	private Home selectedHome;
	private List<Home> listaHomes;
	
	public HomeBB() {
		util = Util.getInstance();
		home = new Home();
		selectedHome = new Home();
		listaHomes = getHomeService().getHomes();
	}
	
	public void addHome() {
		try {
			if(home == null && home.getNombre() == null || 
					"".equals(home.getNombre())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getHomeService().addHome(home);
				listaHomes = getHomeService().getHomes();
				home = new Home();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateHome() {
		try {
			if(selectedHome == null && selectedHome.getNombre() == null || 
					"".equals(selectedHome.getNombre())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getHomeService().updateHome(selectedHome);
				listaHomes = getHomeService().getHomes();
				selectedHome = new Home();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteHome() {
		try {
			getHomeService().deleteHome(selectedHome);
			listaHomes = getHomeService().getHomes();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
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

	

 }