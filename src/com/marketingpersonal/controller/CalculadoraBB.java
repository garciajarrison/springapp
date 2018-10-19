package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Calculadora;
import com.marketingpersonal.service.ICalculadoraService;

@ManagedBean(name = "calculadoraBB")
@ViewScoped
public class CalculadoraBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICalculadoraService calculadoraService;
	private Util util;
	private Calculadora calculadora;
	private Calculadora selectedCalculadora;
	private List<Calculadora> listaCalculadoras;
	private List<Calculadora[]> listaCalculadoraCM;
	private List<Calculadora[]> listaCalculadoraMC;
	private int camapanaMaxima;
	
	public CalculadoraBB() {
		util = Util.getInstance();
		calculadora = new Calculadora();
		selectedCalculadora = new Calculadora();
		this.cargarListaCalculadora();
		this.totalizar("CM");
		this.totalizar("MC");
	}
	
	public void cargarListaCalculadora() {
		try {
			listaCalculadoras = getCalculadoraService().getCalculadoras("CM");
			
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
			
			listaCalculadoras = getCalculadoraService().getCalculadoras("MC");
			
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
	
	public void agregarCampana() {
		try {
			
			//Primer se guardan los cambios pendientes
			this.guardarCampana("CM");
			this.guardarCampana("MC");
			camapanaMaxima = camapanaMaxima + 1;
			
			this.getCalculadoraService().addCampaniaCalculadora(camapanaMaxima);
			
			cargarListaCalculadora();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarCampana() {
		try {
			
			//Primer se guardan los cambios pendientes
			this.guardarCampana("CM");
			this.guardarCampana("MC");
			
			this.getCalculadoraService().eliminarCampaniaCalculadora(camapanaMaxima);
			
			cargarListaCalculadora();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean validarCampanas() {
		boolean continuar = true;
		try {
			//Validamos los totales CM
			double total = 0;
			for(Calculadora[] objSuma : listaCalculadoraCM) {
				total = 0;
				for(int m = 0; m <= 11; m++) {
					total += objSuma[m].getPorcentaje();
				}
				if(total > 100) {
					util.mostrarError("Hay porcentajes mayores al 100% en la calculadora Campaña / Mes.");
					continuar = false;
					break;
				}
			}
			
			//Validamos los totales MC
			List<Double> totales = totalizar("MC");
			for(Double dl : totales) {
				if(dl > 100) {
					util.mostrarError("Hay porcentajes mayores al 100% en la calculadora Mes / Campaña.");
					continuar = false;
					break;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return continuar;
	}
	
	public void guardarCampana(String tipo) {
		try {
			if(validarCampanas()) {
				this.getCalculadoraService().updateCalculadoras(listaCalculadoraCM, "CM", camapanaMaxima);
				this.getCalculadoraService().updateCalculadoras(listaCalculadoraMC, "MC", camapanaMaxima);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Double> totalizar(String tipo) {
		
		 List<Double> totales = new ArrayList<Double>();
		if("CM".equals(tipo)) {
			double total = 0;
			for(Calculadora[] objSuma : listaCalculadoraCM) {
				total = 0;
				for(int m = 0; m <= 11; m++) {
					total += objSuma[m].getPorcentaje();
				}
				objSuma[objSuma.length -1].setPorcentaje(total);
			}
		}else {
			
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
			
			int i = 1;
			for(Calculadora[] objSuma : listaCalculadoraMC) {
				
				if(i > camapanaMaxima)
					break;
				
				for(int m = 0; m <= 11; m++) {
					if(m == 0)
						total1 += objSuma[m].getPorcentaje();
					else if(m == 1)
						total2 += objSuma[m].getPorcentaje();
					else if(m == 2)
						total3 += objSuma[m].getPorcentaje();
					else if(m == 3)
						total4 += objSuma[m].getPorcentaje();
					else if(m == 4)
						total5 += objSuma[m].getPorcentaje();
					else if(m == 5)
						total6 += objSuma[m].getPorcentaje();
					else if(m == 6)
						total7 += objSuma[m].getPorcentaje();
					else if(m == 7)
						total8 += objSuma[m].getPorcentaje();
					else if(m == 8)
						total9 += objSuma[m].getPorcentaje();
					else if(m ==9)
						total10 += objSuma[m].getPorcentaje();
					else if(m == 10)
						total11 += objSuma[m].getPorcentaje();
					else
						total12 += objSuma[m].getPorcentaje();
				}
				i++;
			}
			
			Calculadora[] objSuma = listaCalculadoraMC.get(listaCalculadoraMC.size() - 1);
			objSuma[0].setPorcentaje(total1);
			objSuma[1].setPorcentaje(total2);
			objSuma[2].setPorcentaje(total3);
			objSuma[3].setPorcentaje(total4);
			objSuma[4].setPorcentaje(total5);
			objSuma[5].setPorcentaje(total6);
			objSuma[6].setPorcentaje(total7);
			objSuma[7].setPorcentaje(total8);
			objSuma[8].setPorcentaje(total9);
			objSuma[9].setPorcentaje(total10);
			objSuma[10].setPorcentaje(total11);
			objSuma[11].setPorcentaje(total12);
			
			totales.add(total1);
			totales.add(total2);
			totales.add(total3);
			totales.add(total4);
			totales.add(total5);
			totales.add(total6);
			totales.add(total7);
			totales.add(total8);
			totales.add(total9);
			totales.add(total10);
			totales.add(total11);
			totales.add(total12);
			
		}
		return totales;
	}
	
	public void guardar(String tipo) {
		try {
			if("CM".equals(tipo)) {
				for(Calculadora[] objSuma : listaCalculadoraCM) {
					for(int m = 0; m <= 11; m++) {
						this.getCalculadoraService().updateCalculadora(objSuma[m]);
					}
				}
			}else {
				
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Calculadora getCalculadora() {
		return calculadora;
	}

	public void setCalculadora(Calculadora calculadora) {
		this.calculadora = calculadora;
	}

	public Calculadora getSelectedCalculadora() {
		return selectedCalculadora;
	}

	public void setSelectedCalculadora(Calculadora selectedCalculadora) {
		this.selectedCalculadora = selectedCalculadora;
	}

	public List<Calculadora> getListaCalculadoras() {
		return listaCalculadoras;
	}

	public void setListaCalculadoras(List<Calculadora> listaCalculadoras) {
		this.listaCalculadoras = listaCalculadoras;
	}

	public ICalculadoraService getCalculadoraService() {
		return calculadoraService;
	}

	public void setCalculadoraService(ICalculadoraService calculadoraService) {
		this.calculadoraService = calculadoraService;
	}

	public List<Calculadora[]> getListaCalculadoraCM() {
		return listaCalculadoraCM;
	}

	public void setListaCalculadoraCM(List<Calculadora[]> listaCalculadoraCM) {
		this.listaCalculadoraCM = listaCalculadoraCM;
	}

	public List<Calculadora[]> getListaCalculadoraMC() {
		return listaCalculadoraMC;
	}

	public void setListaCalculadoraMC(List<Calculadora[]> listaCalculadoraMC) {
		this.listaCalculadoraMC = listaCalculadoraMC;
	}

	public int getCamapanaMaxima() {
		return camapanaMaxima;
	}

	public void setCamapanaMaxima(int camapanaMaxima) {
		this.camapanaMaxima = camapanaMaxima;
	}

 }