package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
