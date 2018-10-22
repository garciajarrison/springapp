package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IParametroDAO;
import com.marketingpersonal.model.entity.Parametro;


@Service
@Transactional(readOnly = true)
public class ParametroService implements IParametroService {

	@Autowired
	private IParametroDAO entityDAO;

	public IParametroDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IParametroDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	
	@Transactional(readOnly = false)
	public void addParametro(Parametro entity) {
		getEntityDAO().addParametro(entity);
	}

	@Transactional(readOnly = false)
	public void deleteParametro(Parametro entity) {
		getEntityDAO().deleteParametro(entity);
	}

	@Transactional(readOnly = false)
	public void updateParametro(Parametro entity) {
		getEntityDAO().updateParametro(entity);
	}

	public Parametro getParametroById(int id) {
		return getEntityDAO().getParametroById(id);
	}

	public List<Parametro> getParametros() {	
		return getEntityDAO().getParametros();
	}
	
	public Parametro getParametroByCodigo(String codigoParametro) {
		return getEntityDAO().getParametroByCodigo(codigoParametro);
	}

}
