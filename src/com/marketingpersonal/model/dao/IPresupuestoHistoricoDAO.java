package com.marketingpersonal.model.dao;

import java.util.List;

import javax.faces.model.SelectItem;

import com.marketingpersonal.model.entity.PresupuestoHistorico;

/**
 * Interface que contiene los metodos implementados en la clase PreuspuestoHistoricoDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface IPresupuestoHistoricoDAO {
	
	void addPresupuestoHistorico(PresupuestoHistorico entity);
	
	void deletePresupuestoHistorico(PresupuestoHistorico entity);
	
	PresupuestoHistorico getPresupuestoHistoricoById(int id);

	List<PresupuestoHistorico> getPresupuestosHistoricos();
	
	List<PresupuestoHistorico> getPresupuestoHistoricoPorAnio(Integer anioConsulta);
	
	List<PresupuestoHistorico> getPresupuestoHistoricoPorUsuario(Integer idUsuario);

	void deletePresupuestoHistoricoPorAnio(int anio);
	
	List<SelectItem> getListaAnios();
	
}
