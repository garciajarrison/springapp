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

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Presupuestos
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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

	/**
     * Método que permite almacenar la información de un Presupuesto en la base de datos
     * @param entity variable que contiene la información de la entidad Presupuesto a almacenar
     */
	public void addPresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}
	
	/**
     * Método que permite consultar la información de los Presupuestos de un Usuario almacenadas en la base de datos
     * @param idUsuario: variable que indica el id del usuario asociado al presupuesto
     * @return List<Presupuesto>: variable que contiene la información de los Presupuestos consultadas
     */
	public List<Presupuesto> getPresupuestosPorUsuario(int idUsuario) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session
				.createQuery("from Presupuesto where usuario.id = :idUsuario").setParameter("idUsuario", idUsuario)
				.list();
	}
	
	/**
     * Método que permite consultar la información de los Presupuestos por año almacenadas en la base de datos
     * @param anioConsulta: variable que año que para el preuspuesto a consultar
     * @return List<Presupuesto>: variable que contiene la información de los Presupuestos consultadas
     */
	public List<Presupuesto> getPresupuestosPorAnio(Integer anioConsulta) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session
				.createQuery("from Presupuesto where anio = :anioConsulta").setParameter("anioConsulta", anioConsulta)
				.list();
	}
	
	/**
     * Método que permite consultar la información de los Presupuestos por año y por usuario almacenadas en la base de datos
     * @param anioConsulta: variable que año que para el presupuesto a consultar
     * @param idUsuario:  variable que indica el id del usuario asociado al presupuesto
     * @return List<Presupuesto>: variable que contiene la información de los Presupuestos consultadas
     */
	public List<Presupuesto> getPresupuestosPorAnioPorUsuario(Integer anioConsulta, int idUsuario) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session
				.createQuery("from Presupuesto where anio = :anioConsulta and usuario.id = :idUsuario")
				.setParameter("anioConsulta", anioConsulta)
				.setParameter("idUsuario", idUsuario)
				.list();
	}

	/**
     * Método que permite eliminar la información de un Presupuesto de la base de datos
     * @param entity variable que contiene la información de la entidad Presupuesto a eliminar
     */
	public void deletePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}
	
	/**
     * Método que permite eliminar la información de un Presupuesto de la base de datos
     * @param entity variable que contiene la información de la entidad Presupuesto a eliminar
     */
	public void deleteDetallePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		
		if(entity.getDetalleCampania() != null && !entity.getDetalleCampania().isEmpty()) {
			session.createSQLQuery("delete from dbo.observacion where id_detalle_presupuesto_campania = :id")
				.setParameter("id", entity.getDetalleCampania().get(0).getId()).executeUpdate();
			session.flush();
		}
		
		if(entity.getDetalleMes() != null && !entity.getDetalleMes().isEmpty()) {
			session.createSQLQuery("delete from dbo.observacion where id_detalle_presupuesto_mes = :id")
				.setParameter("id", entity.getDetalleMes().get(0).getId()).executeUpdate();
			session.flush();
		}
		
		session.createSQLQuery("delete from dbo.detalle_presupuesto_mes where id_presupuesto = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();

		session.createSQLQuery("delete from dbo.detalle_presupuesto_campania where id_presupuesto = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
		session.refresh(entity);
	}

	/**
     * Método que permite actualizar la información de un Presupuesto en la base de datos
     * @param entity variable que contiene la información del Presupuesto a actualizar
     */
	public void updatePresupuesto(Presupuesto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de un Presupuesto a partir del id
     * @param id: variable que contiene el id del Presupuesto a consultar
     * @return Presupuesto: variable que contiene la información del Presupuesto a retornar
     */
	public Presupuesto getPresupuestoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (Presupuesto) session
				.createQuery("from Presupuesto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de los Presupuesto almacenadas en la base de datos
     * @return List<Presupuesto>: variable que contiene la información de los Presupuesto consultados
     */
	public List<Presupuesto> getPresupuestos() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<Presupuesto>) session.createQuery("from Presupuesto").list();
	}
	
	/**
     * Método que permite almacenar la información de un Detalle de Presupuesto Mensual en la base de datos
     * @param entity variable que contiene la información de la entidad PresupuestoDetalleMes a almacenar
     */
	public void addPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	/**
     * Método que permite eliminar la información de un Detalle de Presupuesto Mensual de la base de datos
     * @param entity variable que contiene la información de la entidad PresupuestoDetalleMes a eliminar
     */
	public void deletePresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de un Detalle de Presupuesto Mensual en la base de datos
     * @param entity variable que contiene la información del PresupuestoDetalleMes a actualizar
     */
	public void updatePresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	/**
     * Método que permite consultar la información de un Detalle de Presupuesto Mensual a partir del id
     * @param id: variable que contiene el id del Detalle de Presupuesto Mensual a consultar
     * @return PresupuestoDetalleMes: variable que contiene la información del Detalle de Presupuesto Mensual a retornar
     */
	public PresupuestoDetalleMes getPresupuestoDetalleMesById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoDetalleMes) session
				.createQuery("from PresupuestoDetalleMes where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de los Detalles de Presupuesto Mensual almacenadas en la base de datos
     * @return List<PresupuestoDetalleMes>: variable que contiene la información de los Detalles de Presupuesto Mensual consultadas
     */
	public List<PresupuestoDetalleMes> getPresupuestoDetallesMes() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleMes>) session.createQuery("from PresupuestoDetalleMes").list();
	}

	/**
     * Método que permite actualizar el estado de un Detalle de Presupuesto Mensual en la base de datos
     * @param entity variable que contiene la información del Detalles de Presupuesto Mensual a actualizar
     */
	public void actualizarEstadoPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("update presupuestomd.dbo.detalle_presupuesto_mes set estado = :estado where id = :id")
			.setParameter("estado", entity.getEstado())
			.setParameter("id", entity.getId())
			.executeUpdate();
		session.flush();
		session.refresh(entity);
	}
	
	/**
     * Método que permite almacenar la información de un Detalle de Presupuesto Campañal en la base de datos
     * @param entity variable que contiene la información de la entidad PresupuestoDetalleCampania a almacenar
     */
	public void addPresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	/**
     * Método que permite eliminar la información de un Detalle de Presupuesto Campañal de la base de datos
     * @param entity variable que contiene la información de la entidad PresupuestoDetalleCampania a eliminar
     */
	public void deletePresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de un Detalle de Presupuesto Campañal en la base de datos
     * @param entity variable que contiene la información del PresupuestoDetalleCampania a actualizar
     */
	public void updatePresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	/**
     * Método que permite consultar la información de un Detalle de Presupuesto Campañal a partir del id
     * @param id: variable que contiene el id del Detalle de Presupuesto Campañal a consultar
     * @return PresupuestoDetalleCampania: variable que contiene la información del Detalle de Presupuesto Campañal a retornar
     */
	public PresupuestoDetalleCampania getPresupuestoDetalleCampaniaById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		
		return (PresupuestoDetalleCampania) session
				.createQuery("from PresupuestoDetalleCampania where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de los Detalles de Presupuesto Campañal almacenadas en la base de datos
     * @return List<PresupuestoDetalleMes>: variable que contiene la información de los Detalles de Presupuesto Mensual consultadas
     */
	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania() {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleCampania>) session.createQuery("from PresupuestoDetalleCampania").list();
	}

	/**
     * Método que permite actualizar el estado de un Detalle de Presupuesto Campañal en la base de datos
     * @param entity variable que contiene la información del Detalles de Presupuesto Campañal a actualizar
     */
	public void actualizarEstadoPresupuestoDetalleCampania(PresupuestoDetalleCampania entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("update presupuestomd.dbo.detalle_presupuesto_campania set estado = :estado where id = :id")
			.setParameter("estado", entity.getEstado())
			.setParameter("id", entity.getId())
			.executeUpdate();
		session.flush();
		session.refresh(entity);
	}

	/**
     * Método que permite consultar la información de los Detalles de Presupuesto Mensual a partir del id
     * @param idPresupuesto: variable que contiene el id del Detalle de Presupuesto Mensual a consultar
     * @return List<PresupuestoDetalleMes>: variable que contiene la información del listado de Detalles de Presupuesto Mensual a retornar
     */
	public List<PresupuestoDetalleMes> getPresupuestoDetallesMes(int idPresupuesto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleMes>) session.createQuery("from PresupuestoDetalleMes where presupuesto.id = :id")
				.setParameter("id", idPresupuesto).list();
	}

	/**
     * Método que permite consultar la información de los Detalles de Presupuesto Campañal a partir del id
     * @param idPresupuesto: variable que contiene el id del Detalle de Presupuesto Campañal a consultar
     * @return List<PresupuestoDetalleCampania>: variable que contiene la información del listado de Detalles de Presupuesto Campañal a retornar
     */
	public List<PresupuestoDetalleCampania> getPresupuestoDetallesCampania(int idPresupuesto) {
		Session session = getSessionFactory().getCurrentSession();
		return (List<PresupuestoDetalleCampania>) session.createQuery("from PresupuestoDetalleCampania where presupuesto.id = :id")
				.setParameter("id", idPresupuesto).list();
	}

	/**
     * Método que permite almacenar la información de una Observacion en la base de datos
     * @param entity variable que contiene la información de la entidad Observacion a almacenar
     */
	public void addObservacion(Observacion observacion) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(observacion);
	}

	/**
     * Método que permite consultar la información de los Presupuestos asociados a un aprobador inicial a partir del usuario
     * @param usuario: variable que contiene el usuario para el cual se requiere consultar los presupuestos
     * @return List<Presupuesto>: variable que contiene la información del listado de presupuestos asociados al usuario a retornar
     */
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

	/**
     * Método que permite consultar la información de los Presupuestos asociados a un aprobador final a partir del usuario
     * @param usuario: variable que contiene el usuario para el cual se requiere consultar los presupuestos
     * @return List<Presupuesto>: variable que contiene la información del listado de presupuestos asociados al usuario a retornar
     */
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

	/**
     * Método que permite consultar la información de los Detalles de Presupuestos Campañales asociados a un aprobador inicial a partir del usuario y id de presupuesto
     * @param idPresupuesto: variable que contiene el id de presupuesto para el cual se requiere consultar el detalle
     * @param usuario: variable que contiene el usuario para el cual se requiere consultar el detalle de presupuesto
     * @return List<PresupuestoDetalleCampania>: variable que contiene la información del listado de detalle de presupuestos asociados id de preuspuesto y al usuario a retornar
     */
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

	/**
     * Método que permite consultar la información de los Detalles de Presupuestos Mensuales asociados a un aprobador inicial a partir del usuario y id de presupuesto
     * @param idPresupuesto: variable que contiene el id de presupuesto para el cual se requiere consultar el detalle
     * @param usuario: variable que contiene el usuario para el cual se requiere consultar el detalle de presupuesto
     * @return List<PresupuestoDetalleMes>: variable que contiene la información del listado de detalle de presupuestos asociados id de presupuesto y al usuario a retornar
     */
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

	/**
     * Método que permite consultar la información de los Detalles de Presupuestos Campañales asociados a un aprobador final a partir del usuario y id de presupuesto
     * @param idPresupuesto: variable que contiene el id de presupuesto para el cual se requiere consultar el detalle
     * @param usuario: variable que contiene el usuario para el cual se requiere consultar el detalle de presupuesto
     * @return List<PresupuestoDetalleCampania>: variable que contiene la información del listado de detalle de presupuestos asociados id de presupuesto y al usuario a retornar
     */
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

	/**
     * Método que permite consultar la información de los Detalles de Presupuestos Mensuales asociados a un aprobador final a partir del usuario y id de presupuesto
     * @param idPresupuesto: variable que contiene el id de presupuesto para el cual se requiere consultar el detalle
     * @param usuario: variable que contiene el usuario para el cual se requiere consultar el detalle de presupuesto
     * @return List<PresupuestoDetalleMes>: variable que contiene la información del listado de detalle de presupuestos asociados id de presupuesto y al usuario a retornar
     */
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
