package com.marketingpersonal.common;

public enum EnumEstadosPresupuesto {

	ENVIADO("ENVIADO", "Enviado al aprobador inicial"),
	RECHAZADO("RECHAZADO", "Rechazado aprobador incial"),
	APROBADO("APROBADO", "Enviado al aprobador final"),
	RECHAZADO2("RECHAZADO2", "Rechazado por aprobador final"),
	FINALIZADO("FINALIZADO", "Finalizado");
	
	private String nombre;
	private String codigo;
	
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
