package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;

public class CompanyCity implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Column(name = "address")
	private int address;
	@Column(name = "url_maps")
	private String urlMaps;
	@Column(name = "phone")
	private String phone;
	
	
}
