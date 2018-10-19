package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Cuenta;


public interface ICuentaDAO {
	
	void addCuenta(Cuenta entity);

	void updateCuenta(Cuenta entity);
	
	void deleteCuenta(Cuenta entity);
	
	Cuenta getCuentaById(int id);

	List<Cuenta> getCuentas(boolean activo);

	List<Cuenta> getCuentaPorCentroCosto(int idCuenta);

	List<Cuenta> getCuentasPorUsuario(int idUsuario);

}
