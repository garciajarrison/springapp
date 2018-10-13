package com.marketingpersonal.model.entity;

import java.util.Date;

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
@Table(name = "observacion", schema = "presupuestoMD")
public class Observacion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String observacion;
	private Usuario usuarioEnvia;
	private Usuario usuarioRecibe;
	private PresupuestoDetalleMes presupuestoDetalleMes;
	private PresupuestoDetalleCampania presupuestoDetalleCampania;
	private Date fecha;
	private String estado;

	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "observacion")
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_envia", nullable = false)
	public Usuario getUsuarioEnvia() {
		return usuarioEnvia;
	}

	public void setUsuarioEnvia(Usuario usuarioEnvia) {
		this.usuarioEnvia = usuarioEnvia;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_recibe", nullable = false)
	public Usuario getUsuarioRecibe() {
		return usuarioRecibe;
	}

	public void setUsuarioRecibe(Usuario usuarioRecibe) {
		this.usuarioRecibe = usuarioRecibe;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_detalle_presupuesto_mes", nullable = false)
	public PresupuestoDetalleMes getPresupuestoDetalleMes() {
		return presupuestoDetalleMes;
	}

	public void setPresupuestoDetalleMes(PresupuestoDetalleMes presupuestoDetalleMes) {
		this.presupuestoDetalleMes = presupuestoDetalleMes;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_detalle_presupuesto_campania", nullable = false)
	public PresupuestoDetalleCampania getPresupuestoDetalleCampania() {
		return presupuestoDetalleCampania;
	}

	public void setPresupuestoDetalleCampania(PresupuestoDetalleCampania presupuestoDetalleCampania) {
		this.presupuestoDetalleCampania = presupuestoDetalleCampania;
	}

	@Column(name = "fecha")
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "estado")
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
