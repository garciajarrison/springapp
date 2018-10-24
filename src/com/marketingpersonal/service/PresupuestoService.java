package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IPresupuestoDAO;
import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;


@Service
@Transactional(readOnly = true)
public class PresupuestoService implements IPresupuestoService {

	@Autowired
	private IPresupuestoDAO entityDAO;
	
	public IPresupuestoDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IPresupuestoDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

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
	
	//Detalle Mes
	@Transactional(readOnly = false)
	public void addPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		getEntityDAO().addPresupuestoDetalleMes(entity);
	}

	@Transactional(readOnly = false)
	public void deletePresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		getEntityDAO().deletePresupuestoDetalleMes(entity);
	}

	@Transactional(readOnly = false)
	public void updatePresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		getEntityDAO().updatePresupuestoDetalleMes(entity);
	}

	public PresupuestoDetalleMes getPresupuestoDetalleMesById(int id) {
		return getEntityDAO().getPresupuestoDetalleMesById(id);
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMes() {	
		return getEntityDAO().getPresupuestoDetallesMes();
	}
	
	@Transactional(readOnly = false)
	public void actualizarEstadoPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		getEntityDAO().actualizarEstadoPresupuestoDetalleMes(entity);
	}
	
	//Detalle Campania
	@Transactional(readOnly = false)
	public void addPresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		getEntityDAO().addPresupuestoDetalleCampania(entity);
	}

	@Transactional(readOnly = false)
	public void deletePresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		getEntityDAO().deletePresupuestoDetalleCampania(entity);
	}

	@Transactional(readOnly = false)
	public void updatePresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		getEntityDAO().updatePresupuestoDetalleCampania(entity);
	}

	public PresupuestoDetalleCampania getPresupuestoDetalleCampaniaById(int id) {
		return getEntityDAO().getPresupuestoDetalleCampaniaById(id);
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania() {	
		return getEntityDAO().getPresupuestoDetallesCampania();
	}
	
	@Transactional(readOnly = false)
	public void actualizarEstadoPresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		getEntityDAO().actualizarEstadoPresupuestoDetalleCampania(entity);
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMes(int idPresupuesto) {
		return getEntityDAO().getPresupuestoDetallesMes(idPresupuesto);
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania(int idPresupuesto) {
		return getEntityDAO().getPresupuestoDetallesCampania(idPresupuesto);
	}

	@Transactional(readOnly = false)
	public void addObservacion(Observacion observacion) {
		getEntityDAO().addObservacion(observacion);
	}

	public List<Presupuesto> getPresupuestosAprobadorInicial(Usuario usuario) {
		return getEntityDAO().getPresupuestosAprobadorInicial(usuario);
	}

	public List<Presupuesto> getPresupuestosAprobadorFinal(Usuario usuario) {
		return getEntityDAO().getPresupuestosAprobadorFinal(usuario);
	}
	
	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampaniaAprobadorInicial(int idPresupuesto, Usuario usuario){
		return getEntityDAO().getPresupuestoDetallesCampaniaAprobadorInicial(idPresupuesto, usuario);
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMesAprobadorInicial(int idPresupuesto, Usuario usuario){
		return getEntityDAO().getPresupuestoDetallesMesAprobadorInicial(idPresupuesto, usuario);
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampaniaAprobadorFinal(int idPresupuesto, Usuario usuario){
		return getEntityDAO().getPresupuestoDetallesCampaniaAprobadorFinal(idPresupuesto, usuario);
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMesAprobadorFinal(int idPresupuesto, Usuario usuario){
		return getEntityDAO().getPresupuestoDetallesMesAprobadorFinal(idPresupuesto, usuario);
	}
	

}
