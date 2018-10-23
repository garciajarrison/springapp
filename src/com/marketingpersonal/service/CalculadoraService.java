package com.marketingpersonal.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.ICalculadoraDAO;
import com.marketingpersonal.model.entity.Calculadora;


@Service
@Transactional(readOnly = true)
public class CalculadoraService implements ICalculadoraService {

	@Autowired
	private ICalculadoraDAO entityDAO;
	
	public ICalculadoraDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(ICalculadoraDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Transactional(readOnly = false)
	public void addCalculadora(Calculadora entity) {
		getEntityDAO().addCalculadora(entity);
	}

	@Transactional(readOnly = false)
	public void deleteCalculadora(Calculadora entity) {
		getEntityDAO().deleteCalculadora(entity);
	}

	@Transactional(readOnly = false)
	public void updateCalculadora(Calculadora entity) {
		getEntityDAO().updateCalculadora(entity);
	}

	public Calculadora getCalculadoraById(int id) {
		return getEntityDAO().getCalculadoraById(id);
	}
	
	@Transactional(readOnly = false)
	public List<Calculadora> getCalculadoras(String tipo, Integer anio) {	
		List<Calculadora> retorno = getEntityDAO().getCalculadoras(tipo, anio);
		
		if(retorno == null || retorno.size() < 1) {
			retorno = new ArrayList<>();
			for(int c = 1; c <= 18; c++) {
				for(int m = 1; m <=12; m++) {
					this.addCalculadora(new Calculadora(0,c, m, anio, tipo, 0d));
				}
			}
			retorno = getEntityDAO().getCalculadoras(tipo, anio);
		}
		
		return retorno;
	}

	@Transactional(readOnly = false)
	public void addCampaniaCalculadora(int campania, Integer anio) {
		for(int m = 1; m <=12; m++) {
			this.addCalculadora(new Calculadora(0,campania, m, anio, "CM", 0d));
			this.addCalculadora(new Calculadora(0,campania, m, anio, "MC", 0d));
		}
	}

	@Transactional(readOnly = false)
	public void eliminarCampaniaCalculadora(int camapanaMaxima) {
		getEntityDAO().eliminarCampaniaCalculadora(camapanaMaxima);
	}

	@Transactional(readOnly = false)
	public void updateCalculadoras(List<Calculadora[]> listaCalculadora, String tipo, int camapanaMaxima) {
		getEntityDAO().updateCalculadoras(listaCalculadora, tipo, camapanaMaxima);
	}

	public int getCampanaMaxima(Integer anioGeneral) {
		return getEntityDAO().getCampanaMaxima(anioGeneral);
	}

	public List<SelectItem> getListaAnios() {
		return getEntityDAO().getListaAnios();
	}

	
}
