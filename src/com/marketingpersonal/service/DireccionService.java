package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IDireccionDAO;
import com.marketingpersonal.model.entity.Direccion;


@Service
@Transactional(readOnly = true)
public class DireccionService implements IDireccionService {

	@Autowired
	private IDireccionDAO entityDAO;

	@Transactional(readOnly = false)
	public void addDireccion(Direccion entity) {
		getEntityDAO().addDireccion(entity);
	}

	@Transactional(readOnly = false)
	public void deleteDireccion(Direccion entity) {
		getEntityDAO().deleteDireccion(entity);
	}

	@Transactional(readOnly = false)
	public void updateDireccion(Direccion entity) {
		getEntityDAO().updateDireccion(entity);
	}

	public Direccion getDireccionById(int id) {
		return getEntityDAO().getDireccionById(id);
	}

	public List<Direccion> getDireccions(boolean activo) {	
		return getEntityDAO().getDireccions(activo);
	}
	
	public IDireccionDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IDireccionDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

}
