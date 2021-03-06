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

import org.apache.commons.lang3.text.WordUtils;
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
import com.marketingpersonal.model.entity.Direccion;
import com.marketingpersonal.model.entity.Gerencia;
import com.marketingpersonal.model.entity.Jefatura;
import com.marketingpersonal.model.entity.Validacion;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.IDireccionService;
import com.marketingpersonal.service.IGerenciaService;
import com.marketingpersonal.service.IJefaturaService;

/**
 * Clase controladora para manejo de Centros de Costo
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */

@ManagedBean(name = "centroCostoBB")
@ViewScoped
public class CentroCostoBB extends SpringBeanAutowiringSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	//Campos de la clase
	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private IGerenciaService gerenciaService;
	@Autowired
	private IDireccionService direccionService;
	@Autowired
	private IJefaturaService jefaturaService;
	private Util util;
	private CentroCosto centroCosto;
	private CentroCosto selectedCentroCosto;
	private List<CentroCosto> listaCentroCostos;
	private UploadedFile file;
	private StreamedContent fileDescargar;

	private Gerencia gerencia;
	private Direccion direccion;
	private Jefatura jefatura;

	// Listas
	private List<Gerencia> lstGerencias;
	private List<Direccion> lstDireccions;
	private List<Jefatura> lstJefaturas;

	private Validacion validacion;
	private List<Validacion> listaValidacion;

	/**
     * Constructor para controlador de Centros de Costo
     */
	public CentroCostoBB() {
		util = Util.getInstance();
		centroCosto = new CentroCosto();
		selectedCentroCosto = new CentroCosto();
		listaCentroCostos = getCentroCostoService().getCentroCostos(false);

		lstGerencias = getGerenciaService().getGerencias(true);
		lstDireccions = getDireccionService().getDirecciones(true);
		lstJefaturas = getJefaturaService().getJefaturas(true);
	}

	/**
     * M�todo que valida la obligatoriedad de los campos
     * @param cc: Variable de tipo Centro de Costo
     * @return permiteGuardar: variable booleana que indica si es posible guardar o no el nuevo centro de costo
     */
	private boolean validar(CentroCosto cc) {
		boolean permiteGuardar = true;

		if (cc.getCentroCosto() == null || "".equals(cc.getCentroCosto().trim())) {
			util.mostrarError("El campo Centro de Costo es requerido.");
			permiteGuardar = false;
		}

		if (cc.getGerencia().getId() <= 0) {
			util.mostrarError("El campo Gerencia es requerido.");
			permiteGuardar = false;
		}

		if (cc.getDireccion().getId() <= 0) {
			util.mostrarError("El campo Direcci�n es requerido.");
			permiteGuardar = false;
		}

		if (cc.getJefatura().getId() <= 0) {
			util.mostrarError("El campo Jefatura es requerido.");
			permiteGuardar = false;
		}

		return permiteGuardar;
	}

	/**
     *Metodo para crear un nuevo Centro de Costo
     */
	public void addCentroCosto() {
		try {
			boolean guardar = true;

			if (validar(centroCosto)) {

				for (CentroCosto ceco : listaCentroCostos) {

					if ((ceco.getCentroCosto().equals(centroCosto.getCentroCosto().trim()))
							&& (ceco.getGerencia().getId() == centroCosto.getGerencia().getId())
							&& (ceco.getDireccion().getId() == centroCosto.getDireccion().getId())
							&& (ceco.getJefatura().getId() == centroCosto.getJefatura().getId())) {
						guardar = false;
					}
				}

				if (guardar) {
					getCentroCostoService().addCentroCosto(centroCosto);
					listaCentroCostos = getCentroCostoService().getCentroCostos(false);
					centroCosto = new CentroCosto();
					util.mostrarMensaje("Registro agregado con �xito.");
				} else {
					util.mostrarError("Ya existe un Centro de Costo creado con lo datos ingresados");
				}
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}

	/**
     *Metodo para modificar un Centro de Costo
     */
	public void updateCentroCosto() {
		try {
			boolean actualizar = true;

			if (validar(selectedCentroCosto)) {
				for (CentroCosto ceco : listaCentroCostos) {
					if (ceco.getId() != selectedCentroCosto.getId()) {
						if ((ceco.getCentroCosto().equals(selectedCentroCosto.getCentroCosto().trim()))
								&& (ceco.getGerencia().getId() == selectedCentroCosto.getGerencia().getId())
								&& (ceco.getDireccion().getId() == selectedCentroCosto.getDireccion().getId())
								&& (ceco.getJefatura().getId() == selectedCentroCosto.getJefatura().getId())) {
							actualizar = false;
							break;
						}
					}
				}

				if (actualizar) {
					getCentroCostoService().updateCentroCosto(selectedCentroCosto);
					listaCentroCostos = getCentroCostoService().getCentroCostos(false);
					selectedCentroCosto = new CentroCosto();
					util.mostrarMensaje("Registro actualizado con �xito.");
				} else {
					util.mostrarError("Ya existe una Gerencia con el mismo nombre ingresado");
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		}
	}

	/**
     *Metodo para eliminar un Centro de Costo
     */
	public void deleteCentroCosto() {
		try {
			getCentroCostoService().deleteCentroCosto(selectedCentroCosto);
			listaCentroCostos = getCentroCostoService().getCentroCostos(false);
			util.mostrarMensaje("Registro eliminado con �xito.");

		} catch (DataAccessException e) {
			e.printStackTrace();
			if((e.toString()).contains("ConstraintViolationException")) {
				util.mostrarError("Error eliminando el registro. No puede eliminar una centro de costo que tenga cuentas o presupuestos asociados");
			}else {
				util.mostrarError("Error eliminando el registro.");
			}
		} 
	}
	
	/**
     *Metodo para validar y cargar un archivo plano de Centros de Costo
     *@param event variable que contiene informaci�n del archivo plano a cargar
     */
	public void uploadPlanoCentrosCosto(FileUploadEvent event) {

		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			XSSFSheet sheet = workbook.getSheetAt(0);

			if (validarArchivoPlano(workbook)) {
				insertarCentrosCosto(sheet);

				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Centros de Costo",
						event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *Metodo para descarga de archivo plano de Centros de Costo de ejemplo
	 */
	public StreamedContent getFileDescargar() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/files/Archivo Plano Centros de Costo.xlsx");
		fileDescargar = new DefaultStreamedContent(stream,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"Archivo Plano Centros de Costo.xlsx");
		return fileDescargar;
	}

	/**
     *Metodo para validar Centros de Costo desde archivo plano
     *@param workbook: variable que contiene libro de excel
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

		if (!(sheet.getRow(0).getCell(0)+"").trim().equals("Centro de Costo")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la primer columna debe ser Centro de Costo");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(1)+"").trim().equals("Gerencia")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la segunda columna debe ser Gerencia");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(2)+"").trim().equals("Direcci�n")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la tercer columna debe ser Direcci�n");
			validacion.setFila("1");
			validacion.setColumna("C");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(3)+"").trim().equals("Jefatura")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la cuarta columna debe ser Jefatura");
			validacion.setFila("1");
			validacion.setColumna("D");
			listaValidacion.add(validacion);
		}

		// Validar que no existe un registro de centro de costo duplicado
		Row row;

		int idGerencia;
		int idDireccion;
		int idJefatura;

		for (CentroCosto ceco : listaCentroCostos) {
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);

				// Obtenemos los ids de Gerencia, Direccion y Jefatura a partir de los nombres
				// ingresados en el archivo plano
				// ya que el usuario no conoce los codigos
				idGerencia = getIdGerenciaByNombre((row.getCell(1) + "").trim());
				idDireccion = getIdDireccionByNombre((row.getCell(2) + "").trim());
				idJefatura = getIdJefaturaByNombre((row.getCell(3) + "").trim());

				if ((ceco.getCentroCosto().equals((row.getCell(0) + "").trim())) && (ceco.getGerencia().getId() == idGerencia)
						&& (ceco.getDireccion().getId() == idDireccion) && (ceco.getJefatura().getId() == idJefatura)) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe un Centro de Costo creado con lo datos ingresados");
					validacion.setFila((fila+1)+"");
					validacion.setColumna("--");
					listaValidacion.add(validacion);
				}
			}
		}
		
		//Validar que no existan campos vacios
		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);

			if("".equals((row.getCell(0)+"").trim()) || "null".equals((row.getCell(0)+"").toLowerCase().trim())) {
				validacion = new Validacion();
				validacion.setMensaje("Debe ingresar un Centro de Costos");
				validacion.setFila((fila+1)+"");
				validacion.setColumna("A");
				listaValidacion.add(validacion);
			}			
		}
		
		//Validar que no ingresen en el archivo plano una Gerencia, Direccion o Jefatura que no exista 
		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);

			// Obtenemos los ids de Gerencia, Direccion y Jefatura a partir de los nombres
			// ingresados en el archivo plano
			// ya que el usuario no conoce los codigos
			idGerencia = getIdGerenciaByNombre((row.getCell(1) + "").trim());
			idDireccion = getIdDireccionByNombre((row.getCell(2) + "").trim());
			idJefatura = getIdJefaturaByNombre((row.getCell(3) + "").trim());

			if (idGerencia==0) {
				validacion = new Validacion();
				validacion.setMensaje("La gerencia: "+row.getCell(1) + ""+" no existe en el maestro de Gerencias");
				validacion.setFila((fila+1)+"");
				validacion.setColumna("B");
				listaValidacion.add(validacion);
			}
			
			if (idDireccion==0) {
				validacion = new Validacion();
				validacion.setMensaje("La direcci�n: "+row.getCell(2) + ""+" no existe en el maestro de Direcciones");
				validacion.setFila((fila+1)+"");
				validacion.setColumna("C");
				listaValidacion.add(validacion);
			}
			
			if (idJefatura==0) {
				validacion = new Validacion();
				validacion.setMensaje("La jefatura: "+row.getCell(3) + ""+" no existe en el maestro de Jefaturas");
				validacion.setFila((fila+1)+"");
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
     *Metodo para insertar Centros de Costo desde archivo plano
     *@param sheet: variable que contiene la hoja del archivo de excel
     */
	public void insertarCentrosCosto(XSSFSheet sheet) {
		Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);

			centroCosto = new CentroCosto();

			gerencia = new Gerencia();
			gerencia.setId(getIdGerenciaByNombre(row.getCell(1) + ""));

			direccion = new Direccion();
			direccion.setId(getIdDireccionByNombre(row.getCell(2) + ""));

			jefatura = new Jefatura();
			jefatura.setId(getIdJefaturaByNombre(row.getCell(3) + ""));

			centroCosto.setCentroCosto(row.getCell(0) + "");
			centroCosto.setGerencia(gerencia);
			centroCosto.setDireccion(direccion);
			centroCosto.setJefatura(jefatura);

			getCentroCostoService().addCentroCosto(centroCosto);
		}
		
		listaCentroCostos = getCentroCostoService().getCentroCostos(false);
	}

	/**
     *Metodo para obtener el id de una gerencia a partir del nombre
     *@param nombreGerencia: variable que contiene el nombre de la gerencia
     */
	public int getIdGerenciaByNombre(String nombreGerencia) {
		for (Gerencia ger : lstGerencias) {
			if (ger.getNombre().equals(WordUtils.capitalizeFully(nombreGerencia).trim())) {
				return ger.getId();
			}
		}
		return 0;
	}

	/**
     *Metodo para obtener el id de una direccion a partir del nombre
     *@param nombreDireccion: variable que contiene el nombre de la direccion
     */
	public int getIdDireccionByNombre(String nombreDireccion) {
		for (Direccion dir : lstDireccions) {
			if (dir.getNombre().equals(WordUtils.capitalizeFully(nombreDireccion))) {
				return dir.getId();
			}
		}
		return 0;
	}

	/**
     *Metodo para obtener el id de una jefatura a partir del nombre
     *@param nombreJefatura: variable que contiene el nombre de la jefatura
     */
	public int getIdJefaturaByNombre(String nombreJefatura) {
		for (Jefatura jef : lstJefaturas) {
			if (jef.getNombre().equals(WordUtils.capitalizeFully(nombreJefatura))) {
				return jef.getId();
			}
		}
		return 0;
	}

	public ICentroCostoService getCentroCostoService() {
		return centroCostoService;
	}

	public void setCentroCostoService(ICentroCostoService centroCostoService) {
		this.centroCostoService = centroCostoService;
	}

	public IGerenciaService getGerenciaService() {
		return gerenciaService;
	}

	public void setGerenciaService(IGerenciaService gerenciaService) {
		this.gerenciaService = gerenciaService;
	}

	public IDireccionService getDireccionService() {
		return direccionService;
	}

	public void setDireccionService(IDireccionService direccionService) {
		this.direccionService = direccionService;
	}

	public IJefaturaService getJefaturaService() {
		return jefaturaService;
	}

	public void setJefaturaService(IJefaturaService jefaturaService) {
		this.jefaturaService = jefaturaService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public CentroCosto getSelectedCentroCosto() {
		return selectedCentroCosto;
	}

	public void setSelectedCentroCosto(CentroCosto selectedCentroCosto) {
		this.selectedCentroCosto = selectedCentroCosto;
	}

	public List<CentroCosto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CentroCosto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Gerencia getGerencia() {
		return gerencia;
	}

	public void setGerencia(Gerencia gerencia) {
		this.gerencia = gerencia;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Jefatura getJefatura() {
		return jefatura;
	}

	public void setJefatura(Jefatura jefatura) {
		this.jefatura = jefatura;
	}

	public List<Gerencia> getLstGerencias() {
		return lstGerencias;
	}

	public void setLstGerencias(List<Gerencia> lstGerencias) {
		this.lstGerencias = lstGerencias;
	}

	public List<Direccion> getLstDireccions() {
		return lstDireccions;
	}

	public void setLstDireccions(List<Direccion> lstDireccions) {
		this.lstDireccions = lstDireccions;
	}

	public List<Jefatura> getLstJefaturas() {
		return lstJefaturas;
	}

	public void setLstJefaturas(List<Jefatura> lstJefaturas) {
		this.lstJefaturas = lstJefaturas;
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

}