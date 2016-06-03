package co.bandsportal.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Song implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_song")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "compositer")
	private String compositer;
	@Column(name = "lyric")
	private String lyric;
	@Column(name = "tablature")
	private String tablature;
	@Column(name = "partiture")
	private String partiture;
	@Column(name = "release_date")
	private Date releaseDate;
	@Column(name = "song")
	private String song;
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
	public String getCompositer() {
		return compositer;
	}
	public void setCompositer(String compositer) {
		this.compositer = compositer;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public String getTablature() {
		return tablature;
	}
	public void setTablature(String tablature) {
		this.tablature = tablature;
	}
	public String getPartiture() {
		return partiture;
	}
	public void setPartiture(String partiture) {
		this.partiture = partiture;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getSong() {
		return song;
	}
	public void setSong(String song) {
		this.song = song;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
