package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.IHomeDAO;
import com.marketingpersonal.model.entity.Home;


@Service
@Transactional(readOnly = true)
public class HomeService implements IHomeService {

	@Autowired
	private IHomeDAO entityDAO;

	@Transactional(readOnly = false)
	public void addHome(Home entity) {
		getEntityDAO().addHome(entity);
	}

	@Transactional(readOnly = false)
	public void deleteHome(Home entity) {
		getEntityDAO().deleteHome(entity);
	}

	@Transactional(readOnly = false)
	public void updateHome(Home entity) {
		getEntityDAO().updateHome(entity);
	}

	public Home getHomeById(int id) {
		return getEntityDAO().getHomeById(id);
	}

	public List<Home> getHomes() {	
		return getEntityDAO().getHomes();
	}
	
	public IHomeDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(IHomeDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

}
