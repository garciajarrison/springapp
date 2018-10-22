package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Parametro;


public interface IParametroDAO {
	
	void addParametro(Parametro entity);

	void updateParametro(Parametro entity);
	
	void deleteParametro(Parametro entity);
	
	Parametro getParametroById(int id);

	List<Parametro> getParametros();

	Parametro getParametroByCodigo(String codigoParametro);

}
