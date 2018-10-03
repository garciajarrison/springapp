package com.marketingpersonal.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "usuario", schema = "presupuestoMD")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String numeroDocumento;
	private String nombre;
	private String usuario;
	private String correo;
	private String cargo;
	private String rol;
	private String contrasena;
	private boolean estado = true;

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "numero_documento")
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "usuario")
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "correo")
	public String getCorreo() {
		return this.correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@Column(name = "rol")
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Column(name = "cargo")
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Transient
	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	@Column(name = "estado")
	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
