package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Calculadora;

@Repository
public class CalculadoraDAO implements ICalculadoraDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addCalculadora(Calculadora entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteCalculadora(Calculadora entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateCalculadora(Calculadora entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Calculadora getCalculadoraById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Calculadora) session
				.createQuery("from Calculadora where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Calculadora> getCalculadoras(String tipo) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Calculadora>) session.createQuery("from Calculadora where tipo = :tipo")
				.setParameter("tipo", tipo).list();
	}

}
