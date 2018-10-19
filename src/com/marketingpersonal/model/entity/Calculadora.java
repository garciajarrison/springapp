package com.marketingpersonal.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "calculadora", schema = "presupuestoMD")
public class Calculadora implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	@Column(name = "campana")
	private int campana;
	@Column(name = "mes")
	private int mes;
	@Column(name = "anio")
	private int anio;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "porcentaje")
	private double porcentaje;
	
}
