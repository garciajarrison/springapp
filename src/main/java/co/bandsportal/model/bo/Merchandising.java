package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Merchandising implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_merchandising")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "picture")
	private String picture;
	@Column(name = "status")
	private String status;
	@Column(name = "price")
	private Double price;
	@Column(name = "quantity")
	private Double quantity;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	

}
