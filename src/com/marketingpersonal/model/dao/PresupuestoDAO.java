package com.marketingpersonal.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;

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

	public void addObservacion(Observacion entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.saveOrUpdate(entity);
	}

	public void actualizarEstadoPresupuestoDetalleMes(PresupuestoDetalleMes entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery("update presupuestoMD.detalle_presupuesto_mes set estado = :estado where id = :id")
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
			session.createSQLQuery("update presupuestoMD.detalle_presupuesto_campania set estado = :estado where id = :id")
				.setParameter("estado", entity.getEstado())
				.setParameter("id", entity.getId())
				.executeUpdate();
			session.flush();
			session.refresh(entity);
		}

}
