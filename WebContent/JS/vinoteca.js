function validacion_actualizar_stock(){
	if(document.getElementById('cantidad')){
		var cantidad = document.getElementById('cantidad').value;
	}
	if(document.getElementById('precio')){
		var precio = document.getElementById('precio').value;
	}
	if (cantidad && /^\s+$/.test(cantidad)){
		alert("La cantidad ingresada est\u00e1 formada por espacios");
		return false;
	}
	else if (isNaN(cantidad)){
		alert("La cantidad ingresada debe ser un n\u00FAmero");
		return false;
	}
	else if(cantidad < 0){
		alert("La cantidad ingresada debe ser mayor a cero");
		return false;
	}
	else if(precio && /^\s+$/.test(precio)){
		alert("El precio ingresado est\u00e1 formado por espacios");
		return false;
	}
	else if(isNaN(precio)){
		alert("El precio ingresado debe ser un n\u00FAmero");
		return false;
	}
	else if(precio < 0){
		alert("El precio ingresado debe ser mayor a cero");
		return false;
	}
	else{
		return true;
	}
}

function validacion_alta_categoria(){
	if(document.getElementById('categoria')){
		var categoria = document.getElementById('categoria').value;
	}
	if(categoria && categoria == ""){
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