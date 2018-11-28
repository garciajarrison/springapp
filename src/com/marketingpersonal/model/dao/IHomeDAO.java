package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Home;

/**
 * Interface que contiene los metodos implementados en la clase HomeDAO
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public interface IHomeDAO {
	
	void addHome(Home entity);

	void updateHome(Home entity);
	
	void deleteHome(Home entity);
	
	Home getHomeById(int id);

	List<Home> getHomes(boolean activo);

}
