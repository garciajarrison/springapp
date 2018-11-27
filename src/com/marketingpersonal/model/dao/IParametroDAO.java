package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Parametro;

/**
 * Interface que contiene los metodos implementados en la clase ParametroDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface IParametroDAO {
	
	void addParametro(Parametro entity);

	void updateParametro(Parametro entity);
	
	void deleteParametro(Parametro entity);
	
	Parametro getParametroById(int id);

	List<Parametro> getParametros();

	Parametro getParametroByCodigo(String codigoParametro);

}
