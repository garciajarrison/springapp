package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Direccion;


public interface IDireccionDAO {
	
	void addDireccion(Direccion entity);

	void updateDireccion(Direccion entity);
	
	void deleteDireccion(Direccion entity);
	
	Direccion getDireccionById(int id);

	List<Direccion> getDireccions(boolean activo);

}
