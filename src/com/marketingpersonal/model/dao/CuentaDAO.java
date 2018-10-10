package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Cuenta;

@Repository
public class CuentaDAO implements ICuentaDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Cuenta getCuentaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Cuenta) session
				.createQuery("from Cuenta where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Cuenta> getCuentas(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Cuenta>) session.createQuery("from Cuenta where estado = true").list();
		}else {
			return (List<Cuenta>) session.createQuery("from Cuenta").list();
		}
	}

	public List<Cuenta> getCuentaPorCentroCosto(int idCentroCosto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Cuenta>) session.createQuery("select c from CentroCostoPorCuenta as u, "
					+ "Cuenta as c where u.cuenta.id = c.id and u.centroCosto.id = :id")
					.setParameter("id", idCentroCosto).list();
	}
	
}
