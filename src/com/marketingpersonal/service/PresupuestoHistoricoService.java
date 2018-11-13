package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IPresupuestoHistoricoDAO;
import com.marketingpersonal.model.entity.PresupuestoHistorico;


@Service
@Transactional(readOnly = true)
public class PresupuestoHistoricoService implements IPresupuestoHistoricoService {

	@Autowired
	private IPresupuestoHistoricoDAO entityDAO;
	
	public IPresupuestoHistoricoDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IPresupuestoHistoricoDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Transactional(readOnly = false)
	public void addPresupuestoHistorico(PresupuestoHistorico entity) {
		getEntityDAO().addPresupuestoHistorico(entity);
	}

	@Transactional(readOnly = false)
	public void deletePresupuestoHistorico(PresupuestoHistorico entity) {
		getEntityDAO().deletePresupuestoHistorico(entity);
	}
	
	public void deletePresupuestoHistoricoPorAnio(int anio) {
		getEntityDAO().deletePresupuestoHistoricoPorAnio(anio);
	}

	public PresupuestoHistorico getPresupuestoHistoricoById(int id) {
		return getEntityDAO().getPresupuestoHistoricoById(id);
	}

	public List<PresupuestoHistorico> getPresupuestosHistoricos() {	
		return getEntityDAO().getPresupuestosHistoricos();
	}
	
	public List<PresupuestoHistorico> getPresupuestosHistoricosPorAnio(Integer anioConsulta) {
		return getEntityDAO().getPresupuestoHistoricoPorAnio(anioConsulta);
	}
	
	public List<PresupuestoHistorico> getPresupuestosHistoricosPorUsuario(Integer idUsuario) {
		return getEntityDAO().getPresupuestoHistoricoPorUsuario(idUsuario);
	}	

}
