function validacion_categoria(){
	if(document.getElementById('categoria')){
		var categoria = document.getElementById('categoria').value;
	}
	if(categoria == ""){
		alert("Nombre de categor\u00EDa vac\u00EDo");
		return false;
	}
	else if (categoria && /^\s+$/.test(categoria)){
		alert("La categor\u00EDa ingresada est\u00e1 formada por espacios");
		return false;
	}
	else if(categoria && categoria.length > 45){
		alert("El nombre de la categor\u00EDa debe tener una longitud menor a 45");
		return false;
	}
	else{
		return true;
	}
}

function validacion_producto(){
	if(document.getElementById('nombre')){
		var nombre = document.getElementById('nombre').value
	}
	if(document.getElementById('stock')){
		var stock = document.getElementById('stock').value
	}
	if(document.getElementById('precio')){
		var precio = document.getElementById('precio').value
	}
	if(nombre == ""){
		alert("El nombre se encuentra vac\u00edo");
		return false;
	}
	else if(nombre && /^\s+$/.test(nombre)){
		alert("El nombre ingresado est\u00e1 formado por espacios en blanco");
		return false;
	}
	else if(stock && stock.length > 45){
		alert("El nombre del producto debe tener una longitud menor a 45");
		return false;
	}
	else if(stock && /^\s+$/.test(stock)){
		alert("La cantidad ingresada est\u00e1 formada por espacios");
		return false;
	}
	else if(stock && isNaN(stock)){
		alert("La cantidad ingresada debe ser un n\u00FAmero");
		return false;
	}
	else if(stock && stock <= 0){
		alert("La cantidad ingresada debe ser mayor a cero");
		return false;
	}
	else if(stock == ""){
		alert("Cantidad vac\355o");
		return false;
	}
	else if(precio && /^\s+$/.test(precio)){
		alert("El precio ingresado est\u00e1 formado por espacios");
		return false;
	}
	else if(precio && isNaN(precio)){
		alert("El precio ingresado debe ser un n\u00FAmero");
		return false;
	}
	else if(precio && precio <= 0){
		alert("El precio ingresado debe ser mayor a cero");
		return false;
	}
	else if(precio == ""){
		alert("Precio vac\355o");
		return false;
	}
	else{
		return true;
	}
}

function validacion_cambiar_contrasena(){
    if(document.getElementById('cont_act')){
        var contrasena_actual = document.getElementById('cont_act').value
    }
    if(document.getElementById('cont_nueva')){
        var contrasena_nueva = document.getElementById('cont_nueva').value
    }
    if(document.getElementById('cont_nueva_rep')){
        var contrasena_nueva_rep = document.getElementById('cont_nueva_rep').value
    }
    else if(contrasena_nueva && contrasena_nueva_rep && contrasena_nueva !== contrasena_nueva_rep){
    	alert("Las contrase\361as ingresadas no coinciden");
    	return false;
    }
    else if  (contrasena_actual && /^\s+$/.test(contrasena_actual)){
        alert("La contrase\361a actual ingresada solo est\341 formada por espacios en blanco");
        return false;
    }
    else if  (contrasena_actual && (contrasena_actual.length < 5 || contrasena_actual.length > 16)){
        alert("La contrase\361a actual debe tener una longitud entre 6 y 16 caracteres");
        return false;
    }
    else if (contrasena_actual == ""){
        alert("Contrase\361a actual vac\355a");
        return false;
    }
    else if  (contrasena_nueva && /^\s+$/.test(contrasena_nueva)){
        alert("La contrase\361a nueva ingresada solo est\341 formada por espacios en blanco");
        return false;
    }
    else if  (contrasena_nueva && (contrasena_nueva.length < 5 || contrasena_nueva.length > 16)){
        alert("La contrase\361a nueva debe tener una longitud entre 6 y 16 caracteres");
        return false;
    }
    else if (contrasena_nueva == ""){
        alert("Contrase\361a actual vac\355a");
        return false;
    }
    else{
        return true;
    }
}

function validacion_cambio_descuento_socio(){
	if(document.getElementById('porcentajeDescuento')){
		var descuento = document.getElementById('porcentajeDescuento').value
	}
	if(descuento && /^\s+$/.test(descuento)){
		alert("El porcentaje de descuento ingresado est\u00e1 formado por espacios");
		return false;
	}
	else if(descuento && isNaN(descuento)){
		alert("El porcentaje de descuento ingresado debe ser un n\u00FAmero");
		return false;
	}
	else if(descuento && descuento <= 0){
		alert("El porcentaje de descuento ingresado debe ser mayor a cero");
		return false;
	}
	else if(descuento == ""){
		alert("Porcentaje de descuento vac\355o");
		return false;
	}
	else{
		return true;
	}
}

function validacion_cambio_plazo_entrega(){
	if(document.getElementById('cantidadDias')){
		var dias = document.getElementById('cantidadDias').value
	}
	if(dias && /^\s+$/.test(dias)){
		alert("La cantidad de d\355as ingresada est\u00e1 formada por espacios");
		return false;
	}
	else if(dias && isNaN(dias)){
		alert("La cantidad de d\355as ingresada debe ser un n\u00FAmero");
		return false;
	}
	else if(dias && dias <= 0){
		alert("La cantidad de d\355as ingresada debe ser mayor a cero");
		return false;
	}
	else if(dias == ""){
		alert("Cantidad de d\355as vac\355o");
		return false;
	}
	else{
		return true;
	}
}

function validacion_cambio_porcentaje_ganancia(){
	if(document.getElementById('porcentajeGanancia')){
		var ganancia = document.getElementById('porcentajeGanancia').value
	}
	if(ganancia && /^\s+$/.test(ganancia)){
		alert("El porcentaje de ganancia ingresado est\u00e1 formado por espacios");
		return false;
	}
	else if(ganancia && isNaN(ganancia)){
		alert("El porcentaje de ganancia ingresado debe ser un n\u00FAmero");
		return false;
	}
	else if(ganancia && ganancia <= 0){
		alert("El porcentaje de ganancia ingresado debe ser mayor a cero");
		return false;
	}
	else if(ganancia == ""){
		alert("Porcentaje de ganancia vac\355o");
		return false;
	}
	else{
		return true;
	}
}

function validacion_cambio_valor_cuotas(){
	if(document.getElementById('valorCuota')){
		var valor = document.getElementById('valorCuota').value
	}
	if(valor && /^\s+$/.test(valor)){
		alert("El valor de cuota ingresado est\u00e1 formado por espacios");
		return false;
	}
	else if(valor && isNaN(valor)){
		alert("El valor de cuota ingresado debe ser un n\u00FAmero");
		return false;
	}
	else if(valor && valor <= 0){
		alert("El valor de cuota ingresado debe ser mayor a cero");
		return false;
	}
	else if(valor == ""){
		alert("Valor cuota vac\355o");
		return false;
	}
	else{
		return true;
	}
}

function validacion_confirmar_entrega(){
	if(document.getElementById('nro_pedido')){
		var pedido = document.getElementById('nro_pedido').value
	}
	if(pedido && /^\s+$/.test(pedido)){
		alert("El nro de pedido ingresado est\u00e1 formado por espacios en blanco");
		return false;
	}
	else if(pedido && isNaN(pedido)){
		alert("El nro de pedido ingresado debe ser un n\u00FAmero");
		return false;
	}
	else if(pedido && pedido <= 0){
		alert("El nro de pedido ingresado debe ser mayor a cero");
		return false;
	}
	else if(pedido == ""){
		alert("Nro de pedido vac\355o");
		return false;
	}
	else{
		return true;
	}
}

function validacion_cliente(){
    if(document.getElementById('dni')){
        var dni = document.getElementById('dni').value;
    }
    if(document.getElementById('nombre')){
        var nombre = document.getElementById('nombre').value
    }
    if(document.getElementById('apellido')){
        var apellido = document.getElementById('apellido').value
    }
    if (document.getElementById('telefono')){
        var telefono = document.getElementById('telefono').value
    }
    if (document.getElementById('direccion')){
        var direccion = document.getElementById('direccion').value
    }
    if(document.getElementById('mail')){
        var email = document.getElementById('mail').value
    }
    if(document.getElementById('usuario')){
        var usuario = document.getElementById('usuario').value
    }
    if(document.getElementById('contrasena')){
        var contrasena = document.getElementById('contrasena').value
    }
    if(document.getElementById('contrasena2')){
        var contrasena2 = document.getElementById('contrasena2').value
    }
    if(dni && isNaN(dni)){
    	if (!(dni.startsWith("F") || dni.startsWith("f") || dni.startsWith("M") || dni.startsWith("m"))){
    		alert("El formato del DNI no es v\341lido");
    		return false;
    	}
    	else{
    		for(var i = 1; i < dni.length; i++){
    			if (isNaN(dni[i])){
    				alert("El formato del DNI no es v\341lido");
    	    		return false;
    	    		break;
    			}
    		}
    		if (dni && dni.length < 7 | dni.length > 8){
    	    	alert("El DNI ingresado es incorrecto, la longitud del mismo debe ser igual a 7 u 8");
    		    return false;
    	    }
    		else if  (dni && /^\s+$/.test(dni)){
    	        alert("El DNI ingresado es incorrecto,no puede estar formado por espacios en blanco");
    	        return false;
    	    }
    	    else if (dni == ""){
    	        alert("DNI Vac\355o");
    	        return false;
    	    }
    	}
    }
    else if (dni && dni.length < 7 | dni.length > 8){
    	alert("El DNI ingresado es incorrecto, la longitud del mismo debe ser igual a 7 u 8");
	    return false;
    }
	else if  (dni && /^\s+$/.test(dni)){
        alert("El DNI ingresado es incorrecto,no puede estar formado por espacios en blanco");
        return false;
    }
    else if (dni == ""){
        alert("DNI Vac\355o");
        return false;
    }
    else if  (nombre && /^\s+$/.test(nombre)){
        alert("El nombre ingresado solo est\341 formado por espacios en blanco");
        return false;
    }
    else if (nombre && nombre == ""){
        alert("Nombre Vac\355o");
        return false;
    }
    else if  (apellido && /^\s+$/.test(apellido)){
        alert("El apellido ingresado solo est\341 formado por espacios en blanco");
        return false;
    }
    else if (apellido == ""){
        alert("Apellido Vac\355o");
        return false;
    }
    else if (telefono && (isNaN(telefono) || telefono.length != 10)){
        alert("N\372mero de tel\351fono celular no v\341lido (Longitud distinta de 10 o no formado por n\372meros)");
        return false;
    }
    else if  (telefono && /^\s+$/.test(telefono)){
        alert("Tel\351fono celular formado solo por espacios en blanco");
        return false;
    }
    else if (telefono == ""){
        alert("Tel\351fono celular vac\355o");
        return false;
    }
    else if  (direccion && /^\s+$/.test(direccion)){
        alert("La dirección ingresada solo est\341 formada por espacios en blanco");
        return false;
    }
    else if (direccion && direccion == ""){
        alert("Dirección Vac\355a");
        return false;
    }
    else if  (email && /^\s+$/.test(email)){
        alert("El email ingresado solo est\341 formado por espacios en blanco");
        return false;
    }
    else if  (email && !(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))){
        alert("El formato del email es inv\341lido");
        return false;
    }
    else if (email == ""){
        alert("E-Mail Vac\355o");
        return false;
    }
    else if  (usuario &&/^\s+$/.test(usuario)){
        alert("El usuario ingresado solo est\341 formado por espacios en blanco");
        return false;
    }
    else if  (usuario && usuario.length < 4){
        alert("El usuario debe tener una longitud mayor o igual a 4");
        return false;
    }
    else if (usuario == ""){
        alert("Usuario Vac\355o");
        return false;
    }
    else if(contrasena && contrasena2 && contrasena !== contrasena2){
    	alert("Las contrase\361as ingresadas no coinciden");
    	return false;
    }
    else if  (contrasena && /^\s+$/.test(contrasena)){
        alert("La contrase\361a ingresada solo est\341 formada por espacios en blanco");
        return false;
    }
    else if  (contrasena && (contrasena.length < 5 || contrasena.length > 16)){
        alert("La contrase\361a debe tener una longitud entre 6 y 16 caracteres");
        return false;
    }
    else if (contrasena && contrasena == ""){
        alert("Contrase\361a Vac\355a");
        return false;
    }
    else{
        return true;
    }
}

function validacion_login(){
    if(document.getElementById('usuario')){
        var usuario = document.getElementById('usuario').value
    }
    if(document.getElementById('contrasena')){
        var contrasena = document.getElementById('contrasena').value
    }
    if  (usuario &&/^\s+$/.test(usuario)){
        alert("El usuario ingresado solo est\341 formado por espacios en blanco");
        return false;
    }
    else if  (usuario && usuario.length < 4){
        alert("El usuario debe tener una longitud mayor o igual a 4");
        return false;
    }
    else if (usuario == ""){
        alert("Usuario Vac\355o");
        return false;
    }
    else if  (contrasena && /^\s+$/.test(contrasena)){
        alert("La contrase\361a ingresada solo est\341 formada por espacios en blanco");
        return false;
    }
    else if  (contrasena && (contrasena.length < 5 || contrasena.length > 16)){
        alert("La contrase\361a debe tener una longitud entre 6 y 16 caracteres");
        return false;
    }
    else if (contrasena && contrasena == ""){
        alert("Contrase\361a Vac\355a");
        return false;
    }
    else{
        return true;
    }
}

function validacion_pago_cuotas(){
    if(document.getElementById('dniCliente')){
        var dni = document.getElementById('dniCliente').value;
    }
    if(dni && isNaN(dni)){
    	if (!(dni.startsWith("F") || dni.startsWith("f") || dni.startsWith("M") || dni.startsWith("m"))){
    		alert("El formato del DNI no es v\341lido");
    		return false;
    	}
    	else{
    		for(var i = 1; i < dni.length; i++){
    			if (isNaN(dni[i])){
    				alert("El formato del DNI no es v\341lido");
    	    		return false;
    	    		break;
    			}
    		}
    		if (dni && dni.length < 7 | dni.length > 8){
    	    	alert("El DNI ingresado es incorrecto, la longitud del mismo debe ser igual a 7 u 8");
    		    return false;
    	    }
    		else if  (dni && /^\s+$/.test(dni)){
    	        alert("El DNI ingresado es incorrecto,no puede estar formado por espacios en blanco");
    	        return false;
    	    }
    	    else if (dni == ""){
    	        alert("DNI Vac\355o");
    	        return false;
    	    }
    	}
    }
    else if (dni && dni.length < 7 | dni.length > 8){
    	alert("El DNI ingresado es incorrecto, la longitud del mismo debe ser igual a 7 u 8");
	    return false;
    }
	else if  (dni && /^\s+$/.test(dni)){
        alert("El DNI ingresado es incorrecto,no puede estar formado por espacios en blanco");
        return false;
    }
    else if (dni == ""){
        alert("DNI Vac\355o");
        return false;
    }
    else{
        return true;
    }
}

/*Esto es ejemplo
function validacion_formularios(){
	
	if(document.getElementById('categoria')){
		var categoria = document.getElementById('categoria').value;
	}
    if(document.getElementById('inputDni')){
        var dni = document.getElementById('inputDni').value;
    }
    if(document.getElementById('inputNombre')){
        var nombre = document.getElementById('inputNombre').value
    }
    if(document.getElementById('inputApellido')){
        var apellido = document.getElementById('inputApellido').value
    }
    if(document.getElementById('inputUsuario')){
        var usuario = document.getElementById('inputUsuario').value
    }
    if(document.getElementById('inputContrasena')){
        var contrasena = document.getElementById('inputContrasena').value
    }
    if(document.getElementById('inputEmail')){
        var email = document.getElementById('inputEmail').value
    }
    if (document.getElementById('inputDireccion')){
        var direccion = document.getElementById('inputDireccion').value
    }
    if (document.getElementById('legajo')){
        var legajo = document.getElementById('legajo').value
    }
    if  (dni && dni.length < 7 | dni.length > 8){
        alert("El DNI ingresado es incorrecto, la longitud del mismo debe ser igual a 7 u 8");
        return false;
    }
    else if  (dni && /^\s+$/.test(dni)){
        alert("El DNI ingresado es incorrecto,no puede estar formado por espacios en blanco");
        return false;
    }
    else if (dni == ""){
        alert("DNI Vacío");
        return false;
    }
    else if  (nombre && /^\s+$/.test(nombre)){
        alert("El nombre ingresado solo está formado por espacios en blanco");
        return false;
    }
    else if (nombre == ""){
        alert("Nombre Vacío");
        return false;
    }
    else if  (apellido && /^\s+$/.test(apellido)){
        alert("El apellido ingresado solo está formado por espacios en blanco");
        return false;
    }
    else if (apellido == ""){
        alert("Apellido Vacío");
        return false;
    }
    else if  (usuario &&/^\s+$/.test(usuario)){
        alert("El usuario ingresado solo está formado por espacios en blanco");
        return false;
    }
    else if  (usuario && usuario.length < 4){
        alert("El usuario debe tener una longitud mayor o igual a 4");
        return false;
    }
    else if (usuario == ""){
        alert("Usuario Vacío");
        return false;
    }
    else if  (contrasena && /^\s+$/.test(contrasena)){
        alert("La contraseña ingresada solo está formada por espacios en blanco");
        return false;
    }
    else if  (contrasena && (contrasena.length < 6 || contrasena.length > 16)){
        alert("La contraseña debe tener una longitud entre 6 y 16 caracteres");
        return false;
    }
    else if (contrasena == ""){
        alert("Contraseña Vacía");
        return false;
    }
    else if  (email && /^\s+$/.test(email)){
        alert("El email ingresado solo está formado por espacios en blanco");
        return false;
    }
    else if  (email && !(/^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i.test(email))){
        alert("El formato del email es inválido");
        return false;
    }
    else if (email == ""){
        alert("E-Mail Vacío");
        return false;
    }
    else if  (direccion && /^\s+$/.test(direccion)){
        alert("La dirección ingresada solo está formada por espacios en blanco");
        return false;
    }
    else if (direccion == ""){
        alert("Dirección Vacía");
        return false;
    }
    else if (legajo && legajo.length != 5){
        alert("La longitud del legajo debe ser igual a 5");
        return false;
    }
    else if (legajo == ""){
        alert("Legajo Vacío");
        return false;
    }
    else{
        return true;
    }
}
*/