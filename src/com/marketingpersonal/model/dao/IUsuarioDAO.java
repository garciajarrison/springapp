package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;


public interface IUsuarioDAO {
	
	Usuario login(Usuario users);
	
	void addUsuario(Usuario entity);

	void deleteUsuario(Usuario entity);

	void updateUsuario(Usuario entity);

	Usuario getUsuarioById(int id);

	List<Usuario> getUsuarios();
	
	//Usuario por centro de costo
	void addUsuarioPorCentroCosto(UsuarioPorCentroCosto entity);

	void deleteUsuarioPorCentroCosto(UsuarioPorCentroCosto entity);

	void updateUsuarioPorCentroCosto(UsuarioPorCentroCosto entity);

	UsuarioPorCentroCosto getUsuarioPorCentroCostoById(int id);

	List<UsuarioPorCentroCosto> getUsuarioPorCentroCostos();

	void addUsuariosArchivoPlano(XSSFSheet sheet);
}
