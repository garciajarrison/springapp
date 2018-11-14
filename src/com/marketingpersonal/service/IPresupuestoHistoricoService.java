package com.marketingpersonal.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.marketingpersonal.model.entity.PresupuestoHistorico;


public interface IPresupuestoHistoricoService {
	
	void addPresupuestoHistorico(PresupuestoHistorico entity);
	
	void deletePresupuestoHistorico(PresupuestoHistorico entity);
	
	void deletePresupuestoHistoricoPorAnio(int anio);
	
	PresupuestoHistorico getPresupuestoHistoricoById(int id);
	
	List<PresupuestoHistorico> getPresupuestosHistoricos();

	List<PresupuestoHistorico> getPresupuestosHistoricosPorAnio(Integer anioConsulta);
	
	List<PresupuestoHistorico> getPresupuestosHistoricosPorUsuario(Integer idUsuario);
	
	List<SelectItem> getListaAnios();
}
