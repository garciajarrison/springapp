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
import javax.persistence.Table;

@Entity
@Table(name = "presupuesto", schema = "presupuestoMD")
public class Presupuesto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "anio")
	private Integer anio;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "clasificacion")
	private String clasificacion;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario = new Usuario();;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	private List<PresupuestoDetalleMes> detalleMes = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	private List<PresupuestoDetalleCampania> detalleCampania = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<PresupuestoDetalleMes> getDetalleMes() {
		return detalleMes;
	}

	public void setDetalleMes(List<PresupuestoDetalleMes> detalleMes) {
		this.detalleMes = detalleMes;
	}

	public List<PresupuestoDetalleCampania> getDetalleCampania() {
		return detalleCampania;
	}

	public void setDetalleCampania(List<PresupuestoDetalleCampania> detalleCampania) {
		this.detalleCampania = detalleCampania;
	}
	
}
