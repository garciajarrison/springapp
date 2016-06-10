package co.bandsportal.model.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import co.bandsportal.model.manager.ListaValoresManager;


public class ListaValores implements Serializable{

	private static final long serialVersionUID = 1L;
	private static ListaValores instance = null;
	
	@Autowired
    private ListaValoresManager listaValoresManager;
	
	private List<Genre> genreList;
	private List<Country> countryList;

	private ListaValores(){
		loadList();
	}
	
	public ListaValores getInstace(){
		if(instance == null){
			instance = new ListaValores();
		}
		return instance;
	}
	
	private void loadList(){
		this.loadGenres();
		this.loadCountries();
	}
	
	public void resetLists(){
		loadList();
	}
	
	/**
	 * Metodos de carga
	 */
	
	private void loadGenres(){
		try{
			genreList = listaValoresManager.loadGenres();
		}catch(Exception e){
			
		}
	}

	private void loadCountries(){
		try{
			countryList = listaValoresManager.loadCountries();
		}catch(Exception e){
			
		}
	}
	
	
	/**
	 * Metodos Get
	 * @return
	 */
	
	public List<Genre> getGenreList(){
		return this.genreList;
	}
	public List<Country> getCountryList(){
		return this.countryList;
	}
	
	public List<City> getCityList(int stateCod){
		return listaValoresManager.loadCities(stateCod);
	}
	public List<State> getStateList(int countryCod){
		return listaValoresManager.loadState(countryCod);
	}

	public void setListaValoresManager(ListaValoresManager listaValoresManager) {
        this.listaValoresManager = listaValoresManager;
    }
	
}
