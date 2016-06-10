package co.bandsportal.model.manager;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.bandsportal.model.bo.City;
import co.bandsportal.model.bo.Country;
import co.bandsportal.model.bo.Genre;
import co.bandsportal.model.bo.Member;
import co.bandsportal.model.bo.State;
import co.bandsportal.model.manager.dao.ListaValoresDao;
import co.bandsportal.model.manager.dao.MemberDao;


@Component
public class ListaValoresManager implements IListaValoresManager {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ListaValoresDao listaValoresDao;
    public void setListaValoresDao(ListaValoresDao listaValoresDao) {
        this.listaValoresDao = listaValoresDao;
    }
    
    
	public List<Genre> loadGenres() throws Exception {
		return listaValoresDao.loadGenres();
	}

	public List<Country> loadCountries() throws Exception {
		return listaValoresDao.loadCountries();
	}

	public List<State> loadState(int countryCod){
		return listaValoresDao.loadState(countryCod);
	}
	
	public List<City> loadCities(int stateCod){
		return listaValoresDao.loadCities(stateCod);
	}

}