package com.marketingpersonal.model.entity;

import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

@Entity
@Table(name = "detalle_presupuesto_campania", schema = "presupuestomd.dbo")
public class PresupuestoDetalleCampania implements java.io.Serializable {

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
	
	@Column(name = "valor_c1")
	private Double valorC1 = 0d;
	
	@Column(name = "valor_c2")
	private Double valorC2 = 0d;
	
	@Column(name = "valor_c3")
	private Double valorC3 = 0d;
	
	@Column(name = "valor_c4")
	private Double valorC4 = 0d;
	
	@Column(name = "valor_c5")
	private Double valorC5 = 0d;
	
	@Column(name = "valor_c6")
	private Double valorC6 = 0d;

	@Column(name = "valor_c7")
	private Double valorC7 = 0d;

	@Column(name = "valor_c8")
	private Double valorC8 = 0d;

	@Column(name = "valor_c9")
	private Double valorC9 = 0d;

	@Column(name = "valor_c10")
	private Double valorC10 = 0d;

	@Column(name = "valor_c11")
	private Double valorC11 = 0d;

	@Column(name = "valor_c12")
	private Double valorC12 = 0d;

	@Column(name = "valor_c13")
	private Double valorC13 = 0d;

	@Column(name = "valor_c14")
	private Double valorC14 = 0d;

	@Column(name = "valor_c15")
	private Double valorC15 = 0d;

	@Column(name = "valor_c16")
	private Double valorC16 = 0d;

	@Column(name = "valor_c17")
	private Double valorC17 = 0d;

	@Column(name = "valor_c18")
	private Double valorC18 = 0d;

	@Column(name = "valor_c19")
	private Double valorC19 = 0d;

	@Column(name = "valor_c20")
	private Double valorC20 = 0d;

	@Column(name = "valor_c21")
	private Double valorC21 = 0d;

	@Column(name = "valor_c22")
	private Double valorC22 = 0d;

	@Column(name = "valor_c23")
	private Double valorC23 = 0d;

	@Column(name = "valor_c24")
	private Double valorC24 = 0d;

	@Column(name = "valor_c25")
	private Double valorC25 = 0d;
	
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
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuestoDetalleCampania")
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

	public Double getValorC1() {
		return valorC1;
	}

	public void setValorC1(Double valorC1) {
		this.valorC1 = valorC1;
	}

	public Double getValorC2() {
		return valorC2;
	}

	public void setValorC2(Double valorC2) {
		this.valorC2 = valorC2;
	}

	public Double getValorC3() {
		return valorC3;
	}

	public void setValorC3(Double valorC3) {
		this.valorC3 = valorC3;
	}

	public Double getValorC4() {
		return valorC4;
	}

	public void setValorC4(Double valorC4) {
		this.valorC4 = valorC4;
	}

	public Double getValorC5() {
		return valorC5;
	}

	public void setValorC5(Double valorC5) {
		this.valorC5 = valorC5;
	}

	public Double getValorC6() {
		return valorC6;
	}

	public void setValorC6(Double valorC6) {
		this.valorC6 = valorC6;
	}

	public Double getValorC7() {
		return valorC7;
	}

	public void setValorC7(Double valorC7) {
		this.valorC7 = valorC7;
	}

	public Double getValorC8() {
		return valorC8;
	}

	public void setValorC8(Double valorC8) {
		this.valorC8 = valorC8;
	}

	public Double getValorC9() {
		return valorC9;
	}

	public void setValorC9(Double valorC9) {
		this.valorC9 = valorC9;
	}

	public Double getValorC10() {
		return valorC10;
	}

	public void setValorC10(Double valorC10) {
		this.valorC10 = valorC10;
	}

	public Double getValorC11() {
		return valorC11;
	}

	public void setValorC11(Double valorC11) {
		this.valorC11 = valorC11;
	}

	public Double getValorC12() {
		return valorC12;
	}

	public void setValorC12(Double valorC12) {
		this.valorC12 = valorC12;
	}

	public Double getValorC13() {
		return valorC13;
	}

	public void setValorC13(Double valorC13) {
		this.valorC13 = valorC13;
	}

	public Double getValorC14() {
		return valorC14;
	}

	public void setValorC14(Double valorC14) {
		this.valorC14 = valorC14;
	}

	public Double getValorC15() {
		return valorC15;
	}

	public void setValorC15(Double valorC15) {
		this.valorC15 = valorC15;
	}

	public Double getValorC16() {
		return valorC16;
	}

	public void setValorC16(Double valorC16) {
		this.valorC16 = valorC16;
	}

	public Double getValorC17() {
		return valorC17;
	}

	public void setValorC17(Double valorC17) {
		this.valorC17 = valorC17;
	}

	public Double getValorC18() {
		return valorC18;
	}

	public void setValorC18(Double valorC18) {
		this.valorC18 = valorC18;
	}

	public Double getValorC19() {
		return valorC19;
	}

	public void setValorC19(Double valorC19) {
		this.valorC19 = valorC19;
	}

	public Double getValorC20() {
		return valorC20;
	}

	public void setValorC20(Double valorC20) {
		this.valorC20 = valorC20;
	}

	public Double getValorC21() {
		return valorC21;
	}

	public void setValorC21(Double valorC21) {
		this.valorC21 = valorC21;
	}

	public Double getValorC22() {
		return valorC22;
	}

	public void setValorC22(Double valorC22) {
		this.valorC22 = valorC22;
	}

	public Double getValorC23() {
		return valorC23;
	}

	public void setValorC23(Double valorC23) {
		this.valorC23 = valorC23;
	}

	public Double getValorC24() {
		return valorC24;
	}

	public void setValorC24(Double valorC24) {
		this.valorC24 = valorC24;
	}

	public Double getValorC25() {
		return valorC25;
	}

	public void setValorC25(Double valorC25) {
		this.valorC25 = valorC25;
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
