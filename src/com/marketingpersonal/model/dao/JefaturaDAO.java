package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Jefatura;

@Repository
public class JefaturaDAO implements IJefaturaDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addJefatura(Jefatura entity) {
		Session session = getSessionFactory().getCurrentSession();
		
		session.save(entity);
	}

	public void deleteJefatura(Jefatura entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateJefatura(Jefatura entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Jefatura getJefaturaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Jefatura) session
				.createQuery("from Jefatura where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Jefatura> getJefaturas(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Jefatura>) session.createQuery("from Jefatura where estado = true").list();
		}else {
			return (List<Jefatura>) session.createQuery("from Jefatura").list();
		}
	}

}
