package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalle;

@Repository
public class PresupuestoDAO implements IPresupuestoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addPresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deletePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updatePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Presupuesto getPresupuestoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Presupuesto) session
				.createQuery("from Presupuesto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Presupuesto> getPresupuestos() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session.createQuery("from Presupuesto").list();
	}
	
	//Detalle
	public void addPresupuestoDetalle(PresupuestoDetalle entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public void deletePresupuestoDetalle(PresupuestoDetalle entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updatePresupuestoDetalle(PresupuestoDetalle entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public PresupuestoDetalle getPresupuestoDetalleById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoDetalle) session
				.createQuery("from PresupuestoDetalle where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<PresupuestoDetalle> getPresupuestoDetalles() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalle>) session.createQuery("from PresupuestoDetalle").list();
	}

	public void addObservacion(Observacion entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

}
