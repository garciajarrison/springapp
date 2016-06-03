package co.bandsportal.model.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Video implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_video")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "video_date")
	private Date videoDate;
	@Column(name = "release_date")
	private Date releaseDate;
	@Column(name = "producer")
	private String producer;
	@Column(name = "lyric")
	private String lyric;
	@Column(name = "url")
	private String url;
	@Column(name = "official")
	private String official;
	
	
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
	public Date getVideoDate() {
		return videoDate;
	}
	public void setVideoDate(Date videoDate) {
		this.videoDate = videoDate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOfficial() {
		return official;
	}
	public void setOfficial(String official) {
		this.official = official;
	}
	
	

}
