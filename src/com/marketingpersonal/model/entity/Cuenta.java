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
@Table(name = "cuenta", schema = "presupuestoMD")
public class Cuenta implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "cuenta")
	private String cuenta;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "estado")
	private boolean estado = true;

	@Transient
	public String getCuentaNombre() {
		return cuenta + " - " + nombre;
	}
}
