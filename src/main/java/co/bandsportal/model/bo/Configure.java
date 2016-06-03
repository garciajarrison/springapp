package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="configure") 
public class Configure implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_configure")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "language")
	private String language;
	@Column(name = "name")
	private String name;
	@Column(name = "status")
	private String status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
