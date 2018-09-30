package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Gerencia;


public interface IGerenciaService {
	
	void addGerencia(Gerencia gerencia);

	void updateGerencia(Gerencia gerencia);
	
	void deleteGerencia(Gerencia gerencia);
	
	Gerencia getGerenciaById(int id);
	
	List<Gerencia> getGerencias();
}
