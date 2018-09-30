package com.marketingpersonal.common;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public class ListasGenericas {
	
	private static ListasGenericas instance;
	private List<SelectItem> lstCargos;
	private List<SelectItem> lstRoles;
	
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
	}

	public List<SelectItem> getLstCargos() {
		return lstCargos;
	}

	public List<SelectItem> getLstRoles() {
		return lstRoles;
	}


}
