package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Parametro;


public interface IParametroService {
	
	void addParametro(Parametro entity);

	void updateParametro(Parametro entity);
	
	void deleteParametro(Parametro entity);
	
	Parametro getParametroById(int id);
	
	List<Parametro> getParametros();
}
