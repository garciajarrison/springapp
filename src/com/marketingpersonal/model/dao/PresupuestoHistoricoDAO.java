package com.marketingpersonal.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.PresupuestoHistorico;
import com.marketingpersonal.model.entity.Usuario;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Presupuesto Histórico
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@Repository
public class PresupuestoHistoricoDAO implements IPresupuestoHistoricoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
     * Método que permite almacenar la información de un Presupuesto Histórico en la base de datos
     * @param entity variable que contiene la información de la entidad PresupuestoHistorico a almacenar
     */
	public void addPresupuestoHistorico(PresupuestoHistorico entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}
	
	/**
     * Método que permite eliminar la información de un Presupuesto Histórico de la base de datos
     * @param entity variable que contiene la información de la entidad PresupuestoHistorico a eliminar
     */
	public void deletePresupuestoHistorico(PresupuestoHistorico entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}	
	
	/**
     * Método que permite consultar la información de un Presupuesto Historico a partir del id
     * @param id: variable que contiene el id del Presupuesto Historico a consultar
     * @return PresupuestoHistorico: variable que contiene la información del Presupuesto Historico a retornar
     */
	public PresupuestoHistorico getPresupuestoHistoricoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoHistorico) session
				.createQuery("from PresupuestoHistorico where id=?").setParameter(0, id)
				.uniqueResult();
	}
	
	/**
     * Método que permite consultar la información de los Presupuestos Historicos almacenadas en la base de datos
     * @return List<PresupuestoHistorico>: variable que contiene la información de los Presupuestos Historicos consultados
     */
	public List<PresupuestoHistorico> getPresupuestosHistoricos() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session.createQuery("from PresupuestoHistorico").list();
	}	
	
	/**
     * Método que permite consultar la información de los Presupuestos Historicos por año almacenadas en la base de datos
     * @param anioConsulta: variable que contiene el año del Presupuesto Historico a consultar
     * @return List<PresupuestoHistorico>: variable que contiene la información de los Presupuestos Historicos consultados
     */
	public List<PresupuestoHistorico> getPresupuestoHistoricoPorAnio(Integer anioConsulta) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session
				.createQuery("from PresupuestoHistorico where anio = :anioConsulta").setParameter("anioConsulta", anioConsulta)
				.list();
	}
	
	/**
     * Método que permite consultar la información de los Presupuestos Historicos por Usuario almacenadas en la base de datos
     * @param idUsuario: variable que contiene el id del usuario asociado al Presupuesto Historico a consultar
     * @return List<PresupuestoHistorico>: variable que contiene la información de los Presupuestos Historicos consultados
     */
	public List<PresupuestoHistorico> getPresupuestoHistoricoPorUsuario(Integer idUsuario) {
		
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select ph                                         ")
				.append(" from PresupuestoHistorico ph,			    ")
				.append("	   UsuarioPorCentroCosto ucc             	    ")
				.append(" where ph.centroCosto.id = ucc.centroCosto.id     ")
				.append("	and ucc.usuarioResponsable.id = :idUsuario ");
		
		return (List<PresupuestoHistorico>) session.createQuery(query.toString())
				.setParameter("idUsuario", idUsuario).list();
	}

	/**
     * Método que permite eliminar la información de un Presupuesto Histórico de la base de datos
     * @param anio: variable que contiene el año del Presupuesto Histórico a eliminar
     */
	public void deletePresupuestoHistoricoPorAnio(int anio) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.dbo.presupuesto_historico where anio = :anio")
				.setParameter("anio", anio).executeUpdate();
		
		session.flush();
	}
	
	/**
     * Método que permite consultar el listado de años para los cuales existen presupuestos historicos creados
      * @return List<SelectItem>: variable que contiene la lista de años
     */
	public List<SelectItem> getListaAnios() {
		List<SelectItem> retorno = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		List<Integer> listado = (List<Integer>)session.createSQLQuery("select distinct anio from presupuestomd.dbo.presupuesto_historico").list();
		if(listado != null && !listado.isEmpty()) {
			for(Integer tmp: listado) {
				retorno.add(new SelectItem(String.valueOf(tmp), String.valueOf(tmp)));
			}
		}
		
		return retorno;
	}

}
