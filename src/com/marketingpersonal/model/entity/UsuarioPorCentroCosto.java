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
@Table(name = "usuario_x_centrocosto", schema = "presupuestoMD")
public class UsuarioPorCentroCosto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private CentroCosto centroCosto;
	private Usuario usuarioResponsable;
	private Usuario usuarioAprobadorInicial;
	private Usuario usuarioAprobadorFinal;
	
	public UsuarioPorCentroCosto() {
		centroCosto = new CentroCosto();
		usuarioResponsable = new Usuario();
		usuarioAprobadorInicial = new Usuario();
		usuarioAprobadorFinal = new Usuario();
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_resp", nullable = false)
	public Usuario getUsuarioResponsable() {
		return usuarioResponsable;
	}

	public void setUsuarioResponsable(Usuario usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprini", nullable = false)
	public Usuario getUsuarioAprobadorInicial() {
		return usuarioAprobadorInicial;
	}

	public void setUsuarioAprobadorInicial(Usuario usuarioAprobadorInicial) {
		this.usuarioAprobadorInicial = usuarioAprobadorInicial;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprfin", nullable = false)
	public Usuario getUsuarioAprobadorFinal() {
		return usuarioAprobadorFinal;
	}

	public void setUsuarioAprobadorFinal(Usuario usuarioAprobadorFinal) {
		this.usuarioAprobadorFinal = usuarioAprobadorFinal;
	}
	
}
