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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
	
	private boolean validar(Gerencia ger) {
		boolean permiteGuardar = true;
		
		if(ger.getNombre() == null ||"".equals(ger.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addGerencia() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(gerencia)) {
				
				//Validar que no exista un registro duplicado
				for(Gerencia ger : listaGerencias) {
					
					if(ger.getNombre().equals(gerencia.getNombre().trim())) {
						guardar = false;	
						break;
					}
				}
				
				if(guardar) {
					getGerenciaService().addGerencia(gerencia);
					listaGerencias = getGerenciaService().getGerencias(false);
					gerencia = new Gerencia();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Gerencia con el mismo nombre");
				}
			}			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateGerencia() {
		try {
			boolean actualizar = true;
			
			if(validar(selectedGerencia)) {
				
				//Validar que no exista un registro duplicado
				for(Gerencia ger : listaGerencias) {
					if(ger.getId() != selectedGerencia.getId())	 {
						if(ger.getNombre().equals(selectedGerencia.getNombre().trim()))	 {
							actualizar = false;	
							break;
						}					
					}					
				}
				
				if(actualizar) {
					getGerenciaService().updateGerencia(selectedGerencia);
					listaGerencias = getGerenciaService().getGerencias(false);
					selectedGerencia = new Gerencia();
					util.mostrarMensaje("Registro actualizado con éxito.");
				}else {
					util.mostrarError("Ya existe una Gerencia con el mismo nombre ingresado");
				}
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
	
 }