package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Direccion;
import com.marketingpersonal.service.IDireccionService;

@ManagedBean(name = "direccionBB")
@ViewScoped
public class DireccionBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IDireccionService direccionService;
	private Util util;
	private Direccion direccion;
	private Direccion selectedDireccion;
	private List<Direccion> listaDireccions;
	
	public DireccionBB() {
		util = Util.getInstance();
		direccion = new Direccion();
		selectedDireccion = new Direccion();
		listaDireccions = getDireccionService().getDireccions(false);
	}
	
	public void addDireccion() {
		try {
			if(direccion == null && direccion.getNombre() == null || 
					"".equals(direccion.getNombre().trim())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getDireccionService().addDireccion(direccion);
				listaDireccions = getDireccionService().getDireccions(false);
				direccion = new Direccion();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateDireccion() {
		try {
			if(selectedDireccion == null && selectedDireccion.getNombre() == null || 
					"".equals(selectedDireccion.getNombre().trim())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getDireccionService().updateDireccion(selectedDireccion);
				listaDireccions = getDireccionService().getDireccions(false);
				selectedDireccion = new Direccion();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteDireccion() {
		try {
			getDireccionService().deleteDireccion(selectedDireccion);
			listaDireccions = getDireccionService().getDireccions(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public IDireccionService getDireccionService() {
		return direccionService;
	}

	public void setDireccionService(IDireccionService direccionService) {
		this.direccionService = direccionService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Direccion getSelectedDireccion() {
		return selectedDireccion;
	}

	public void setSelectedDireccion(Direccion selectedDireccion) {
		this.selectedDireccion = selectedDireccion;
	}

	public List<Direccion> getListaDireccions() {
		return listaDireccions;
	}

	public void setListaDireccions(List<Direccion> listaDireccions) {
		this.listaDireccions = listaDireccions;
	}

	

 }