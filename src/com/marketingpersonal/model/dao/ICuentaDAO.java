package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Cuenta;

/**
 * Interface que contiene los metodos implementados en la clase CuentaDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface ICuentaDAO {
	
	void addCuenta(Cuenta entity);

	void updateCuenta(Cuenta entity);
	
	void deleteCuenta(Cuenta entity);
	
	Cuenta getCuentaById(int id);

	List<Cuenta> getCuentas(boolean activo);

	List<Cuenta> getCuentaPorCentroCosto(int idCuenta);

	List<Cuenta> getCuentasPorUsuario(int idUsuario);

}
