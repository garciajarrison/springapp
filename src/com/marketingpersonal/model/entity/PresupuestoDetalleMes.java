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
@Table(name = "detalle_presupuesto_mes", schema = "presupuestoMD")
public class PresupuestoDetalleMes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Presupuesto presupuesto;
	private String observacion;
	private Double valorM1 = 0d;
	private Double valorM2 = 0d;
	private Double valorM3 = 0d;
	private Double valorM4 = 0d;
	private Double valorM5 = 0d;
	private Double valorM6 = 0d;
	private Double valorM7 = 0d;
	private Double valorM8 = 0d;
	private Double valorM9 = 0d;
	private Double valorM10 = 0d;
	private Double valorM11 = 0d;
	private Double valorM12 = 0d;
	private String estado;
	private CentroCosto centroCosto;
	private Cuenta cuenta;
	private Usuario usuarioAprobadorInicial;
	private Usuario usuarioAprobadorFinal;
	
	public PresupuestoDetalleMes() {
		presupuesto = new Presupuesto();
		centroCosto = new CentroCosto();
		cuenta = new Cuenta();
		usuarioAprobadorInicial = new Usuario();
		usuarioAprobadorFinal = new Usuario();
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
	@JoinColumn(name = "id_presupuesto", nullable = false)
	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	@Column(name = "observacion")
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@Column(name = "valor_m1")
	public Double getValorM1() {
		return valorM1;
	}

	public void setValorM1(Double valorM1) {
		this.valorM1 = valorM1;
	}
	
	@Column(name = "valor_m2")
	public Double getValorM2() {
		return valorM2;
	}

	public void setValorM2(Double valorM2) {
		this.valorM2 = valorM2;
	}
	
	@Column(name = "valor_m3")
	public Double getValorM3() {
		return valorM3;
	}

	public void setValorM3(Double valorM3) {
		this.valorM3 = valorM3;
	}
	
	@Column(name = "valor_m4")
	public Double getValorM4() {
		return valorM4;
	}

	public void setValorM4(Double valorM4) {
		this.valorM4 = valorM4;
	}
	
	@Column(name = "valor_m5")
	public Double getValorM5() {
		return valorM5;
	}

	public void setValorM5(Double valorM5) {
		this.valorM5 = valorM5;
	}
	
	@Column(name = "valor_m6")
	public Double getValorM6() {
		return valorM6;
	}

	public void setValorM6(Double valorM6) {
		this.valorM6 = valorM6;
	}
	
	@Column(name = "valor_m7")
	public Double getValorM7() {
		return valorM7;
	}

	public void setValorM7(Double valorM7) {
		this.valorM7 = valorM7;
	}
	
	@Column(name = "valor_m8")
	public Double getValorM8() {
		return valorM8;
	}

	public void setValorM8(Double valorM8) {
		this.valorM8 = valorM8;
	}
	
	@Column(name = "valor_m9")
	public Double getValorM9() {
		return valorM9;
	}

	public void setValorM9(Double valorM9) {
		this.valorM9 = valorM9;
	}
	
	@Column(name = "valor_m10")
	public Double getValorM10() {
		return valorM10;
	}

	public void setValorM10(Double valorM10) {
		this.valorM10 = valorM10;
	}
	
	@Column(name = "valor_m11")
	public Double getValorM11() {
		return valorM11;
	}

	public void setValorM11(Double valorM11) {
		this.valorM11 = valorM11;
	}
	
	@Column(name = "valor_m12")
	public Double getValorM12() {
		return valorM12;
	}

	public void setValorM12(Double valorM12) {
		this.valorM12 = valorM12;
	}
	
	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cuenta", nullable = false)
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprini", nullable = false)
	public Usuario getUsuarioAprobadorInicial() {
		return usuarioAprobadorInicial;
	}

	public void setUsuarioAprobadorInicial(Usuario usuarioAprobadorInicial) {
		this.usuarioAprobadorInicial = usuarioAprobadorInicial;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprfin", nullable = false)
	public Usuario getUsuarioAprobadorFinal() {
		return usuarioAprobadorFinal;
	}

	public void setUsuarioAprobadorFinal(Usuario usuarioAprobadorFinal) {
		this.usuarioAprobadorFinal = usuarioAprobadorFinal;
	}

}
