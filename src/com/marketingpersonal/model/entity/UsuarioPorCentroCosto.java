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
@Table(name = "usuario_x_centrocosto", schema = "presupuestomd")
public class UsuarioPorCentroCosto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_centrocosto", nullable = false)
	private CentroCosto centroCosto = new CentroCosto();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_resp", nullable = false)
	private Usuario usuarioResponsable = new Usuario();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprini", nullable = false)
	private Usuario usuarioAprobadorInicial = new Usuario();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_aprfin", nullable = false)
	private Usuario usuarioAprobadorFinal = new Usuario();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Usuario getUsuarioResponsable() {
		return usuarioResponsable;
	}

	public void setUsuarioResponsable(Usuario usuarioResponsable) {
		this.usuarioResponsable = usuarioResponsable;
	}

	public Usuario getUsuarioAprobadorInicial() {
		return usuarioAprobadorInicial;
	}

	public void setUsuarioAprobadorInicial(Usuario usuarioAprobadorInicial) {
		this.usuarioAprobadorInicial = usuarioAprobadorInicial;
	}

	public Usuario getUsuarioAprobadorFinal() {
		return usuarioAprobadorFinal;
	}

	public void setUsuarioAprobadorFinal(Usuario usuarioAprobadorFinal) {
		this.usuarioAprobadorFinal = usuarioAprobadorFinal;
	}
	
}
