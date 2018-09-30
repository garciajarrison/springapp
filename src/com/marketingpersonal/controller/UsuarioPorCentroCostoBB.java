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
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IUsuarioService;

@ManagedBean(name = "usuarioPorCentroCostoBB")
@ViewScoped
public class UsuarioPorCentroCostoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ICentroCostoService centroCostoService;
	private Util util;
	private UsuarioPorCentroCosto usuarioPorCentroCosto;
	private UsuarioPorCentroCosto selectedUsuarioPorCentroCosto;
	private List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos;
	
	private List<CentroCosto> lstCentroCostos;
	private List<Usuario> lstUsuarios;
	
	public UsuarioPorCentroCostoBB() {
		util = Util.getInstance();
		usuarioPorCentroCosto = new UsuarioPorCentroCosto();
		selectedUsuarioPorCentroCosto = new UsuarioPorCentroCosto();
		listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
		lstCentroCostos = getCentroCostoService().getCentroCostos(true);
		lstUsuarios = getUsuarioService().getUsuarios();
	}
	
	private boolean validar(UsuarioPorCentroCosto cue) {
		boolean permiteGuardar = true;
		
		if(cue.getCentroCosto().getId() <= 0) {
			util.mostrarError("El campo Centro de costo es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioResponsable().getId() <= 0) {
			util.mostrarError("El campo Responsable es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioAprobadorInicial().getId() <= 0) {
			util.mostrarError("El campo Aprobador inicial es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioAprobadorFinal().getId() <= 0) {
			util.mostrarError("El campo Aprobador final es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addUsuarioPorCentroCosto() {
		try {
			if(validar(usuarioPorCentroCosto)) {
				getUsuarioService().addUsuarioPorCentroCosto(usuarioPorCentroCosto);
				listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
				usuarioPorCentroCosto = new UsuarioPorCentroCosto();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updateUsuarioPorCentroCosto() {
		try {
			if(validar(selectedUsuarioPorCentroCosto)) {
				getUsuarioService().updateUsuarioPorCentroCosto(selectedUsuarioPorCentroCosto);
				listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
				selectedUsuarioPorCentroCosto = new UsuarioPorCentroCosto();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void deleteUsuarioPorCentroCosto() {
		try {
			getUsuarioService().deleteUsuarioPorCentroCosto(selectedUsuarioPorCentroCosto);
			listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public UsuarioPorCentroCosto getUsuarioPorCentroCosto() {
		return usuarioPorCentroCosto;
	}

	public void setUsuarioPorCentroCosto(UsuarioPorCentroCosto usuarioPorCentroCosto) {
		this.usuarioPorCentroCosto = usuarioPorCentroCosto;
	}

	public UsuarioPorCentroCosto getSelectedUsuarioPorCentroCosto() {
		return selectedUsuarioPorCentroCosto;
	}

	public void setSelectedUsuarioPorCentroCosto(UsuarioPorCentroCosto selectedUsuarioPorCentroCosto) {
		this.selectedUsuarioPorCentroCosto = selectedUsuarioPorCentroCosto;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public List<UsuarioPorCentroCosto> getListaUsuarioPorCentroCostos() {
		return listaUsuarioPorCentroCostos;
	}

	public void setListaUsuarioPorCentroCostos(List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos) {
		this.listaUsuarioPorCentroCostos = listaUsuarioPorCentroCostos;
	}

	public List<CentroCosto> getLstCentroCostos() {
		return lstCentroCostos;
	}

	public void setLstCentroCostos(List<CentroCosto> lstCentroCostos) {
		this.lstCentroCostos = lstCentroCostos;
	}

	public List<Usuario> getLstUsuarios() {
		return lstUsuarios;
	}

	public void setLstUsuarios(List<Usuario> lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
	}

 }