package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Gerencia;


public interface IGerenciaDAO {
	
	void addGerencia(Gerencia entity);

	void updateGerencia(Gerencia entity);
	
	void deleteGerencia(Gerencia entity);
	
	Gerencia getGerenciaById(int id);

	List<Gerencia> getGerencias(boolean activo);

}
