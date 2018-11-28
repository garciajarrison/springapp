package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Parametro;
import com.marketingpersonal.service.IParametroService;

/**
 * Clase controladora para manejo de Parametros
 * @author Jarrison Garcia Y Juan Camilo Monsalve
 * @date 30/10/2018
 */
@ManagedBean(name = "parametroBB")
@ViewScoped
public class ParametroBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//Campos de la clase
	@Autowired
	private IParametroService parametroService;
	private Util util;
	private Parametro selectedParametro;
	private List<Parametro> listaParametros;
	
	/**
	* Constructor para controlador de Parametros
	*/
	public ParametroBB() {
		util = Util.getInstance();
		selectedParametro = new Parametro();
		listaParametros = getParametroService().getParametros();
	}
	
	/**
     * Método que valida la obligatoriedad de los campos
     * @param jef: Variable de tipo Parametro
     * @return permiteGuardar: variable booleana que indica si es posible guardar o no el nuevo Parametro
     */
	private boolean validar(Parametro jef) {
		boolean permiteGuardar = true;
		
		if(jef.getValor() == null ||"".equals(jef.getValor().trim())) {
			util.mostrarError("El campo Valor es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	/**
     *Metodo para modificar un Parametro
     */
	public void updateParametro() {
		try {
			boolean actualizar = true;
			
			if(validar(selectedParametro)) {
				//Validar que no exista un registro duplicado
				for(Parametro jef : listaParametros) {
					if(jef.getId() != selectedParametro.getId())	 {
						if(jef.getValor().equals(selectedParametro.getValor().trim()))	 {
							actualizar = false;	
							break;
						}					
					}					
				}
				
				if(actualizar) {
					getParametroService().updateParametro(selectedParametro);
					listaParametros = getParametroService().getParametros();
					selectedParametro = new Parametro();
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
     *Metodo para eliminar un Parametro
     */
	public void deleteParametro() {
		try {
			getParametroService().deleteParametro(selectedParametro);
			listaParametros = getParametroService().getParametros();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public IParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(IParametroService parametroService) {
		this.parametroService = parametroService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Parametro getSelectedParametro() {
		return selectedParametro;
	}

	public void setSelectedParametro(Parametro selectedParametro) {
		this.selectedParametro = selectedParametro;
	}

	public List<Parametro> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<Parametro> listaParametros) {
		this.listaParametros = listaParametros;
	}

 }