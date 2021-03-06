package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Direccion;


public interface IDireccionService {
	
	void addDireccion(Direccion entity);

	void updateDireccion(Direccion entity);
	
	void deleteDireccion(Direccion entity);
	
	Direccion getDireccionById(int id);
	
	List<Direccion> getDirecciones(boolean activo);
}
