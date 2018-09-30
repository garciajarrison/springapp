package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IJefaturaDAO;
import com.marketingpersonal.model.entity.Jefatura;


@Service
@Transactional(readOnly = true)
public class JefaturaService implements IJefaturaService {

	@Autowired
	private IJefaturaDAO entityDAO;

	@Transactional(readOnly = false)
	public void addJefatura(Jefatura entity) {
		getEntityDAO().addJefatura(entity);
	}

	@Transactional(readOnly = false)
	public void deleteJefatura(Jefatura entity) {
		getEntityDAO().deleteJefatura(entity);
	}

	@Transactional(readOnly = false)
	public void updateJefatura(Jefatura entity) {
		getEntityDAO().updateJefatura(entity);
	}

	public Jefatura getJefaturaById(int id) {
		return getEntityDAO().getJefaturaById(id);
	}

	public List<Jefatura> getJefaturas(boolean activo) {	
		return getEntityDAO().getJefaturas(activo);
	}
	
	public IJefaturaDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IJefaturaDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

}
