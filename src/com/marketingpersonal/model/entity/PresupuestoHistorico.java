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

@Entity
@Table(name = "presupuesto_historico", schema = "presupuestomd.dbo")
public class PresupuestoHistorico implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;	
	
	@Column(name = "anio")
	private Integer anio;	
	
	@Column(name = "valor_m1")
	private Double valorM1 = 0d;

	@Column(name = "valor_m2")
	private Double valorM2 = 0d;

	@Column(name = "valor_m3")
	private Double valorM3 = 0d;

	@Column(name = "valor_m4")
	private Double valorM4 = 0d;

	@Column(name = "valor_m5")
	private Double valorM5 = 0d;

	@Column(name = "valor_m6")
	private Double valorM6 = 0d;

	@Column(name = "valor_m7")
	private Double valorM7 = 0d;

	@Column(name = "valor_m8")
	private Double valorM8 = 0d;

	@Column(name = "valor_m9")
	private Double valorM9 = 0d;

	@Column(name = "valor_m10")
	private Double valorM10 = 0d;

	@Column(name = "valor_m11")
	private Double valorM11 = 0d;

	@Column(name = "valor_m12")
	private Double valorM12 = 0d;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	private CentroCosto centroCosto = new CentroCosto();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cuenta", nullable = false)
	private Cuenta cuenta = new Cuenta();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Double getValorM1() {
		return valorM1;
	}

	public void setValorM1(Double valorM1) {
		this.valorM1 = valorM1;
	}

	public Double getValorM2() {
		return valorM2;
	}

	public void setValorM2(Double valorM2) {
		this.valorM2 = valorM2;
	}

	public Double getValorM3() {
		return valorM3;
	}

	public void setValorM3(Double valorM3) {
		this.valorM3 = valorM3;
	}

	public Double getValorM4() {
		return valorM4;
	}

	public void setValorM4(Double valorM4) {
		this.valorM4 = valorM4;
	}

	public Double getValorM5() {
		return valorM5;
	}

	public void setValorM5(Double valorM5) {
		this.valorM5 = valorM5;
	}

	public Double getValorM6() {
		return valorM6;
	}

	public void setValorM6(Double valorM6) {
		this.valorM6 = valorM6;
	}

	public Double getValorM7() {
		return valorM7;
	}

	public void setValorM7(Double valorM7) {
		this.valorM7 = valorM7;
	}

	public Double getValorM8() {
		return valorM8;
	}

	public void setValorM8(Double valorM8) {
		this.valorM8 = valorM8;
	}

	public Double getValorM9() {
		return valorM9;
	}

	public void setValorM9(Double valorM9) {
		this.valorM9 = valorM9;
	}

	public Double getValorM10() {
		return valorM10;
	}

	public void setValorM10(Double valorM10) {
		this.valorM10 = valorM10;
	}

	public Double getValorM11() {
		return valorM11;
	}

	public void setValorM11(Double valorM11) {
		this.valorM11 = valorM11;
	}

	public Double getValorM12() {
		return valorM12;
	}

	public void setValorM12(Double valorM12) {
		this.valorM12 = valorM12;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

}
