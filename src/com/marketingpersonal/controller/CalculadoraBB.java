package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Calculadora;
import com.marketingpersonal.service.ICalculadoraService;

@ManagedBean(name = "calculadoraBB")
@ViewScoped
public class CalculadoraBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICalculadoraService calculadoraService;
	private Util util;
	private Calculadora calculadora;
	private Calculadora selectedCalculadora;
	private List<Calculadora> listaCalculadoras;
	private List<Calculadora[]> listaCalculadoraCM;
	private List<Calculadora[]> listaCalculadoraMC;
	
	public CalculadoraBB() {
		util = Util.getInstance();
		calculadora = new Calculadora();
		selectedCalculadora = new Calculadora();
		listaCalculadoras = getCalculadoraService().getCalculadoras();
		
		//Carga de calculadora campaña / mes
		listaCalculadoraCM = new ArrayList<>();
		Calculadora[] objTmp = new Calculadora[13];
		int i = 1;
		for(Calculadora cal : listaCalculadoras) {
			if(i == 1) {
				objTmp = new Calculadora[13];
			}
			
			objTmp[i-1] = cal;
			if(i == 12) {
				//La posicion 13 es para el total
				objTmp[i] = new Calculadora();
				i = 0;
				listaCalculadoraCM.add(objTmp);
			}
			i++;
		}
		
		//Carga de calculadora campaña / mes
		/*listaCalculadoraMC = new ArrayList<>();
		objTmp = new Calculadora[13];
		i = 1;
		for(Calculadora cal : listaCalculadoras) {
			if(i == 1) {
				objTmp = new Calculadora[13];
			}
			
			objTmp[i-1] = cal;
			if(i == 12) {
				//La posicion 13 es para el total
				objTmp[i] = new Calculadora();
				i = 0;
				listaCalculadoraCM.add(objTmp);
			}
			i++;
		}*/
		
	}
	
	private boolean validar(Calculadora cue) {
		boolean permiteGuardar = true;		

		/*if(cue.getCuenta() == null || "".equals(cue.getCuenta().trim())) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getNombre() == null || "".equals(cue.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}*/
		
		return permiteGuardar;
	}
	
	public void addCalculadora() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(calculadora)) {
				
				//Validar que no exista un registro duplicado
				for(Calculadora cue : listaCalculadoras) {
					/*if(cue.getCuenta().equals(calculadora.getCuenta())) {
						guardar = false;						
					}*/
				}
				
				if(guardar) {
					getCalculadoraService().addCalculadora(calculadora);
					listaCalculadoras = getCalculadoraService().getCalculadoras();
					calculadora = new Calculadora();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Calculadora con el mismo código");
				}
			}			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 		
	}

	public void updateCalculadora() {
		try {
			if(validar(selectedCalculadora)) {
				getCalculadoraService().updateCalculadora(selectedCalculadora);
				listaCalculadoras = getCalculadoraService().getCalculadoras();
				selectedCalculadora = new Calculadora();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCuenta() {
		try {
			getCalculadoraService().deleteCalculadora(selectedCalculadora);
			listaCalculadoras = getCalculadoraService().getCalculadoras();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Calculadora getCalculadora() {
		return calculadora;
	}

	public void setCalculadora(Calculadora calculadora) {
		this.calculadora = calculadora;
	}

	public Calculadora getSelectedCalculadora() {
		return selectedCalculadora;
	}

	public void setSelectedCalculadora(Calculadora selectedCalculadora) {
		this.selectedCalculadora = selectedCalculadora;
	}

	public List<Calculadora> getListaCalculadoras() {
		return listaCalculadoras;
	}

	public void setListaCalculadoras(List<Calculadora> listaCalculadoras) {
		this.listaCalculadoras = listaCalculadoras;
	}

	public ICalculadoraService getCalculadoraService() {
		return calculadoraService;
	}

	public void setCalculadoraService(ICalculadoraService calculadoraService) {
		this.calculadoraService = calculadoraService;
	}

	public List<Calculadora[]> getListaCalculadoraCM() {
		return listaCalculadoraCM;
	}

	public void setListaCalculadoraCM(List<Calculadora[]> listaCalculadoraCM) {
		this.listaCalculadoraCM = listaCalculadoraCM;
	}

	public List<Calculadora[]> getListaCalculadoraMC() {
		return listaCalculadoraMC;
	}

	public void setListaCalculadoraMC(List<Calculadora[]> listaCalculadoraMC) {
		this.listaCalculadoraMC = listaCalculadoraMC;
	}


 }