package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="media_status") 
public class MediaStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_media_status")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "status")
	private String status;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
