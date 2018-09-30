package com.marketingpersonal.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "cuenta_x_centrocosto", schema = "presupuestoMD")
public class CentroCostoPorCuenta implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private CentroCosto centroCosto;
	private Cuenta cuenta;
	private Sublink sublink;
	
	public CentroCostoPorCuenta() {
		centroCosto = new CentroCosto();
		cuenta = new Cuenta();
		sublink = new Sublink();
	}

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cuenta", nullable = false)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sublink", nullable = false)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public Sublink getSublink() {
		return sublink;
	}

	public void setSublink(Sublink sublink) {
		this.sublink = sublink;
	}
	
}
