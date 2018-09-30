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

	public List<Cuenta> getCuentas() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Cuenta>) session.createQuery("from Cuenta").list();
	}

}
