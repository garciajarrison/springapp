package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Gerencia;

@Repository
public class GerenciaDAO implements IGerenciaDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addGerencia(Gerencia entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteGerencia(Gerencia entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateGerencia(Gerencia entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Gerencia getGerenciaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Gerencia) session
				.createQuery("from Gerencia where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Gerencia> getGerencias() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Gerencia>) session.createQuery("from Gerencia").list();
	}

}
