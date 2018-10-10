package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Usuario;


public interface ICentroCostoService {
	
	void addCentroCosto(CentroCosto entity);

	void updateCentroCosto(CentroCosto entity);
	
	void deleteCentroCosto(CentroCosto entity);
	
	CentroCosto getCentroCostoById(int id);
	
	List<CentroCosto> getCentroCostos(boolean activo);
	
	//Centro costo por cuenta
	void addCentroCostoPorCuenta(CentroCostoPorCuenta entity);

	void updateCentroCostoPorCuenta(CentroCostoPorCuenta entity);
	
	void deleteCentroCostoPorCuenta(CentroCostoPorCuenta entity);
	
	CentroCostoPorCuenta getCentroCostoPorCuentaById(int id);
	
	List<CentroCostoPorCuenta> getCentroCostoPorCuentas();
	
	List<CentroCosto> getCentroCostoPorUsuario(int idUsuario);

	Usuario getUsuarioAprobadorInicial(int idCentroCosto);

	Usuario getUsuarioAprobadorFinal(int idCentroCosto);
}
