package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="language_name") 
public class LanguageName implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_language")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "type_table")
	private String typeTable;
	@Column(name = "id_table")
	private String idTable;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeTable() {
		return typeTable;
	}
	public void setTypeTable(String typeTable) {
		this.typeTable = typeTable;
	}
	public String getIdTable() {
		return idTable;
	}
	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}
	
	

}
