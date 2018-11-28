package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Direccion;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Direcciones
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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

	/**
     * Método que permite almacenar la información de una Direccion en la base de datos
     * @param entity variable que contiene la información de la entidad Direccion a almacenar
     */
	public void addDireccion(Direccion entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre()).trim());
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de una Direccion de la base de datos
     * @param entity variable que contiene la información de la entidad Direccion a eliminar
     */
	public void deleteDireccion(Direccion entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de una Direccion en la base de datos
     * @param entity variable que contiene la información de la Direccion a actualizar
     */
	public void updateDireccion(Direccion entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre()).trim());
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de una Direccion a partir del id
     * @param id: variable que contiene el id de la Direccion a consultar
     * @return Direccion: variable que contiene la información de la Direccion a retornar
     */
	public Direccion getDireccionById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Direccion) session
				.createQuery("from Direccion where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de las Direcciones almacenadas en la base de datos
     * @param activo: variable que indica si se consultaran solo Direcciones activas o todos
     * @return List<Direccion>: variable que contiene la información de las Direcciones consultadas
     */
	public List<Direccion> getDireccions(boolean activo) {
		Session session = getSessionFactory().getCurrentSession();
		if(activo) {
			return (List<Direccion>) session.createQuery("from Direccion where estado = true").list();
		}else {
			return (List<Direccion>) session.createQuery("from Direccion").list();
		}
	}

}
