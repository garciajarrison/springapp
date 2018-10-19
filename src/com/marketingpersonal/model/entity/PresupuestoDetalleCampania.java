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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "detalle_presupuesto_campania", schema = "presupuestoMD")
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

}
