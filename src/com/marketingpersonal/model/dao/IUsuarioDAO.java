package com.marketingpersonal.model.dao;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;

/**
 * Interface que contiene los metodos implementados en la clase UsuarioDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
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

	boolean isAprobadorInicial(int idUsuario);

	boolean isAprobadorFinal(int idUsuario);
}
