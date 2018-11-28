package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Gerencia;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Gerencias
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@Repository
public class GerenciaDAO implements IGerenciaDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
     * Método que permite almacenar la información de una Gerencia en la base de datos
     * @param entity variable que contiene la información de la entidad Gerencia a almacenar
     */
	public void addGerencia(Gerencia entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre()).trim());
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de una Gerencia de la base de datos
     * @param entity variable que contiene la información de la entidad Gerencia a eliminar
     */
	public void deleteGerencia(Gerencia entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de una Gerencia en la base de datos
     * @param entity variable que contiene la información de la Gerencia a actualizar
     */
	public void updateGerencia(Gerencia entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre()).trim());
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de una Gerencia a partir del id
     * @param id: variable que contiene el id de la Gerencia a consultar
     * @return Gerencia: variable que contiene la información de la Gerencia a retornar
     */
	public Gerencia getGerenciaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Gerencia) session
				.createQuery("from Gerencia where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de las Gerencias almacenadas en la base de datos
     * @param activo: variable que indica si se consultaran solo Gerencias activas o todos
     * @return List<Gerencia>: variable que contiene la información de las Gerencias consultadas
     */
	public List<Gerencia> getGerencias(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Gerencia>) session.createQuery("from Gerencia where estado = true").list();
		}else {
			return (List<Gerencia>) session.createQuery("from Gerencia").list();
		}
	}

}
