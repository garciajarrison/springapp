package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.PresupuestoHistorico;


public interface IPresupuestoHistoricoDAO {
	
	void addPresupuestoHistorico(PresupuestoHistorico entity);
	
	void deletePresupuestoHistorico(PresupuestoHistorico entity);
	
	PresupuestoHistorico getPresupuestoHistoricoById(int id);

	List<PresupuestoHistorico> getPresupuestosHistoricos();
	
	List<PresupuestoHistorico> getPresupuestoHistoricoPorAnio(Integer anioConsulta);
	
	List<PresupuestoHistorico> getPresupuestoHistoricoPorUsuario(Integer idUsuario);

	void deletePresupuestoHistoricoPorAnio(int anio);
	
}
