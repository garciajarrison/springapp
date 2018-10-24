package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
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
		entity.setCuenta(entity.getCuenta().trim());
		entity.setNombre(entity.getNombre().toUpperCase().trim());
		session.save(entity);
	}

	public void deleteCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setCuenta(entity.getCuenta().trim());
		entity.setNombre(entity.getNombre().toUpperCase().trim());
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
	
	public List<Cuenta> getCuentasPorUsuario(int idUsuario) {
		
		StringBuilder sql = new StringBuilder()
				.append("select distinct c from CentroCostoPorCuenta as cc, ")
				.append(" Cuenta as c, UsuarioPorCentroCosto as ucc ")
				.append(" where cc.cuenta.id = c.id ")
				.append(" and cc.centroCosto.id = ucc.centroCosto.id ")
				.append(" and ucc.usuarioResponsable.id = :id");
				
		Session session = getSessionFactory().getCurrentSession();
		return (List<Cuenta>) session.createQuery(sql.toString())
					.setParameter("id", idUsuario).list();
	}
}
