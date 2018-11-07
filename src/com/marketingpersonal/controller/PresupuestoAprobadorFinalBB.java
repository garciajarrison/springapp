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
	private Observacion observacionApr;
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
		observacionApr = new Observacion();
		mostrarDetalle = false;
	}
	
	public void actualizarMes(PresupuestoDetalleMes detPpto) {
		try {
			this.presupuestoDetalleMes = detPpto;    
			totalizarMes();
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void actualizarCamp(PresupuestoDetalleCampania detPpto) {
		try {
			this.presupuestoDetalleCampania = detPpto;
			totalizarCamp();
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void totalizarMes() {
		try {
			totalMes = 0d;
			totalMes += presupuestoDetalleMes.getValorM1();
			totalMes += presupuestoDetalleMes.getValorM2();
			totalMes += presupuestoDetalleMes.getValorM3();
			totalMes += presupuestoDetalleMes.getValorM4();
			totalMes += presupuestoDetalleMes.getValorM5();
			totalMes += presupuestoDetalleMes.getValorM6();
			totalMes += presupuestoDetalleMes.getValorM7();
			totalMes += presupuestoDetalleMes.getValorM8();
			totalMes += presupuestoDetalleMes.getValorM9();
			totalMes += presupuestoDetalleMes.getValorM10();
			totalMes += presupuestoDetalleMes.getValorM11();
			totalMes += presupuestoDetalleMes.getValorM12();
			presupuestoDetalleMes.setTotal(totalMes);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalizarCamp() {
		try {
			totalCamp = 0d;
			totalCamp += presupuestoDetalleCampania.getValorC1();
			totalCamp += presupuestoDetalleCampania.getValorC2();
			totalCamp += presupuestoDetalleCampania.getValorC3();
			totalCamp += presupuestoDetalleCampania.getValorC4();
			totalCamp += presupuestoDetalleCampania.getValorC5();
			totalCamp += presupuestoDetalleCampania.getValorC6();
			totalCamp += presupuestoDetalleCampania.getValorC7();
			totalCamp += presupuestoDetalleCampania.getValorC8();
			totalCamp += presupuestoDetalleCampania.getValorC9();
			totalCamp += presupuestoDetalleCampania.getValorC10();
			totalCamp += presupuestoDetalleCampania.getValorC11();
			totalCamp += presupuestoDetalleCampania.getValorC12();
			totalCamp += presupuestoDetalleCampania.getValorC13();
			totalCamp += presupuestoDetalleCampania.getValorC14();
			totalCamp += presupuestoDetalleCampania.getValorC15();
			totalCamp += presupuestoDetalleCampania.getValorC16();
			totalCamp += presupuestoDetalleCampania.getValorC17();
			totalCamp += presupuestoDetalleCampania.getValorC18();
			totalCamp += presupuestoDetalleCampania.getValorC19();
			totalCamp += presupuestoDetalleCampania.getValorC20();
			totalCamp += presupuestoDetalleCampania.getValorC21();
			totalCamp += presupuestoDetalleCampania.getValorC22();
			totalCamp += presupuestoDetalleCampania.getValorC23();
			totalCamp += presupuestoDetalleCampania.getValorC24();
			totalCamp += presupuestoDetalleCampania.getValorC25();
			presupuestoDetalleCampania.setTotal(totalCamp);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void aprobarPresupuesto(){
		try {
			String tipo = this.detalle.getTipo();
			Integer centroCosto = 0;
			Observacion observacionTmp = new Observacion();
			if("Mensual".equals(tipo)) {
				
				if(detalle.getDetalleMes() != null && !detalle.getDetalleMes().isEmpty()) {
					for(PresupuestoDetalleMes det : detalle.getDetalleMes()) {
						
						observacionTmp = new Observacion();
						observacionTmp.setObservacion(observacionApr.getObservacion());
						det.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
						getPresupuestoService().actualizarEstadoPresupuestoDetalleMes(det);
						observacionTmp.setPresupuestoDetalleMes(det);
						observacionTmp.setUsuarioRecibe(detalle.getUsuario());
						centroCosto = det.getCentroCosto().getId();
						observacionTmp.setFecha(new Date());
						observacionTmp.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
						observacionTmp.setUsuarioEnvia(usuario);
						
						getPresupuestoService().addObservacion(observacionTmp);
						selectedPresupuestoDetalleMes = det;
					}
				}
				
			}else {
				
				if(detalle.getDetalleCampania() != null && !detalle.getDetalleCampania().isEmpty()) {
					for(PresupuestoDetalleCampania det : detalle.getDetalleCampania()) {
						
						observacionTmp = new Observacion();
						observacionTmp.setObservacion(observacionApr.getObservacion());
						det.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
						getPresupuestoService().actualizarEstadoPresupuestoDetalleCampania(det);
						observacionTmp.setPresupuestoDetalleCampania(det);
						observacionTmp.setUsuarioRecibe(detalle.getUsuario());
						centroCosto = det.getCentroCosto().getId();
						observacionTmp.setFecha(new Date());
						observacionTmp.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
						observacionTmp.setUsuarioEnvia(usuario);
						
						getPresupuestoService().addObservacion(observacionTmp);
						selectedPresupuestoDetalleCampania = det;
					}
				}
			}
			
			if(centroCosto > 0) {
				//Envio de correo
				EnviarCorreo enviarCorreo = new EnviarCorreo();
				enviarCorreo.enviaCorreoResponsable(detalle, 
						detalle.getUsuario(), 
						EnumEstadosPresupuesto.FINALIZADO);
				
				observacion = new Observacion();
				observacionApr = new Observacion();
				detalle = null;
				listaPresupuestos = getPresupuestoService().getPresupuestosAprobadorFinal(usuario);
				util.mostrarMensaje("El presupuesto aprobado con éxito.");
			}else {
				util.mostrarError("El presupuesto no tiene detalle para aprobar.");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error enviando el registro.");
		} 	
	}
	
	public void rechazarPresupuesto(){
		try {
			String tipo = this.detalle.getTipo();
			Integer centroCosto = 0;
			Observacion observacionTmp = new Observacion();
			if("Mensual".equals(tipo)) {
				
				if(detalle.getDetalleMes() != null && !detalle.getDetalleMes().isEmpty()) {
					for(PresupuestoDetalleMes det : detalle.getDetalleMes()) {
						
						observacionTmp = new Observacion();
						observacionTmp.setObservacion(observacion.getObservacion());
						det.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
						getPresupuestoService().actualizarEstadoPresupuestoDetalleMes(det);
						observacionTmp.setPresupuestoDetalleMes(det);
						observacionTmp.setUsuarioRecibe(detalle.getUsuario());
						centroCosto = det.getCentroCosto().getId();
						observacionTmp.setFecha(new Date());
						observacionTmp.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
						observacionTmp.setUsuarioEnvia(usuario);
						
						getPresupuestoService().addObservacion(observacionTmp);
						selectedPresupuestoDetalleMes = det;
					}
				}
			}else {
				
				if(detalle.getDetalleCampania() != null && !detalle.getDetalleCampania().isEmpty()) {
					for(PresupuestoDetalleCampania det : detalle.getDetalleCampania()) {
						
						observacionTmp = new Observacion();
						observacionTmp.setObservacion(observacion.getObservacion());
						det.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
						getPresupuestoService().actualizarEstadoPresupuestoDetalleCampania(det);
						observacionTmp.setPresupuestoDetalleCampania(det);
						observacionTmp.setUsuarioRecibe(detalle.getUsuario());
						centroCosto = det.getCentroCosto().getId();
						observacionTmp.setFecha(new Date());
						observacionTmp.setEstado(EnumEstadosPresupuesto.RECHAZADO.getCodigo());
						observacionTmp.setUsuarioEnvia(usuario);
						
						getPresupuestoService().addObservacion(observacionTmp);
						selectedPresupuestoDetalleCampania = det;
					}
				}
			}

			if(centroCosto > 0) {
				//Envio de correo
				EnviarCorreo enviarCorreo = new EnviarCorreo();
				enviarCorreo.enviaCorreoResponsable(detalle, 
						detalle.getUsuario(), 
						EnumEstadosPresupuesto.RECHAZADO);
				
				observacion = new Observacion();
				detalle = null;
				listaPresupuestos = getPresupuestoService().getPresupuestosAprobadorFinal(usuario);
				util.mostrarMensaje("El presupuesto rechazado con éxito.");
			}else {
				util.mostrarError("El presupuesto no tiene detalle para rechazar.");
			}
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

	public Observacion getObservacionApr() {
		return observacionApr;
	}

	public void setObservacionApr(Observacion observacionApr) {
		this.observacionApr = observacionApr;
	}
	
 }