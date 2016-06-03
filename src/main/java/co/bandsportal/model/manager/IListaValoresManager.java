package co.bandsportal.model.manager;

import java.io.Serializable;
import java.util.List;

import co.bandsportal.model.bo.City;
import co.bandsportal.model.bo.Country;
import co.bandsportal.model.bo.Genre;

public interface IListaValoresManager extends Serializable {

	List<Genre> loadGenres() throws Exception;
	List<City> loadCities() throws Exception;
	List<Country> loadCountries() throws Exception;

}