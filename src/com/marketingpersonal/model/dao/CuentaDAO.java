package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Cuenta;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Cuentas
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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

	/**
     * Método que permite almacenar la información de una cuenta en la base de datos
     * @param entity variable que contiene la información de la entidad Cuenta a almacenar
     */
	public void addCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setCuenta(entity.getCuenta().trim());
		entity.setNombre(entity.getNombre().toUpperCase().trim());
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de una cuenta de la base de datos
     * @param entity variable que contiene la información de la entidad Cuenta a eliminar
     */
	public void deleteCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de una Cuenta en la base de datos
     * @param entity variable que contiene la información de la Cuenta a actualizar
     */
	public void updateCuenta(Cuenta entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setCuenta(entity.getCuenta().trim());
		entity.setNombre(entity.getNombre().toUpperCase().trim());
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de una Cuenta a partir del id
     * @param id: variable que contiene el id de la Cuenta a consultar
     * @return Cuenta: variable que contiene la información de la Cuenta a retornar
     */
	public Cuenta getCuentaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Cuenta) session
				.createQuery("from Cuenta where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de las Cuentas almacenadas en la base de datos
     * @param activo: variable que indica si se consultaran solo Cuentas activas o todos
     * @return List<Cuenta>: variable que contiene la información de las Cuentas consultadas
     */
	public List<Cuenta> getCuentas(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Cuenta>) session.createQuery("from Cuenta where estado = true").list();
		}else {
			return (List<Cuenta>) session.createQuery("from Cuenta").list();
		}
	}

	/**
     * Método que permite consultar la información de las Cuentas por Centro de Costo almacenados en la base de datos
     * @param idCentroCosto: variable que contiene el id del centro de costo
     * @return List<Cuenta>: variable que contiene la información de las Cuentas
     */
	public List<Cuenta> getCuentaPorCentroCosto(int idCentroCosto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Cuenta>) session.createQuery("select c from CentroCostoPorCuenta as u, "
					+ "Cuenta as c where u.cuenta.id = c.id and u.centroCosto.id = :id")
					.setParameter("id", idCentroCosto).list();
	}
	
	/**
     * Método que permite consultar la información de las Cuentas por Usuario almacenados en la base de datos
     * @param idUsuario: variable que contiene el id del usuario
     * @return List<Cuenta>: variable que contiene la información de las Cuentas
     */
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
