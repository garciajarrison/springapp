package com.marketingpersonal.model.dao;

import java.util.List;

import javax.faces.model.SelectItem;

import com.marketingpersonal.model.entity.Calculadora;

/**
 * Interface que contiene los metodos implementados en la clase CalculadoraDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface ICalculadoraDAO {
	
	void addCalculadora(Calculadora entity);

	void updateCalculadora(Calculadora entity);
	
	void deleteCalculadora(Calculadora entity);
	
	Calculadora getCalculadoraById(int id);

	List<Calculadora> getCalculadoras(String tipo, Integer anio);

	void eliminarCampaniaCalculadora(int camapanaMaxima);

	void updateCalculadoras(List<Calculadora[]> listaCalculadora, String tipo, int camapanaMaxima);

	int getCampanaMaxima(Integer anioGeneral);

	List<SelectItem> getListaAnios();

}
