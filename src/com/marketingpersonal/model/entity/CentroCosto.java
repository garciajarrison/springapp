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
@Table(name = "centrocosto", schema = "presupuestoMD")
public class CentroCosto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "centrocosto")
	private String centroCosto;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_gerencia", nullable = false)
	private Gerencia gerencia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_direccion", nullable = false)
	private Direccion direccion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_jefatura", nullable = false)
	private Jefatura jefatura;
	
	@Column(name = "estado")
	private boolean estado = true;

}
