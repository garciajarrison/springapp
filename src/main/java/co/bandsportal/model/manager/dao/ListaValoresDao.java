package co.bandsportal.model.manager.dao;

import java.util.List;

import co.bandsportal.model.bo.City;
import co.bandsportal.model.bo.Country;
import co.bandsportal.model.bo.Genre;
import co.bandsportal.model.bo.State;

public interface ListaValoresDao {
	
	List<Genre> loadGenres() throws Exception;
	List<City> loadCities(int stateCod);
	List<Country> loadCountries() throws Exception;
	List<State> loadState(int countryCod);

}