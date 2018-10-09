/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marketingpersonal.controller;

import java.io.IOException;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import com.marketingpersonal.common.Util;
import com.marketingpersonal.model.entity.Usuario;
import com.marketingpersonal.service.IUsuarioService;


@ManagedBean(name = "archivosPlanosBB")
@ViewScoped
public class ArchivosPlanosBB {
    
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private UploadedFile file;
    private Util util;
    private IUsuarioService usuarioService;
    private Usuario usuario;
    
    public ArchivosPlanosBB() {
		util = Util.getInstance();
	}

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void subirArchivoPlanoUsuarios(FileUploadEvent event) {
				
		try {
			InputStream input = (InputStream) event.getFile().getInputstream();
			XSSFWorkbook workbook = new XSSFWorkbook(input);
			
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int numColumnas = sheet.getRow(0).getPhysicalNumberOfCells();
			int numFilas = sheet.getPhysicalNumberOfRows();
			
			validarNumeroColumnas(numColumnas);
			
			insertarUsuarios(sheet, numFilas);
			
			FacesMessage msg = new FacesMessage("Carga Archivo Plano de Usuarios", event.getFile().getFileName() + " fue cargado correctamente");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    public void validarNumeroColumnas(int numColumnas) {
		if (numColumnas != 6) {
			util.mostrarError("El numero de columnas que tiene la hoja no es válido");
		}
	}
    
    public void insertarUsuarios(XSSFSheet sheet, int numFilas) {
		Row row;
				
		// Recorrido de filas
		for (int fila = 1; fila < numFilas; fila++) {

			row = sheet.getRow(fila);
			
			/*usuario = new Usuario();

			usuario.setNumeroDocumento(row.getCell(0)+"");
			usuario.setNombre(row.getCell(1)+"");
			usuario.setUsuario(row.getCell(2)+"");
			usuario.setCorreo(row.getCell(3)+"");
			usuario.setCargo(row.getCell(4)+"");
			usuario.setRol(row.getCell(5)+"");*/
						
			// insertar.out.println();
			//getUsuarioService().addUsuario(usuario);		
		}
	}
    
    /*public IUsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}*/
    
}
