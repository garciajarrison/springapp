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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoHistorico;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.Validacion;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;
import com.marketingpersonal.service.IPresupuestoHistoricoService;

@ManagedBean(name = "presupuestoHistoricoBB")
@ViewScoped
public class PresupuestoHistoricoBB extends SpringBeanAutowiringSupport implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoHistoricoService presupuestoHistoricoService;
	@Autowired
	private ICuentaService cuentaService;
	@Autowired
	private ICentroCostoService centroCostoService;

	private Util util;
	private PresupuestoHistorico presupuestoHistorico;
	private PresupuestoHistorico selectedPresupuestoHistorico;
	private List<PresupuestoHistorico> listaPresupuestoHistorico;

	private Usuario usuario;
	private List<Cuenta> listaCuentas;
	private List<CentroCosto> listaCentroCostos;
	private Integer anioGeneral;

	// Carga Archivo Plano Historico
	private UploadedFile file;
	private StreamedContent fileDescargar;
	private Validacion validacion;
	private List<Validacion> listaValidacion;

	public PresupuestoHistoricoBB() {
		util = Util.getInstance();
		presupuestoHistorico = new PresupuestoHistorico();
		selectedPresupuestoHistorico = new PresupuestoHistorico();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		listaCuentas = this.getCuentaService().getCuentas(true);
		listaCentroCostos = this.getCentroCostoService().getCentroCostos(true);
		listaPresupuestoHistorico = getPresupuestoHistoricoService().getPresupuestosHistoricosPorUsuario(usuario.getId());
	}

	/***
	 * Elimiaciones de presupuesto y detalle
	 */

	public void deletePresupuesto() {
		try {
			getPresupuestoHistoricoService().deletePresupuestoHistorico(selectedPresupuestoHistorico);
			// cargarListaPresupuesto();
			util.mostrarMensaje("Registro eliminado con éxito.");

		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}

	public IPresupuestoHistoricoService getPresupuestoHistoricoService() {
		return presupuestoHistoricoService;
	}

	public void setPresupuestoHistoricoService(IPresupuestoHistoricoService presupuestoHistoricoService) {
		this.presupuestoHistoricoService = presupuestoHistoricoService;
	}

	public ICuentaService getCuentaService() {
		return cuentaService;
	}

	public void setCuentaService(ICuentaService cuentaService) {
		this.cuentaService = cuentaService;
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

	public PresupuestoHistorico getPresupuestoHistorico() {
		return presupuestoHistorico;
	}

	public void setPresupuestoHistorico(PresupuestoHistorico presupuestoHistorico) {
		this.presupuestoHistorico = presupuestoHistorico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Cuenta> getListaCuentas() {
		return listaCuentas;
	}

	public void setListaCuentas(List<Cuenta> listaCuentas) {
		this.listaCuentas = listaCuentas;
	}

	public List<CentroCosto> getListaCentroCostos() {
		return listaCentroCostos;
	}

	public void setListaCentroCostos(List<CentroCosto> listaCentroCostos) {
		this.listaCentroCostos = listaCentroCostos;
	}

	public Integer getAnioGeneral() {
		return anioGeneral;
	}

	public void setAnioGeneral(Integer anioGeneral) {
		this.anioGeneral = anioGeneral;
	}

	public PresupuestoHistorico getSelectedPresupuestoHistorico() {
		return selectedPresupuestoHistorico;
	}

	public void setSelectedPresupuestoHistorico(PresupuestoHistorico selectedPresupuestoHistorico) {
		this.selectedPresupuestoHistorico = selectedPresupuestoHistorico;
	}

	public StreamedContent getFileDescargar() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/files/Archivo Plano Presupuesto Histórico.xlsx");
		fileDescargar = new DefaultStreamedContent(stream,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"Archivo Plano Presupuesto Histórico.xlsx");
		return fileDescargar;
	}

	public void uploadPlanoHistorico(FileUploadEvent event) {

		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			XSSFSheet sheet = workbook.getSheetAt(0);

			if (validarArchivoPlano(workbook)) {
				insertarPlanoPresupuestoHistorico(sheet);

				FacesMessage msg = new FacesMessage("Carga Archivo Plano Presupuesto Histórico",
						event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				util.mostrarMensaje("Carga Archivo Plano Presupuesto Histórico " + 
						event.getFile().getFileName() + " fue cargado correctamente");
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

		if (!(getValorCelda(sheet.getRow(0), 0)).equals("Año")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna A debe ser Año");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 1)).equals("Cuenta")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna B debe ser Cuenta");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 2)).equals("Centro de Costo")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna C debe ser Centro de Costo");
			validacion.setFila("1");
			validacion.setColumna("C");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 3)).equals("Mes 1")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna D debe ser Mes 1");
			validacion.setFila("1");
			validacion.setColumna("D");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 4)).equals("Mes 2")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna E debe ser Mes 2");
			validacion.setFila("1");
			validacion.setColumna("E");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 5)).equals("Mes 3")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna F debe ser Mes 3");
			validacion.setFila("1");
			validacion.setColumna("F");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 6)).equals("Mes 4")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna G debe ser Mes 4");
			validacion.setFila("1");
			validacion.setColumna("G");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 7)).equals("Mes 5")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna H debe ser Mes 5");
			validacion.setFila("1");
			validacion.setColumna("H");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 8)).equals("Mes 6")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna I debe ser Mes 6");
			validacion.setFila("1");
			validacion.setColumna("I");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 9)).equals("Mes 7")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna J debe ser Mes 7");
			validacion.setFila("1");
			validacion.setColumna("J");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 10)).equals("Mes 8")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna K debe ser Mes 8");
			validacion.setFila("1");
			validacion.setColumna("K");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 11)).equals("Mes 9")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna L debe ser Mes 9");
			validacion.setFila("1");
			validacion.setColumna("L");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 12)).equals("Mes 10")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna M debe ser Mes 10");
			validacion.setFila("1");
			validacion.setColumna("M");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 13)).equals("Mes 11")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna N debe ser Mes 11");
			validacion.setFila("1");
			validacion.setColumna("N");
			listaValidacion.add(validacion);
		}

		if (!(getValorCelda(sheet.getRow(0), 14)).equals("Mes 12")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna O debe ser Mes 12");
			validacion.setFila("1");
			validacion.setColumna("O");
			listaValidacion.add(validacion);
		}

		Row row;
		int idCuenta;
		int idCentroCosto;

		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);

			if (getValorCelda(row, 0).equals("")) {
				validacion = new Validacion();
				validacion.setMensaje("Debe ingresar un año");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("A");
				listaValidacion.add(validacion);
			}

			idCuenta = getIdCuentaByCuenta(getValorCelda(row, 1));
			idCentroCosto = getIdCentroCostoByCentroCosto(getValorCelda(row, 2));

			if (idCuenta == 0) {
				validacion = new Validacion();
				validacion.setMensaje("La cuenta: " + (getValorCelda(row, 1)) + " no existe en el maestro de Cuentas");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("B");
				listaValidacion.add(validacion);
			}

			if (idCentroCosto == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El centro de costo: " + (getValorCelda(row, 2))
						+ " no existe en el maestro de Centros de Costo");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("C");
				listaValidacion.add(validacion);
			}
		}

		if (listaValidacion.size() >= 1) {
			permiteGuardar = false;
		}
		return permiteGuardar;
	}

	public int getIdCuentaByCuenta(String cuenta) {
		for (Cuenta cue : listaCuentas) {
			if (cue.getCuenta().equals(cuenta.trim())) {
				return cue.getId();
			}
		}
		return 0;
	}

	public int getIdCentroCostoByCentroCosto(String centroCosto) {
		for (CentroCosto ceco : listaCentroCostos) {
			if (ceco.getCentroCosto().equals(centroCosto.trim())) {
				return ceco.getId();
			}
		}
		return 0;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
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

	public void setFileDescargar(StreamedContent fileDescargar) {
		this.fileDescargar = fileDescargar;
	}

	// Carga Archivo Plano Nomina

	public void insertarPlanoPresupuestoHistorico(XSSFSheet sheet) {
		Row row;
		int idCuenta;
		int idCentroCosto;
		int numFilas = sheet.getPhysicalNumberOfRows();
		
		presupuestoHistorico = new PresupuestoHistorico();
		presupuestoHistorico.setAnio(2018);
		
		this.getPresupuestoHistoricoService().deletePresupuestoHistoricoPorAnio(Integer.parseInt(getValorCelda(sheet.getRow(1), 0).replace(".0", "")));
		
		for (int fila = 1; fila <= numFilas; fila++) {
			row = sheet.getRow(fila);

			idCuenta = getIdCuentaByCuenta(getValorCelda(row, 1));
			idCentroCosto = getIdCentroCostoByCentroCosto(getValorCelda(row, 2));
			if(idCuenta > 0 && idCentroCosto > 0) {
				presupuestoHistorico = new PresupuestoHistorico();
	
				presupuestoHistorico.setAnio(Integer.parseInt(getValorCelda(row, 0).replace(".0", "")));
				presupuestoHistorico.getCuenta().setId(idCuenta);
				presupuestoHistorico.getCentroCosto().setId(idCentroCosto);
				presupuestoHistorico.setValorM1(convertirADouble(getValorCelda(row, 3)));
				presupuestoHistorico.setValorM2(convertirADouble(getValorCelda(row, 4)));
				presupuestoHistorico.setValorM3(convertirADouble(getValorCelda(row, 5)));
				presupuestoHistorico.setValorM4(convertirADouble(getValorCelda(row, 6)));
				presupuestoHistorico.setValorM5(convertirADouble(getValorCelda(row, 7)));
				presupuestoHistorico.setValorM6(convertirADouble(getValorCelda(row, 8)));
				presupuestoHistorico.setValorM7(convertirADouble(getValorCelda(row, 9)));
				presupuestoHistorico.setValorM8(convertirADouble(getValorCelda(row, 10)));
				presupuestoHistorico.setValorM9(convertirADouble(getValorCelda(row, 11)));
				presupuestoHistorico.setValorM10(convertirADouble(getValorCelda(row, 12)));
				presupuestoHistorico.setValorM11(convertirADouble(getValorCelda(row, 13)));
				presupuestoHistorico.setValorM12(convertirADouble(getValorCelda(row, 14)));			
	
				this.getPresupuestoHistoricoService().addPresupuestoHistorico(presupuestoHistorico);
			}
		}
		presupuestoHistorico = new PresupuestoHistorico();
	}

	public String getValorCelda(Row row, int col) {
		try {	
			Cell cell = row.getCell(col, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			cell.setCellType(CellType.STRING);
			
			return cell.getStringCellValue();
		} catch (NullPointerException e) {
			return "";
		}
	}
	
	public double convertirADouble(String valor) {
		if(valor.equals("") || valor == null) {
			return 0;
		}else {
			return Double.parseDouble(valor);
		}
	}

	public List<PresupuestoHistorico> getListaPresupuestoHistorico() {
		return listaPresupuestoHistorico;
	}

	public void setListaPresupuestoHistorico(List<PresupuestoHistorico> listaPresupuestoHistorico) {
		this.listaPresupuestoHistorico = listaPresupuestoHistorico;
	}
	
	
	
}