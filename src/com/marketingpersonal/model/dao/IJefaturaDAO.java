package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Jefatura;


public interface IJefaturaDAO {
	
	void addJefatura(Jefatura entity);

	void updateJefatura(Jefatura entity);
	
	void deleteJefatura(Jefatura entity);
	
	Jefatura getJefaturaById(int id);

	List<Jefatura> getJefaturas(boolean activo);

}
