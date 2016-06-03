package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="company_city") 
public class CompanyCity implements Serializable {

private static final long serialVersionUID = 1L;

	@EmbeddedId  
	private CompanyCityPK idCompanyCity; 
	@Column(name = "address")
	private int address;
	@Column(name = "url_maps")
	private String urlMaps;
	@Column(name = "phone")
	private String phone;
	
	public CompanyCity(){
		this.idCompanyCity = new CompanyCityPK();
	}
	
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getUrlMaps() {
		return urlMaps;
	}
	public void setUrlMaps(String urlMaps) {
		this.urlMaps = urlMaps;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public CompanyCityPK getIdCompanyCity() {
		return idCompanyCity;
	}
	public void setIdCompanyCity(CompanyCityPK idCompanyCity) {
		this.idCompanyCity = idCompanyCity;
	}

}
