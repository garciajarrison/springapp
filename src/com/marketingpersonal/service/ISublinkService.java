package com.marketingpersonal.service;

import java.util.List;

import com.marketingpersonal.model.entity.Sublink;


public interface ISublinkService {
	
	void addSublink(Sublink entity);

	void updateSublink(Sublink entity);
	
	void deleteSublink(Sublink entity);
	
	Sublink getSublinkById(int id);
	
	List<Sublink> getSublinks(boolean activo);
}
