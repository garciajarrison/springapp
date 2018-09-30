package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Gerencia;


public interface IGerenciaService {
	
	void addGerencia(Gerencia entity);

	void updateGerencia(Gerencia entity);
	
	void deleteGerencia(Gerencia entity);
	
	Gerencia getGerenciaById(int id);
	
	List<Gerencia> getGerencias(boolean activo);
}
