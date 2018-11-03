function pegadoEspecial(campo, idBoton){
	try{
		var tituloId = campo.id;
		var tituloAux;
		fields = campo.value.split("\t", -1); 
		if(fields.length > 1){
			for (i = 0; i < fields.length; ++i) {
				if(i == 0){
					document.getElementById(tituloId).value = fields[i];
					document.getElementById(tituloId).click();
					document.getElementById(tituloId).focus();
				}else{
					tituloAux = (tituloId.replace('Mes1', ('Mes' + (i+1))));
					document.getElementById(tituloAux).value = fields[i];
					document.getElementById(tituloAux).focus();
				}
			}
			document.getElementById(idBoton).focus();
			document.getElementById(idBoton).click();
		}
	}catch(err){}
}

function pegadoEspecialCamp(campo, idBoton){
	try{
		var tituloId = campo.id;
		var tituloAux;
		fields = campo.value.split("\t", -1); 
		if(fields.length > 1){
			for (i = 0; i < fields.length; ++i) {
				if(i == 0){
					document.getElementById(tituloId).value = fields[i];
					document.getElementById(tituloId).click();
					document.getElementById(tituloId).focus();
				}else{
					tituloAux = (tituloId.replace('inCampania1', ('inCampania' + (i+1))));
					document.getElementById(tituloAux).value = fields[i];
					document.getElementById(tituloAux).focus();
				}
			}
			document.getElementById(idBoton).focus();
			document.getElementById(idBoton).click();
		}
	}catch(err){}
}

function pegadoEspecialCalculadora(campo, idBoton){
	var tituloId = campo.id;
	var tituloAux;
	fields = campo.value.split("\t", -1); 
	if(fields.length > 1){
		for (i = 0; i < fields.length; ++i) {
			if(i == 0){
				document.getElementById(tituloId).value = fields[i];
				document.getElementById(tituloId).click();
				document.getElementById(tituloId).focus();
			}else if(i == 1){
				tituloAux = (tituloId.replace('Enero', 'Febrero'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}else if(i == 2){
				tituloAux = (tituloId.replace('Enero', 'Marzo'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}else if(i == 3){
				tituloAux = (tituloId.replace('Enero', 'Abril'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 4){
				tituloAux = (tituloId.replace('Enero', 'Mayo'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 5){
				tituloAux = (tituloId.replace('Enero', 'Junio'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 6){
				tituloAux = (tituloId.replace('Enero', 'Julio'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 7){
				tituloAux = (tituloId.replace('Enero', 'Agosto'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 8){
				tituloAux = (tituloId.replace('Enero', 'Septiembre'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 9){
				tituloAux = (tituloId.replace('Enero', 'Octubre'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 10){
				tituloAux = (tituloId.replace('Enero', 'Noviembre'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
			else if(i == 11){
				tituloAux = (tituloId.replace('Enero', 'Diciembre'));
				document.getElementById(tituloAux).value = fields[i];
				document.getElementById(tituloAux).focus();
			}
		}
		document.getElementById(idBoton).focus();
		document.getElementById(idBoton).click();
	}
}