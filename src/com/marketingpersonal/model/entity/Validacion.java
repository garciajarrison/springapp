package com.marketingpersonal.model.entity;

/**
 * Clase que contiene los atributos para la entidad Validacion
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
public class Validacion {
	
	private String mensaje;
	private String fila;
	private String columna;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getFila() {
		return fila;
	}
	public void setFila(String fila) {
		this.fila = fila;
	}
	public String getColumna() {
		return columna;
	}
	public void setColumna(String columna) {
		this.columna = columna;
	}
	
}
