package com.marketingpersonal.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gerencia", schema = "presupuestoMD")
public class Gerencia implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private boolean estado = true;
	
	private List<CentroCosto> lstCentroCostos = new ArrayList<>();

	public Gerencia() {
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
	
	@Column(name = "nombre")
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "estado")
	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gerencia")
	public List<CentroCosto> getLstCentroCostos() {
		return lstCentroCostos;
	}

	public void setLstCentroCostos(List<CentroCosto> lstCentroCostos) {
		this.lstCentroCostos = lstCentroCostos;
	}

}
