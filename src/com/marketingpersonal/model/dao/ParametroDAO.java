package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Parametro;

@Repository
public class ParametroDAO implements IParametroDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addParametro(Parametro entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteParametro(Parametro entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateParametro(Parametro entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Parametro getParametroById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Parametro) session
				.createQuery("from Parametro where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Parametro> getParametros() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Parametro>) session.createQuery("from Parametro").list();
	}

	public Parametro getParametroByCodigo(String codigoParametro) {
		Session session = getSessionFactory().getCurrentSession();
		return (Parametro) session.createQuery("from Parametro where codigo = :codigo")
				.setParameter("codigo", codigoParametro)
				.uniqueResult();
	}

}
