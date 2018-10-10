package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Usuario;

@Repository
public class CentroCostoDAO implements ICentroCostoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.centrocosto where id = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
	}

	public void updateCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public CentroCosto getCentroCostoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (CentroCosto) session
				.createQuery("from CentroCosto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<CentroCosto> getCentroCostos(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<CentroCosto>) session.createQuery("from CentroCosto where estado = true").list();
		}else {
			return (List<CentroCosto>) session.createQuery("from CentroCosto").list();
		}
	}
	
	//Centro costo por cuenta
	public void addCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.cuenta_x_centrocosto where id = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
	}

	public void updateCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public CentroCostoPorCuenta getCentroCostoPorCuentaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (CentroCostoPorCuenta) session
				.createQuery("from CentroCostoPorCuenta where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<CentroCostoPorCuenta> getCentroCostoPorCuentas() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<CentroCostoPorCuenta>) session.createQuery("from CentroCostoPorCuenta").list();
	}
	
	public List<CentroCosto> getCentroCostoPorUsuario(int idUsuario) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<CentroCosto>) session.createQuery("select c from UsuarioPorCentroCosto as u, "
					+ "CentroCosto as c where u.centroCosto.id = c.id and u.usuarioResponsable.id = :id")
					.setParameter("id", idUsuario).list();
	}
	
	public Usuario getUsuarioAprobadorInicial(int idCentroCosto) {
		Session session = getSessionFactory().getCurrentSession();
		return (Usuario) session.createQuery("select u from UsuarioPorCentroCosto as c, "
					+ "usuario as u where = u.id = c.usuarioAprobadorInicial.id and c.centroCosto.id  = :id")
					.setParameter("id", idCentroCosto).uniqueResult();
	}

	public Usuario getUsuarioAprobadorFinal(int idCentroCosto) {
		Session session = getSessionFactory().getCurrentSession();
		return (Usuario) session.createQuery("select u from UsuarioPorCentroCosto as c, "
					+ "usuario as u where = u.id = c.usuarioAprobadorFinal.id and c.centroCosto.id  = :id")
					.setParameter("id", idCentroCosto).uniqueResult();
	}

}
