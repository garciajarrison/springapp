package com.marketingpersonal.service;

import java.util.ArrayList;
import java.util.List;

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
	public List<Calculadora> getCalculadoras() {	
		List<Calculadora> retorno = getEntityDAO().getCalculadoras();
		
		if(retorno == null || retorno.size() < 1) {
			retorno = new ArrayList<>();
			for(int c = 1; c <= 18; c++) {
				for(int m = 1; m <=12; m++) {
					this.addCalculadora(new Calculadora(c, m, 0d));
				}
			}
			retorno = getEntityDAO().getCalculadoras();
		}
		
		return retorno;
	}
	
	
	
}
