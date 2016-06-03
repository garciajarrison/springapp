package co.bandsportal.model.manager.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.bandsportal.model.bo.City;
import co.bandsportal.model.bo.Country;
import co.bandsportal.model.bo.Genre;
import co.bandsportal.model.bo.Member;


@Repository(value = "listaValoresDao")
public class JPAListaValoresDao implements ListaValoresDao {

    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }


	@Override
	@Transactional(readOnly = false)
	public List<Genre> loadGenres() throws Exception {
		List<Genre> retorno = new ArrayList<Genre>();
    	
    	try{
    		
    		Query q = em.createQuery("select m from Member m "
            						+ "WHERE m.password = :password"
            						+ "AND m.username = :username");
    	
    		
    		Member mem = (Member)q.getSingleResult();
        
    	}catch(Exception e){
    		throw e;
    	}
    	return retorno;
	}

	@Override
	@Transactional(readOnly = false)
	public List<City> loadCities() throws Exception {
		List<City> retorno = new ArrayList<City>();;
    	
    	try{
    		
    		Query q = em.createQuery("select m from Member m "
            						+ "WHERE m.password = :password"
            						+ "AND m.username = :username");
    	
    		
    		Member mem = (Member)q.getSingleResult();
        
    	}catch(Exception e){
    		throw e;
    	}
    	return retorno;
	}

	@Override
	@Transactional(readOnly = false)
	public List<Country> loadCountries() throws Exception {
		List<Country> retorno = new ArrayList<Country>();;
    	
    	try{
    		
    		Query q = em.createQuery("select m from Member m "
            						+ "WHERE m.password = :password"
            						+ "AND m.username = :username");
    		
    		Member mem = (Member)q.getSingleResult();
        
    	}catch(Exception e){
    		throw e;
    	}
    	return retorno;
	}

}
