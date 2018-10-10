package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.ICuentaDAO;
import com.marketingpersonal.model.entity.Cuenta;


@Service
@Transactional(readOnly = true)
public class CuentaService implements ICuentaService {

	@Autowired
	private ICuentaDAO entityDAO;
	
	public ICuentaDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(ICuentaDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Transactional(readOnly = false)
	public void addCuenta(Cuenta entity) {
		getEntityDAO().addCuenta(entity);
	}

	@Transactional(readOnly = false)
	public void deleteCuenta(Cuenta entity) {
		getEntityDAO().deleteCuenta(entity);
	}

	@Transactional(readOnly = false)
	public void updateCuenta(Cuenta entity) {
		getEntityDAO().updateCuenta(entity);
	}

	public Cuenta getCuentaById(int id) {
		return getEntityDAO().getCuentaById(id);
	}

	public List<Cuenta> getCuentas(boolean activo) {	
		return getEntityDAO().getCuentas(activo);
	}
	
	public List<Cuenta> getCuentaPorCentroCosto(int idCuenta) {	
		return getEntityDAO().getCuentaPorCentroCosto(idCuenta);
	}
	
}
