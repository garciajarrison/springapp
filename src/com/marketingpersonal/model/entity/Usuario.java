package com.marketingpersonal.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "usuario", schema = "presupuestoMD")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "numero_documento")
	private String numeroDocumento;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "cargo")
	private String cargo;
	
	@Column(name = "rol")
	private String rol;
	
	@Transient
	private String contrasena;
	
	@Column(name = "estado")
	private boolean estado = true;

}
