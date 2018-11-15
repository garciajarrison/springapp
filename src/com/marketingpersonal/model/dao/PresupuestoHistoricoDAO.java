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

	public void addPresupuestoHistorico(PresupuestoHistorico entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}
	
	public void deletePresupuestoHistorico(PresupuestoHistorico entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}	
	
	public PresupuestoHistorico getPresupuestoHistoricoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoHistorico) session
				.createQuery("from PresupuestoHistorico where id=?").setParameter(0, id)
				.uniqueResult();
	}
	
	public List<PresupuestoHistorico> getPresupuestosHistoricos() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session.createQuery("from PresupuestoHistorico").list();
	}	
	
	
	public List<PresupuestoHistorico> getPresupuestoHistoricoPorAnio(Integer anioConsulta) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoHistorico>) session
				.createQuery("from PresupuestoHistorico where anio = :anioConsulta").setParameter("anioConsulta", anioConsulta)
				.list();
	}
	
	
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

	public void deletePresupuestoHistoricoPorAnio(int anio) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("delete from presupuestomd.dbo.presupuesto_historico where anio = :anio")
				.setParameter("anio", anio).executeUpdate();
		
		session.flush();
	}
	
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
