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
import com.marketingpersonal.model.entity.CentroCostoPorCuenta;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Validacion;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;

/**
 * Clase controladora para manejo de Centros de Costo por Cuenta
 * @author Jarrison Garcia, Juan Camilo Monsalve 
 * @date 30/10/2018
 */

@ManagedBean(name = "centroCostoPorCuentaBB")
@ViewScoped
public class CentroCostoPorCuentaBB extends SpringBeanAutowiringSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	//Campos de la clase
	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICuentaService cuentaService;
	private Util util;
	private CentroCostoPorCuenta centroCostoPorCuenta;
	private CentroCostoPorCuenta selectedCentroCostoPorCuenta;
	private List<CentroCostoPorCuenta> listaCentroCostoPorCuentas;
	private UploadedFile file;
	private StreamedContent fileDescargar;

	private Cuenta cuenta;
	private CentroCosto centroCosto;

	private List<Cuenta> lstCuenta;
	private List<CentroCosto> lstCentroCosto;

	private Validacion validacion;
	private List<Validacion> listaValidacion;

	/**
     * Constructor para controlador de Centros de Costo por Cuenta
     */
	public CentroCostoPorCuentaBB() {
		util = Util.getInstance();
		centroCostoPorCuenta = new CentroCostoPorCuenta();
		selectedCentroCostoPorCuenta = new CentroCostoPorCuenta();
		listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();

		lstCuenta = getCuentaService().getCuentas(true);
		lstCentroCosto = getCentroCostoService().getCentroCostos(true);
	}

	/**
     * Método que valida la obligatoriedad de los campos
     * @param cue: Variable de tipo Centro de Costo por Cuenta
     * @return permiteGuardar: variable booleana que indica si es posible guardar o no el nuevo centro de costo por cuenta
     */
	private boolean validar(CentroCostoPorCuenta cue) {
		boolean permiteGuardar = true;

		if (cue.getCuenta().getId() <= 0) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}

		if (cue.getCentroCosto().getId() <= 0) {
			util.mostrarError("El campo Centro de Costo es requerido.");
			permiteGuardar = false;
		}

		return permiteGuardar;
	}

	/**
     *Metodo para crear un nuevo Centro de Costo por Cuenta
     */
	public void addCentroCostoPorCuenta() {
		try {
			boolean guardar = true;

			if (validar(centroCostoPorCuenta)) {

				for (CentroCostoPorCuenta ceco : listaCentroCostoPorCuentas) {
					if ((ceco.getCuenta().getId() == centroCostoPorCuenta.getCuenta().getId())
							&& (ceco.getCentroCosto().getId() == centroCostoPorCuenta.getCentroCosto().getId())) {
						guardar = false;
					}
				}

				if (guardar) {
					getCentroCostoService().addCentroCostoPorCuenta(centroCostoPorCuenta);
					listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
					centroCostoPorCuenta = new CentroCostoPorCuenta();
					util.mostrarMensaje("Registro agregado con éxito.");
				} else {
					util.mostrarError("Ya existe una Cuenta y Centro de Costo creado con los datos ingresados");
				}
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}

	/**
     *Metodo para modificar un Centro de Costo por Cuenta
     */
	public void updateCentroCostoPorCuenta() {
		try {
			boolean actualizar = true;

			if (validar(selectedCentroCostoPorCuenta)) {
				for (CentroCostoPorCuenta ceco : listaCentroCostoPorCuentas) {
					if (ceco.getId() != selectedCentroCostoPorCuenta.getId()) {
						if ((ceco.getCuenta().getId() == centroCostoPorCuenta.getCuenta().getId())
								&& (ceco.getCentroCosto().getId() == centroCostoPorCuenta.getCentroCosto().getId())) {
							actualizar = false;
						}
					}
				}

				if (actualizar) {
					getCentroCostoService().updateCentroCostoPorCuenta(selectedCentroCostoPorCuenta);
					listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
					selectedCentroCostoPorCuenta = new CentroCostoPorCuenta();
					util.mostrarMensaje("Registro actualizado con éxito.");
				} else {
					util.mostrarError("Ya existe una Cuenta y Centro de Costo creado con los datos ingresados");
				}
			}

		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		}
	}

	/**
     *Metodo para eliminar un Centro de Costo por Cuenta
     */
	public void deleteCentroCostoPorCuenta() {
		try {
			getCentroCostoService().deleteCentroCostoPorCuenta(selectedCentroCostoPorCuenta);
			listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
			util.mostrarMensaje("Registro eliminado con éxito.");

		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}

	/**
     *Metodo para validar y cargar un archivo plano de Centros de Costo por Cuenta
     *@param event variable que contiene información del archivo plano a cargar
     */
	public void uploadPlanoCentrosCostoPorCuenta(FileUploadEvent event) {

		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			XSSFSheet sheet = workbook.getSheetAt(0);

			if (validarArchivoPlano(workbook)) {
				insertarCentroCostoPorCuenta(sheet);

				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Cuentas por Centros de Costo",
						event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 *Metodo para descarga de archivo plano de Centros de Costo por Cuenta de ejemplo
	 */
	public StreamedContent getFileDescargar() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/files/Archivo Plano Cuentas por Centros de Costo.xlsx");
		fileDescargar = new DefaultStreamedContent(stream,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"Archivo Plano Cuentas por Centros de Costo.xlsx");
		return fileDescargar;
	}

	/**
     *Metodo para validar Centros de Costo por Cuenta desde archivo plano
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

		if (!(sheet.getRow(0).getCell(0)+"").trim().equals("Cuenta")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la primer columna debe ser Cuenta");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(1)+"").trim().equals("Centro de Costo")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la segunda columna debe ser Centro de Costo");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}

		// Validar que no existe un registro de centro de costo duplicado
		Row row;

		int idCuenta;
		int idCentroCosto;

		for (CentroCostoPorCuenta ceco : listaCentroCostoPorCuentas) {
			for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
				row = sheet.getRow(fila);

				idCuenta = getIdCuentaByCuenta((row.getCell(0) + "").trim());
				idCentroCosto = getIdCentroCostoByCentroCosto((row.getCell(1) + "").trim());

				if ((ceco.getCuenta().getId() == idCuenta) && (ceco.getCentroCosto().getId() == idCentroCosto)) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe un Centro de Costo y Cuenta creado con lo datos ingresados");
					validacion.setFila((fila + 1) + "");
					validacion.setColumna("--");
					listaValidacion.add(validacion);
				}
			}
		}
		
		// Validar que no ingresen en el archivo plano una Cuenta o
		// Centro de Costo que no exista
		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);

			// Obtenemos los ids de Cuenta y Centro de Costo a partir de los nombres
			// ingresados en el archivo plano
			// ya que el usuario no conoce los codigos
			idCuenta = getIdCuentaByCuenta((row.getCell(0) + "").trim());
			idCentroCosto = getIdCentroCostoByCentroCosto((row.getCell(1) + "").trim());

			if (idCuenta == 0) {
				validacion = new Validacion();
				validacion.setMensaje("La cuenta: " + row.getCell(0) + "" + " no existe en el maestro de Cuentas");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("A");
				listaValidacion.add(validacion);
			}

			if (idCentroCosto == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El centro de costo: " + row.getCell(1) + "" + " no existe en el maestro de Centros de Costo");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("B");
				listaValidacion.add(validacion);
			}
		}

		if (listaValidacion.size() >= 1) {
			permiteGuardar = false;
		}

		return permiteGuardar;
	}

	/**
     *Metodo para insertar Centros de Costo por Cuenta desde archivo plano
     *@param sheet: variable que contiene la hoja del archivo de excel
     */
	public void insertarCentroCostoPorCuenta(XSSFSheet sheet) {
		Row row;
		int numFilas = sheet.getPhysicalNumberOfRows();
		for (int fila = 1; fila < numFilas; fila++) {
			row = sheet.getRow(fila);

			centroCostoPorCuenta = new CentroCostoPorCuenta();

			cuenta = new Cuenta();
			cuenta.setId(getIdCuentaByCuenta(row.getCell(0) + ""));

			centroCosto = new CentroCosto();
			centroCosto.setId(getIdCentroCostoByCentroCosto(row.getCell(1) + ""));

			centroCostoPorCuenta.setCentroCosto(centroCosto);
			centroCostoPorCuenta.setCuenta(cuenta);

			getCentroCostoService().addCentroCostoPorCuenta(centroCostoPorCuenta);
		}
		listaCentroCostoPorCuentas = getCentroCostoService().getCentroCostoPorCuentas();
	}

	/**
     *Metodo para obtener el id de una cuenta a partir del nombre
     *@param cuenta: variable que contiene el nombre de la cuenta
     */
	public int getIdCuentaByCuenta(String cuenta) {
		for (Cuenta cue : lstCuenta) {
			if (cue.getCuenta().equals(cuenta.trim())) {
				return cue.getId();
			}
		}
		return 0;
	}

	/**
     *Metodo para obtener el id de un Centro de costo a partir del codigo
     *@param centroCosto: variable que contiene el Centro de Costo
     */
	public int getIdCentroCostoByCentroCosto(String centroCosto) {
		for (CentroCosto ceco : lstCentroCosto) {
			if (ceco.getCentroCosto().equals(centroCosto.trim())) {
				return ceco.getId();
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

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public CentroCostoPorCuenta getCentroCostoPorCuenta() {
		return centroCostoPorCuenta;
	}

	public void setCentroCostoPorCuenta(CentroCostoPorCuenta centroCostoPorCuenta) {
		this.centroCostoPorCuenta = centroCostoPorCuenta;
	}

	public CentroCostoPorCuenta getSelectedCentroCostoPorCuenta() {
		return selectedCentroCostoPorCuenta;
	}

	public void setSelectedCentroCostoPorCuenta(CentroCostoPorCuenta selectedCentroCostoPorCuenta) {
		this.selectedCentroCostoPorCuenta = selectedCentroCostoPorCuenta;
	}

	public List<CentroCostoPorCuenta> getListaCentroCostoPorCuentas() {
		return listaCentroCostoPorCuentas;
	}

	public void setListaCentroCostoPorCuentas(List<CentroCostoPorCuenta> listaCentroCostoPorCuentas) {
		this.listaCentroCostoPorCuentas = listaCentroCostoPorCuentas;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public CentroCosto getCentroCosto() {
		return centroCosto;
	}

	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}

	public List<Cuenta> getLstCuenta() {
		return lstCuenta;
	}

	public void setLstCuenta(List<Cuenta> lstCuenta) {
		this.lstCuenta = lstCuenta;
	}

	public List<CentroCosto> getLstCentroCosto() {
		return lstCentroCosto;
	}

	public void setLstCentroCosto(List<CentroCosto> lstCentroCosto) {
		this.lstCentroCosto = lstCentroCosto;
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