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
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;

@ManagedBean(name = "centroCostoPorCuentaBB")
@ViewScoped
public class CentroCostoPorCuentaBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICuentaService cuentaService;
	private Util util;
	private CentroCostoPorCuenta centroCostoPorCuenta;
	private CentroCostoPorCuenta selectedCentroCostoPorCuenta;
	private List<CentroCostoPorCuenta> listaCentroCostoPorCuentas;
	
	private List<CentroCosto> lstCentroCosto;
	private List<Cuenta> lstCuenta;
	
	public CentroCostoPorCuentaBB() {
		util = Util.getInstance();
		centroCostoPorCuenta = new CentroCostoPorCuenta();
		selectedCentroCostoPorCuenta = new CentroCostoPorCuenta();
		listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
		
		lstCentroCosto = getCentroCostoService().getCentroCostos(true);
		lstCuenta = getCuentaService().getCuentas(true);
	}
	
	private boolean validar(CentroCostoPorCuenta cue) {
		boolean permiteGuardar = true;
		
		if(cue.getCentroCosto().getId() <= 0) {
			util.mostrarError("El campo Centro de costo es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getCuenta().getId() <= 0) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addCentroCostoPorCuenta() {
		try {
			if(validar(centroCostoPorCuenta)) {
				getCentroCostoService().addCentroCostoPorCuenta(centroCostoPorCuenta);
				listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
				centroCostoPorCuenta = new CentroCostoPorCuenta();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateCentroCostoPorCuenta() {
		try {
			if(validar(selectedCentroCostoPorCuenta)) {
				getCentroCostoService().updateCentroCostoPorCuenta(selectedCentroCostoPorCuenta);
				listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
				selectedCentroCostoPorCuenta = new CentroCostoPorCuenta();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCentroCostoPorCuenta() {
		try {
			getCentroCostoService().deleteCentroCostoPorCuenta(selectedCentroCostoPorCuenta);
			listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public CentroCostoPorCuenta getCentroCostoPorCuenta() {
		return centroCostoPorCuenta;
	}

	public void setCentroCostoPorCuenta(CentroCostoPorCuenta centroCostoPorCuenta) {
		this.centroCostoPorCuenta = centroCostoPorCuenta;
	}

	public CentroCostoPorCuenta getSelectedCentroCostoPorCuenta() {
		return selectedCentroCostoPorCuenta;
	}

	public void setSelectedCentroCostoPorCuenta(CentroCostoPorCuenta selectedCentroCostoPorCuenta) {
		this.selectedCentroCostoPorCuenta = selectedCentroCostoPorCuenta;
	}

	public List<CentroCostoPorCuenta> getListaCentroCostoPorCuentas() {
		return listaCentroCostoPorCuentas;
	}

	public void setListaCentroCostoPorCuentas(List<CentroCostoPorCuenta> listaCentroCostoPorCuentas) {
		this.listaCentroCostoPorCuentas = listaCentroCostoPorCuentas;
	}

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public List<CentroCosto> getLstCentroCosto() {
		return lstCentroCosto;
	}

	public void setLstCentroCosto(List<CentroCosto> lstCentroCosto) {
		this.lstCentroCosto = lstCentroCosto;
	}

	public List<Cuenta> getLstCuenta() {
		return lstCuenta;
	}

	public void setLstCuenta(List<Cuenta> lstCuenta) {
		this.lstCuenta = lstCuenta;
	}

 }