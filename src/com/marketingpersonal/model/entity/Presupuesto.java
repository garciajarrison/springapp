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

@Entity
@Table(name = "presupuesto", schema = "presupuestoMD")
public class Presupuesto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String descripcion;
	private String tipo;
	private Integer anio;
	private Integer mesCampania;
	private String estado = "PENDIENTE";
	private CentroCosto centroCosto;
	private Cuenta cuenta;
	
	private List<PresupuestoDetalle> detalle = new ArrayList<>();
	private List<Observacion> observaciones = new ArrayList<>();
	
	public Presupuesto() {
		this.centroCosto = new CentroCosto();
		this.cuenta = new Cuenta();
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

	@Column(name = "tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "anio")
	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	@Column(name = "mes_campania")
	public Integer getMesCampania() {
		return mesCampania;
	}

	public void setMesCampania(Integer mesCampania) {
		this.mesCampania = mesCampania;
	}

	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	public List<PresupuestoDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<PresupuestoDetalle> detalle) {
		this.detalle = detalle;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	public List<Observacion> getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(List<Observacion> observaciones) {
		this.observaciones = observaciones;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cuenta", nullable = false)
	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
}
