package com.marketingpersonal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import com.marketingpersonal.common.EnumEstadosPresupuesto;
import com.marketingpersonal.common.EnumSessionAttributes;
import com.marketingpersonal.common.EnviarCorreo;
import com.marketingpersonal.common.ListasGenericas;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.CentroCosto;
import com.marketingpersonal.model.entity.Cuenta;
import com.marketingpersonal.model.entity.Direccion;
import com.marketingpersonal.model.entity.Gerencia;
import com.marketingpersonal.model.entity.Jefatura;
import com.marketingpersonal.model.entity.Observacion;
import com.marketingpersonal.model.entity.Presupuesto;
import com.marketingpersonal.model.entity.PresupuestoDetalleCampania;
import com.marketingpersonal.model.entity.PresupuestoDetalleMes;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.model.entity.Validacion;
import com.marketingpersonal.service.ICalculadoraService;
import com.marketingpersonal.service.ICentroCostoService;
import com.marketingpersonal.service.ICuentaService;
import com.marketingpersonal.service.IParametroService;
import com.marketingpersonal.service.IPresupuestoService;

@ManagedBean(name = "presupuestoBB")
@ViewScoped
public class PresupuestoBB extends SpringBeanAutowiringSupport implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private IPresupuestoService presupuestoService;
	@Autowired
	private ICuentaService cuentaService;
	@Autowired
	private ICentroCostoService centroCostoService;
	@Autowired
	private ICalculadoraService calculadoraService;
	@Autowired
	private IParametroService parametroService;
	
	private Util util;
	private Presupuesto presupuesto;
	private Presupuesto detalle;
	private Presupuesto selectedPresupuesto;
	private PresupuestoDetalleMes presupuestoDetalleMes;
	private PresupuestoDetalleMes selectedPresupuestoDetalleMes;
	private PresupuestoDetalleCampania presupuestoDetalleCampania;
	private PresupuestoDetalleCampania selectedPresupuestoDetalleCampania;
	private List<Presupuesto> listaPresupuestos;
	private ListasGenericas listasGenericas;
	private Usuario usuario;
	private Observacion observacion;
	private List<Cuenta> listaCuentas;
	private List<CentroCosto> listaCentroCostos;
	private boolean mostrarDetalle;
	private int camapanaMaxima;
	private Double totalMes = 0d;
	private Double totalCamp = 0d;
	private Integer anioGeneral;
	
	//Carga Archivo Plano Nomina
	private UploadedFile file;
	private StreamedContent fileDescargar;
	private Validacion validacion;
	private List<Validacion> listaValidacion;
	
	public PresupuestoBB() {
		util = Util.getInstance();
		presupuesto = new Presupuesto();
		presupuestoDetalleMes = new PresupuestoDetalleMes();
		presupuestoDetalleCampania = new PresupuestoDetalleCampania();
		selectedPresupuesto = new Presupuesto();
		listaPresupuestos = getPresupuestoService().getPresupuestos();
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		anioGeneral = Integer.valueOf(parametroService.getParametroByCodigo("ANIO_CALCULADORA").getValor());
		listaCuentas = this.getCuentaService().getCuentasPorUsuario(usuario.getId());
		mostrarDetalle = false;
		camapanaMaxima = getCalculadoraService().getCampanaMaxima(anioGeneral);
		observacion = new Observacion();
	}
	
	public void totalizarMes() {
		try {
			totalMes = 0d;
			totalMes += presupuestoDetalleMes.getValorM1();
			totalMes += presupuestoDetalleMes.getValorM2();
			totalMes += presupuestoDetalleMes.getValorM3();
			totalMes += presupuestoDetalleMes.getValorM4();
			totalMes += presupuestoDetalleMes.getValorM5();
			totalMes += presupuestoDetalleMes.getValorM6();
			totalMes += presupuestoDetalleMes.getValorM7();
			totalMes += presupuestoDetalleMes.getValorM8();
			totalMes += presupuestoDetalleMes.getValorM9();
			totalMes += presupuestoDetalleMes.getValorM10();
			totalMes += presupuestoDetalleMes.getValorM11();
			totalMes += presupuestoDetalleMes.getValorM12();
			presupuestoDetalleMes.setTotal(totalMes);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void totalizarCamp() {
		try {
			totalCamp = 0d;
			totalCamp += presupuestoDetalleCampania.getValorC1();
			totalCamp += presupuestoDetalleCampania.getValorC2();
			totalCamp += presupuestoDetalleCampania.getValorC3();
			totalCamp += presupuestoDetalleCampania.getValorC4();
			totalCamp += presupuestoDetalleCampania.getValorC5();
			totalCamp += presupuestoDetalleCampania.getValorC6();
			totalCamp += presupuestoDetalleCampania.getValorC7();
			totalCamp += presupuestoDetalleCampania.getValorC8();
			totalCamp += presupuestoDetalleCampania.getValorC9();
			totalCamp += presupuestoDetalleCampania.getValorC10();
			totalCamp += presupuestoDetalleCampania.getValorC11();
			totalCamp += presupuestoDetalleCampania.getValorC12();
			totalCamp += presupuestoDetalleCampania.getValorC13();
			totalCamp += presupuestoDetalleCampania.getValorC14();
			totalCamp += presupuestoDetalleCampania.getValorC15();
			totalCamp += presupuestoDetalleCampania.getValorC16();
			totalCamp += presupuestoDetalleCampania.getValorC17();
			totalCamp += presupuestoDetalleCampania.getValorC18();
			totalCamp += presupuestoDetalleCampania.getValorC19();
			totalCamp += presupuestoDetalleCampania.getValorC20();
			totalCamp += presupuestoDetalleCampania.getValorC21();
			totalCamp += presupuestoDetalleCampania.getValorC22();
			totalCamp += presupuestoDetalleCampania.getValorC23();
			totalCamp += presupuestoDetalleCampania.getValorC24();
			totalCamp += presupuestoDetalleCampania.getValorC25();
			presupuestoDetalleCampania.setTotal(totalCamp);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean validar(Presupuesto pr) {
		boolean permiteGuardar = true;
		
		if(pr.getNombre() == null || 
				"".equals(pr.getNombre().trim())) {
			util.mostrarError("El campo Nombre es requerido.");
			permiteGuardar = false;
		}
		
		if(pr.getClasificacion() == null || 
				"".equals(pr.getClasificacion().trim())) {
			util.mostrarError("El campo Clasificación es requerido.");
			permiteGuardar = false;
		}
				
		if(pr.getTipo() == null || 
				"".equals(pr.getTipo().trim())) {
			util.mostrarError("El campo Tipo es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}

	public void addPresupuesto() {
		try {
			if(validar(presupuesto)) {
				presupuesto.setAnio(anioGeneral);
				presupuesto.setUsuario(usuario);
				getPresupuestoService().addPresupuesto(presupuesto);
				listaPresupuestos = getPresupuestoService().getPresupuestos();
				presupuesto = new Presupuesto();
				util.mostrarMensaje("Registro agregado con éxito."); 
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		} 	
	}
	
	private boolean validarDetalle(Presupuesto detalle, PresupuestoDetalleMes mes, PresupuestoDetalleCampania campania) {
		
		boolean permiteGuardar = true;
		Cuenta cuentaV = null;
		CentroCosto centroCostoV = null;
		
		if("Mensual".equals(detalle.getTipo())) {
			cuentaV = mes.getCuenta();
			centroCostoV = mes.getCentroCosto();
		}else {
			cuentaV = campania.getCuenta();
			centroCostoV = campania.getCentroCosto();
		}
		
		if(cuentaV == null || cuentaV.getId() <= 0) {
			util.mostrarError("El campo Cuenta es requerido.");
			permiteGuardar = false;
		}
		
		if(centroCostoV == null || centroCostoV.getId() <= 0) {
			util.mostrarError("El campo Centro costo es requerido.");
			permiteGuardar = false;
		}
		
		return permiteGuardar;
	}
	
	public void addPresupuestoMes(){
		try {
			if(validarDetalle(detalle, presupuestoDetalleMes, null)){
				
				presupuestoDetalleMes.setEstado(EnumEstadosPresupuesto.PENDIENTE.getCodigo());
				presupuestoDetalleMes.setPresupuesto(detalle);
				presupuestoDetalleMes.setUsuarioAprobadorInicial(
						this.getCentroCostoService().getUsuarioAprobadorInicial(presupuestoDetalleMes.getCentroCosto().getId()).get(0));
				presupuestoDetalleMes.setUsuarioAprobadorFinal(
						this.getCentroCostoService().getUsuarioAprobadorFinal(presupuestoDetalleMes.getCentroCosto().getId()).get(0));
				
				this.getPresupuestoService().addPresupuestoDetalleMes(presupuestoDetalleMes);
				this.detalle.setDetalleMes(this.getPresupuestoService().getPresupuestoDetallesMes(detalle.getId()));
				presupuestoDetalleMes = new PresupuestoDetalleMes();
				totalMes = 0d;
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}
	
	public void addPresupuestoCampania(){
		try {
			if(validarDetalle(detalle, null,  presupuestoDetalleCampania)){
				
				presupuestoDetalleCampania.setEstado(EnumEstadosPresupuesto.PENDIENTE.getCodigo());
				presupuestoDetalleCampania.setPresupuesto(detalle);
				presupuestoDetalleCampania.setUsuarioAprobadorInicial(this.getCentroCostoService().getUsuarioAprobadorInicial(presupuestoDetalleCampania.getCentroCosto().getId()).get(0));
				presupuestoDetalleCampania.setUsuarioAprobadorFinal(this.getCentroCostoService().getUsuarioAprobadorFinal(presupuestoDetalleCampania.getCentroCosto().getId()).get(0));
				
				this.getPresupuestoService().addPresupuestoDetalleCampania(presupuestoDetalleCampania);
				this.detalle.setDetalleCampania(this.getPresupuestoService().getPresupuestoDetallesCampania(detalle.getId()));
				presupuestoDetalleCampania = new PresupuestoDetalleCampania();
				totalCamp = 0d;
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error guardando el registro.");
		}
	}
	
	/**
	 * Actualizaciones
	 */
	public void updatePresupuesto() {
		try {
			if(validar(selectedPresupuesto)) {
				getPresupuestoService().updatePresupuesto(selectedPresupuesto);
				listaPresupuestos = getPresupuestoService().getPresupuestos();
				selectedPresupuesto = new Presupuesto();
				util.mostrarMensaje("Registro actualizado con éxito.");
			}
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void actualizarMes(PresupuestoDetalleMes detPpto) {
		try {
			this.presupuestoDetalleMes = detPpto;    
			this.cargarListaCentroCostosPresupuestoMes("NO");
			totalizarMes();
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void actualizarCamp(PresupuestoDetalleCampania detPpto) {
		try {
			this.presupuestoDetalleCampania = detPpto;
			this.cargarListaCentroCostosPresupuestoCampania("NO");
			totalizarCamp();
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	/***
	 * Elimiaciones de presupuesto y detalle
	 */
	
	public void deletePresupuesto() {
		try {
			getPresupuestoService().deletePresupuesto(selectedPresupuesto);
			listaPresupuestos = getPresupuestoService().getPresupuestos();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}
	
	public void deletePresupuestoMes() {
		try {
			getPresupuestoService().deletePresupuestoDetalleMes(selectedPresupuestoDetalleMes);
			listaPresupuestos = getPresupuestoService().getPresupuestos();
			detalle = this.getPresupuestoService().getPresupuestoById(detalle.getId());
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}
	
	public void deletePresupuestoCamp() {
		try {
			getPresupuestoService().deletePresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
			listaPresupuestos = getPresupuestoService().getPresupuestos();
			detalle = this.getPresupuestoService().getPresupuestoById(detalle.getId());
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}
	
	public void cargarListaCentroCostosPresupuestoMes(String actualiza) {
		try {
			PresupuestoDetalleMes prepDetMesTmp = null;
			if("SI".equals(actualiza)) {
				prepDetMesTmp = selectedPresupuestoDetalleMes;
			}else {
				prepDetMesTmp = presupuestoDetalleMes;
			}
			
			listaCentroCostos = new ArrayList<>();
			
			if(prepDetMesTmp.getCuenta() != null &&
					prepDetMesTmp.getCuenta().getId() > 0) {
				listaCentroCostos = this.getCentroCostoService().getCentroCostosPorCuenta(prepDetMesTmp.getCuenta().getId(), usuario.getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}
	
	public void cargarListaCentroCostosPresupuestoCampania(String actualiza) {
		try {
			PresupuestoDetalleCampania prepDetCampaniaTmp = null;
			if("SI".equals(actualiza)) {
				prepDetCampaniaTmp = selectedPresupuestoDetalleCampania;
			}else {
				prepDetCampaniaTmp = presupuestoDetalleCampania;
			}
			
			listaCentroCostos = new ArrayList<>();
			
			if(prepDetCampaniaTmp.getCuenta() != null &&
					prepDetCampaniaTmp.getCuenta().getId() > 0) {
				listaCentroCostos = this.getCentroCostoService().getCentroCostosPorCuenta(prepDetCampaniaTmp.getCuenta().getId(), usuario.getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}
	
	public void enviarPresupuestoAprobadorInicial(String tipo){
		try {
			Integer centroCosto;
			if("MES".equals(tipo)) {
				selectedPresupuestoDetalleMes.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
				getPresupuestoService().actualizarEstadoPresupuestoDetalleMes(selectedPresupuestoDetalleMes);
				observacion.setPresupuestoDetalleMes(selectedPresupuestoDetalleMes);
				observacion.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorInicial(selectedPresupuestoDetalleMes.getCentroCosto().getId()).get(0));
				centroCosto = selectedPresupuestoDetalleMes.getCentroCosto().getId();
			}else {
				selectedPresupuestoDetalleCampania.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
				getPresupuestoService().actualizarEstadoPresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
				observacion.setPresupuestoDetalleCampania(selectedPresupuestoDetalleCampania);
				observacion.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorInicial(selectedPresupuestoDetalleCampania.getCentroCosto().getId()).get(0));
				centroCosto = selectedPresupuestoDetalleCampania.getCentroCosto().getId();
			}

			//Guardamos la observacion
			observacion.setFecha(new Date());
			observacion.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
			observacion.setUsuarioEnvia(usuario);
			getPresupuestoService().addObservacion(observacion);
			
			//Envio de correo
			EnviarCorreo enviarCorreo = new EnviarCorreo();
			enviarCorreo.enviaCorreoAprobadorInicial(detalle, 
					detalle.getUsuario(), 
					this.getCentroCostoService().getUsuarioAprobadorInicial(centroCosto), 
					EnumEstadosPresupuesto.ENVIADO);
			
			observacion = new Observacion();
			util.mostrarMensaje("El presupuesto fue enviado al aprobador incial con éxito.");
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error enviando el registro.");
		} 	
	}

	public IPresupuestoService getPresupuestoService() {
		return presupuestoService;
	}

	public void setPresupuestoService(IPresupuestoService presupuestoService) {
		this.presupuestoService = presupuestoService;
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

	public ICalculadoraService getCalculadoraService() {
		return calculadoraService;
	}

	public void setCalculadoraService(ICalculadoraService calculadoraService) {
		this.calculadoraService = calculadoraService;
	}

	public Util getUtil() {
		return util;
	}

	public void setUtil(Util util) {
		this.util = util;
	}

	public Presupuesto getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Presupuesto getDetalle() {
		return detalle;
	}

	public void setDetalle(Presupuesto detalle) {
		this.detalle = detalle;
		if(detalle != null) {
			mostrarDetalle = true;
		}
	}

	public Presupuesto getSelectedPresupuesto() {
		return selectedPresupuesto;
	}

	public void setSelectedPresupuesto(Presupuesto selectedPresupuesto) {
		this.selectedPresupuesto = selectedPresupuesto;
	}

	public PresupuestoDetalleMes getPresupuestoDetalleMes() {
		return presupuestoDetalleMes;
	}

	public void setPresupuestoDetalleMes(PresupuestoDetalleMes presupuestoDetalleMes) {
		this.presupuestoDetalleMes = presupuestoDetalleMes;
	}

	public PresupuestoDetalleMes getSelectedPresupuestoDetalleMes() {
		return selectedPresupuestoDetalleMes;
	}

	public void setSelectedPresupuestoDetalleMes(PresupuestoDetalleMes selectedPresupuestoDetalleMes) {
		this.selectedPresupuestoDetalleMes = selectedPresupuestoDetalleMes;
	}

	public PresupuestoDetalleCampania getPresupuestoDetalleCampania() {
		return presupuestoDetalleCampania;
	}

	public void setPresupuestoDetalleCampania(PresupuestoDetalleCampania presupuestoDetalleCampania) {
		this.presupuestoDetalleCampania = presupuestoDetalleCampania;
	}

	public PresupuestoDetalleCampania getSelectedPresupuestoDetalleCampania() {
		return selectedPresupuestoDetalleCampania;
	}

	public void setSelectedPresupuestoDetalleCampania(PresupuestoDetalleCampania selectedPresupuestoDetalleCampania) {
		this.selectedPresupuestoDetalleCampania = selectedPresupuestoDetalleCampania;
	}

	public List<Presupuesto> getListaPresupuestos() {
		return listaPresupuestos;
	}

	public void setListaPresupuestos(List<Presupuesto> listaPresupuestos) {
		this.listaPresupuestos = listaPresupuestos;
	}

	public ListasGenericas getListasGenericas() {
		return listasGenericas;
	}

	public void setListasGenericas(ListasGenericas listasGenericas) {
		this.listasGenericas = listasGenericas;
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

	public boolean isMostrarDetalle() {
		return mostrarDetalle;
	}

	public void setMostrarDetalle(boolean mostrarDetalle) {
		this.mostrarDetalle = mostrarDetalle;
	}

	public int getCamapanaMaxima() {
		return camapanaMaxima;
	}

	public void setCamapanaMaxima(int camapanaMaxima) {
		this.camapanaMaxima = camapanaMaxima;
	}

	public Double getTotalMes() {
		return totalMes;
	}

	public void setTotalMes(Double totalMes) {
		this.totalMes = totalMes;
	}

	public Double getTotalCamp() {
		return totalCamp;
	}

	public void setTotalCamp(Double totalCamp) {
		this.totalCamp = totalCamp;
	}

	public Integer getAnioGeneral() {
		return anioGeneral;
	}

	public void setAnioGeneral(Integer anioGeneral) {
		this.anioGeneral = anioGeneral;
	}

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}
	
	public IParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(IParametroService parametroService) {
		this.parametroService = parametroService;
	}
	
	//Carga Archivo Plano Nomina
	/*public void uploadPlanoNomina(FileUploadEvent event) {

		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			XSSFSheet sheet = workbook.getSheetAt(0);

			if (validarArchivoPlano(workbook)) {
				insertarPlanoPresupuestoNomina(sheet);

				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Nómina",
						event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public StreamedContent getFileDescargar() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/files/Archivo Plano Presupuesto Nómina.xlsx");
		fileDescargar = new DefaultStreamedContent(stream,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"Archivo Plano Centros de Costo.xlsx");
		return fileDescargar;
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

		if (!(sheet.getRow(0).getCell(0)).toString().trim().equals("Centro de Costo")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la primer columna debe ser Centro de Costo");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(1)).toString().trim().equals("Gerencia")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la segunda columna debe ser Gerencia");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(2).toString()).trim().equals("Direccion")) {
			validacion = new Validacion();
			validacion.setMensaje("El encabezado de la tercer columna debe ser Direccion");
			validacion.setFila("1");
			validacion.setColumna("C");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(0).getCell(3).toString()).trim().equals("Jefatura")) {
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
				idGerencia = getIdGerenciaByNombre(row.getCell(1) + "".trim());
				idDireccion = getIdDireccionByNombre(row.getCell(2) + "".trim());
				idJefatura = getIdJefaturaByNombre(row.getCell(3) + "".trim());

				if ((ceco.getCentroCosto().equals(row.getCell(0) + "".trim())) && (ceco.getGerencia().getId() == idGerencia)
						&& (ceco.getDireccion().getId() == idDireccion) && (ceco.getJefatura().getId() == idJefatura)) {
					validacion = new Validacion();
					validacion.setMensaje("Ya existe un Centro de Costo creado con lo datos ingresados");
					validacion.setFila((fila+1)+"");
					validacion.setColumna("--");
					listaValidacion.add(validacion);
				}
			}
		}
		
		//Validar que no ingresen en el archivo plano una Gerencia, Direccion o Jefatura que no exista 
		for (int fila = 1; fila < sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);

			// Obtenemos los ids de Gerencia, Direccion y Jefatura a partir de los nombres
			// ingresados en el archivo plano
			// ya que el usuario no conoce los codigos
			idGerencia = getIdGerenciaByNombre(row.getCell(1) + "".trim());
			idDireccion = getIdDireccionByNombre(row.getCell(2) + "".trim());
			idJefatura = getIdJefaturaByNombre(row.getCell(3) + "".trim());

			if (idGerencia==0) {
				validacion = new Validacion();
				validacion.setMensaje("La gerencia: "+row.getCell(1) + ""+" no existe en el maestro de Gerencias");
				validacion.setFila((fila+1)+"");
				validacion.setColumna("B");
				listaValidacion.add(validacion);
			}
			
			if (idDireccion==0) {
				validacion = new Validacion();
				validacion.setMensaje("La dirección: "+row.getCell(2) + ""+" no existe en el maestro de Direcciones");
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

	public void insertarPlanoPresupuestoNomina(XSSFSheet sheet) {
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

	public int getIdGerenciaByNombre(String nombreGerencia) {
		for (Gerencia ger : lstGerencias) {
			if (ger.getNombre().equals(WordUtils.capitalizeFully(nombreGerencia).trim())) {
				return ger.getId();
			}
		}
		return 0;
	}

	public int getIdDireccionByNombre(String nombreDireccion) {
		for (Direccion dir : lstDireccions) {
			if (dir.getNombre().equals(WordUtils.capitalizeFully(nombreDireccion))) {
				return dir.getId();
			}
		}
		return 0;
	}

	public int getIdJefaturaByNombre(String nombreJefatura) {
		for (Jefatura jef : lstJefaturas) {
			if (jef.getNombre().equals(WordUtils.capitalizeFully(nombreJefatura))) {
				return jef.getId();
			}
		}
		return 0;
	}*/
 }