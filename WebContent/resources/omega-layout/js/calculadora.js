var tituloTotalCMA = 'formID:TabViewCal:tblCalculadoraCM:';
var tituloTotalCMB = ':totalCM_';

function totalizar(obj, tipo, idTotal, mes){

	var spl = obj.id.split(":");
	var tituloFin = spl[spl.length-1];
	var total = 0;
	
	try{
		for(i = 0; i < 12; i++){
			total += parseInt(document.getElementById((tituloTotalCMA + i + ":" + tituloFin)).value.replace('%', ''));
		}
	
	}catch(err){}

	//alert(total);
}