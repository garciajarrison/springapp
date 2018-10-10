package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalle;


public interface IPresupuestoDAO {
	
	void addPresupuesto(Presupuesto entity);

	void updatePresupuesto(Presupuesto entity);
	
	void deletePresupuesto(Presupuesto entity);
	
	Presupuesto getPresupuestoById(int id);

	List<Presupuesto> getPresupuestos();
	
	//Detalle
	void addPresupuestoDetalle(PresupuestoDetalle entity);

	void updatePresupuestoDetalle(PresupuestoDetalle entity);
	
	void deletePresupuestoDetalle(PresupuestoDetalle entity);
	
	PresupuestoDetalle getPresupuestoDetalleById(int id);

	List<PresupuestoDetalle> getPresupuestoDetalles();

	void addObservacion(Observacion entity);

	void actualizarEstadoPresupuesto(Presupuesto entity);

}
