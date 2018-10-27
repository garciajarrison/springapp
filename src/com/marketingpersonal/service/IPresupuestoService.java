package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;


public interface IPresupuestoService {
	
	void addPresupuesto(Presupuesto entity);

	void updatePresupuesto(Presupuesto entity);
	
	void deletePresupuesto(Presupuesto entity);
	
	Presupuesto getPresupuestoById(int id);
	
	List<Presupuesto> getPresupuestos();
	
	//Detalle Mes
	void addPresupuestoDetalleMes(PresupuestoDetalleMes entity);

	void updatePresupuestoDetalleMes(PresupuestoDetalleMes entity);
	
	void deletePresupuestoDetalleMes(PresupuestoDetalleMes entity);
	
	PresupuestoDetalleMes getPresupuestoDetalleMesById(int id);
	
	List<PresupuestoDetalleMes> getPresupuestoDetallesMes();

	void actualizarEstadoPresupuestoDetalleMes(PresupuestoDetalleMes selectedPresupuesto);
	
	//Detalle Campania
	void addPresupuestoDetalleCampania(PresupuestoDetalleCampania entity);

	void updatePresupuestoDetalleCampania(PresupuestoDetalleCampania entity);
	
	void deletePresupuestoDetalleCampania(PresupuestoDetalleCampania entity);
	
	PresupuestoDetalleCampania getPresupuestoDetalleCampaniaById(int id);
	
	List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania();

	void actualizarEstadoPresupuestoDetalleCampania(PresupuestoDetalleCampania selectedPresupuesto);

	List<PresupuestoDetalleMes> getPresupuestoDetallesMes(int idPresupuesto);

	List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania(int idPresupuesto);

	void addObservacion(Observacion observacion);

	List<Presupuesto> getPresupuestosAprobadorInicial(Usuario usuario);
	
	List<Presupuesto> getPresupuestosAprobadorFinal(Usuario usuario);
	

	List<PresupuestoDetalleCampania> getPresupuestoDetallesCampaniaAprobadorInicial(int idPresupuesto, Usuario usuario);

	List<PresupuestoDetalleMes> getPresupuestoDetallesMesAprobadorInicial(int idPresupuesto, Usuario usuario);

	List<PresupuestoDetalleCampania> getPresupuestoDetallesCampaniaAprobadorFinal(int idPresupuesto, Usuario usuario);

	List<PresupuestoDetalleMes> getPresupuestoDetallesMesAprobadorFinal(int ididPresupuesto, Usuario usuario);

	List<Presupuesto> getPresupuestos(int idUsuario);
	
}
