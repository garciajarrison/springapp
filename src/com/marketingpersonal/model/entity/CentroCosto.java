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

@Entity
@Table(name = "centrocosto", schema = "presupuestomd")
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
	private Gerencia gerencia = new Gerencia();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_direccion", nullable = false)
	private Direccion direccion = new Direccion();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_jefatura", nullable = false)
	private Jefatura jefatura = new Jefatura();
	
	@Column(name = "estado")
	private boolean estado = true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Jefatura getJefatura() {
		return jefatura;
	}

	public void setJefatura(Jefatura jefatura) {
		this.jefatura = jefatura;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
