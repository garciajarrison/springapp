package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Usuario;

/**
 * Interface que contiene los metodos implementados en la clase CentroCostoDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface ICentroCostoDAO {
	
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

	List<Usuario> getUsuarioAprobadorInicial(int idCentroCosto);

	List<Usuario> getUsuarioAprobadorFinal(int idCentroCosto);

	List<CentroCosto> getCentroCostosPorCuenta(int idCuenta, int idUsuario);

}
