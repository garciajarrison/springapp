package com.marketingpersonal.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class ListasGenericas {
	
	private static ListasGenericas instance;
	private List<SelectItem> lstCargos;
	private List<SelectItem> lstRoles;
	private List<SelectItem> lstTipoPresupuesto;
	
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

}
