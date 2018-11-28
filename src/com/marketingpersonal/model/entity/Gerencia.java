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

/**
 * Clase que contiene los atributos para la entidad Gerencia
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@Entity
@Table(name = "gerencia", schema = "presupuestomd.dbo")
public class Gerencia implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "estado")
	private boolean estado = true;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gerencia")
	private List<CentroCosto> lstCentroCostos = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<CentroCosto> getLstCentroCostos() {
		return lstCentroCostos;
	}

	public void setLstCentroCostos(List<CentroCosto> lstCentroCostos) {
		this.lstCentroCostos = lstCentroCostos;
	}

}
