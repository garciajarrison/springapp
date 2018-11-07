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
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.marketingpersonal.common.EnumEstadosPresupuesto;

@Entity
@Table(name = "presupuesto", schema = "presupuestomd.dbo")
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
	private Usuario usuario = new Usuario();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	private List<PresupuestoDetalleMes> detalleMes = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "presupuesto")
	private List<PresupuestoDetalleCampania> detalleCampania = new ArrayList<>();
	
	@Formula(value="(isnull((SELECT TOP (1) p.estado " + 
			"		 FROM presupuestomd.dbo.detalle_presupuesto_mes p " + 
			"				where p.id_presupuesto = id), " + 
			"		isnull((SELECT TOP (1) e.estado " + 
			"		 FROM presupuestomd.dbo.detalle_presupuesto_campania e " + 
			"				where e.id_presupuesto = id),'PENDIENTE'))) ")
	private String estadoDetalle;
	
	@Formula(value="(isnull((select sum(d1.valor_m1) + sum(d1.valor_m2) + sum(d1.valor_m3) + sum(d1.valor_m4) + sum(d1.valor_m5) " + 
			"  + sum(d1.valor_m6) + sum(d1.valor_m7) + sum(d1.valor_m8) + sum(d1.valor_m9) + sum(d1.valor_m10) " + 
			"   + sum(d1.valor_m11) + sum(d1.valor_m12) " + 
			" from dbo.detalle_presupuesto_mes d1 " + 
			" where d1.id_presupuesto = id),0) +  " + 
			" isnull((select sum(d2.valor_c1) + sum(d2.valor_c2) + sum(d2.valor_c3) + sum(d2.valor_c4) + sum(d2.valor_c5)  " + 
			" + sum(d2.valor_c6) + sum(d2.valor_c7) + sum(d2.valor_c8) + sum(d2.valor_c9) + sum(d2.valor_c10)  " + 
			" + sum(d2.valor_c11) + sum(d2.valor_c12) + sum(d2.valor_c13) + sum(d2.valor_c14) + sum(d2.valor_c15)  " + 
			" + sum(d2.valor_c16) + sum(d2.valor_c17) + sum(d2.valor_c18) + sum(d2.valor_c19) + sum(d2.valor_c20)  " + 
			" + sum(d2.valor_c21) + sum(d2.valor_c22) + sum(d2.valor_c23) + sum(d2.valor_c24)  + sum(d2.valor_c25) " + 
			" from dbo.detalle_presupuesto_campania d2 " + 
			" where d2.id_presupuesto = id),0))")
	private Double total;

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
	
	@Transient
	public boolean isPermiteAcciones() {
		boolean retorno = true;
		try {
			if(this.getDetalleCampania() != null && !this.getDetalleCampania().isEmpty()) {
				for(PresupuestoDetalleCampania  tmp: this.getDetalleCampania()) {
					if(EnumEstadosPresupuesto.APROBADO.getCodigo().equals(tmp.getEstado()) || 
							EnumEstadosPresupuesto.ENVIADO.getCodigo().equals(tmp.getEstado()) || 
							EnumEstadosPresupuesto.FINALIZADO.getCodigo().equals(tmp.getEstado())) {
						retorno = false;
						break;
					}
				}
			}
			
			if(retorno && this.getDetalleMes() != null && !this.getDetalleMes().isEmpty()) {
				for(PresupuestoDetalleMes tmp: this.getDetalleMes()) {
					if(EnumEstadosPresupuesto.APROBADO.getCodigo().equals(tmp.getEstado()) || 
							EnumEstadosPresupuesto.ENVIADO.getCodigo().equals(tmp.getEstado()) || 
							EnumEstadosPresupuesto.FINALIZADO.getCodigo().equals(tmp.getEstado())) {
						retorno = false;
						break;
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public String getEstadoDetalle() {
		return estadoDetalle;
	}

	public void setEstadoDetalle(String estadoDetalle) {
		this.estadoDetalle = estadoDetalle;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
}
