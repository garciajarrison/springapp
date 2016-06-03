package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable  
public class CompanyCityPK implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name="id_company")
	private long codigoCompany;
	@Column(name="id_city") 
	private long codigoCity;

	public CompanyCityPK(){
		super();
	}
	
	public CompanyCityPK(long codigoCompany, long codigoCity) {  
		super();  
		this.codigoCompany = codigoCompany;  
		this.codigoCity = codigoCity;  
	 }

	public long getCodigoCompany() {
		return codigoCompany;
	}
	public void setCodigoCompany(long codigoCompany) {
		this.codigoCompany = codigoCompany;
	}
	public long getCodigoCity() {
		return codigoCity;
	}
	public void setCodigoCity(long codigoCity) {
		this.codigoCity = codigoCity;
	}  
	
	
	
}
