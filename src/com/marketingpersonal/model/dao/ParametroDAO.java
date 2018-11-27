package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Parametro;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Parametros
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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

	/**
     * Método que permite almacenar la información de un Parametro en la base de datos
     * @param entity variable que contiene la información de la entidad Parametro a almacenar
     */
	public void addParametro(Parametro entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de un Parametro de la base de datos
     * @param entity variable que contiene la información de la entidad Parametro a eliminar
     */
	public void deleteParametro(Parametro entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de un Parametro en la base de datos
     * @param entity variable que contiene la información de la entidad Parametro a actualizar
     */
	public void updateParametro(Parametro entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de un Parametro a partir del id
     * @param id: variable que contiene el id del Parametro a consultar
     * @return Parametro: variable que contiene la información del Parametro a retornar
     */
	public Parametro getParametroById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Parametro) session
				.createQuery("from Parametro where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información del los Parametros almacenadss en la base de datos
     * @return List<Parametro>: variable que contiene la información de los Parametros consultados
     */
	public List<Parametro> getParametros() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Parametro>) session.createQuery("from Parametro").list();
	}

	/**
     * Método que permite consultar la información de un Parametro a partir del codigo
     * @param codigoParametro: variable que contiene el codigo del Parametro a consultar
     * @return Parametro: variable que contiene la información del Parametro a retornar
     */
	public Parametro getParametroByCodigo(String codigoParametro) {
		Session session = getSessionFactory().getCurrentSession();
		return (Parametro) session.createQuery("from Parametro where codigo = :codigo")
				.setParameter("codigo", codigoParametro)
				.uniqueResult();
	}

}
