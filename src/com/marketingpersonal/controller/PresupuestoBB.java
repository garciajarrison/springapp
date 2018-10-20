package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.ICalculadoraService;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;
import com.marketingpersonal.service.IPresupuestoService;

@ManagedBean(name = "presupuestoBB")
@ViewScoped
public class PresupuestoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoService presupuestoService;
	@Autowired
	private ICuentaService cuentaService;
	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICalculadoraService calculadoraService;
	
	private Util util;
	private Presupuesto presupuesto;
	private Presupuesto detalle;
	private Presupuesto selectedPresupuesto;
	private PresupuestoDetalleMes presupuestoDetalleMes;
	private PresupuestoDetalleMes selectedPresupuestoDetalleMes;
	private PresupuestoDetalleCampania presupuestoDetalleCampania;
	private PresupuestoDetalleCampania selectedPresupuestoDetalleCampania;
	//private Observacion observacion;
	private List<Presupuesto> listaPresupuestos;
	private ListasGenericas listasGenericas;
	//private boolean mostrarDetalle = false;
	private Usuario usuario;
	private List<Cuenta> listaCuentas;
	private List<CentroCosto> listaCentroCostos;
	private boolean mostrarDetalle;
	private int camapanaMaxima;
	
	
	public PresupuestoBB() {
		util = Util.getInstance();
		presupuesto = new Presupuesto();
		presupuestoDetalleMes = new PresupuestoDetalleMes();
		presupuestoDetalleCampania = new PresupuestoDetalleCampania();
		//observacion = new Observacion();
		selectedPresupuesto = new Presupuesto();
		listaPresupuestos = getPresupuestoService().getPresupuestos();
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		listaCuentas = this.getCuentaService().getCuentasPorUsuario(usuario.getId());
		mostrarDetalle = false;
		camapanaMaxima = getCalculadoraService().getCampanaMaxima();
	}
	
	public void verDetalle(SelectEvent event) {
		detalle = (Presupuesto) event.getObject();
		mostrarDetalle = true;
	}
	
	private boolean validar(Presupuesto pr) {
		boolean permiteGuardar = true;
		
		if(pr.getNombre() == null || 
				"".equals(pr.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		if(pr.getClasificacion() == null || 
				"".equals(pr.getClasificacion().trim())) {
			util.mostrarError("El campo Clasificación es requerido.");
			permiteGuardar = false;
		}
				
		if(pr.getTipo() == null || 
				"".equals(pr.getTipo().trim())) {
			util.mostrarError("El campo Tipo es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}

	public void addPresupuesto() {
		try {
			if(validar(presupuesto)) {
				presupuesto.setUsuario(usuario);
				getPresupuestoService().addPresupuesto(presupuesto);
				listaPresupuestos = getPresupuestoService().getPresupuestos();
				presupuesto = new Presupuesto();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}
	
	private boolean validarDetalle(Presupuesto detalle, PresupuestoDetalleMes mes, PresupuestoDetalleCampania campania) {
		
		boolean permiteGuardar = true;
		Cuenta cuentaV = null;
		CentroCosto centroCostoV = null;
		
		if("Mensual".equals(detalle.getTipo())) {
			cuentaV = mes.getCuenta();
			centroCostoV = mes.getCentroCosto();
		}else {
			cuentaV = campania.getCuenta();
			centroCostoV = campania.getCentroCosto();
		}
		
		if(cuentaV == null || cuentaV.getId() <= 0) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		if(centroCostoV == null || centroCostoV.getId() <= 0) {
			util.mostrarError("El campo Centro costo es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addPresupuestoMes(){
		try {
			if(validarDetalle(detalle, presupuestoDetalleMes, null)){
				
				presupuestoDetalleMes.setEstado(EnumEstadosPresupuesto.PENDIENTE.getNombre());
				presupuestoDetalleMes.setPresupuesto(detalle);
				presupuestoDetalleMes.setUsuarioAprobadorInicial(
						this.getCentroCostoService().getUsuarioAprobadorInicial(presupuestoDetalleMes.getCentroCosto().getId()));
				presupuestoDetalleMes.setUsuarioAprobadorFinal(
						this.getCentroCostoService().getUsuarioAprobadorFinal(presupuestoDetalleMes.getCentroCosto().getId()));
				
				this.getPresupuestoService().addPresupuestoDetalleMes(presupuestoDetalleMes);
				this.detalle.setDetalleMes(this.getPresupuestoService().getPresupuestoDetallesMes(detalle.getId()));
				presupuestoDetalleMes = new PresupuestoDetalleMes();
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}
	
	public void addPresupuestoCampania(){
		try {
			if(validarDetalle(detalle, null,  presupuestoDetalleCampania)){
				
				presupuestoDetalleCampania.setEstado(EnumEstadosPresupuesto.PENDIENTE.getNombre());
				presupuestoDetalleCampania.setPresupuesto(detalle);
				presupuestoDetalleCampania.setUsuarioAprobadorInicial(
								this.getCentroCostoService().getUsuarioAprobadorInicial(presupuestoDetalleCampania.getCentroCosto().getId()));
				presupuestoDetalleCampania.setUsuarioAprobadorFinal(
						this.getCentroCostoService().getUsuarioAprobadorFinal(presupuestoDetalleCampania.getCentroCosto().getId()));
				
				this.getPresupuestoService().addPresupuestoDetalleCampania(presupuestoDetalleCampania);
				this.detalle.setDetalleCampania(this.getPresupuestoService().getPresupuestoDetallesCampania(detalle.getId()));
				presupuestoDetalleCampania = new PresupuestoDetalleCampania();
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
	
	public void cargarListaCentroCostosPresupuestoMes(String actualiza) {
		try {
			PresupuestoDetalleMes prepDetMesTmp = null;
			if("SI".equals(actualiza)) {
				prepDetMesTmp = selectedPresupuestoDetalleMes;
			}else {
				prepDetMesTmp = presupuestoDetalleMes;
			}
			
			listaCentroCostos = new ArrayList<>();
			
			if(prepDetMesTmp.getCuenta() != null &&
					prepDetMesTmp.getCuenta().getId() > 0) {
				listaCentroCostos = this.getCentroCostoService().getCentroCostosPorCuenta(prepDetMesTmp.getCuenta().getId(), usuario.getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}
	
	
	public void cargarListaCentroCostosPresupuestoCampania(String actualiza) {
		try {
			PresupuestoDetalleCampania prepDetCampaniaTmp = null;
			if("SI".equals(actualiza)) {
				prepDetCampaniaTmp = selectedPresupuestoDetalleCampania;
			}else {
				prepDetCampaniaTmp = presupuestoDetalleCampania;
			}
			
			listaCentroCostos = new ArrayList<>();
			
			if(prepDetCampaniaTmp.getCuenta() != null &&
					prepDetCampaniaTmp.getCuenta().getId() > 0) {
				listaCentroCostos = this.getCentroCostoService().getCentroCostosPorCuenta(prepDetCampaniaTmp.getCuenta().getId(), usuario.getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}

	public IPresupuestoService getPresupuestoService() {
		return presupuestoService;
	}

	public void setPresupuestoService(IPresupuestoService presupuestoService) {
		this.presupuestoService = presupuestoService;
	}

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public ICalculadoraService getCalculadoraService() {
		return calculadoraService;
	}

	public void setCalculadoraService(ICalculadoraService calculadoraService) {
		this.calculadoraService = calculadoraService;
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

	public Presupuesto getDetalle() {
		return detalle;
	}

	public void setDetalle(Presupuesto detalle) {
		this.detalle = detalle;
	}

	public Presupuesto getSelectedPresupuesto() {
		return selectedPresupuesto;
	}

	public void setSelectedPresupuesto(Presupuesto selectedPresupuesto) {
		this.selectedPresupuesto = selectedPresupuesto;
	}

	public PresupuestoDetalleMes getPresupuestoDetalleMes() {
		return presupuestoDetalleMes;
	}

	public void setPresupuestoDetalleMes(PresupuestoDetalleMes presupuestoDetalleMes) {
		this.presupuestoDetalleMes = presupuestoDetalleMes;
	}

	public PresupuestoDetalleMes getSelectedPresupuestoDetalleMes() {
		return selectedPresupuestoDetalleMes;
	}

	public void setSelectedPresupuestoDetalleMes(PresupuestoDetalleMes selectedPresupuestoDetalleMes) {
		this.selectedPresupuestoDetalleMes = selectedPresupuestoDetalleMes;
	}

	public PresupuestoDetalleCampania getPresupuestoDetalleCampania() {
		return presupuestoDetalleCampania;
	}

	public void setPresupuestoDetalleCampania(PresupuestoDetalleCampania presupuestoDetalleCampania) {
		this.presupuestoDetalleCampania = presupuestoDetalleCampania;
	}

	public PresupuestoDetalleCampania getSelectedPresupuestoDetalleCampania() {
		return selectedPresupuestoDetalleCampania;
	}

	public void setSelectedPresupuestoDetalleCampania(PresupuestoDetalleCampania selectedPresupuestoDetalleCampania) {
		this.selectedPresupuestoDetalleCampania = selectedPresupuestoDetalleCampania;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}

	public List<CentroCosto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CentroCosto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public boolean isMostrarDetalle() {
		return mostrarDetalle;
	}

	public void setMostrarDetalle(boolean mostrarDetalle) {
		this.mostrarDetalle = mostrarDetalle;
	}

	public int getCamapanaMaxima() {
		return camapanaMaxima;
	}

	public void setCamapanaMaxima(int camapanaMaxima) {
		this.camapanaMaxima = camapanaMaxima;
	}
		
	/*	
	
	public void enviarPresupuestoAprobadorInicial(){
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
	}

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}

*/
 }