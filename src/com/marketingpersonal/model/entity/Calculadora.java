package com.marketingpersonal.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calculadora", schema = "presupuestoMD")
public class Calculadora implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int campana;
	private int mes;
	private int anio;
	private String tipo;
	private double porcentaje;
	
	public Calculadora() {
	}
	
	public Calculadora(int campana, int mes, double porcentaje, String tipo) {
		this.campana = campana;
		this.mes = mes;
		this.porcentaje = porcentaje;
		this.tipo = tipo;
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
	
	@Column(name = "campana")
	public int getCampana() {
		return campana;
	}

	public void setCampana(int campana) {
		this.campana = campana;
	}

	@Column(name = "mes")
	public int getMes() {
		return mes;
	}
	
	public void setMes(int mes) {
		this.mes = mes;
	}

	@Column(name = "anio")
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@Column(name = "porcentaje")
	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	@Column(name = "tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
}
