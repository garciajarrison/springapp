package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="country") 
public class Country implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_country")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "code")
	private String code;
	@Formula("SELECT name FROM LanguageName WHERE typeTable = 'COUNTRY' AND idConfigure = 1 AND idTable = id")
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
