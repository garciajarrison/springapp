package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;

@Repository
public class UsuarioDAO implements IUsuarioDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Usuario login(Usuario users) {
		Session session = getSessionFactory().getCurrentSession();
		Usuario usuarioTmp = (Usuario)session.createQuery("from Usuario where correo=:correo")
				.setParameter("correo", users.getCorreo())
				.uniqueResult();
		
		return usuarioTmp;
	}
	
	public void addUsuario(Usuario entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteUsuario(Usuario entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updateUsuario(Usuario entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Usuario getUsuarioById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		return (Usuario) session
				.createQuery("from Usuario where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Usuario> getUsuarios() {
		Session session = getSessionFactory().getCurrentSession();
		return session.createQuery("from Usuario").list();
	}
	
	//Usuario por centro de costo
	public void addUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	public void deleteUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		//session.delete(entity);
		session.createSQLQuery("delete from presupuestomd.usuario_x_centrocosto where id = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
	}

	public void updateUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public UsuarioPorCentroCosto getUsuarioPorCentroCostoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		return (UsuarioPorCentroCosto) session
				.createQuery("from UsuarioPorCentroCosto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<UsuarioPorCentroCosto> getUsuarioPorCentroCostos() {
		Session session = getSessionFactory().getCurrentSession();
		return session.createQuery("from UsuarioPorCentroCosto").list();
	}
	
	
}
