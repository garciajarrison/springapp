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
					if(jef.getNombre().equals(jefatura.getNombre())) {
						guardar = false;						
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
			if(validar(selectedJefatura)) {
				getJefaturaService().updateJefatura(selectedJefatura);
				listaJefaturas = getJefaturaService().getJefaturas(false);
				selectedJefatura = new Jefatura();
				util.mostrarMensaje("Registro actualizado con éxito.");
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

	public IJefaturaService getJefaturaService() {
		return jefaturaService;
	}

	public void setJefaturaService(IJefaturaService jefaturaService) {
		this.jefaturaService = jefaturaService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Jefatura getJefatura() {
		return jefatura;
	}

	public void setJefatura(Jefatura jefatura) {
		this.jefatura = jefatura;
	}

	public Jefatura getSelectedJefatura() {
		return selectedJefatura;
	}

	public void setSelectedJefatura(Jefatura selectedJefatura) {
		this.selectedJefatura = selectedJefatura;
	}

	public List<Jefatura> getListaJefaturas() {
		return listaJefaturas;
	}

	public void setListaJefaturas(List<Jefatura> listaJefaturas) {
		this.listaJefaturas = listaJefaturas;
	}

	

 }