package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Jefatura;

/**
 * Interface que contiene los metodos implementados en la clase JefaturaDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface IJefaturaDAO {
	
	void addJefatura(Jefatura entity);

	void updateJefatura(Jefatura entity);
	
	void deleteJefatura(Jefatura entity);
	
	Jefatura getJefaturaById(int id);

	List<Jefatura> getJefaturas(boolean activo);

}
