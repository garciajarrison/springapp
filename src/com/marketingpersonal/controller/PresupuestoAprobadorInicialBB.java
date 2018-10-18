package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.EnumEstadosPresupuesto;
import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;
import com.marketingpersonal.service.IPresupuestoService;
import com.marketingpersonal.service.IUsuarioService;

@ManagedBean(name = "preAprIniBB")
@ViewScoped
public class PresupuestoAprobadorInicialBB extends SpringBeanAutowiringSupport implements Serializable {
	
	/*private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoService presupuestoService;
	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICuentaService cuentaService;
	private Util util;
	private Presupuesto presupuesto;
	private Presupuesto selectedPresupuesto;
	private Presupuesto detalle;
	private Observacion observacion;
	private List<Presupuesto> listaPresupuestos;
	private ListasGenericas listasGenericas;
	private boolean mostrarDetalle = false;
	private Usuario usuario;
	private List<CentroCosto> listaCentroCostos;
	private List<Cuenta> listaCuentas;
	
	
	@Autowired
	private IUsuarioService usuarioService;
	
	private Usuario responsable;
	private UsuarioPorCentroCosto usuarioPorCentroCosto;
	private List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos;
	
	public PresupuestoAprobadorInicialBB() {
		util = Util.getInstance();
		presupuesto = new Presupuesto();
		observacion = new Observacion();
		selectedPresupuesto = new Presupuesto();
		listaPresupuestos = getPresupuestoService().getPresupuestos();
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		listaCentroCostos = this.getCentroCostoService().getCentroCostoPorUsuario(usuario.getId());
		
		
		usuarioPorCentroCosto = new UsuarioPorCentroCosto();
		listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
		
	}
	
	public void verDetalle(SelectEvent event) {
		detalle = (Presupuesto) event.getObject();
		mostrarDetalle = true;
	}
	
	
	/*public void cambioTipo() {
		presupuesto.setDetalle(new ArrayList<>());
		if("Campañal".equals(presupuesto.getTipo())) {
			for(int i = 0; i <= 17; i++) {
				presupuesto.getDetalle().add(new PresupuestoDetalleMes());
			}
		}else if("Mensual".equals(presupuesto.getTipo())) {
			for(int i = 0; i <= 11; i++) {
				presupuesto.getDetalle().add(new PresupuestoDetalleMes());
			}
		}
	}
	
	private boolean validar(Presupuesto pr) {
		boolean permiteGuardar = true;
		
		if(pr.getNombre() == null || 
				"".equals(pr.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		if(pr.getCentroCosto() == null || 
				pr.getCentroCosto().getId() < 1) {
			util.mostrarError("El campo Centro costo es requerido.");
			permiteGuardar = false;
		}
		
		if(pr.getCuenta() == null || 
				pr.getCuenta().getId() < 1) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		if(pr.getAnio() == null || pr.getAnio() < 1) {
			util.mostrarError("El campo Año es requerido.");
			permiteGuardar = false;
		}
		
		if(pr.getTipo() == null || 
				"".equals(pr.getTipo().trim())) {
			util.mostrarError("El campo Tipo es requerido.");
			permiteGuardar = false;
			
		}else if(pr.getMesCampania() == null || pr.getMesCampania() < 1) {
			String tipo = ("Mensual".equals(pr.getTipo()) ? "Mes inicial" : "Campaña inicial");
			util.mostrarError("El campo "+ tipo + " es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}*/
	
	/*public void enviarPresupuestoAprobadorInicial(){
		try {
			selectedPresupuesto.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
			getPresupuestoService().actualizarEstadoPresupuesto(selectedPresupuesto);
			
			//Guardamos la observacion
			observacion.setFecha(new Date());
			observacion.setPresupuesto(selectedPresupuesto);
			observacion.setEstado(selectedPresupuesto.getEstado());
			observacion.setUsuarioEnvia(usuario);
			//TODO pendiente consultar el usuario q recibe
			observacion.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorInicial(selectedPresupuesto.getCentroCosto().getId()));
			getPresupuestoService().addObservacion(observacion);
			
			//TODO pendiente enviar correo al aprobador inicial
			
			observacion = new Observacion();
			util.mostrarMensaje("El presupuesto fue enviado al aprobador incial con éxito.");
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error enviando el registro.");
		} 	
	}
	
	public void addPresupuesto() {
		try {
			if(validar(presupuesto)) {
				getPresupuestoService().addPresupuesto(presupuesto);
				for(PresupuestoDetalleMes pd : presupuesto.getDetalle()) {
					pd.setPresupuesto(presupuesto);
					getPresupuestoService().addPresupuestoDetalle(pd);
				}
				listaPresupuestos = getPresupuestoService().getPresupuestos();
				presupuesto = new Presupuesto();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}
	
	public void actualizarValores() {
		try {
			for(PresupuestoDetalleMes pd : detalle.getDetalle()) {
				pd.setPresupuesto(presupuesto);
				getPresupuestoService().addPresupuestoDetalle(pd);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}*/

/*	public void updatePresupuesto() {
		try {
			if(validar(selectedPresupuesto)) {
				getPresupuestoService().updatePresupuesto(selectedPresupuesto);
				listaPresupuestos = getPresupuestoService().getPresupuestos();
				selectedPresupuesto = new Presupuesto();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}*/
	
	public void deletePresupuesto() {
		try {
			getPresupuestoService().deletePresupuesto(selectedPresupuesto);
			listaPresupuestos = getPresupuestoService().getPresupuestos();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}
	
	/*public void cargarListaCuentas(String actualiza) {
		try {
			Presupuesto prepTmp = null;
			if("SI".equals(actualiza)) {
				prepTmp = selectedPresupuesto;
			}else {
				prepTmp = presupuesto;
			}
			
			listaCuentas = new ArrayList<>();
			
			if(prepTmp.getCentroCosto() != null &&
					prepTmp.getCentroCosto().getId() > 0) {
				listaCuentas = this.getCuentaService().getCuentaPorCentroCosto(prepTmp.getCentroCosto().getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}*/

	public IPresupuestoService getPresupuestoService() {
		return presupuestoService;
	}

	public void setPresupuestoService(IPresupuestoService presupuestoService) {
		this.presupuestoService = presupuestoService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Presupuesto getSelectedPresupuesto() {
		return selectedPresupuesto;
	}

	public void setSelectedPresupuesto(Presupuesto selectedPresupuesto) {
		this.selectedPresupuesto = selectedPresupuesto;
	}

	public List<Presupuesto> getListaPresupuestos() {
		return listaPresupuestos;
	}

	public void setListaPresupuestos(List<Presupuesto> listaPresupuestos) {
		this.listaPresupuestos = listaPresupuestos;
	}

	public ListasGenericas getListasGenericas() {
		return listasGenericas;
	}

	public void setListasGenericas(ListasGenericas listasGenericas) {
		this.listasGenericas = listasGenericas;
	}

	public Presupuesto getDetalle() {
		return detalle;
	}

	public void setDetalle(Presupuesto detalle) {
		this.detalle = detalle;
	}

	public boolean isMostrarDetalle() {
		return mostrarDetalle;
	}

	public void setMostrarDetalle(boolean mostrarDetalle) {
		this.mostrarDetalle = mostrarDetalle;
	}

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public List<CentroCosto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CentroCosto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}
	
	
	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
*/
 }