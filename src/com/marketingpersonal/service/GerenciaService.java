package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IGerenciaDAO;
import com.marketingpersonal.model.entity.Gerencia;


@Service
@Transactional(readOnly = true)
public class GerenciaService implements IGerenciaService {

	@Autowired
	private IGerenciaDAO entityDAO;
	
	public IGerenciaDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IGerenciaDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Transactional(readOnly = false)
	public void addGerencia(Gerencia entity) {
		getEntityDAO().addGerencia(entity);
	}

	@Transactional(readOnly = false)
	public void deleteGerencia(Gerencia entity) {
		getEntityDAO().deleteGerencia(entity);
	}

	@Transactional(readOnly = false)
	public void updateGerencia(Gerencia entity) {
		getEntityDAO().updateGerencia(entity);
	}

	public Gerencia getGerenciaById(int id) {
		return getEntityDAO().getGerenciaById(id);
	}

	public List<Gerencia> getGerencias(boolean activo) {	
		return getEntityDAO().getGerencias(activo);
	}
}
