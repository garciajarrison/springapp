package com.marketingpersonal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Direccion;
import com.marketingpersonal.service.IDireccionService;

@ManagedBean(name = "direccionBB")
@ViewScoped
public class DireccionBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IDireccionService direccionService;
	private Util util;
	private Direccion direccion;
	private Direccion selectedDireccion;
	private List<Direccion> listaDirecciones;
	
	public DireccionBB() {
		util = Util.getInstance();
		direccion = new Direccion();
		selectedDireccion = new Direccion();
		listaDirecciones = getDireccionService().getDirecciones(false);
	}
	
	private boolean validar(Direccion dir) {
		boolean permiteGuardar = true;
		
		if(dir.getNombre() == null ||"".equals(dir.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addDireccion() {
		try {
			boolean guardar = true;
			
			//Validar obligatoriedad de campos
			if(validar(direccion)) {
				
				//Validar que no exista un registro duplicado
				for(Direccion dir : listaDirecciones) {
					if(dir.getNombre().equals(WordUtils.capitalizeFully(direccion.getNombre().trim()))) {
						guardar = false;	
						break;
					}
				}
				
				if(guardar) {
					getDireccionService().addDireccion(direccion);
					listaDirecciones = getDireccionService().getDirecciones(false);
					direccion = new Direccion();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe una Dirección con el mismo nombre");
				}
			}			
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 
	}

	public void updateDireccion() {
		try {
			boolean actualizar = true;
			if(validar(selectedDireccion)) {
				
				//Validar que no exista un registro duplicado
				for(Direccion dir : listaDirecciones) {
					if(dir.getId() != selectedDireccion.getId())	 {
						if(dir.getNombre().equals(WordUtils.capitalizeFully(selectedDireccion.getNombre().trim())))	 {
							actualizar = false;	
							break;
						}					
					}					
				}
				
				if(actualizar) {
					getDireccionService().updateDireccion(selectedDireccion);
					listaDirecciones = getDireccionService().getDirecciones(false);
					selectedDireccion = new Direccion();
					util.mostrarMensaje("Registro actualizado con éxito.");
				}else {
					util.mostrarError("Ya existe una Dirección con el mismo nombre");
				}
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		}
	}
	
	public void deleteDireccion() {
		try {
			getDireccionService().deleteDireccion(selectedDireccion);
			listaDirecciones = getDireccionService().getDirecciones(false);
			util.mostrarMensaje("Registro eliminado con éxito.");  			
		}  catch (DataAccessException e) {
			e.printStackTrace();
			if((e.toString()).contains("ConstraintViolationException")) {
				util.mostrarError("Error eliminando el registro. No puede eliminar una dirección que tenga centros de costo asociados");
			}else {
				util.mostrarError("Error eliminando el registro.");
			}
		} 	
	}

	public IDireccionService getDireccionService() {
		return direccionService;
	}

	public void setDireccionService(IDireccionService direccionService) {
		this.direccionService = direccionService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Direccion getSelectedDireccion() {
		return selectedDireccion;
	}

	public void setSelectedDireccion(Direccion selectedDireccion) {
		this.selectedDireccion = selectedDireccion;
	}

	public List<Direccion> getListaDirecciones() {
		return listaDirecciones;
	}

	public void setListaDirecciones(List<Direccion> listaDirecciones) {
		this.listaDirecciones = listaDirecciones;
	}

 }