package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Home;


public interface IHomeService {
	
	void addHome(Home entity);

	void updateHome(Home entity);
	
	void deleteHome(Home entity);
	
	Home getHomeById(int id);
	
	List<Home> getHomes();
}
