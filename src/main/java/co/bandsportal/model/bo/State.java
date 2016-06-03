package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class State implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_state")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "code")
	private String code;
	
	
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
		

}
