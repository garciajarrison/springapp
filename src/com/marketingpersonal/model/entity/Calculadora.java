package com.marketingpersonal.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calculadora", schema = "presupuestomd")
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
	private float porcentaje;
	
	public Calculadora() {
	}
	
	public Calculadora(int id, int campana, int mes, int anio, String tipo, float porcentaje) {
		this.id = id;
		this.campana = campana;
		this.mes = mes;
		this.anio = anio;
		this.tipo = tipo;
		this.porcentaje = porcentaje;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCampana() {
		return campana;
	}
	public void setCampana(int campana) {
		this.campana = campana;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	
}
