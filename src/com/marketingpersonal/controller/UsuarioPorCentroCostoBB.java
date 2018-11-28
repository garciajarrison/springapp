package com.marketingpersonal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.UsuarioPorCentroCosto;
import com.marketingpersonal.model.entity.Validacion;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IUsuarioService;

/**
 * Clase controladora para manejo de Usuarios por Centros de Costo
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */
@ManagedBean(name = "usuarioPorCentroCostoBB")
@ViewScoped
public class UsuarioPorCentroCostoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//Campos de la clase
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private ICentroCostoService centroCostoService;
	private Util util;
	private UsuarioPorCentroCosto usuarioPorCentroCosto;
	private UsuarioPorCentroCosto selectedUsuarioPorCentroCosto;
	private List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos;
	private UploadedFile file;
	private StreamedContent fileDescargar;
	
	private CentroCosto centroCosto;
	private Usuario responsable;
	private Usuario aprobadorInicial;
	private Usuario aprobadorFinal;
	
	private List<CentroCosto> lstCentroCostos;
	private List<Usuario> lstUsuarios;
	
	private Validacion validacion;
	private List<Validacion> listaValidacion;
	
	private int idr;
	private int idi;
	private int idf;
	private int idcc;
	
	/**
     * Constructor para controlador de Usuarios por Centros de Costo
     */
	public UsuarioPorCentroCostoBB() {
		util = Util.getInstance();
		usuarioPorCentroCosto = new UsuarioPorCentroCosto();
		selectedUsuarioPorCentroCosto = new UsuarioPorCentroCosto();
		listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
		lstCentroCostos = getCentroCostoService().getCentroCostos(true);
		lstUsuarios = getUsuarioService().getUsuarios();
	}
	
	/**
     * Método que valida la obligatoriedad de los campos
     * @param usu: Variable de tipo Usuario por Centro de Costo
     * @return permiteGuardar: variable booleana que indica si es posible guardar o no el nuevo usuario por centro de costo
     */
	private boolean validar(UsuarioPorCentroCosto cue) {
		boolean permiteGuardar = true;
		
		if(cue.getCentroCosto().getId() <= 0) {
			util.mostrarError("El campo Centro de Costo es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioResponsable().getId() <= 0) {
			util.mostrarError("El campo Responsable es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioAprobadorInicial().getId() <= 0) {
			util.mostrarError("El campo Aprobador Inicial es requerido.");
			permiteGuardar = false;
		}
		
		if(cue.getUsuarioAprobadorFinal().getId() <= 0) {
			util.mostrarError("El campo Aprobador Final es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	/**
     *Metodo para crear un nuevo usuario por centro de costo
     */
	public void addUsuarioPorCentroCosto() {
		try {
			boolean guardar = true;
			
			if(validar(usuarioPorCentroCosto)) {
				
				for(UsuarioPorCentroCosto usceco : listaUsuarioPorCentroCostos) {					
					if((usceco.getCentroCosto().getId()==usuarioPorCentroCosto.getCentroCosto().getId()) 
							&& (usceco.getUsuarioResponsable().getId()==usuarioPorCentroCosto.getUsuarioResponsable().getId())
							&& (usceco.getUsuarioAprobadorInicial().getId()==usuarioPorCentroCosto.getUsuarioAprobadorInicial().getId())
							&& (usceco.getUsuarioAprobadorFinal().getId()==usuarioPorCentroCosto.getUsuarioAprobadorFinal().getId())) {
						guardar = false;			
					}						
				}
				
				if(guardar) {
					getUsuarioService().addUsuarioPorCentroCosto(usuarioPorCentroCosto);
					listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
					usuarioPorCentroCosto = new UsuarioPorCentroCosto();
					util.mostrarMensaje("Registro agregado con éxito."); 
				}else {
					util.mostrarError("Ya existe un registro creado con los datos ingresados");
				}				
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}

	/**
     *Metodo para modificar un usuario por centro de costo
     */
	public void updateUsuarioPorCentroCosto() {
		try {
			boolean actualizar = true;
			
			selectedUsuarioPorCentroCosto.setIdcc(idcc);
			selectedUsuarioPorCentroCosto.setIdr(idr);
			selectedUsuarioPorCentroCosto.setIdi(idi);
			selectedUsuarioPorCentroCosto.setIdf(idf);
			
			if(validar(selectedUsuarioPorCentroCosto)) {
				for(UsuarioPorCentroCosto usceco : listaUsuarioPorCentroCostos) {	
					if (usceco.getId() != selectedUsuarioPorCentroCosto.getId()) {
						if((usceco.getCentroCosto().getId()==usuarioPorCentroCosto.getIdcc()) 
								&& (usceco.getUsuarioResponsable().getId()==usuarioPorCentroCosto.getIdr())
								&& (usceco.getUsuarioAprobadorInicial().getId()==usuarioPorCentroCosto.getIdi())
								&& (usceco.getUsuarioAprobadorFinal().getId()==usuarioPorCentroCosto.getIdf())) {
							actualizar = false;			
						}	
					}
				}
				if(actualizar) {
					getUsuarioService().updateUsuarioPorCentroCosto(selectedUsuarioPorCentroCosto);
					listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
					selectedUsuarioPorCentroCosto = new UsuarioPorCentroCosto();
					idcc = 0;
					idr = 0;
					idi = 0;
					idf = 0;
					util.mostrarMensaje("Registro actualizado con éxito.");
				}else {
					util.mostrarError("Ya existe un registro creado con los datos ingresados");
				}	
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	/**
     *Metodo para eliminar un usuario por centro de costo
     */
	public void deleteUsuarioPorCentroCosto() {
		try {
			getUsuarioService().deleteUsuarioPorCentroCosto(selectedUsuarioPorCentroCosto);
			listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		} 	
	}
	
	/**
     *Metodo para validar y cargar un archivo plano de usuarios por centros de costo
     */
	public void uploadPlanoUsuariosPorCentrosCosto(FileUploadEvent event) {
		
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
						
			if(validarArchivoPlano(workbook)) {
				insertarUsuarioPorCentroCosto(sheet);
				
				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Usuarios por Centros de Costo", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			
			workbook.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *Metodo para descarga de archivo plano de usuarios por cnetros de costo de ejemplo
	 */
	public StreamedContent getFileDescargar() {
    	InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/files/Archivo Plano Usuarios por Centros de Costo.xlsx");
        fileDescargar = new DefaultStreamedContent(stream, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "Archivo Plano Usuarios por Centros de Costo.xlsx");
        return fileDescargar;
    }
	
	/**
     *Metodo para validar usuarios por centros de costo desde archivo plano
     */
	private boolean validarArchivoPlano(XSSFWorkbook workbook) {
		
		boolean permiteGuardar = true;

		XSSFSheet sheet = workbook.getSheetAt(0);

		listaValidacion = new ArrayList<>();

		if (workbook.getNumberOfSheets() > 1) {
			validacion = new Validacion();
			validacion.setMensaje("El archivo de excel a cargar solo puede tener 1 hoja con los datos");
			validacion.setFila("--");
			validacion.setColumna("--");
			listaValidacion.add(validacion);
		}

		if (sheet.getPhysicalNumberOfRows() <= 1) {
			validacion = new Validacion();
			validacion.setMensaje("El archivo no contiene registros con datos");
			validacion.setFila("--");
			validacion.setColumna("--");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(0)+"").trim().equals("Centro Costo")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la primer columna debe ser Centro Costo");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(1)+"").trim().equals("Responsable")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la segunda columna debe ser Responsable");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(0).getCell(2)+"").trim().equals("Aprobador Inicial")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la tercera columna debe ser Aprobador Inicial");
			validacion.setFila("1");
			validacion.setColumna("C");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(0).getCell(3)+"").trim().equals("Aprobador Final")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la cuarta columna debe ser Aprobador Final");
			validacion.setFila("1");
			validacion.setColumna("D");
			listaValidacion.add(validacion);
		}
		
		//Validar que no existe un registro de centro de costo duplicado
		Row row;	
		
		int idCentroCosto;
		int idResponsable;
		int idAprobadorInicial;
		int idAprobadorFinal;

		for(UsuarioPorCentroCosto usceco : listaUsuarioPorCentroCostos) {	
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);	
				
				idCentroCosto = getIdCentroCostoByCentroCosto(row.getCell(0)+"");
				idResponsable = getIdUsuarioByUsuario(row.getCell(1)+"");
				idAprobadorInicial = getIdUsuarioByUsuario(row.getCell(2)+"");
				idAprobadorFinal = getIdUsuarioByUsuario(row.getCell(3)+"");
				
				if((usceco.getCentroCosto().getId()==idCentroCosto) 
						&& (usceco.getUsuarioResponsable().getId()==idResponsable)
						&& (usceco.getUsuarioAprobadorInicial().getId()==idAprobadorInicial)
						&& (usceco.getUsuarioAprobadorFinal().getId()==idAprobadorFinal)) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe un Centro de Costo, Responsable, Aprobador Inicial y Aprobador Final creado con lo datos ingresados");
					validacion.setFila((fila + 1) + "");
					validacion.setColumna("--");
					listaValidacion.add(validacion);
				}	
			}
		}	

		// Validar que no ingresen en el archivo plano una Gerencia, Direccion o
		// Jefatura que no exista
		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);

			// Obtenemos los ids de Cuenta y Centro de Costo a partir de los nombres
			// ingresados en el archivo plano
			// ya que el usuario no conoce los codigos
			idCentroCosto = getIdCentroCostoByCentroCosto(row.getCell(0)+"");
			idResponsable = getIdUsuarioByUsuario(row.getCell(1)+"");
			idAprobadorInicial = getIdUsuarioByUsuario(row.getCell(2)+"");
			idAprobadorFinal = getIdUsuarioByUsuario(row.getCell(3)+"");

			if (idCentroCosto == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El centro de costo: " + row.getCell(0) + "" + " no existe en el maestro de Centros de Costo");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("A");
				listaValidacion.add(validacion);
			}

			if (idResponsable == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El responsable: " + row.getCell(1) + "" + " no existe en el maestro de Usuario");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("B");
				listaValidacion.add(validacion);
			}
			
			if (idAprobadorInicial == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El aprobador inicial: " + row.getCell(2) + "" + " no existe en el maestro de Usuario");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("C");
				listaValidacion.add(validacion);
			}
			
			if (idAprobadorFinal == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El aprobador final: " + row.getCell(3) + "" + " no existe en el maestro de Usuario");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("D");
				listaValidacion.add(validacion);
			}
		}

		if (listaValidacion.size() >= 1) {
			permiteGuardar = false;
		}

		return permiteGuardar;
	}
    
	/**
     *Metodo para insertar usuarios por centros de costo desde archivo plano
     */
    public void insertarUsuarioPorCentroCosto(XSSFSheet sheet) {
    	Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();	
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);
			
			usuarioPorCentroCosto = new UsuarioPorCentroCosto();
			
			centroCosto = new CentroCosto();
			centroCosto.setId(getIdCentroCostoByCentroCosto(row.getCell(0)+""));		
			
			responsable = new Usuario();
			responsable.setId(getIdUsuarioByUsuario(row.getCell(1)+""));
			
			aprobadorInicial = new Usuario();
			aprobadorInicial.setId(getIdUsuarioByUsuario(row.getCell(2)+""));
			
			aprobadorFinal = new Usuario();
			aprobadorFinal.setId(getIdUsuarioByUsuario(row.getCell(3)+""));
			
			usuarioPorCentroCosto.setCentroCosto(centroCosto);
			usuarioPorCentroCosto.setUsuarioResponsable(responsable);
			usuarioPorCentroCosto.setUsuarioAprobadorInicial(aprobadorInicial);
			usuarioPorCentroCosto.setUsuarioAprobadorFinal(aprobadorFinal);
			
			getUsuarioService().addUsuarioPorCentroCosto(usuarioPorCentroCosto);		
		} 
		listaUsuarioPorCentroCostos = getUsuarioService().getUsuarioPorCentroCostos();
	}	
    
    /**
     *Metodo para obtener el id de un Centro de Costo a partir del codigo
     *@param v: variable que contiene el codigo del centro de costo
     */
    public int getIdCentroCostoByCentroCosto(String centroCosto) {
		for(CentroCosto ceco : lstCentroCostos) {
			if(ceco.getCentroCosto().equals(centroCosto.trim())) {
				return ceco.getId();
			}	
		}		
		return 0;
	}
    
    /**
     *Metodo para obtener el id de un Usuario a partir del usuario
     *@param usuario: variable que contiene el usuario
     */
    public int getIdUsuarioByUsuario(String usuario) {
		for(Usuario usu : lstUsuarios) {
			if(usu.getUsuario().equals(usuario.trim())) {
				return usu.getId();
			}	
		}		
		return 0;
	}

	public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public UsuarioPorCentroCosto getUsuarioPorCentroCosto() {
		return usuarioPorCentroCosto;
	}

	public void setUsuarioPorCentroCosto(UsuarioPorCentroCosto usuarioPorCentroCosto) {
		this.usuarioPorCentroCosto = usuarioPorCentroCosto;
	}

	public UsuarioPorCentroCosto getSelectedUsuarioPorCentroCosto() {
		return selectedUsuarioPorCentroCosto;
	}

	public void setSelectedUsuarioPorCentroCosto(UsuarioPorCentroCosto selectedUsuarioPorCentroCosto) {
		this.selectedUsuarioPorCentroCosto = selectedUsuarioPorCentroCosto;
		this.idr = selectedUsuarioPorCentroCosto.getUsuarioResponsable().getId();
		this.idi = selectedUsuarioPorCentroCosto.getUsuarioAprobadorInicial().getId();
		this.idf = selectedUsuarioPorCentroCosto.getUsuarioAprobadorFinal().getId();
		this.idcc = selectedUsuarioPorCentroCosto.getCentroCosto().getId();
	}

	public List<UsuarioPorCentroCosto> getListaUsuarioPorCentroCostos() {
		return listaUsuarioPorCentroCostos;
	}

	public void setListaUsuarioPorCentroCostos(List<UsuarioPorCentroCosto> listaUsuarioPorCentroCostos) {
		this.listaUsuarioPorCentroCostos = listaUsuarioPorCentroCostos;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	public Usuario getAprobadorInicial() {
		return aprobadorInicial;
	}

	public void setAprobadorInicial(Usuario aprobadorInicial) {
		this.aprobadorInicial = aprobadorInicial;
	}

	public Usuario getAprobadorFinal() {
		return aprobadorFinal;
	}

	public void setAprobadorFinal(Usuario aprobadorFinal) {
		this.aprobadorFinal = aprobadorFinal;
	}

	public List<CentroCosto> getLstCentroCostos() {
		return lstCentroCostos;
	}

	public void setLstCentroCostos(List<CentroCosto> lstCentroCostos) {
		this.lstCentroCostos = lstCentroCostos;
	}

	public List<Usuario> getLstUsuarios() {
		return lstUsuarios;
	}

	public void setLstUsuarios(List<Usuario> lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
	}

	public void setFileDescargar(StreamedContent fileDescargar) {
		this.fileDescargar = fileDescargar;
	}
	
	public Validacion getValidacion() {
		return validacion;
	}

	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;
	}

	public List<Validacion> getListaValidacion() {
		return listaValidacion;
	}

	public void setListaValidacion(List<Validacion> listaValidacion) {
		this.listaValidacion = listaValidacion;
	}

	public int getIdr() {
		return idr;
	}

	public void setIdr(int idr) {
		this.idr = idr;
	}

	public int getIdi() {
		return idi;
	}

	public void setIdi(int idi) {
		this.idi = idi;
	}

	public int getIdf() {
		return idf;
	}

	public void setIdf(int idf) {
		this.idf = idf;
	}

	public int getIdcc() {
		return idcc;
	}

	public void setIdcc(int idcc) {
		this.idcc = idcc;
	}
        
 }