package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PictureAlbum implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_picture_album")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "url")
	private String url;
	@Column(name = "picture")
	private String picture;
	@Column(name = "name")
	private String name;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
