package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;

/**
 * Clase DAO encargada de realizar la gestión de la base de datos para Usuarios
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@Repository
public class UsuarioDAO implements IUsuarioDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
     * Método que permite consultar la información de un Usuario 
     * @param users: variable que contiene la información del usuario que se esta logueando
     * @return Usuario: variable que contiene la información del Usuario a retornar
     */
	public Usuario login(Usuario users) {
		Session session = getSessionFactory().getCurrentSession();
		Usuario usuarioTmp = (Usuario)session.createQuery("from Usuario where usuario=:usuario")
				.setParameter("usuario", users.getUsuario())
				.uniqueResult();
		
		return usuarioTmp;
	}
	
	/**
     * Método que permite almacenar la información de un Usuario en la base de datos
     * @param entity variable que contiene la información de la entidad Usuario a almacenar
     */
	public void addUsuario(Usuario entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNumeroDocumento(entity.getNumeroDocumento().trim());
		entity.setUsuario(entity.getUsuario().toLowerCase().trim());
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre().trim()));
		entity.setCorreo(entity.getCorreo().toLowerCase().trim());
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de un Usuario de la base de datos
     * @param entity variable que contiene la información de la entidad Usuario a eliminar
     */
	public void deleteUsuario(Usuario entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.delete(entity);
	}

	/**
     * Método que permite actualizar la información de un Usuario en la base de datos
     * @param entity variable que contiene la información del Usuario a actualizar
     */
	public void updateUsuario(Usuario entity) {
		Session session = getSessionFactory().getCurrentSession();
		entity.setNumeroDocumento(entity.getNumeroDocumento().trim());
		entity.setUsuario(entity.getUsuario().toLowerCase().trim());
		entity.setNombre(WordUtils.capitalizeFully(entity.getNombre().trim()));
		entity.setCorreo(entity.getCorreo().toLowerCase().trim());
		session.update(entity);
	}

	/**
     * Método que permite consultar la información de un Usuario a partir del id
     * @param id: variable que contiene el id del Usuario a consultar
     * @return Usuario: variable que contiene la información del Usuario a retornar
     */
	public Usuario getUsuarioById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		return (Usuario) session
				.createQuery("from Usuario where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de los Usuarios almacenados en la base de datos
     * @return List<Usuario>: variable que contiene la información de los Usuarios consultados
     */
	public List<Usuario> getUsuarios() {
		Session session = getSessionFactory().getCurrentSession();
		return session.createQuery("from Usuario").list();
	}
	
	/**
     * Método que permite almacenar la información de un Usuario por Centro de Costo en la base de datos
     * @param entity variable que contiene la información de la entidad Usuario por Centro de Costo a almacenar
     */
	public void addUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(entity);
	}

	/**
     * Método que permite eliminar la información de un Usuario por Centro de Costo de la base de datos
     * @param entity variable que contiene la información de la entidad Usuario por Centro de Costo a eliminar
     */
	public void deleteUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		Session session = getSessionFactory().getCurrentSession();
		//session.delete(entity);
		session.createSQLQuery("delete from presupuestomd.dbo.usuario_x_centrocosto where id = :id")
			.setParameter("id", entity.getId()).executeUpdate();
		session.flush();
	}

	/**
     * Método que permite actualizar la información de un Usuario por Centro de Costo en la base de datos
     * @param entity variable que contiene la información del Usuario por Centro de Costo a actualizar
     */
	public void updateUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		StringBuilder query = new StringBuilder("update presupuestomd.dbo.usuario_x_centrocosto set ")
				.append(" id_centrocosto = :id_centrocosto, ")
				.append(" id_usuario_resp = :id_usuario_resp, ")
				.append(" id_usuario_aprini = :id_usuario_aprini, ")
				.append(" id_usuario_aprfin = :id_usuario_aprfin ")
				.append(" where id = :id ");
		
		Session session = getSessionFactory().getCurrentSession();
		session.createSQLQuery(query.toString())
			.setParameter("id_centrocosto", entity.getIdcc())
			.setParameter("id_usuario_resp", entity.getIdr())
			.setParameter("id_usuario_aprini", entity.getIdi())
			.setParameter("id_usuario_aprfin", entity.getIdf())
			.setParameter("id", entity.getId())
			.executeUpdate();
		session.flush();
	}

	/**
     * Método que permite consultar la información de un Usuario por Centro de Costo a partir del id
     * @param id: variable que contiene el id del Usuario por Centro de Costo a consultar
     * @return UsuarioPorCentroCosto: variable que contiene la información del Usuario por Centro de Costo a retornar
     */
	public UsuarioPorCentroCosto getUsuarioPorCentroCostoById(int id) {
		Session session = getSessionFactory().getCurrentSession();
		return (UsuarioPorCentroCosto) session
				.createQuery("from UsuarioPorCentroCosto where id=?").setParameter(0, id)
				.uniqueResult();
	}

	/**
     * Método que permite consultar la información de los Usuarios por Centro de Costo almacenados en la base de datos
     * @return List<UsuarioPorCentroCosto>: variable que contiene la información de los Usuarios por Centro de Costo consultados
     */
	public List<UsuarioPorCentroCosto> getUsuarioPorCentroCostos() {
		Session session = getSessionFactory().getCurrentSession();
		return session.createQuery("from UsuarioPorCentroCosto").list();
	}

	/**
     * Método que permite almacenar la información de un Usuario en la base de datos a partir de un archivo plano
     * @param sheet: variable que contiene la hoja de excel desde la cual se cargaran los usuarios en la base de datos
     */
	public void addUsuariosArchivoPlano(XSSFSheet sheet) {
		Session session = getSessionFactory().getCurrentSession();
		
		Row row;
		Usuario usuario;
		int numFilas = sheet.getPhysicalNumberOfRows();	
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);
			
			usuario = new Usuario();

			usuario.setNumeroDocumento(row.getCell(0)+"".trim());
			usuario.setNombre(WordUtils.capitalizeFully(row.getCell(1)+"".trim()));
			usuario.setUsuario(row.getCell(2)+"".toLowerCase().trim());
			usuario.setCorreo(row.getCell(3)+"".toLowerCase().trim());
			usuario.setCargo(row.getCell(4)+"");
			usuario.setRol(row.getCell(5)+"");
						
			session.save(usuario);	
		}
	}

	/**
     * Método que permite consultar si un usuario es aprobador inicial a partir del id
     * @param idUsuario: variable que contiene el id del Usuario a consultar
     * @return retorno: variable booleana que indica si el usuario es aprobador inicial o no
     */
	@Override
	public boolean isAprobadorInicial(int idUsuario) {
		boolean retorno = false;
		try {
			Session session = getSessionFactory().getCurrentSession();
			List<UsuarioPorCentroCosto> objTmp = (List<UsuarioPorCentroCosto>)session
					.createQuery("from UsuarioPorCentroCosto where usuarioAprobadorInicial.id = ?")
					.setParameter(0, idUsuario)
					.list();
			
			if(objTmp != null && !objTmp.isEmpty()) {
				retorno = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
     * Método que permite consultar si un usuario es aprobador final a partir del id
     * @param idUsuario: variable que contiene el id del Usuario a consultar
     * @return retorno: variable booleana que indica si el usuario es aprobador final o no
     */
	@Override
	public boolean isAprobadorFinal(int idUsuario) {
		boolean retorno = false;
		try {
			Session session = getSessionFactory().getCurrentSession();
			List<UsuarioPorCentroCosto> objTmp = (List<UsuarioPorCentroCosto>)session
					.createQuery("from UsuarioPorCentroCosto where usuarioAprobadorFinal.id = ?")
					.setParameter(0, idUsuario)
					.list();
			
			if(objTmp != null && !objTmp.isEmpty()) {
				retorno = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	
}
