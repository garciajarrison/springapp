package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;

@Repository
public class PresupuestoDAO implements IPresupuestoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void addPresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}
	
	public List<Presupuesto> getPresupuestosPorUsuario(int idUsuario) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session
				.createQuery("from Presupuesto where usuario.id = :idUsuario").setParameter("idUsuario", idUsuario)
				.list();
	}
	
	public List<Presupuesto> getPresupuestosPorAnio(Integer anioConsulta) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session
				.createQuery("from Presupuesto where anio = :anioConsulta").setParameter("anioConsulta", anioConsulta)
				.list();
	}

	public void deletePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updatePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Presupuesto getPresupuestoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Presupuesto) session
				.createQuery("from Presupuesto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<Presupuesto> getPresupuestos() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session.createQuery("from Presupuesto").list();
	}
	
	//Detalle Mes
	public void addPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public void deletePresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updatePresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public PresupuestoDetalleMes getPresupuestoDetalleMesById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoDetalleMes) session
				.createQuery("from PresupuestoDetalleMes where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMes() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleMes>) session.createQuery("from PresupuestoDetalleMes").list();
	}

	/*public void addObservacion(Observacion entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}*/

	public void actualizarEstadoPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("update presupuestomd.dbo.detalle_presupuesto_mes set estado = :estado where id = :id")
			.setParameter("estado", entity.getEstado())
			.setParameter("id", entity.getId())
			.executeUpdate();
		session.flush();
		session.refresh(entity);
	}
	
	//Detalle Campania
	public void addPresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public void deletePresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	public void updatePresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public PresupuestoDetalleCampania getPresupuestoDetalleCampaniaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoDetalleCampania) session
				.createQuery("from PresupuestoDetalleCampania where id=?").setParameter(0, id)
				.uniqueResult();
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleCampania>) session.createQuery("from PresupuestoDetalleCampania").list();
	}

	public void actualizarEstadoPresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("update presupuestomd.dbo.detalle_presupuesto_campania set estado = :estado where id = :id")
			.setParameter("estado", entity.getEstado())
			.setParameter("id", entity.getId())
			.executeUpdate();
		session.flush();
		session.refresh(entity);
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMes(int idPresupuesto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleMes>) session.createQuery("from PresupuestoDetalleMes where presupuesto.id = :id")
				.setParameter("id", idPresupuesto).list();
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania(int idPresupuesto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleCampania>) session.createQuery("from PresupuestoDetalleCampania where presupuesto.id = :id")
				.setParameter("id", idPresupuesto).list();
	}

	public void addObservacion(Observacion observacion) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(observacion);
	}

	public List<Presupuesto> getPresupuestosAprobadorInicial(Usuario usuario) {
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select p                                                      ")
				.append("from Presupuesto p                                			   ")
				.append("where (p.id in (select dpc.presupuesto.id                     ")
				.append("			   from PresupuestoDetalleCampania dpc,			   ")
				.append("			   		UsuarioPorCentroCosto ucc             	   ")
				.append("			   where p.id = dpc.presupuesto.id                 ")
				.append("			  and dpc.centroCosto.id = ucc.centroCosto.id      ")
				.append("			  and dpc.estado = 'ENVIADO'      				   ");
		if(!"Administrador".equals(usuario.getRol())) {
			query.append("			  and ucc.usuarioAprobadorInicial.id = :idUsuario ");
		}
		
		query.append(" )	 or                                                        ")
				.append("	   p.id in (select dpm.presupuesto.id                      ")
				.append("			   from PresupuestoDetalleMes dpm,     			   ")
				.append("			   		UsuarioPorCentroCosto ucc             	   ")
				.append("			   where p.id = dpm.presupuesto.id                 ")
				.append("			  and dpm.centroCosto.id = ucc.centroCosto.id      ")
				.append("			  and dpm.estado = 'ENVIADO'      				   ");
		
		if(!"Administrador".equals(usuario.getRol())) {
			query.append("			  and ucc.usuarioAprobadorInicial.id = :idUsuario");
		}
				
		query.append(" ))");
		
		Query q = session.createQuery(query.toString());
		if(!"Administrador".equals(usuario.getRol())) {
			q.setParameter("idUsuario", usuario.getId());
		}
		
		return (List<Presupuesto>) q.list();
	}

	public List<Presupuesto> getPresupuestosAprobadorFinal(Usuario usuario) {
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select p                                                      ")
				.append("from Presupuesto p                                			   ")
				.append("where (p.id in (select dpc.presupuesto.id                     ")
				.append("			   from PresupuestoDetalleCampania dpc,			   ")
				.append("			   		UsuarioPorCentroCosto ucc             	   ")
				.append("			   where p.id = dpc.presupuesto.id                 ")
				.append("			  and dpc.centroCosto.id = ucc.centroCosto.id      ")
				.append("			  and dpc.estado = 'APROBADO'      				   ");
		
		if(!"Administrador".equals(usuario.getRol())) {
			query.append("			  and ucc.usuarioAprobadorFinal.id = :idUsuario   ");
		}
				
		query.append(" )	 or                                                        ")
				.append("	   p.id in (select dpm.presupuesto.id                      ")
				.append("			   from PresupuestoDetalleMes dpm,     			   ")
				.append("			   		UsuarioPorCentroCosto ucc             	   ")
				.append("			   where p.id = dpm.presupuesto.id                 ")
				.append("			  and dpm.centroCosto.id = ucc.centroCosto.id      ")
				.append("			  and dpm.estado = 'APROBADO'      				   ");
				
		if(!"Administrador".equals(usuario.getRol())) {
			query.append("			  and ucc.usuarioAprobadorFinal.id = :idUsuario  ");
		}
				
		query.append("	))  ");
		
		Query q = session.createQuery(query.toString());
		if(!"Administrador".equals(usuario.getRol())) {
			q.setParameter("idUsuario", usuario.getId());
		}
		
		return (List<Presupuesto>) q.list();
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampaniaAprobadorInicial(int idPresupuesto, Usuario usuario) {
		
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select dpc                                         ")
				.append(" from PresupuestoDetalleCampania dpc,			    ")
				.append("	   UsuarioPorCentroCosto ucc             	    ")
				.append(" where dpc.presupuesto.id = :idPresupuesto         ")
				.append("	and dpc.centroCosto.id = ucc.centroCosto.id     ")
				.append("	and dpc.estado = 'ENVIADO'      				")
				.append("	and ucc.usuarioAprobadorInicial.id = :idUsuario ");
		
		return (List<PresupuestoDetalleCampania>) session.createQuery(query.toString())
				.setParameter("idPresupuesto", idPresupuesto)
				.setParameter("idUsuario", usuario.getId()).list();
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMesAprobadorInicial(int idPresupuesto, Usuario usuario) {
		
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select dpm                                         ")
				.append(" from PresupuestoDetalleMes dpm,			        ")
				.append("	   UsuarioPorCentroCosto ucc             	    ")
				.append(" where dpm.presupuesto.id = :idPresupuesto         ")
				.append("	and dpm.centroCosto.id = ucc.centroCosto.id     ")
				.append("	and dpm.estado = 'ENVIADO'      				")
				.append("	and ucc.usuarioAprobadorInicial.id = :idUsuario ");
		
		return (List<PresupuestoDetalleMes>) session.createQuery(query.toString())
				.setParameter("idPresupuesto", idPresupuesto)
				.setParameter("idUsuario", usuario.getId()).list();
	}

	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampaniaAprobadorFinal(int idPresupuesto, Usuario usuario) {
		
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select dpc                                         ")
				.append(" from PresupuestoDetalleCampania dpc,			    ")
				.append("	   UsuarioPorCentroCosto ucc             	    ")
				.append(" where dpc.presupuesto.id = :idPresupuesto         ")
				.append("	and dpc.centroCosto.id = ucc.centroCosto.id     ")
				.append("	and dpc.estado = 'APROBADO'      				")
				.append("	and ucc.usuarioAprobadorInicial.id = :idUsuario ");
		
		return (List<PresupuestoDetalleCampania>) session.createQuery(query.toString())
				.setParameter("idPresupuesto", idPresupuesto)
				.setParameter("idUsuario", usuario.getId()).list();
	}

	public List<PresupuestoDetalleMes> getPresupuestoDetallesMesAprobadorFinal(int idPresupuesto, Usuario usuario) {
		Session session = getSessionFactory().getCurrentSession();
		StringBuilder query = new StringBuilder()
				.append("select dpm                                         ")
				.append(" from PresupuestoDetalleMes dpm,			        ")
				.append("	   UsuarioPorCentroCosto ucc             	    ")
				.append(" where dpm.presupuesto.id = :idPresupuesto         ")
				.append("	and dpm.centroCosto.id = ucc.centroCosto.id     ")
				.append("	and dpm.estado = 'APROBADO'      				")
				.append("	and ucc.usuarioAprobadorInicial.id = :idUsuario ");
		
		return (List<PresupuestoDetalleMes>) session.createQuery(query.toString())
				.setParameter("idPresupuesto", idPresupuesto)
				.setParameter("idUsuario", usuario.getId()).list();
	}

}
