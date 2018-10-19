package com.marketingpersonal.model.entity;

import java.util.ArrayList;
import java.util.Date;
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
@Table(name = "presupuesto", schema = "presupuestoMD")
public class Presupuesto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "anio")
	private Integer anio;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "clasificacion")
	private String clasificacion;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario = new Usuario();;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	private List<PresupuestoDetalleMes> detalleMes = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	private List<PresupuestoDetalleCampania> detalleCampania = new ArrayList<>();
	
}
