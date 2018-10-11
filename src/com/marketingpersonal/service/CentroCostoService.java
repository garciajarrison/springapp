package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.ICentroCostoDAO;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Usuario;


@Service
@Transactional(readOnly = true)
public class CentroCostoService implements ICentroCostoService {

	@Autowired
	private ICentroCostoDAO entityDAO;
	
	public ICentroCostoDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(ICentroCostoDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Transactional(readOnly = false)
	public void addCentroCosto(CentroCosto entity) {
		getEntityDAO().addCentroCosto(entity);
	}

	@Transactional(readOnly = false)
	public void deleteCentroCosto(CentroCosto entity) {
		getEntityDAO().deleteCentroCosto(entity);
	}

	@Transactional(readOnly = false)
	public void updateCentroCosto(CentroCosto entity) {
		getEntityDAO().updateCentroCosto(entity);
	}

	public CentroCosto getCentroCostoById(int id) {
		return getEntityDAO().getCentroCostoById(id);
	}

	public List<CentroCosto> getCentroCostos(boolean activo) {	
		return getEntityDAO().getCentroCostos(activo);
	}
	
	//Centro costo por cuenta
	@Transactional(readOnly = false)
	public void addCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		getEntityDAO().addCentroCostoPorCuenta(entity);
	}

	@Transactional(readOnly = false)
	public void deleteCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		getEntityDAO().deleteCentroCostoPorCuenta(entity);
	}

	@Transactional(readOnly = false)
	public void updateCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		getEntityDAO().updateCentroCostoPorCuenta(entity);
	}

	public CentroCostoPorCuenta getCentroCostoPorCuentaById(int id) {
		return getEntityDAO().getCentroCostoPorCuentaById(id);
	}

	public List<CentroCostoPorCuenta> getCentroCostoPorCuentas() {	
		return getEntityDAO().getCentroCostoPorCuentas();
	}
	
	public List<CentroCosto> getCentroCostoPorUsuario(int idUsuario) {	
		return getEntityDAO().getCentroCostoPorUsuario(idUsuario);
	}

	public Usuario getUsuarioAprobadorInicial(int idCentroCosto) {
		return getEntityDAO().getUsuarioAprobadorInicial(idCentroCosto);
	}

	public Usuario getUsuarioAprobadorFinal(int idCentroCosto) {
		return getEntityDAO().getUsuarioAprobadorFinal(idCentroCosto);
	}

}
