package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Home;

@Repository
public class HomeDAO implements IHomeDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addHome(Home entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteHome(Home entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateHome(Home entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Home getHomeById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Home) session
				.createQuery("from Home where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Home> getHomes() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Home>) session.createQuery("from Home").list();
	}

}
