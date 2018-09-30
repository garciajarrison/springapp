package com.marketingpersonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketingpersonal.model.dao.ISublinkDAO;
import com.marketingpersonal.model.entity.Sublink;


@Service
@Transactional(readOnly = true)
public class SublinkService implements ISublinkService {

	@Autowired
	private ISublinkDAO entityDAO;

	@Transactional(readOnly = false)
	public void addSublink(Sublink entity) {
		getEntityDAO().addSublink(entity);
	}

	@Transactional(readOnly = false)
	public void deleteSublink(Sublink entity) {
		getEntityDAO().deleteSublink(entity);
	}

	@Transactional(readOnly = false)
	public void updateSublink(Sublink entity) {
		getEntityDAO().updateSublink(entity);
	}

	public Sublink getSublinkById(int id) {
		return getEntityDAO().getSublinkById(id);
	}

	public List<Sublink> getSublinks() {	
		return getEntityDAO().getSublinks();
	}
	
	public ISublinkDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(ISublinkDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

}
