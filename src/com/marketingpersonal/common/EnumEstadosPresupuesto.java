package com.marketingpersonal.common;

/**
 * Enum para manejo de estados de presupuesto
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public enum EnumEstadosPresupuesto {

	PENDIENTE("PENDIENTE", "Pendiente de envio"),
	ENVIADO("ENVIADO", "Enviado al aprobador inicial"),
	RECHAZADO("RECHAZADO", "Rechazado"),
	APROBADO("APROBADO", "Enviado al aprobador final"),
	FINALIZADO("FINALIZADO", "Finalizado");
	
	private String codigo;
	private String nombre;
	
	private EnumEstadosPresupuesto(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

}
