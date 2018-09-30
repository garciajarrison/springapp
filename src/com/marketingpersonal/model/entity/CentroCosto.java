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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "centrocosto", schema = "presupuestoMD")
public class CentroCosto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private Gerencia gerencia;
	private Direccion direccion;
	private Jefatura jefatura;
	private boolean estado = true;
	
	public CentroCosto() {
		gerencia = new Gerencia();
		direccion = new Direccion();
		jefatura = new Jefatura();
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
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_gerencia", nullable = false)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_direccion", nullable = false)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_jefatura", nullable = false)
	@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	public Jefatura getJefatura() {
		return jefatura;
	}

	public void setJefatura(Jefatura jefatura) {
		this.jefatura = jefatura;
	}

	@Column(name = "estado")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
