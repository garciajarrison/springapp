package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.PresupuestoHistorico;
import com.marketingpersonal.model.entity.Usuario;

@Repository
public class PresupuestoHistoricoDAO implements IPresupuestoHistoricoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addPresupuestoHistorico(PresupuestoHistorico entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}
	
	public void deletePresupuestoHistorico(PresupuestoHistorico entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}	
	
	public PresupuestoHistorico getPresupuestoHistoricoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoHistorico) session
				.createQuery("from PresupuestoHistorico where id=?").setParameter(0, id)
				.uniqueResult();
	}
	
	public List<PresupuestoHistorico> getPresupuestosHistoricos() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session.createQuery("from PresupuestoHistorico").list();
	}	
	
	
	public List<PresupuestoHistorico> getPresupuestoHistoricoPorAnio(Integer anioConsulta) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session
				.createQuery("from PresupuestoHistorico where anio = :anioConsulta").setParameter("anioConsulta", anioConsulta)
				.list();
	}
	
	public List<PresupuestoHistorico> getPresupuestoHistoricoPorUsuario(Integer idUsuario) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session
				.createQuery("from PresupuestoHistorico")
				.list();
	}

	public void deletePresupuestoHistoricoPorAnio(int anio) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.dbo.presupuesto_historico where anio = :anio")
				.setParameter("anio", anio).executeUpdate();
		
		session.flush();
	}
	
	



}
