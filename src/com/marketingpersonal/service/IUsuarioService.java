package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Usuario;


public interface IUsuarioService {
	
	void addUsuario(Usuario entity);
	
	void updateUsuario(Usuario entity);

	void deleteUsuario(Usuario entity);
	
	Usuario getUsuarioById(int id);
	
	List<Usuario> getUsuarios();

	Usuario login(Usuario usuario);

}
