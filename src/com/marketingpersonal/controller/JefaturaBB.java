package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Jefatura;
import com.marketingpersonal.service.IJefaturaService;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@ManagedBean(name = "jefaturaBB")
@ViewScoped
public class JefaturaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IJefaturaService jefaturaService;
	private Util util;
	private Jefatura jefatura;
	private Jefatura selectedJefatura;
	private List<Jefatura> listaJefaturas;
	
	public JefaturaBB() {
		util = Util.getInstance();
		jefatura = new Jefatura();
		selectedJefatura = new Jefatura();
		listaJefaturas = getJefaturaService().getJefaturas(false);
	}
	
	private boolean validar(Jefatura jef) {
		boolean permiteGuardar = true;
		
		if(jef.getNombre() == null ||"".equals(jef.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addJefatura() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(jefatura)) {
				
				//Validar que no existe un registro duplicado
				for(Jefatura jef : listaJefaturas) {
					if(jef.getNombre().equals(jefatura.getNombre().trim())) {
						guardar = false;	
						
						break;
					}
				}
				
				if(guardar) {
					getJefaturaService().addJefatura(jefatura);
					listaJefaturas = getJefaturaService().getJefaturas(false);
					jefatura = new Jefatura();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Jefatura con el mismo nombre");
				}
			}			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 
	}

	public void updateJefatura() {
		try {
			boolean actualizar = true;
			
			if(validar(selectedJefatura)) {
				//Validar que no exista un registro duplicado
				for(Jefatura jef : listaJefaturas) {
					if(jef.getId() != selectedJefatura.getId())	 {
						if(jef.getNombre().equals(selectedJefatura.getNombre().trim()))	 {
							actualizar = false;	
							break;
						}					
					}					
				}
				
				if(actualizar) {
					getJefaturaService().updateJefatura(selectedJefatura);
					listaJefaturas = getJefaturaService().getJefaturas(false);
					selectedJefatura = new Jefatura();
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
	
	public void deleteJefatura() {
		try {
			getJefaturaService().deleteJefatura(selectedJefatura);
			listaJefaturas = getJefaturaService().getJefaturas(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

 }