package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.EnumEstadosPresupuesto;
import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.EnviarCorreo;
import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.ICalculadoraService;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IParametroService;
import com.marketingpersonal.service.IPresupuestoService;

@ManagedBean(name = "preAprFinBB")
@ViewScoped
public class PresupuestoAprobadorFinalBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoService presupuestoService;
	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICalculadoraService calculadoraService;
	@Autowired
	private IParametroService parametroService;
	
	private Util util;
	private Presupuesto detalle;
	private Presupuesto selectedPresupuesto;
	private PresupuestoDetalleMes presupuestoDetalleMes;
	private PresupuestoDetalleMes selectedPresupuestoDetalleMes;
	private PresupuestoDetalleCampania presupuestoDetalleCampania;
	private PresupuestoDetalleCampania selectedPresupuestoDetalleCampania;
	private List<Presupuesto> listaPresupuestos;
	private ListasGenericas listasGenericas;
	private Usuario usuario;
	private Observacion observacion;
	private boolean mostrarDetalle;
	private int camapanaMaxima;
	private Double totalMes = 0d;
	private Double totalCamp = 0d;
	private Integer anioGeneral;
	
	public PresupuestoAprobadorFinalBB() {
		util = Util.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		listaPresupuestos = getPresupuestoService().getPresupuestosAprobadorFinal(usuario);
		
		presupuestoDetalleMes = new PresupuestoDetalleMes();
		presupuestoDetalleCampania = new PresupuestoDetalleCampania();
		selectedPresupuesto = new Presupuesto();
		
		listasGenericas = ListasGenericas.getInstance();
		anioGeneral = Integer.valueOf(parametroService.getParametroByCodigo("ANIO_CALCULADORA").getValor());
		camapanaMaxima = getCalculadoraService().getCampanaMaxima(anioGeneral);
		observacion = new Observacion();
		mostrarDetalle = false;
	}
	
	public void aprobarPresupuesto(String tipo){
		try {
			if("MES".equals(tipo)) {
				selectedPresupuestoDetalleMes.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
				getPresupuestoService().actualizarEstadoPresupuestoDetalleMes(selectedPresupuestoDetalleMes);
				observacion.setPresupuestoDetalleMes(selectedPresupuestoDetalleMes);
				observacion.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorFinal(selectedPresupuestoDetalleMes.getCentroCosto().getId()).get(0));
			}else {
				selectedPresupuestoDetalleCampania.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
				getPresupuestoService().actualizarEstadoPresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
				observacion.setPresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
				observacion.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorFinal(selectedPresupuestoDetalleCampania.getCentroCosto().getId()).get(0));
			}

			//Guardamos la observacion
			observacion.setFecha(new Date());
			observacion.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
			observacion.setUsuarioEnvia(usuario);
			getPresupuestoService().addObservacion(observacion);
			
			//Envio de correo
			EnviarCorreo enviarCorreo = new EnviarCorreo();
			enviarCorreo.enviaCorreoResponsable(detalle, 
					detalle.getUsuario(), 
					EnumEstadosPresupuesto.FINALIZADO);
			
			observacion = new Observacion();
			util.mostrarMensaje("El presupuesto aprobado con éxito.");
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error enviando el registro.");
		} 	
	}
	
	public void rechazarPresupuesto(String tipo){
		try {
			Integer centroCosto = null;
			if("MES".equals(tipo)) {
				selectedPresupuestoDetalleMes.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
				getPresupuestoService().actualizarEstadoPresupuestoDetalleMes(selectedPresupuestoDetalleMes);
				observacion.setPresupuestoDetalleMes(selectedPresupuestoDetalleMes);
				observacion.setUsuarioRecibe(detalle.getUsuario());
				centroCosto = selectedPresupuestoDetalleMes.getCentroCosto().getId();
			}else {
				selectedPresupuestoDetalleCampania.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
				getPresupuestoService().actualizarEstadoPresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
				observacion.setPresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
				observacion.setUsuarioRecibe(detalle.getUsuario());
				centroCosto = selectedPresupuestoDetalleCampania.getCentroCosto().getId();
			}

			//Guardamos la observacion
			observacion.setFecha(new Date());
			observacion.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
			observacion.setUsuarioEnvia(usuario);
			getPresupuestoService().addObservacion(observacion);
			
			//Envio de correo
			EnviarCorreo enviarCorreo = new EnviarCorreo();
			enviarCorreo.enviaCorreoResponsable(detalle, 
					detalle.getUsuario(), 
					EnumEstadosPresupuesto.RECHAZADO);
			
			observacion = new Observacion();
			util.mostrarMensaje("El presupuesto fue rechazado con éxito.");
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error enviando el registro.");
		} 	
	}

	public IPresupuestoService getPresupuestoService() {
		return presupuestoService;
	}

	public void setPresupuestoService(IPresupuestoService presupuestoService) {
		this.presupuestoService = presupuestoService;
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

	public Presupuesto getDetalle() {
		return detalle;
	}

	public void setDetalle(Presupuesto detalle) {
		this.detalle = detalle;
		if(detalle != null) {
			if(!"Administrador".equals(usuario.getRol())) {
				//Cargamos los detalles
				detalle.setDetalleCampania(this.getPresupuestoService().getPresupuestoDetallesCampaniaAprobadorFinal(detalle.getId(), usuario));
				detalle.setDetalleMes(this.getPresupuestoService().getPresupuestoDetallesMesAprobadorFinal(detalle.getId(), usuario));
			}
			mostrarDetalle = true;
		}
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

	public Double getTotalMes() {
		return totalMes;
	}

	public void setTotalMes(Double totalMes) {
		this.totalMes = totalMes;
	}

	public Double getTotalCamp() {
		return totalCamp;
	}

	public void setTotalCamp(Double totalCamp) {
		this.totalCamp = totalCamp;
	}

	public Integer getAnioGeneral() {
		return anioGeneral;
	}

	public void setAnioGeneral(Integer anioGeneral) {
		this.anioGeneral = anioGeneral;
	}

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}

	public IParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(IParametroService parametroService) {
		this.parametroService = parametroService;
	}
	
 }