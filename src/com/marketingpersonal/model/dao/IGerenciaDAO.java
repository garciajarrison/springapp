package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Gerencia;

/**
 * Interface que contiene los metodos implementados en la clase GerenciaDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface IGerenciaDAO {
	
	void addGerencia(Gerencia entity);

	void updateGerencia(Gerencia entity);
	
	void deleteGerencia(Gerencia entity);
	
	Gerencia getGerenciaById(int id);

	List<Gerencia> getGerencias(boolean activo);

}
