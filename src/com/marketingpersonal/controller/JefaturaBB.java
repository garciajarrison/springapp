package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Jefatura;
import com.marketingpersonal.service.IJefaturaService;

/**
 * Clase controladora para manejo de Jefaturas
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@ManagedBean(name = "jefaturaBB")
@ViewScoped
public class JefaturaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//Campos de la clase
	@Autowired
	private IJefaturaService jefaturaService;
	private Util util;
	private Jefatura jefatura;
	private Jefatura selectedJefatura;
	private List<Jefatura> listaJefaturas;
	
	/**
    * Constructor para controlador de Jefaturas
    */
	public JefaturaBB() {
		util = Util.getInstance();
		jefatura = new Jefatura();
		selectedJefatura = new Jefatura();
		listaJefaturas = getJefaturaService().getJefaturas(false);
	}
	
	/**
     * Método que valida la obligatoriedad de los campos
     * @param jef: Variable de tipo Jefatura
     * @return permiteGuardar: variable booleana que indica si es posible guardar o no la nueva Jefatura
     */
	private boolean validar(Jefatura jef) {
		boolean permiteGuardar = true;
		
		if(jef.getNombre() == null ||"".equals(jef.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	/**
     *Metodo para crear una nueva Jefatura
     */
	public void addJefatura() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(jefatura)) {
				
				//Validar que no existe un registro duplicado
				for(Jefatura jef : listaJefaturas) {
					if(jef.getNombre().equals(WordUtils.capitalizeFully(jefatura.getNombre().trim()))) {
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

	/**
     *Metodo para modificar una Jefatura
     */
	public void updateJefatura() {
		try {
			boolean actualizar = true;
			
			if(validar(selectedJefatura)) {
				//Validar que no exista un registro duplicado
				for(Jefatura jef : listaJefaturas) {
					if(jef.getId() != selectedJefatura.getId())	 {
						if(jef.getNombre().equals(WordUtils.capitalizeFully(selectedJefatura.getNombre().trim())))	 {
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
	
	/**
     *Metodo para eliminar una Jefatura
     */
	public void deleteJefatura() {
		try {
			getJefaturaService().deleteJefatura(selectedJefatura);
			listaJefaturas = getJefaturaService().getJefaturas(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			if((e.toString()).contains("ConstraintViolationException")) {
				util.mostrarError("Error eliminando el registro. No puede eliminar una jefatura que tenga centros de costo asociados");
			}else {
				util.mostrarError("Error eliminando el registro.");
			}
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