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
import com.marketingpersonal.model.entity.Direccion;
import com.marketingpersonal.model.entity.Gerencia;
import com.marketingpersonal.model.entity.Jefatura;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IDireccionService;
import com.marketingpersonal.service.IGerenciaService;
import com.marketingpersonal.service.IJefaturaService;

@ManagedBean(name = "centroCostoBB")
@ViewScoped
public class CentroCostoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private IGerenciaService gerenciaService;
	@Autowired
	private IDireccionService direccionService;
	@Autowired
	private IJefaturaService jefaturaService;
	private Util util;
	private CentroCosto centroCosto;
	private CentroCosto selectedCentroCosto;
	private List<CentroCosto> listaCentroCostos;
	
	//Listas
	private List<Gerencia> lstGerencias;
	private List<Direccion> lstDireccions;
	private List<Jefatura> lstJefaturas;
	
	public CentroCostoBB() {
		util = Util.getInstance();
		centroCosto = new CentroCosto();
		selectedCentroCosto = new CentroCosto();
		listaCentroCostos = getCentroCostoService().getCentroCostos(false);
		
		lstGerencias = getGerenciaService().getGerencias(true);
		lstDireccions = getDireccionService().getDirecciones(true);
		lstJefaturas = getJefaturaService().getJefaturas(true);
	}
	
	private boolean validar(CentroCosto cc) {
		boolean permiteGuardar = true;
		
		if(cc.getNombre() == null || 
				"".equals(cc.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		if(cc.getGerencia().getId() <= 0) {
			util.mostrarError("El campo Gerencia es requerido.");
			permiteGuardar = false;
		}
		
		if(cc.getDireccion().getId() <= 0) {
			util.mostrarError("El campo Dirección es requerido.");
			permiteGuardar = false;
		}
		
		if(cc.getJefatura().getId() <= 0) {
			util.mostrarError("El campo Jefatura es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addCentroCosto() {
		try {
			if(validar(centroCosto)) {
				getCentroCostoService().addCentroCosto(centroCosto);
				listaCentroCostos = getCentroCostoService().getCentroCostos(false);
				centroCosto = new CentroCosto();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateCentroCosto() {
		try {
			if(validar(selectedCentroCosto)) {
				getCentroCostoService().updateCentroCosto(selectedCentroCosto);
				listaCentroCostos = getCentroCostoService().getCentroCostos(false);
				selectedCentroCosto = new CentroCosto();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteCentroCosto() {
		try {
			getCentroCostoService().deleteCentroCosto(selectedCentroCosto);
			listaCentroCostos = getCentroCostoService().getCentroCostos(false);
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

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public CentroCosto getSelectedCentroCosto() {
		return selectedCentroCosto;
	}

	public void setSelectedCentroCosto(CentroCosto selectedCentroCosto) {
		this.selectedCentroCosto = selectedCentroCosto;
	}

	public List<CentroCosto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CentroCosto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public IGerenciaService getGerenciaService() {
		return gerenciaService;
	}

	public void setGerenciaService(IGerenciaService gerenciaService) {
		this.gerenciaService = gerenciaService;
	}

	public IDireccionService getDireccionService() {
		return direccionService;
	}

	public void setDireccionService(IDireccionService direccionService) {
		this.direccionService = direccionService;
	}

	public IJefaturaService getJefaturaService() {
		return jefaturaService;
	}

	public void setJefaturaService(IJefaturaService jefaturaService) {
		this.jefaturaService = jefaturaService;
	}

	public List<Gerencia> getLstGerencias() {
		return lstGerencias;
	}

	public void setLstGerencias(List<Gerencia> lstGerencias) {
		this.lstGerencias = lstGerencias;
	}

	public List<Direccion> getLstDireccions() {
		return lstDireccions;
	}

	public void setLstDireccions(List<Direccion> lstDireccions) {
		this.lstDireccions = lstDireccions;
	}

	public List<Jefatura> getLstJefaturas() {
		return lstJefaturas;
	}

	public void setLstJefaturas(List<Jefatura> lstJefaturas) {
		this.lstJefaturas = lstJefaturas;
	}

 }