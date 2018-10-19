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
@Table(name = "observacion", schema = "presupuestoMD")
public class Observacion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "serial", name = "id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "observacion")
	private String observacion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_envia", nullable = false)
	private Usuario usuarioEnvia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_recibe", nullable = false)
	private Usuario usuarioRecibe;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_detalle_presupuesto_mes")
	private PresupuestoDetalleMes presupuestoDetalleMes;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_detalle_presupuesto_campania")
	private PresupuestoDetalleCampania presupuestoDetalleCampania;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "estado")
	private String estado;

}
