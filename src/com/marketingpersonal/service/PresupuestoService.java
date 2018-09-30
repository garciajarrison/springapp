package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IPresupuestoDAO;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalle;


@Service
@Transactional(readOnly = true)
public class PresupuestoService implements IPresupuestoService {

	@Autowired
	private IPresupuestoDAO entityDAO;

	@Transactional(readOnly = false)
	public void addPresupuesto(Presupuesto entity) {
		getEntityDAO().addPresupuesto(entity);
	}

	@Transactional(readOnly = false)
	public void deletePresupuesto(Presupuesto entity) {
		getEntityDAO().deletePresupuesto(entity);
	}

	@Transactional(readOnly = false)
	public void updatePresupuesto(Presupuesto entity) {
		getEntityDAO().updatePresupuesto(entity);
	}

	public Presupuesto getPresupuestoById(int id) {
		return getEntityDAO().getPresupuestoById(id);
	}

	public List<Presupuesto> getPresupuestos() {	
		return getEntityDAO().getPresupuestos();
	}
	
	//Detalle
	@Transactional(readOnly = false)
	public void addPresupuestoDetalle(PresupuestoDetalle entity) {
		getEntityDAO().addPresupuestoDetalle(entity);
	}

	@Transactional(readOnly = false)
	public void deletePresupuestoDetalle(PresupuestoDetalle entity) {
		getEntityDAO().deletePresupuestoDetalle(entity);
	}

	@Transactional(readOnly = false)
	public void updatePresupuestoDetalle(PresupuestoDetalle entity) {
		getEntityDAO().updatePresupuestoDetalle(entity);
	}

	public PresupuestoDetalle getPresupuestoDetalleById(int id) {
		return getEntityDAO().getPresupuestoDetalleById(id);
	}

	public List<PresupuestoDetalle> getPresupuestoDetalles() {	
		return getEntityDAO().getPresupuestoDetalles();
	}
	
	public IPresupuestoDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IPresupuestoDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

}
