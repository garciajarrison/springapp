package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Sublink;

@Repository
public class SublinkDAO implements ISublinkDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addSublink(Sublink entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteSublink(Sublink entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateSublink(Sublink entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Sublink getSublinkById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Sublink) session
				.createQuery("from Sublink where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Sublink> getSublinks(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Sublink>) session.createQuery("from Sublink where estado = true").list();
		}else {
			return (List<Sublink>) session.createQuery("from Sublink").list();
		}
	}

}
