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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "presupuesto", schema = "presupuestoMD")
public class Presupuesto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Integer anio;
	private String nombre;
	private String descripcion;
	private String tipo;
	private String clasificacion;	
	private Date fechaCreacion;
	private Usuario usuario;
	
	private List<PresupuestoDetalleMes> detalleMes = new ArrayList<>();
	private List<PresupuestoDetalleCampania> detalleCampania = new ArrayList<>();
	
	public Presupuesto() {
		this.usuario = new Usuario();
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
	
	@Column(name = "descripcion")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "tipo")
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(name = "clasificacion")
	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	@Column(name = "anio")
	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	@Column(name = "fecha_creacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	public List<PresupuestoDetalleMes> getDetalleMes() {
		return detalleMes;
	}

	public void setDetalleMes(List<PresupuestoDetalleMes> detalleMes) {
		this.detalleMes = detalleMes;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	public List<PresupuestoDetalleCampania> getDetalleCampania() {
		return detalleCampania;
	}

	public void setDetalleCampania(List<PresupuestoDetalleCampania> detalleCampania) {
		this.detalleCampania = detalleCampania;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
