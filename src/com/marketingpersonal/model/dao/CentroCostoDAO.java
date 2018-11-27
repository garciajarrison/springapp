package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Usuario;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Centros de Costo
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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

	/**
     * Método que permite almacenar la información de un centro de costo en la base de datos
     * @param entity variable que contiene la información de la entidad Centro de Costo a almacenar
     */
	public void addCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setCentroCosto(entity.getCentroCosto().trim());
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de un centro de costo de la base de datos
     * @param entity variable que contiene la información de la entidad Centro de Costo a eliminar
     */
	public void deleteCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.dbo.centrocosto where id = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
	}

	/**
     * Método que permite actualizar la información de un centro de costo en la base de datos
     * @param entity variable que contiene la información del Centro de costo a actualizar
     */
	public void updateCentroCosto(CentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setCentroCosto(entity.getCentroCosto().trim());
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de un centro de costo a partir del id
     * @param id: variable que contiene el id del centro de costo a consultar
     * @return CentroCosto: variable que contiene la información del Centro de Costo a retornar
     */
	public CentroCosto getCentroCostoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (CentroCosto) session
				.createQuery("from CentroCosto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de los centros de costo almacenados en la base de datos
     * @param activo: variable que indica si se consultaran solo centros de costo activos o todos
     * @return List<CentroCosto>: variable que contiene la información de las centros de costo consultados
     */
	public List<CentroCosto> getCentroCostos(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<CentroCosto>) session.createQuery("from CentroCosto where estado = true").list();
		}else {
			return (List<CentroCosto>) session.createQuery("from CentroCosto").list();
		}
	}
	
	/**
     * Método que permite almacenar la información de un centro de costo por cuenta en la base de datos
     * @param entity variable que contiene la información de la entidad Centro de Costo por Cuenta a almacenar
     */
	public void addCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de un centro de costo por cuenta de la base de datos
     * @param entity variable que contiene la información de la entidad Centro de Costo por cuenta a eliminar
     */
	public void deleteCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.dbo.cuenta_x_centrocosto where id = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
	}

	/**
     * Método que permite actualizar la información de un centro de costo por cuenta en la base de datos
     * @param entity variable que contiene la información del Centro de costo por cuenta a actualizar
     */
	public void updateCentroCostoPorCuenta(CentroCostoPorCuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de un centro de costo por cuenta a partir del id
     * @param id: variable que contiene el id del centro de costo por cuenta a consultar
     * @return CentroCostoPorCuenta: variable que contiene la información del Centro de Costo por cuenta a retornar
     */
	public CentroCostoPorCuenta getCentroCostoPorCuentaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (CentroCostoPorCuenta) session
				.createQuery("from CentroCostoPorCuenta where id=?").setParameter(0, id)
				.uniqueResult();
	}
	
	/**
     * Método que permite consultar la información de los centros de costo por cuentas almacenados en la base de datos
     * @return List<CentroCostoPorCuenta>: variable que contiene la información de las centros de costo por cuenta consultados
     */
	public List<CentroCostoPorCuenta> getCentroCostoPorCuentas() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<CentroCostoPorCuenta>) session.createQuery("from CentroCostoPorCuenta").list();
	}
	
	/**
     * Método que permite consultar la información de los centros de costo por usuario almacenados en la base de datos
     * @param idUsuario: variable que contiene el id del usuario responsable del centro de costo
     * @return List<CentroCosto>: variable que contiene la información de las centros de costo asociados a un usuario responsable
     */
	public List<CentroCosto> getCentroCostoPorUsuario(int idUsuario) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<CentroCosto>) session.createQuery("select c from UsuarioPorCentroCosto as u, "
					+ "CentroCosto as c where u.centroCosto.id = c.id and u.usuarioResponsable.id = :id and c.estado = true")
					.setParameter("id", idUsuario).list();
	}
	
	/**
     * Método que permite consultar la información de los usuarios que son aprobadores iniciales de un centro de costo 
     * @param idCentroCosto: variable que contiene el id del centro de costo
     * @return List<Usuario>: variable que contiene la información de los usuarios aprobadores iniciales del centros de costo consultado
     */
	public List<Usuario> getUsuarioAprobadorInicial(int idCentroCosto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Usuario>) session.createQuery("select u from UsuarioPorCentroCosto as c, "
					+ " Usuario as u where u.id = c.usuarioAprobadorInicial.id and c.centroCosto.id  = :id")
					.setParameter("id", idCentroCosto).list();
	}

	/**
     * Método que permite consultar la información de los usuarios que son aprobadores finales de un centro de costo 
     * @param idCentroCosto: variable que contiene el id del centro de costo
     * @return List<Usuario>: variable que contiene la información de los usuarios aprobadores finales del centros de costo consultado
     */
	public List<Usuario> getUsuarioAprobadorFinal(int idCentroCosto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Usuario>) session.createQuery("select u from UsuarioPorCentroCosto as c, "
					+ " Usuario as u where u.id = c.usuarioAprobadorFinal.id and c.centroCosto.id  = :id")
					.setParameter("id", idCentroCosto).list();
	}

	/**
     * Método que permite consultar la información de los centros de costo por cuenta 
     * @param idCuenta: variable que contiene el id de la cuenta
     * @param idUsuario: variable que contiene el id del usuario
     * @return List<CentroCosto>: variable que contiene la información de los centros de costo asociados a la cuenta y al usuario responsable
     */
	public List<CentroCosto> getCentroCostosPorCuenta(int idCuenta, int idUsuario) {
		StringBuilder sql = new StringBuilder()
				.append("select cc from CentroCostoPorCuenta as ccc, ")
				.append(" CentroCosto as cc, UsuarioPorCentroCosto as ucc ")
				.append(" where ccc.cuenta.id = :idCuenta ")
				.append(" and ccc.centroCosto.id = cc.id ")
				.append(" and ccc.centroCosto.id = ucc.centroCosto.id ")
				.append(" and ucc.usuarioResponsable.id = :idUsuario");
				
		Session session = getSessionFactory().getCurrentSession();
		return (List<CentroCosto>) session.createQuery(sql.toString())
					.setParameter("idUsuario", idUsuario)
					.setParameter("idCuenta", idCuenta)
					.list();
	}
	
}
