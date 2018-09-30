package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Gerencia;
import com.marketingpersonal.service.IGerenciaService;

@ManagedBean(name = "gerenciaBB")
@ViewScoped
public class GerenciaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IGerenciaService gerenciaService;
	private Util util;
	private Gerencia gerencia;
	private Gerencia selectedGerencia;
	private List<Gerencia> listaGerencias;
	
	public GerenciaBB() {
		util = Util.getInstance();
		gerencia = new Gerencia();
		selectedGerencia = new Gerencia();
		listaGerencias = getGerenciaService().getGerencias(false);
	}
	
	public void addGerencia() {
		try {
			if(gerencia == null && gerencia.getNombre() == null || 
					"".equals(gerencia.getNombre())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getGerenciaService().addGerencia(gerencia);
				listaGerencias = getGerenciaService().getGerencias(false);
				gerencia = new Gerencia();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateGerencia() {
		try {
			if(selectedGerencia == null && selectedGerencia.getNombre() == null || 
					"".equals(selectedGerencia.getNombre())) {
				util.mostrarError("El campo Nombre es requerido.");
			}else {
				getGerenciaService().updateGerencia(selectedGerencia);
				listaGerencias = getGerenciaService().getGerencias(false);
				selectedGerencia = new Gerencia();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteGerencia() {
		try {
			getGerenciaService().deleteGerencia(selectedGerencia);
			listaGerencias = getGerenciaService().getGerencias(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}
	
	public IGerenciaService getGerenciaService() {
		return gerenciaService;
	}

	public void setGerenciaService(IGerenciaService gerenciaService) {
		this.gerenciaService = gerenciaService;
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public Gerencia getSelectedGerencia() {
		return selectedGerencia;
	}

	public void setSelectedGerencia(Gerencia selectedGerencia) {
		this.selectedGerencia = selectedGerencia;
	}

	public List<Gerencia> getListaGerencias() {
		return listaGerencias;
	}

	public void setListaGerencias(List<Gerencia> listaGerencias) {
		this.listaGerencias = listaGerencias;
	}

 }