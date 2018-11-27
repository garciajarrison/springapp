package com.marketingpersonal.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Calculadora;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para la Calculadora
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */

@Repository
public class CalculadoraDAO implements ICalculadoraDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	/**
     * Método que permite almacenar la información de la calculadora en la base de datos
     * @param entity variable que contiene la información de la entidad Calculadora
     */
	public void addCalculadora(Calculadora entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de la calculadora de la base de datos
     * @param entity variable que contiene la información de la entidad Calculadora
     */
	public void deleteCalculadora(Calculadora entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de la calculadora en la base de datos
     * @param entity variable que contiene la información de la entidad Calculadora
     */
	public void updateCalculadora(Calculadora entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de la calculadora a partir del id
     * @param id: variable que contiene el id de la calculadora a consultar
     * @return Calculadora: variable que contiene la información de la calculadora consultada
     */
	public Calculadora getCalculadoraById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Calculadora) session
				.createQuery("from Calculadora where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de las calculadoras a partir del tipo y al año
     * @param tipo: variable que contiene el tipo de la calculadora a consultar
     * @param anio : variable que contiene el anio de la calculadora a consultar
     * @return List<Calculadora>: variable que contiene la información de las calculadora consultadas
     */
	public List<Calculadora> getCalculadoras(String tipo, Integer anio) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Calculadora>) session.createQuery("from Calculadora where tipo = :tipo and anio = :anio order by tipo, campana, mes")
				.setParameter("tipo", tipo)
				.setParameter("anio", anio)
				.list();
	}

	/**
     * Método que permite eliminar una campaña de las calculadora
     * @param camapanaMaxima: variable que contiene el id de la campaña a eliminar
     */
	public void eliminarCampaniaCalculadora(int camapanaMaxima) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.dbo.calculadora where campana = :campana")
				.setParameter("campana", camapanaMaxima).executeUpdate();
		
		session.flush();
	}

	/**
     * Método que permite actualizar la información de la calculadora en la base de datos
     * @param listaCalculadora: variable que contiene la información de las calculadoras a actualizar
     * @param tipo: variable que contiene el tipo Calculadora
     * @param camapanaMaxima: variable que contiene el numero de campañas en la calculadora
     */
	public void updateCalculadoras(List<Calculadora[]> listaCalculadora, String tipo, int camapanaMaxima) {
		Session session = getSessionFactory().getCurrentSession();
		if("CM".equals(tipo)) {
			
			//Guardamos la CM
			for(Calculadora[] cal : listaCalculadora) {
				for(int m = 1; m <= 12; m++) {
					session.update(cal[m-1]);
				}
			}
			
		}else {
			for(int m = 1; m <= camapanaMaxima; m++) {
				Calculadora[] cals = listaCalculadora.get(m-1);
				for(Calculadora cal : cals) {
					session.update(cal);
				}
			}
		}
	}

	/**
     * Método que permite obtener el id la calculadora maxima
     * @param anioGeneral: variable que contiene la información del año de la calculadora
     * @return int: variable que contiene el numero maximo de campaña en la calculadora
     */
	public int getCampanaMaxima(Integer anioGeneral) {
		Session session = getSessionFactory().getCurrentSession();
		return (Integer)session.createSQLQuery("select max(campana) from presupuestomd.dbo.calculadora where anio = '" + anioGeneral + "'")
				.uniqueResult();
	}

	/**
     * Método que permite obtener el listado de años de calculadora
     * @return List<SelectItem>: variable que contiene la lista de años de las calculadoras
     */
	public List<SelectItem> getListaAnios() {
		List<SelectItem> retorno = new ArrayList<>();
		Session session = getSessionFactory().getCurrentSession();
		List<Integer> listado = (List<Integer>)session.createSQLQuery("select distinct anio from presupuestomd.dbo.calculadora").list();
		if(listado != null && !listado.isEmpty()) {
			for(Integer tmp: listado) {
				retorno.add(new SelectItem(String.valueOf(tmp), String.valueOf(tmp)));
			}
		}
		
		return retorno;
	}

}
