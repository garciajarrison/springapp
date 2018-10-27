package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Calculadora;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.ICalculadoraService;
import com.marketingpersonal.service.IParametroService;
import com.marketingpersonal.service.IPresupuestoService;

@ManagedBean(name = "reporteBB")
@ViewScoped
public class ReporteBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoService presupuestoService;
	@Autowired
	private ICalculadoraService calculadoraService;
	@Autowired
	private IParametroService parametroService;

	private Util util;
	private List<SelectItem> listaAnios;
	private ListasGenericas listasGenericas;
	private List<Presupuesto> listaPresupuestos;
	private List<Calculadora> listaCalculadoras;
	private List<Calculadora[]> listaCalculadoraCM;
	private List<Calculadora[]> listaCalculadoraMC;
	private String tipo;
	private Integer anioConsulta;
	private int camapanaMaxima;
	private Usuario usuario;
	
	private Presupuesto presupuesto;
	private Presupuesto detalle;
	private Presupuesto selectedPresupuesto;
	private PresupuestoDetalleMes presupuestoDetalleMes;
	private PresupuestoDetalleMes selectedPresupuestoDetalleMes;
	private PresupuestoDetalleCampania presupuestoDetalleCampania;
	private PresupuestoDetalleCampania selectedPresupuestoDetalleCampania;
	
	
	
	private Observacion observacion;
	private List<Cuenta> listaCuentas;
	private List<CentroCosto> listaCentroCostos;
	private boolean mostrarDetalle;
	
	private Double totalMes = 0d;
	private Double totalCamp = 0d;
	
	
	public ReporteBB() {
		util = Util.getInstance();
		listaAnios = calculadoraService.getListaAnios();
		anioConsulta = Integer.valueOf(parametroService.getParametroByCodigo("ANIO_CALCULADORA").getValor());
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		cargarListasPorAnio(null);

		
		
		presupuesto = new Presupuesto();
		presupuestoDetalleMes = new PresupuestoDetalleMes();
		presupuestoDetalleCampania = new PresupuestoDetalleCampania();
		selectedPresupuesto = new Presupuesto();
		mostrarDetalle = false;
		observacion = new Observacion();
	}
	
	
	private void cargarListasPorAnio(final AjaxBehaviorEvent event) {
		try {
			cargarListaCalculadora();
			camapanaMaxima = getCalculadoraService().getCampanaMaxima(anioConsulta);
			listaPresupuestos = getPresupuestoService().getPresupuestosPorAnio(anioConsulta);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarListaCalculadora() {
		try {
			listaCalculadoras = getCalculadoraService().getCalculadoras("CM", anioConsulta);
			
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
			
			camapanaMaxima = listaCalculadoraCM.size();
			
			listaCalculadoras = getCalculadoraService().getCalculadoras("MC", anioConsulta);
			
			//Carga de calculadora Mes / campaña
			listaCalculadoraMC = new ArrayList<>();
			objTmp = new Calculadora[12];
			i = 1;
			for(Calculadora cal : listaCalculadoras) {
				if(i == 1) {
					objTmp = new Calculadora[12];
				}
				
				objTmp[i-1] = cal;
				if(i == 12) {
					i = 0;
					listaCalculadoraMC.add(objTmp);
				}
				i++;
			}
			//La ultima fila es para totalizar
			objTmp = new Calculadora[12];
			for(int m = 1; m <= 12; m++) {
				objTmp[m-1] = new Calculadora(0,(camapanaMaxima+1),m,0,"MC",0d);
			}
			listaCalculadoraMC.add(objTmp);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarReporte() {
		try {
			if("Mensual".equals(tipo)) {
				
			}else {
				//Recorremos los presupuestos del año
				for(Presupuesto ppto : listaPresupuestos) {
					
					
					
					
					
				}
				
				
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
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

	public IPresupuestoService getPresupuestoService() {
		return presupuestoService;
	}

	public void setPresupuestoService(IPresupuestoService presupuestoService) {
		this.presupuestoService = presupuestoService;
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

	public List<SelectItem> getListaAnios() {
		return listaAnios;
	}

	public void setListaAnios(List<SelectItem> listaAnios) {
		this.listaAnios = listaAnios;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getAnioConsulta() {
		return anioConsulta;
	}

	public void setAnioConsulta(Integer anioConsulta) {
		this.anioConsulta = anioConsulta;
	}
	
 }