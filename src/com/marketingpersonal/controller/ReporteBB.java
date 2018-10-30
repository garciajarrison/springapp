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
	private List<Presupuesto> listaReporte;
	
	private List<PresupuestoDetalleMes> listaReporteMeses;
	private List<PresupuestoDetalleCampania> listaReporteCampanas;
	
	private List<Calculadora> listaCalculadoras;
	private List<Calculadora[]> listaCalculadoraCM;
	private List<Calculadora[]> listaCalculadoraMC;
	private String tipo;
	private Integer anioConsulta;
	private int camapanaMaxima;
	private Usuario usuario;
	private boolean mes = false;
	private boolean campania = false;
	
	public ReporteBB() {
		util = Util.getInstance();
		listaAnios = calculadoraService.getListaAnios();
		anioConsulta = Integer.valueOf(parametroService.getParametroByCodigo("ANIO_CALCULADORA").getValor());
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		cargarListasPorAnio(null);
	}
	
	private void cargarListasPorAnio(final AjaxBehaviorEvent event) {
		try {
			cargarListaCalculadora();
			camapanaMaxima = getCalculadoraService().getCampanaMaxima(anioConsulta);
			listaPresupuestos = getPresupuestoService().getPresupuestosPorAnio(anioConsulta);
			
			listaReporte = new ArrayList<Presupuesto>();
			listaReporteMeses = new ArrayList<PresupuestoDetalleMes>();
			listaReporteCampanas = new ArrayList<PresupuestoDetalleCampania>();
			mes = false;
			campania = false;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarReporte() {
		try {
			mes = false;
			campania = false;
			listaReporteMeses = new ArrayList<PresupuestoDetalleMes>();
			listaReporteCampanas = new ArrayList<PresupuestoDetalleCampania>();
			
			if("Mensual".equals(tipo)) {
				mes = true;
				PresupuestoDetalleMes detalleTmp = null;
				
				//Recorremos los presupuestos del año
				for(Presupuesto ppto : listaPresupuestos) {
					listaReporteMeses.addAll(ppto.getDetalleMes());
					
					if(ppto.getDetalleCampania() != null && !ppto.getDetalleCampania().isEmpty()) {
						for(PresupuestoDetalleCampania detalleMes : ppto.getDetalleCampania()) {
							//Detalle
							detalleTmp = new PresupuestoDetalleMes();
							detalleTmp.setCentroCosto(detalleMes.getCentroCosto());
							detalleTmp.setCuenta(detalleMes.getCuenta());
							detalleTmp.setEstado(detalleMes.getEstado());
							detalleTmp.setObservacion(detalleMes.getObservacion());
							detalleTmp.setObservaciones(detalleMes.getObservaciones());
							detalleTmp.setPresupuesto(detalleMes.getPresupuesto());
							detalleTmp.setTotal(detalleMes.getTotal());
							detalleTmp.setUsuarioAprobadorFinal(detalleMes.getUsuarioAprobadorFinal());
							detalleTmp.setUsuarioAprobadorInicial(detalleMes.getUsuarioAprobadorInicial());
							
							double total1 = 0;
							double total2 = 0;
							double total3 = 0;
							double total4 = 0;
							double total5 = 0;
							double total6 = 0;
							double total7 = 0;
							double total8 = 0;
							double total9 = 0;
							double total10 = 0;
							double total11 = 0;
							double total12 = 0;
							
							double valorActual = 0;
							
							int i = 1;
							for(Calculadora[] objSuma : listaCalculadoraCM) {
								
								if(i > camapanaMaxima)
									break;
								
								if(i == 1)
									valorActual = detalleMes.getValorC1();
								else if(i == 2)
									valorActual = detalleMes.getValorC2();
								else if(i == 3)
									valorActual = detalleMes.getValorC3();
								else if(i == 4)
									valorActual = detalleMes.getValorC4();
								else if(i == 5)
									valorActual = detalleMes.getValorC5();
								else if(i == 6)
									valorActual = detalleMes.getValorC6();
								else if(i == 7)
									valorActual = detalleMes.getValorC7();
								else if(i == 8)
									valorActual = detalleMes.getValorC8();
								else if(i == 9)
									valorActual = detalleMes.getValorC9();
								else if(i == 10)
									valorActual = detalleMes.getValorC10();
								else if(i == 11)
									valorActual = detalleMes.getValorC11();
								else if(i == 12)
									valorActual = detalleMes.getValorC12();
								else if(i == 13)
									valorActual = detalleMes.getValorC13();
								else if(i == 14)
									valorActual = detalleMes.getValorC14();
								else if(i == 15)
									valorActual = detalleMes.getValorC15();
								else if(i == 16)
									valorActual = detalleMes.getValorC16();
								else if(i == 17)
									valorActual = detalleMes.getValorC17();
								else if(i == 18)
									valorActual = detalleMes.getValorC18();
								else if(i == 19)
									valorActual = detalleMes.getValorC19();
								else if(i == 20)
									valorActual = detalleMes.getValorC20();
								else if(i == 21)
									valorActual = detalleMes.getValorC21();
								else if(i == 22)
									valorActual = detalleMes.getValorC22();
								else if(i == 23)
									valorActual = detalleMes.getValorC23();
								else if(i == 24)
									valorActual = detalleMes.getValorC24();
								else if(i == 25)
									valorActual = detalleMes.getValorC25();
								
								for(int m = 0; m <= 11; m++) {
									if(objSuma[m].getPorcentaje() > 0) {
										if(m == 0)
											total1 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 1)
											total2 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 2)
											total3 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 3)
											total4 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 4)
											total5 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 5)
											total6 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 6)
											total7 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 7)
											total8 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 8)
											total9 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m ==9)
											total10 += (valorActual * objSuma[m].getPorcentaje())/100;
										else if(m == 10)
											total11 += (valorActual * objSuma[m].getPorcentaje())/100;
										else
											total12 += (valorActual * objSuma[m].getPorcentaje())/100;
									}
								}
								i++;
							}
					
							//Validamos a que campaña equivale el valor
							detalleTmp.setValorM1(total1);
							detalleTmp.setValorM2(total2);
							detalleTmp.setValorM3(total3);
							detalleTmp.setValorM4(total4);
							detalleTmp.setValorM5(total5);
							detalleTmp.setValorM6(total6);
							detalleTmp.setValorM7(total7);
							detalleTmp.setValorM8(total8);
							detalleTmp.setValorM9(total9);
							detalleTmp.setValorM10(total10);
							detalleTmp.setValorM11(total11);
							detalleTmp.setValorM12(total12);
						}
						listaReporteMeses.add(detalleTmp);
					}
				}
				
			//Campañal
			}else {
				
				campania = true;
				PresupuestoDetalleCampania detalleTmp = null;
				Double valor = 0d;
				
				//Recorremos los presupuestos del año
				for(Presupuesto ppto : listaPresupuestos) {
					listaReporteCampanas.addAll(ppto.getDetalleCampania());
					
					if(ppto.getDetalleMes() != null && !ppto.getDetalleMes().isEmpty()) {
						for(PresupuestoDetalleMes detalleMes : ppto.getDetalleMes()) {
							//Detalle
							detalleTmp = new PresupuestoDetalleCampania();
							detalleTmp.setCentroCosto(detalleMes.getCentroCosto());
							detalleTmp.setCuenta(detalleMes.getCuenta());
							detalleTmp.setEstado(detalleMes.getEstado());
							detalleTmp.setObservacion(detalleMes.getObservacion());
							detalleTmp.setObservaciones(detalleMes.getObservaciones());
							detalleTmp.setPresupuesto(detalleMes.getPresupuesto());
							detalleTmp.setTotal(detalleMes.getTotal());
							detalleTmp.setUsuarioAprobadorFinal(detalleMes.getUsuarioAprobadorFinal());
							detalleTmp.setUsuarioAprobadorInicial(detalleMes.getUsuarioAprobadorInicial());
							
							int i = 0;
							for(Calculadora[] cal : listaCalculadoraMC) {
								valor = 0d;
								if(cal[0].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM1() * cal[0].getPorcentaje()) / 100;
								}
								if(cal[1].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM2() * cal[1].getPorcentaje()) / 100;
								}
								if(cal[2].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM3() * cal[2].getPorcentaje()) / 100;
								}
								if(cal[3].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM4() * cal[3].getPorcentaje()) / 100;
								}
								if(cal[4].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM5() * cal[4].getPorcentaje()) / 100;
								}
								if(cal[5].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM6() * cal[5].getPorcentaje()) / 100;
								}
								if(cal[6].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM7() * cal[6].getPorcentaje()) / 100;
								}
								if(cal[7].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM8() * cal[7].getPorcentaje()) / 100;
								}
								if(cal[8].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM9() * cal[8].getPorcentaje()) / 100;
								}
								if(cal[9].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM10() * cal[9].getPorcentaje()) / 100;
								}
								if(cal[10].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM11() * cal[10].getPorcentaje()) / 100;
								}
								if(cal[11].getPorcentaje() > 0) {
									valor +=  (detalleMes.getValorM12() * cal[11].getPorcentaje()) / 100;
								}
								//Validamos a que campaña equivale el valor
								if(i == 0)
									detalleTmp.setValorC1(valor);
								else if(i == 1)
									 detalleTmp.setValorC2(valor);
								else if(i == 2)
									 detalleTmp.setValorC3(valor);
								else if(i == 3)
									 detalleTmp.setValorC4(valor);
								else if(i == 4)
									 detalleTmp.setValorC5(valor);
								else if(i == 5)
									 detalleTmp.setValorC6(valor);
								else if(i == 6)
									 detalleTmp.setValorC7(valor);
								else if(i == 7)
									 detalleTmp.setValorC8(valor);
								else if(i == 8)
									 detalleTmp.setValorC9(valor);
								else if(i == 9)
									 detalleTmp.setValorC10(valor);
								else if(i == 10)
									 detalleTmp.setValorC11(valor);
								else if(i == 11)
									 detalleTmp.setValorC12(valor);
								else if(i == 12)
									 detalleTmp.setValorC13(valor);
								else if(i == 13)
									 detalleTmp.setValorC14(valor);
								else if(i == 14)
									 detalleTmp.setValorC15(valor);
								else if(i == 15)
									 detalleTmp.setValorC16(valor);
								else if(i == 16)
									 detalleTmp.setValorC17(valor);
								else if(i == 17)
									 detalleTmp.setValorC18(valor);
								else if(i == 18)
									detalleTmp.setValorC19(valor);
								else if(i == 19)
									detalleTmp.setValorC20(valor);
								else if(i == 20)
									detalleTmp.setValorC21(valor);
								else if(i == 21)
									detalleTmp.setValorC22(valor);
								else if(i == 22)
									detalleTmp.setValorC23(valor);
								else if(i == 23)
									detalleTmp.setValorC24(valor);
								else if(i == 24)
									detalleTmp.setValorC25(valor);
								
								i++;
							}
						}
						listaReporteCampanas.add(detalleTmp);
					}
				}
			}
			
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
				objTmp[m-1] = new Calculadora(0,(camapanaMaxima+1),m,0,"MC",0f);
			}
			listaCalculadoraMC.add(objTmp);
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

	public int getCamapanaMaxima() {
		return camapanaMaxima;
	}

	public void setCamapanaMaxima(int camapanaMaxima) {
		this.camapanaMaxima = camapanaMaxima;
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

	public List<Presupuesto> getListaReporte() {
		return listaReporte;
	}

	public void setListaReporte(List<Presupuesto> listaReporte) {
		this.listaReporte = listaReporte;
	}

	public List<PresupuestoDetalleMes> getListaReporteMeses() {
		return listaReporteMeses;
	}

	public void setListaReporteMeses(List<PresupuestoDetalleMes> listaReporteMeses) {
		this.listaReporteMeses = listaReporteMeses;
	}

	public List<PresupuestoDetalleCampania> getListaReporteCampanas() {
		return listaReporteCampanas;
	}

	public void setListaReporteCampanas(List<PresupuestoDetalleCampania> listaReporteCampanas) {
		this.listaReporteCampanas = listaReporteCampanas;
	}

	public List<Calculadora> getListaCalculadoras() {
		return listaCalculadoras;
	}

	public void setListaCalculadoras(List<Calculadora> listaCalculadoras) {
		this.listaCalculadoras = listaCalculadoras;
	}

	public boolean isMes() {
		return mes;
	}

	public void setMes(boolean mes) {
		this.mes = mes;
	}

	public boolean isCampania() {
		return campania;
	}

	public void setCampania(boolean campania) {
		this.campania = campania;
	}
	
 }