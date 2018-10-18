package com.marketingpersonal.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Home;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;
import com.marketingpersonal.service.IHomeService;
import com.marketingpersonal.service.IUsuarioService;


@ManagedBean(name = "globalBB")
@SessionScoped
public class GlobalBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IHomeService homeService;
	private Usuario usuario;
	private Locale locale;
	private Util util;
	private UploadedFile foto;
	private String urlFotoPerfil;
	private ListasGenericas listasGenericas;
	private List<Home> variablesMacroeconomicas;
	private boolean variasVariables;
	
	private boolean responsable;
	private boolean aprobadorInicial;
	private boolean aprobadorFinal;
	private List<UsuarioPorCentroCosto> listaUsuarioPorCentroCosto;
		
	
	public GlobalBB() {
		util = Util.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		listaUsuarioPorCentroCosto = getUsuarioService().getUsuarioPorCentroCostos();
		responsable = isResponsable(usuario);
		aprobadorInicial = isAprobadorInicial(usuario);
		aprobadorFinal = isAprobadorFinal(usuario);
		if(usuario != null) {
			listasGenericas = ListasGenericas.getInstance();
	        cargarFotoPerfil();
	        cargarVariablesMacroeconomicas();
		}
	}
	
	@PostConstruct
	public void validarSession() {
		util.validarSession();
	}
	
	private void cargarVariablesMacroeconomicas() {
		try {
			variablesMacroeconomicas = getHomeService().getHomes(true);
			if(variablesMacroeconomicas != null && !variablesMacroeconomicas.isEmpty()) {
				variasVariables = variablesMacroeconomicas.size() > 1;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent event) {
		foto = event.getFile();
        if(foto != null) {
        	urlFotoPerfil = util.crearFoto(usuario.getId()+"", foto.getContents());
            util.mostrarMensaje("Foto de perfil actualizada con éxito.");
        }
    }
	
	private void cargarFotoPerfil() {
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		StringBuilder url = new StringBuilder(servletContext.getRealPath("/").replace("/", Util.SEPARADOR_CARPETA))
				.append(Util.SEPARADOR_CARPETA)
				.append(Util.URL_FOTO_PERFIL)
				.append(usuario.getId()).append(".jpg");
		
		try {
			if(util.existeArchivo(url.toString())) {
				url = new StringBuilder(servletContext.getContextPath().replace("/", Util.SEPARADOR_CARPETA))
						.append(Util.SEPARADOR_CARPETA)
						.append(Util.URL_FOTO_PERFIL)
						.append(usuario.getId()).append(".jpg");
				urlFotoPerfil = url.toString();
			}else {
				url = new StringBuilder(servletContext.getContextPath().replace("/", Util.SEPARADOR_CARPETA)) 
						.append(Util.SEPARADOR_CARPETA)
						.append("resources").append(Util.SEPARADOR_CARPETA)
						.append("omega-layout").append(Util.SEPARADOR_CARPETA)
						.append("images").append(Util.SEPARADOR_CARPETA)
						.append("avatar_4.png");
				urlFotoPerfil = url.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método que se encarga del manejo de cierre de sesion del servicio
	 * @return Texto con la navegacion
	 * @throws IOException 
	 */
	public void cerrarSesion() throws IOException {
		util.cerrarSesion();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Locale getLocale() {
	    return locale;
	}

	public UploadedFile getFoto() {
		return foto;
	}

	public void setFoto(UploadedFile foto) {
		this.foto = foto;
	}

	public String getUrlFotoPerfil() {
		return urlFotoPerfil;
	}

	public void setUrlFotoPerfil(String urlFotoPerfil) {
		this.urlFotoPerfil = urlFotoPerfil;
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public ListasGenericas getListasGenericas() {
		return listasGenericas;
	}

	public void setListasGenericas(ListasGenericas listasGenericas) {
		this.listasGenericas = listasGenericas;
	}

	public IHomeService getHomeService() {
		return homeService;
	}

	public void setHomeService(IHomeService homeService) {
		this.homeService = homeService;
	}

	public List<Home> getVariablesMacroeconomicas() {
		return variablesMacroeconomicas;
	}

	public void setVariablesMacroeconomicas(List<Home> variablesMacroeconomicas) {
		this.variablesMacroeconomicas = variablesMacroeconomicas;
	}

	public boolean isVariasVariables() {
		return variasVariables;
	}

	public void setVariasVariables(boolean variasVariables) {
		this.variasVariables = variasVariables;
	}
	
	public boolean isResponsable(Usuario usuario) {
		for(UsuarioPorCentroCosto ucc : listaUsuarioPorCentroCosto) {
			if(ucc.getUsuarioResponsable().getId()==usuario.getId()) {
				return true;
			}	
		}	
		return false;
	}
	
	public boolean isAprobadorInicial(Usuario usuario) {
		for(UsuarioPorCentroCosto ucc : listaUsuarioPorCentroCosto) {
			if(ucc.getUsuarioAprobadorInicial().getId()==usuario.getId()) {
				return true;
			}	
		}	
		return false;
	}
	
	public boolean isAprobadorFinal(Usuario usuario) {
		for(UsuarioPorCentroCosto ucc : listaUsuarioPorCentroCosto) {
			if(ucc.getUsuarioAprobadorFinal().getId()==usuario.getId()) {
				return true;
			}	
		}	
		return false;
	}

	public boolean isResponsable() {
		return responsable;
	}

	public void setResponsable(boolean responsable) {
		this.responsable = responsable;
	}

	public boolean isAprobadorInicial() {
		return aprobadorInicial;
	}

	public void setAprobadorInicial(boolean aprobadorInicial) {
		this.aprobadorInicial = aprobadorInicial;
	}

	public boolean isAprobadorFinal() {
		return aprobadorFinal;
	}

	public void setAprobadorFinal(boolean aprobadorFinal) {
		this.aprobadorFinal = aprobadorFinal;
	}	

 }