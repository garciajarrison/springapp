package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Direccion;

@Repository
public class DireccionDAO implements IDireccionDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addDireccion(Direccion entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre()).trim());
		session.save(entity);
	}

	public void deleteDireccion(Direccion entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateDireccion(Direccion entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre()).trim());
		session.update(entity);
	}

	public Direccion getDireccionById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Direccion) session
				.createQuery("from Direccion where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Direccion> getDireccions(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Direccion>) session.createQuery("from Direccion where estado = true").list();
		}else {
			return (List<Direccion>) session.createQuery("from Direccion").list();
		}
	}

}
