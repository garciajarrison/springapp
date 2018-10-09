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
import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalle;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.IPresupuestoService;

@ManagedBean(name = "presupuestoBB")
@ViewScoped
public class PresupuestoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoService presupuestoService;
	private Util util;
	private Presupuesto presupuesto;
	private Presupuesto selectedPresupuesto;
	private Presupuesto detalle;
	private Observacion observacion;
	private List<Presupuesto> listaPresupuestos;
	private ListasGenericas listasGenericas;
	private boolean mostrarDetalle = false;
	private Usuario usuario;
	
	public PresupuestoBB() {
		util = Util.getInstance();
		presupuesto = new Presupuesto();
		observacion = new Observacion();
		selectedPresupuesto = new Presupuesto();
		listaPresupuestos = getPresupuestoService().getPresupuestos();
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
	}
	
	public void verDetalle(SelectEvent event) {
		detalle = (Presupuesto) event.getObject();
		mostrarDetalle = true;
	}
	
	public void agregarRegistro1() {
		try {
			presupuesto.getDetalle().add(new PresupuestoDetalle());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void agregarRegistro2() {
		try {
			detalle.getDetalle().add(new PresupuestoDetalle());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarRegistro1(int indice) {
		try {
			presupuesto.getDetalle().remove(indice);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarRegistro2(int indice) {
		try {
			detalle.getDetalle().remove(indice);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cambioTipo() {
		presupuesto.setDetalle(new ArrayList<>());
		if("Campañal".equals(presupuesto.getTipo())) {
			for(int i = 0; i <= 17; i++) {
				presupuesto.getDetalle().add(new PresupuestoDetalle());
			}
		}else if("Mensual".equals(presupuesto.getTipo())) {
			for(int i = 0; i <= 11; i++) {
				presupuesto.getDetalle().add(new PresupuestoDetalle());
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
	}
	
	public void enviarPresupuestoAprobadorInicial(){
		try {
			selectedPresupuesto.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
			getPresupuestoService().updatePresupuesto(selectedPresupuesto);
			
			//Guardamos la observacion
			observacion.setFecha(new Date());
			observacion.setPresupuesto(selectedPresupuesto);
			observacion.setEstado(selectedPresupuesto.getEstado());
			observacion.setUsuarioEnvia(usuario);
			//TODO pendiente consultar el usuario q recibe
			observacion.setUsuarioRecibe(usuario);
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
				for(PresupuestoDetalle pd : presupuesto.getDetalle()) {
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
			for(PresupuestoDetalle pd : detalle.getDetalle()) {
				pd.setPresupuesto(presupuesto);
				getPresupuestoService().addPresupuestoDetalle(pd);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}

	public void updatePresupuesto() {
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
	}
	
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

 }