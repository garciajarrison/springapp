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
	private List<Cuenta> listaCuentasPlanoNomina;
	private List<CentroCosto> listaCentroCostosPlanoNomina;
	
	public PresupuestoBB() {
		util = Util.getInstance();
		presupuesto = new Presupuesto();
		presupuestoDetalleMes = new PresupuestoDetalleMes();
		presupuestoDetalleCampania = new PresupuestoDetalleCampania();
		selectedPresupuesto = new Presupuesto();
		
		listasGenericas = ListasGenericas.getInstance();
		usuario = (Usuario) Util.getInstance().getSessionAttribute(EnumSessionAttributes.USUARIO);
		anioGeneral = Integer.valueOf(parametroService.getParametroByCodigo("ANIO_CALCULADORA").getValor());
		listaCentroCostos = this.getCentroCostoService().getCentroCostoPorUsuario(usuario.getId());
		mostrarDetalle = false;
		camapanaMaxima = getCalculadoraService().getCampanaMaxima(anioGeneral);
		observacion = new Observacion();
		cargarListaPresupuesto();
		
		listaCuentasPlanoNomina = this.getCuentaService().getCuentas(true);
		listaCentroCostosPlanoNomina = this.getCentroCostoService().getCentroCostos(true);
	}
	
	private void cargarListaPresupuesto() {
		try {
			if("Administrador".equals(usuario.getRol())) {
				listaPresupuestos = getPresupuestoService().getPresupuestos();
			}else {
				listaPresupuestos = getPresupuestoService().getPresupuestosPorUsuario(usuario.getId());
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}	
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
				cargarListaPresupuesto();
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
				presupuestoDetalleMes.setUsuarioAprobadorInicial(this.getCentroCostoService().getUsuarioAprobadorInicial(presupuestoDetalleMes.getCentroCosto().getId()).get(0));
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
				cargarListaPresupuesto();
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
			this.cargarListaCuentasPresupuestoMes("NO");
			totalizarMes();
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error actualizando el registro.");
		} 	
	}
	
	public void actualizarCamp(PresupuestoDetalleCampania detPpto) {
		try {
			this.presupuestoDetalleCampania = detPpto;
			this.cargarListaCuentasPresupuestoCampania("NO");
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
			cargarListaPresupuesto();
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}
	
	public void deletePresupuestoMes() {
		try {
			getPresupuestoService().deletePresupuestoDetalleMes(selectedPresupuestoDetalleMes);
			cargarListaPresupuesto();
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
			cargarListaPresupuesto();
			detalle = this.getPresupuestoService().getPresupuestoById(detalle.getId());
			util.mostrarMensaje("Registro eliminado con éxito.");  
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			util.mostrarError("Error eliminando el registro.");
		}
	}
	
	public void cargarListaCuentasPresupuestoMes(String actualiza) {
		try {
			PresupuestoDetalleMes prepDetMesTmp = null;
			if("SI".equals(actualiza)) {
				prepDetMesTmp = selectedPresupuestoDetalleMes;
			}else {
				prepDetMesTmp = presupuestoDetalleMes;
			}
			
			listaCuentas = new ArrayList<>();
			
			if(prepDetMesTmp.getCentroCosto() != null &&
					prepDetMesTmp.getCentroCosto().getId() > 0) {
				listaCuentas = this.getCuentaService().getCuentaPorCentroCosto(prepDetMesTmp.getCentroCosto().getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}
	
	public void cargarListaCuentasPresupuestoCampania(String actualiza) {
		try {
			PresupuestoDetalleCampania prepDetCampaniaTmp = null;
			if("SI".equals(actualiza)) {
				prepDetCampaniaTmp = selectedPresupuestoDetalleCampania;
			}else {
				prepDetCampaniaTmp = presupuestoDetalleCampania;
			}
			
			listaCuentas = new ArrayList<>();
			
			if(prepDetCampaniaTmp.getCentroCosto() != null &&
					prepDetCampaniaTmp.getCentroCosto().getId() > 0) {
				listaCuentas = this.getCuentaService().getCuentaPorCentroCosto(prepDetCampaniaTmp.getCentroCosto().getId());
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} 
	}
	
	public void enviarPresupuestoAprobadorInicial(){
		try {
			String tipo = this.detalle.getTipo();
			Integer centroCosto = 0;
			Observacion observacionTmp = new Observacion();
			if("Mensual".equals(tipo)) {
				
				if(detalle.getDetalleMes() != null && !detalle.getDetalleMes().isEmpty()) {
					for(PresupuestoDetalleMes det : detalle.getDetalleMes()) {
						
						observacionTmp = new Observacion();
						observacionTmp.setObservacion(observacion.getObservacion());
						det.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
						getPresupuestoService().actualizarEstadoPresupuestoDetalleMes(det);
						observacionTmp.setPresupuestoDetalleMes(det);
						observacionTmp.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorInicial(det.getCentroCosto().getId()).get(0));
						centroCosto = det.getCentroCosto().getId();
						observacionTmp.setFecha(new Date());
						observacionTmp.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
						observacionTmp.setUsuarioEnvia(usuario);
						
						getPresupuestoService().addObservacion(observacionTmp);
						selectedPresupuestoDetalleMes = det;
					}
				}
				
			}else {
				
				if(detalle.getDetalleCampania() != null && !detalle.getDetalleCampania().isEmpty()) {
					for(PresupuestoDetalleCampania det : detalle.getDetalleCampania()) {
						
						observacionTmp = new Observacion();
						observacionTmp.setObservacion(observacion.getObservacion());
						det.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
						getPresupuestoService().actualizarEstadoPresupuestoDetalleCampania(det);
						observacionTmp.setPresupuestoDetalleCampania(det);
						observacionTmp.setUsuarioRecibe(this.getCentroCostoService().getUsuarioAprobadorInicial(det.getCentroCosto().getId()).get(0));
						centroCosto = det.getCentroCosto().getId();
						observacionTmp.setFecha(new Date());
						observacionTmp.setEstado(EnumEstadosPresupuesto.ENVIADO.getCodigo());
						observacionTmp.setUsuarioEnvia(usuario);
						
						getPresupuestoService().addObservacion(observacionTmp);
						selectedPresupuestoDetalleCampania = det;
					}
				}
			}

			if(centroCosto > 0) {
				//Envio de correo
				EnviarCorreo enviarCorreo = new EnviarCorreo();
				enviarCorreo.enviaCorreoAprobadorInicial(detalle, 
						detalle.getUsuario(), 
						this.getCentroCostoService().getUsuarioAprobadorInicial(centroCosto), 
						EnumEstadosPresupuesto.ENVIADO);
				
				observacion = new Observacion();
				detalle = null;
				cargarListaPresupuesto();
				util.mostrarMensaje("El presupuesto fue enviado al aprobador incial con éxito.");
			}else {
				util.mostrarError("El presupuesto no tiene detalle para enviar.");
			}
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
	
	public StreamedContent getFileDescargar() {
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
				.getResourceAsStream("/resources/files/Archivo Plano Presupuesto Nómina.xlsx");
		fileDescargar = new DefaultStreamedContent(stream,
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"Archivo Plano Presupuesto Nómina.xlsx");
		return fileDescargar;
	}
	
	public void uploadPlanoNomina(FileUploadEvent event) {

		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);

			XSSFSheet sheet = workbook.getSheetAt(0);

			if (validarArchivoPlano(workbook)) {
				insertarPlanoPresupuestoNomina(sheet);

				FacesMessage msg = new FacesMessage("Carga Archivo Plano de Nómina", event.getFile().getFileName() + " fue cargado correctamente");
				FacesContext.getCurrentInstance().addMessage(null, msg);
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

		if (!(sheet.getRow(0).getCell(0)+"").trim().equals("Nombre")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 1, columna A debe ser Nombre");
			validacion.setFila("1");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(1).getCell(0)+"").trim().equals("Descripción")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 2, columna A debe ser Descripción");
			validacion.setFila("2");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}

		if (!(sheet.getRow(2).getCell(0).toString()).trim().equals("Clasificación")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 3, columna A debe ser Clasificación");
			validacion.setFila("3");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}
		
		if((sheet.getRow(0).getCell(1)+"").trim().equals("") || (sheet.getRow(0).getCell(1)+"").toLowerCase().trim().equals("null")){
			validacion = new Validacion();
			validacion.setMensaje("Debe ingresar un nombre para el presupuesto");
			validacion.setFila("1");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}
			
		if (!((sheet.getRow(2)).getCell(1)+"").trim().equals("Gasto") && !((sheet.getRow(2)).getCell(1)+"").trim().equals("Inversión")
				&& !((sheet.getRow(2)).getCell(1)+"").trim().equals("Órdenes") && (sheet.getRow(2).getCell(1)+"").toLowerCase().trim().equals("null")) {
			validacion = new Validacion();
			validacion.setMensaje("Debe ingresar una clasificación válida(Gasto, Inversion, Órdenes) para el presupuesto");
			validacion.setFila("3");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}
				
		if (!(sheet.getRow(4).getCell(0)+"").trim().equals("Cuenta")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna A debe ser Cuenta");
			validacion.setFila("5");
			validacion.setColumna("A");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(1)+"").trim().equals("Centro de Costo")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna B debe ser Centro de Costo");
			validacion.setFila("5");
			validacion.setColumna("B");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(2)+"").trim().equals("Mes 1")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna C debe ser Mes 1");
			validacion.setFila("5");
			validacion.setColumna("C");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(3)+"").trim().equals("Mes 2")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna D debe ser Mes 2");
			validacion.setFila("5");
			validacion.setColumna("D");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(4)+"").trim().equals("Mes 3")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna E debe ser Mes 3");
			validacion.setFila("5");
			validacion.setColumna("E");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(5)+"").trim().equals("Mes 4")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna F debe ser Mes 4");
			validacion.setFila("5");
			validacion.setColumna("F");
			listaValidacion.add(validacion);
		}	
		
		if (!(sheet.getRow(4).getCell(6)+"").trim().equals("Mes 5")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna G debe ser Mes 5");
			validacion.setFila("5");
			validacion.setColumna("G");
			listaValidacion.add(validacion);
		}			
		
		if (!(sheet.getRow(4).getCell(7)+"").trim().equals("Mes 6")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna H debe ser Mes 6");
			validacion.setFila("5");
			validacion.setColumna("H");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(8)+"").trim().equals("Mes 7")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna I debe ser Mes 7");
			validacion.setFila("5");
			validacion.setColumna("I");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(9)+"").trim().equals("Mes 8")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna J debe ser Mes 8");
			validacion.setFila("5");
			validacion.setColumna("J");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(10)+"").trim().equals("Mes 9")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna K debe ser Mes 9");
			validacion.setFila("5");
			validacion.setColumna("K");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(11)+"").trim().equals("Mes 10")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna L debe ser Mes 10");
			validacion.setFila("5");
			validacion.setColumna("L");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(12)+"").trim().equals("Mes 11")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna M debe ser Mes 11");
			validacion.setFila("5");
			validacion.setColumna("M");
			listaValidacion.add(validacion);
		}
		
		if (!(sheet.getRow(4).getCell(13)+"").trim().equals("Mes 12")) {
			validacion = new Validacion();
			validacion.setMensaje("El titulo de la fila 5, columna N debe ser Mes 12");
			validacion.setFila("5");
			validacion.setColumna("N");
			listaValidacion.add(validacion);
		}
		
		if(sheet.getPhysicalNumberOfRows() == 4){
			validacion = new Validacion();
			validacion.setMensaje("Debe ingresar al menos un registro al detalle del presupuesto");
			validacion.setFila("6");
			validacion.setColumna("--");
			listaValidacion.add(validacion);
		}
		
		Row row;
		int idCuenta;
		int idCentroCosto;
		
		for (int fila = 5; fila <= sheet.getPhysicalNumberOfRows(); fila++) {
			row = sheet.getRow(fila);
			
			idCuenta = getIdCuentaByCuenta(row.getCell(0) + "");
			idCentroCosto = getIdCentroCostoByCentroCosto(row.getCell(1) + "");

			if (idCuenta == 0) {
				validacion = new Validacion();
				validacion.setMensaje("La cuenta: " + (row.getCell(0) + "") + " no existe en el maestro de Cuentas");
				validacion.setFila((fila + 1) + "");
				validacion.setColumna("A");
				listaValidacion.add(validacion);
			}

			if (idCentroCosto == 0) {
				validacion = new Validacion();
				validacion.setMensaje("El centro de costo: " + (row.getCell(1) + "") + " no existe en el maestro de Centros de Costo");
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
	
	public int getIdCuentaByCuenta(String cuenta) {
		for (Cuenta cue : listaCuentasPlanoNomina) {
			if (cue.getCuenta().equals(cuenta.trim())) {
				return cue.getId();
			}
		}
		return 0;
	}

	public int getIdCentroCostoByCentroCosto(String centroCosto) {
		for (CentroCosto ceco : listaCentroCostosPlanoNomina) {
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
	
	//Carga Archivo Plano Nomina
		
	public void insertarPlanoPresupuestoNomina(XSSFSheet sheet) {
		presupuesto.setAnio(anioGeneral);
		presupuesto.setNombre(sheet.getRow(0).getCell(1).toString().trim());
		presupuesto.setDescripcion(sheet.getRow(1).getCell(1).toString().trim());
		presupuesto.setTipo("Mensual");
		presupuesto.setClasificacion(sheet.getRow(2).getCell(1).toString().trim());
		presupuesto.setAnio(anioGeneral);
		//pptoNomina.setFechaCreacion("");TODO
		presupuesto.setUsuario(usuario);
		
		getPresupuestoService().addPresupuesto(presupuesto);
		
		System.out.println("ID Encabezado: "+presupuesto.getId());
				
		Row row;
		int idCuenta;
		int idCentroCosto;
		int numFilas = sheet.getPhysicalNumberOfRows();
		
		for (int fila = 5; fila <= numFilas; fila++) {
			row = sheet.getRow(fila);

			idCuenta = getIdCuentaByCuenta(row.getCell(0) + "".trim());
			idCentroCosto = getIdCentroCostoByCentroCosto(row.getCell(1) + "".trim());
			
			presupuestoDetalleMes = new PresupuestoDetalleMes();

			presupuestoDetalleMes.setPresupuesto(presupuesto);
			presupuestoDetalleMes.getCuenta().setId(idCuenta);
			presupuestoDetalleMes.getCentroCosto().setId(idCentroCosto);
			presupuestoDetalleMes.setValorM1(Double.parseDouble(validarValor(row.getCell(2)+"")));
			presupuestoDetalleMes.setValorM2(Double.parseDouble(validarValor(row.getCell(3)+"")));
			presupuestoDetalleMes.setValorM3(Double.parseDouble(validarValor(row.getCell(4)+"")));
			presupuestoDetalleMes.setValorM4(Double.parseDouble(validarValor(row.getCell(5)+"")));
			presupuestoDetalleMes.setValorM5(Double.parseDouble(validarValor(row.getCell(6)+"")));
			presupuestoDetalleMes.setValorM6(Double.parseDouble(validarValor(row.getCell(7)+"")));
			presupuestoDetalleMes.setValorM7(Double.parseDouble(validarValor(row.getCell(8)+"")));
			presupuestoDetalleMes.setValorM8(Double.parseDouble(validarValor(row.getCell(9)+"")));
			presupuestoDetalleMes.setValorM9(Double.parseDouble(validarValor(row.getCell(10)+"")));
			presupuestoDetalleMes.setValorM10(Double.parseDouble(validarValor(row.getCell(11)+"")));
			presupuestoDetalleMes.setValorM11(Double.parseDouble(validarValor(row.getCell(12)+"")));
			presupuestoDetalleMes.setValorM12(Double.parseDouble(validarValor(row.getCell(13)+"")));
			
			totalizarMes();
			
			presupuestoDetalleMes.setEstado(EnumEstadosPresupuesto.FINALIZADO.getCodigo());
			presupuestoDetalleMes.setUsuarioAprobadorInicial(usuario);
			presupuestoDetalleMes.setUsuarioAprobadorFinal(usuario);

			this.getPresupuestoService().addPresupuestoDetalleMes(presupuestoDetalleMes);
		}
		presupuesto = new Presupuesto();
		presupuestoDetalleMes = new PresupuestoDetalleMes();
		totalMes = 0d;
	}
	
	public String validarValor(String valor) {
		if(valor.equals("null")) {
			return "0";
		}
		return valor;
	}
 }