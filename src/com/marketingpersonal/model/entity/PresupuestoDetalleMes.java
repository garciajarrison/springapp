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
@Table(name = "detalle_presupuesto_mes", schema = "presupuestoMD")
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

}
