package com.marketingpersonal.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

public class ListasGenericas {
	
	private static ListasGenericas instance;
	private List<SelectItem> lstCargos;
	private List<SelectItem> lstRoles;
	private List<SelectItem> lstTipoPresupuesto;
	private List<SelectItem> lstMeses;
	private Map<Integer, String> mapMeses;
	
	public static ListasGenericas getInstance() {
		if(instance == null)
			instance = new ListasGenericas();
		return instance;
	}
	
	private ListasGenericas(){
		lstCargos = new ArrayList<>();
		lstCargos.add(new SelectItem("Administrador", "Administrador"));
		lstCargos.add(new SelectItem("Director", "Director"));
		lstCargos.add(new SelectItem("Gerente", "Gerente"));
		lstCargos.add(new SelectItem("Jefe", "Jefe"));
		
		lstRoles = new ArrayList<>();
		lstRoles.add(new SelectItem("Administrador", "Administrador"));
		lstRoles.add(new SelectItem("Usuario", "Usuario"));
		
		lstTipoPresupuesto = new ArrayList<>();
		lstTipoPresupuesto.add(new SelectItem("Campañal", "Campañal"));
		lstTipoPresupuesto.add(new SelectItem("Mensual", "Mensual"));
		
		mapMeses = new HashMap<>();
		mapMeses.put(1, "Enero");
		mapMeses.put(2, "Febrero");
		mapMeses.put(3, "Marzo");
		mapMeses.put(4, "Abril");
		mapMeses.put(5, "Mayo");
		mapMeses.put(6, "Junio");
		mapMeses.put(7, "Julio");
		mapMeses.put(8, "Agosto");
		mapMeses.put(9, "Septiembre");
		mapMeses.put(10, "Octubre");
		mapMeses.put(11, "Noviembre");
		mapMeses.put(12, "Diciembre");
		
		lstMeses = new ArrayList<>();
		lstMeses.add(new SelectItem(1, "Enero"));
		lstMeses.add(new SelectItem(2, "Febrero"));
		lstMeses.add(new SelectItem(3, "Marzo"));
		lstMeses.add(new SelectItem(4, "Abril"));
		lstMeses.add(new SelectItem(5, "Mayo"));
		lstMeses.add(new SelectItem(6, "Junio"));
		lstMeses.add(new SelectItem(7, "Julio"));
		lstMeses.add(new SelectItem(8, "Agosto"));
		lstMeses.add(new SelectItem(9, "Septiembre"));
		lstMeses.add(new SelectItem(10, "Octubre"));
		lstMeses.add(new SelectItem(11, "Noviembre"));
		lstMeses.add(new SelectItem(12, "Diciembre"));
	}

	public List<SelectItem> getLstCargos() {
		return lstCargos;
	}

	public List<SelectItem> getLstRoles() {
		return lstRoles;
	}

	public List<SelectItem> getLstTipoPresupuesto() {
		return lstTipoPresupuesto;
	}

	public List<SelectItem> getLstMeses() {
		return lstMeses;
	}
	
	public String getNombreMes(int numMes) {
		return mapMeses.get(numMes);
	}

}
