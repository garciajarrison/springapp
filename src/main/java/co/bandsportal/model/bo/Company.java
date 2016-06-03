package co.bandsportal.model.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;


@Entity
@Table(name="company") 
@NamedNativeQuery(name="implicitSample", 
					query="select * from SpaceShip", 
					resultSetMapping="implicit")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_company")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "bussiness_name")
	private String bussinessName;
	@Column(name = "address")
	private String address;
	@Column(name = "phone")
	private String phone;
	@Column(name = "cellphone")
	private String cellphone;
	@Column(name = "nit")
	private String nit;
	@Column(name = "web_page")
	private String webPage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBussinessName() {
		return bussinessName;
	}
	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getNit() {
		return nit;
	}
	public void setNit(String nit) {
		this.nit = nit;
	}
	public String getWebPage() {
		return webPage;
	}
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}
	
}
