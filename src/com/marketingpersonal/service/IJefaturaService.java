package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Jefatura;


public interface IJefaturaService {
	
	void addJefatura(Jefatura entity);

	void updateJefatura(Jefatura entity);
	
	void deleteJefatura(Jefatura entity);
	
	Jefatura getJefaturaById(int id);
	
	List<Jefatura> getJefaturas(boolean activo);
}
