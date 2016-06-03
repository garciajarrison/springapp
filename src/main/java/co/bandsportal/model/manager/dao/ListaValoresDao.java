package co.bandsportal.model.manager.dao;

import java.util.List;

import co.bandsportal.model.bo.City;
import co.bandsportal.model.bo.Country;
import co.bandsportal.model.bo.Genre;

public interface ListaValoresDao {
	
	List<Genre> loadGenres() throws Exception;
	List<City> loadCities() throws Exception;
	List<Country> loadCountries() throws Exception;


}