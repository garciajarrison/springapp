package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;

@Repository
public class CentroCostoDAO implements ICentroCostoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public CentroCosto getCentroCostoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (CentroCosto) session
				.createQuery("from CentroCosto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<CentroCosto> getCentroCostos(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<CentroCosto>) session.createQuery("from CentroCosto where estado = true").list();
		}else {
			return (List<CentroCosto>) session.createQuery("from CentroCosto").list();
		}
	}
	
	//Centro costo por cuenta
	public void addCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public CentroCostoPorCuenta getCentroCostoPorCuentaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (CentroCostoPorCuenta) session
				.createQuery("from CentroCostoPorCuenta where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<CentroCostoPorCuenta> getCentroCostoPorCuentas() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<CentroCostoPorCuenta>) session.createQuery("from CentroCostoPorCuenta").list();
	}

}
