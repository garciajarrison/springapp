package co.portalbands.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="album") 
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_album")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "album_date")
    private Date albumDate;
    @Column(name = "label")
    private String label;
    @Column(name = "picture")
    private String picture;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "price")
    private Double price;

    
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
	public Date getAlbumDate() {
		return albumDate;
	}
	public void setAlbumDate(Date albumDate) {
		this.albumDate = albumDate;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
    
}