package com.marketingpersonal.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Validacion {
	
	private String mensaje;
	private String fila;
	private String columna;
	
}
