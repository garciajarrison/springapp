package com.marketingpersonal.service;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;


public interface IUsuarioService {
	
	Usuario login(Usuario usuario);
	
	void addUsuario(Usuario entity);
	
	void updateUsuario(Usuario entity);

	void deleteUsuario(Usuario entity);
	
	Usuario getUsuarioById(int id);
	
	List<Usuario> getUsuarios();
	
	void addUsuariosArchivoPlano(XSSFSheet sheet);

	//Usuario por centro de costo
	void addUsuarioPorCentroCosto(UsuarioPorCentroCosto entity);
	
	void updateUsuarioPorCentroCosto(UsuarioPorCentroCosto entity);

	void deleteUsuarioPorCentroCosto(UsuarioPorCentroCosto entity);
	
	UsuarioPorCentroCosto getUsuarioPorCentroCostoById(int id);
	
	List<UsuarioPorCentroCosto> getUsuarioPorCentroCostos();

}
