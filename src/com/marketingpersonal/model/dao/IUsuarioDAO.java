package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Usuario;


public interface IUsuarioDAO {
	
	Usuario login(Usuario users);
	
	void addUsuario(Usuario entity);

	void deleteUsuario(Usuario entity);

	void updateUsuario(Usuario entity);

	Usuario getUsuarioById(int id);

	List<Usuario> getUsuarios();
	
}
