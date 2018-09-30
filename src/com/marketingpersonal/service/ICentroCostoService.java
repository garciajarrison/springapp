package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;


public interface ICentroCostoService {
	
	void addCentroCosto(CentroCosto entity);

	void updateCentroCosto(CentroCosto entity);
	
	void deleteCentroCosto(CentroCosto entity);
	
	CentroCosto getCentroCostoById(int id);
	
	List<CentroCosto> getCentroCostos();
	
	//Centro costo por cuenta
	void addCentroCostoPorCuenta(CentroCostoPorCuenta entity);

	void updateCentroCostoPorCuenta(CentroCostoPorCuenta entity);
	
	void deleteCentroCostoPorCuenta(CentroCostoPorCuenta entity);
	
	CentroCostoPorCuenta getCentroCostoPorCuentaById(int id);
	
	List<CentroCostoPorCuenta> getCentroCostoPorCuentas();
}
