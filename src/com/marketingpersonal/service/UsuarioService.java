package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IUsuarioDAO;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;

@Service
@Transactional(readOnly = true)
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usersDAO;
	
	public IUsuarioDAO getEntityDAO() {
		return usersDAO;
	}

	public void setEntityDAO(IUsuarioDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	@Transactional(readOnly = false)
	public Usuario login(Usuario users) {
		return usersDAO.login(users);
	}
	
	@Transactional(readOnly = false)
	public void addUsuario(Usuario entity) {
		getEntityDAO().addUsuario(entity);
	}

	@Transactional(readOnly = false)
	public void deleteUsuario(Usuario entity) {
		getEntityDAO().deleteUsuario(entity);
	}

	@Transactional(readOnly = false)
	public void updateUsuario(Usuario entity) {
		getEntityDAO().updateUsuario(entity);
	}

	public Usuario getUsuarioById(int id) {
		return getEntityDAO().getUsuarioById(id);
	}

	public List<Usuario> getUsuarios() {	
		return getEntityDAO().getUsuarios();
	}
	
	//Usuario por centro de costo
	@Transactional(readOnly = false)
	public void addUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		getEntityDAO().addUsuarioPorCentroCosto(entity);
	}

	@Transactional(readOnly = false)
	public void deleteUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		getEntityDAO().deleteUsuarioPorCentroCosto(entity);
	}

	@Transactional(readOnly = false)
	public void updateUsuarioPorCentroCosto(UsuarioPorCentroCosto entity) {
		getEntityDAO().updateUsuarioPorCentroCosto(entity);
	}

	public UsuarioPorCentroCosto getUsuarioPorCentroCostoById(int id) {
		return getEntityDAO().getUsuarioPorCentroCostoById(id);
	}

	public List<UsuarioPorCentroCosto> getUsuarioPorCentroCostos() {	
		return getEntityDAO().getUsuarioPorCentroCostos();
	}
}
