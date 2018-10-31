package com.marketingpersonal.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_presupuesto_mes", schema = "presupuestomd.dbo")
public class PresupuestoDetalleMes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_presupuesto", nullable = false)
	private Presupuesto presupuesto = new Presupuesto();
	
	@Column(name = "observacion")
	private String observacion;
	
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
	
	@Column(name = "total")
	private Double total = 0d;
	
	@Column(name = "estado")
	private String estado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	private CentroCosto centroCosto = new CentroCosto();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cuenta", nullable = false)
	private Cuenta cuenta = new Cuenta();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprini", nullable = false)
	private Usuario usuarioAprobadorInicial = new Usuario();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprfin", nullable = false)
	private Usuario usuarioAprobadorFinal = new Usuario();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuestoDetalleMes")
	private List<Observacion> observaciones = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public Usuario getUsuarioAprobadorInicial() {
		return usuarioAprobadorInicial;
	}

	public void setUsuarioAprobadorInicial(Usuario usuarioAprobadorInicial) {
		this.usuarioAprobadorInicial = usuarioAprobadorInicial;
	}

	public Usuario getUsuarioAprobadorFinal() {
		return usuarioAprobadorFinal;
	}

	public void setUsuarioAprobadorFinal(Usuario usuarioAprobadorFinal) {
		this.usuarioAprobadorFinal = usuarioAprobadorFinal;
	}

	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
