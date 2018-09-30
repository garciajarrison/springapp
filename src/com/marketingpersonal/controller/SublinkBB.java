package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Sublink;
import com.marketingpersonal.service.ISublinkService;

@ManagedBean(name = "sublinkBB")
@ViewScoped
public class SublinkBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ISublinkService sublinkService;
	private Util util;
	private Sublink sublink;
	private Sublink selectedSublink;
	private List<Sublink> listaSublinks;
	
	public SublinkBB() {
		util = Util.getInstance();
		sublink = new Sublink();
		selectedSublink = new Sublink();
		listaSublinks = getSublinkService().getSublinks(false);
	}
	
	public void addSublink() {
		try {
			if(sublink == null && sublink.getNombre() == null || 
					"".equals(sublink.getNombre())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getSublinkService().addSublink(sublink);
				listaSublinks = getSublinkService().getSublinks(false);
				sublink = new Sublink();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateSublink() {
		try {
			if(selectedSublink == null && selectedSublink.getNombre() == null || 
					"".equals(selectedSublink.getNombre())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getSublinkService().updateSublink(selectedSublink);
				listaSublinks = getSublinkService().getSublinks(false);
				selectedSublink = new Sublink();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteSublink() {
		try {
			getSublinkService().deleteSublink(selectedSublink);
			listaSublinks = getSublinkService().getSublinks(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public ISublinkService getSublinkService() {
		return sublinkService;
	}

	public void setSublinkService(ISublinkService sublinkService) {
		this.sublinkService = sublinkService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Sublink getSublink() {
		return sublink;
	}

	public void setSublink(Sublink sublink) {
		this.sublink = sublink;
	}

	public Sublink getSelectedSublink() {
		return selectedSublink;
	}

	public void setSelectedSublink(Sublink selectedSublink) {
		this.selectedSublink = selectedSublink;
	}

	public List<Sublink> getListaSublinks() {
		return listaSublinks;
	}

	public void setListaSublinks(List<Sublink> listaSublinks) {
		this.listaSublinks = listaSublinks;
	}

	

 }