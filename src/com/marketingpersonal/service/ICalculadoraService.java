package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Calculadora;


public interface ICalculadoraService {
	
	void addCalculadora(Calculadora entity);

	void updateCalculadora(Calculadora entity);
	
	void deleteCalculadora(Calculadora entity);
	
	Calculadora getCalculadoraById(int id);
	
	List<Calculadora> getCalculadoras(String tipo);

	void addCampaniaCalculadora(int campania);

	void eliminarCampaniaCalculadora(int camapanaMaxima);

	void updateCalculadoras(List<Calculadora[]> listaCalculadora, String string, int camapanaMaxima);
	
}
