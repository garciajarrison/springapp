package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Cuenta;


public interface ICuentaService {
	
	void addCuenta(Cuenta entity);

	void updateCuenta(Cuenta entity);
	
	void deleteCuenta(Cuenta entity);
	
	Cuenta getCuentaById(int id);
	
	List<Cuenta> getCuentas(boolean activo);
	
	List<Cuenta> getCuentaPorCentroCosto(int idCentroCosto);
}
