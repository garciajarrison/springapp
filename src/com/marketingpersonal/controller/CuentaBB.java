package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.service.ICuentaService;

@ManagedBean(name = "cuentaBB")
@ViewScoped
public class CuentaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICuentaService cuentaService;
	private Util util;
	private Cuenta cuenta;
	private Cuenta selectedCuenta;
	private List<Cuenta> listaCuentas;
	
	public CuentaBB() {
		util = Util.getInstance();
		cuenta = new Cuenta();
		selectedCuenta = new Cuenta();
		listaCuentas = getCuentaService().getCuentas();
	}
	
	private boolean validar(Cuenta cue) {
		boolean permiteGuardar = true;
		
		if(cue.getNombre() == null || 
				"".equals(cue.getNombre())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getGrupo() == null || 
				"".equals(cue.getGrupo())) {
			util.mostrarError("El campo Grupo es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addCuenta() {
		try {
			if(validar(cuenta)) {
				getCuentaService().addCuenta(cuenta);
				listaCuentas = getCuentaService().getCuentas();
				cuenta = new Cuenta();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateCuenta() {
		try {
			if(validar(selectedCuenta)) {
				getCuentaService().updateCuenta(selectedCuenta);
				listaCuentas = getCuentaService().getCuentas();
				selectedCuenta = new Cuenta();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCuenta() {
		try {
			getCuentaService().deleteCuenta(selectedCuenta);
			listaCuentas = getCuentaService().getCuentas();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Cuenta getSelectedCuenta() {
		return selectedCuenta;
	}

	public void setSelectedCuenta(Cuenta selectedCuenta) {
		this.selectedCuenta = selectedCuenta;
	}

	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}
	


 }