package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Calculadora;


public interface ICalculadoraDAO {
	
	void addCalculadora(Calculadora entity);

	void updateCalculadora(Calculadora entity);
	
	void deleteCalculadora(Calculadora entity);
	
	Calculadora getCalculadoraById(int id);

	List<Calculadora> getCalculadoras(String tipo);

	void eliminarCampaniaCalculadora(int camapanaMaxima);

	void updateCalculadoras(List<Calculadora[]> listaCalculadora, String tipo, int camapanaMaxima);

}
