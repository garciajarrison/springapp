package com.marketingpersonal.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.IParametroService;
import com.marketingpersonal.service.IUsuarioService;

@ManagedBean(name = "loginBB")
@ViewScoped
public class LoginBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private IUsuarioService usuarioService;
	private String clave;
	private Util util;
	
	private Usuario usuario = new Usuario();
	
	public LoginBB() {
		resetCampos();
		cerrarSession();
		util = Util.getInstance();
	}
	
	public void resetCampos() {
		usuario.setContrasena("");
	}
	
	private boolean validar() {
		boolean retorno = true;
		
		if(util.validaNuloVacio(usuario.getCorreo())) {
			retorno = false;
			util.mostrarError("El campo Usuario es requerido.");
		}
		
		if(util.validaNuloVacio(usuario.getContrasena())) {
			retorno = false;
			util.mostrarError("El campo Contraseña es requerido.");
		}
		
		return retorno;
	}

	public void login() {
		
		try {
			if(validar()) {
				usuario = this.getUsuarioService().login(usuario);

				if(usuario != null) {
					util.setSessionAttribute(EnumSessionAttributes.USUARIO, usuario);
					util.mostrarMensajeRedirect("Bienvenido: " + usuario.getNombre(), true);
					util.redirect("inicio.xhtml");
				} else{
					usuario = new Usuario();
					util.mostrarError("Datos de ingreso incorrectos.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			util.mostrarError("Datos de ingreso incorrectos");
		} 	
	}
	
	public void cerrarSession() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		}catch(Exception e) {}
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
 }