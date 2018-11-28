package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Direccion;

/**
 * Interface que contiene los metodos implementados en la clase DireccionDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface IDireccionDAO {
	
	void addDireccion(Direccion entity);

	void updateDireccion(Direccion entity);
	
	void deleteDireccion(Direccion entity);
	
	Direccion getDireccionById(int id);

	List<Direccion> getDireccions(boolean activo);

}
