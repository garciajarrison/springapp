package com.marketingpersonal.model.dao;

import java.util.List;

import com.marketingpersonal.model.entity.Sublink;


public interface ISublinkDAO {
	
	void addSublink(Sublink entity);

	void updateSublink(Sublink entity);
	
	void deleteSublink(Sublink entity);
	
	Sublink getSublinkById(int id);

	List<Sublink> getSublinks(boolean activo);

}
