// Esta almacenara la direccion del backend
var urlBackEnd = "http://localhost:8080";
// Comprobar si es correcto el dni
export function dniChecker(dni) {
  if (dni.length === 9) {
    dni = dni.toUpperCase();
    var valueResult = {
      0: "T",
      1: "R",
      2: "W",
      3: "A",
      4: "G",
      5: "M",
      6: "Y",
      7: "F",
      8: "P",
      9: "D",
      10: "X",
      11: "B",
      12: "N",
      13: "J",
      14: "Z",
      15: "S",
      16: "Q",
      17: "V",
      18: "H",
      19: "L",
      20: "C",
      21: "K",
      22: "E",
    };
    var number = Number(dni.slice(0, 8));
    if (isNaN(number) || number < 0) return false;
    return valueResult[number % 23] === dni[8];
  } else {
    return false;
  }
}
// Comprobar formato del telefono
export function check_number_phone(number) {
  return new RegExp("^\\d{9}$").test(info_Form["telefono"]);
}
// Dar de baja a un usuario
export function darDeBajaClienteBanco(jwt) {
  jwt = "Bearer " + jwt;
  // Eliminamos la cookie del navegador
  document.cookie =
    "token" + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
  return fetch(urlBackEnd + "/client/darDeBaja", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      Authorization: jwt,
    },
  })
    .then((data) => data)
    .catch((error) => 403);
}
// Cerrar Session
export function logout(jwt) {
  jwt = "Bearer " + jwt;
  // Eliminamos la cookie del navegador
  document.cookie =
    "token" + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
  return fetch(urlBackEnd + "/auth/logout", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      Authorization: jwt,
    },
  })
    .then((data) => data)
    .catch((error) => 403);
}
// Comprobar passwords
export function check_password(p1, p2) {
  return p1 === p2;
}
// Envia datos para registrar al cliente
export function sendRequestRegister(body) {
  return fetch(urlBackEnd + "/auth/registerCliente", {
    method: "POST", // Método de la solicitud (POST)
    headers: {
      "Content-Type": "application/json", // Indica que el cuerpo es un JSON
      Accept: "application/json", // Indica que esperas una respuesta en formato JSON
    },
    body: JSON.stringify({
      dni_cliente: body["dni"],
      nombre: body["nombre"],
      apellidos: body["apellidos"],
      correo_electronico: body["email"],
      password: body["password"],
      telefono: body["telefono"],
      direccion: body["direccion"],
      ciudad: body["ciudad"],
    }), // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => console.error("Error:", error));
}

// Envia datos para loguear al cliente
export function sendRequestLogin(body) {
  return fetch(urlBackEnd + "/auth/login", {
    method: "POST", // Método de la solicitud (POST)
    headers: {
      "Content-Type": "application/json", // Indica que el cuerpo es un JSON
      Accept: "application/json", // Indica que esperas una respuesta en formato JSON
    },
    body: JSON.stringify({
      dni_cliente: body["dni_usuario"],
      password: body["password"],
    }), // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}

// Obtener cuentas bancarias del cliente
export function getCuentasBancarias(jwt) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/account/getCuentasBancarias", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
    }, // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}

// Obtener tarjetas bancarias del cliente
export function getTarjetasBancarias(jwt) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/getTarjetasBancarias", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
    }, // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Modificar informacion del cliente
export function modifiedInformacionCliente(jwt, estructura) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/client/editInformacion", {
    method: "POST", // Método de la solicitud (POST)
    headers: {
      "Content-Type": "application/json", // Indica que el cuerpo es un JSON
      Accept: "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
    },
    body: JSON.stringify(estructura), // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion permite obtener los diferentes tipos de tarjetas bancarias disponibles
export function getTiposDeTarjetaBancaria(jwt) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/getTiposTarjeta", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
    }, // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion permite obtener los diferentes tipos de cuentas bancarias disponibles
export function getTiposDeCuentaBancaria(jwt) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/account/getTiposCuenta", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
    }, // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite recoger informacion personal del usuario
export function getInformacionUsuario(jwt) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/client/getInformacion", {
    method: "GET", // Método de la solicitud (GET)
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
    }, // Convierte el objeto a JSON
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite recoger estadisticas de los movimientos mensulaes realizados por el usuario
export function getResumenMensualUsuario(jwt) {
  const fechaActual = new Date();
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/data/getResumenMensual", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      year: fechaActual.getFullYear(),
      month: fechaActual.getMonth() + 1,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite recoger los movimientos de una tarjeta bancaria
export function getMovimientosTarjeta(jwt, id_tarjeta) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/getMovimientosTarjeta", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_tarjeta_bancaria: id_tarjeta,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcionos nos permite recoger las movimientos de una cuenta bancaria
export function getMovimientosCuentaBancaria(jwt, id_cuentaBancaria) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/account/getMovimientosCuenta", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_cuenta_bancaria: id_cuentaBancaria,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite modificar el limite de la tarjeta bancaria
export function modificarLimiteTarjeta(jwt, id_tarjeta, limite) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/changeLimiteTarjeta", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_tarjeta_bancaria: id_tarjeta,
      limite_tarjeta: limite,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite solicitar el alta de una nueva tarjeta bancaria
export function solicitarAltaNuevaTarjeta(jwt, id_tipoTarjeta, id_tipoCuenta) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/altaTarjetaCliente", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_tipo_tarjeta: id_tipoTarjeta,
      id_tipo_cuenta: id_tipoCuenta,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite solicitar el alta de una nueva cuenta bancaria
export function solicitarAltaNuevaCuentaBancaria(jwt, id_tipoCuenta) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/account/altaCuentaCliente", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_tipo_cuenta: id_tipoCuenta,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite modificar el estado de la tarjeta bancaria
export function modificarEstadoTarjeta(jwt, id_tarjeta, estado) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/changeEstadoTarjeta", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_tarjeta_bancaria: id_tarjeta,
      is_activa: estado,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite modificar el estado de la cuenta bancaria
export function modificarEstadoCuentaBancaria(jwt, id_cuentaBancaria) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/account/cambiarEstadoCuentaBancaria", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_cuenta_bancaria: id_cuentaBancaria,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite modificar el estado de la tarjeta bancaria
export function traspasoTarjeta(
  jwt,
  numero_tarjeta,
  numero_cuenta,
  type_traspaso,
  cantidadTraspaso
) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/tarjeta/addTraspaso", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      tipoTraspaso: type_traspaso,
      numero_cuenta: numero_cuenta,
      numeroTarjeta: numero_tarjeta,
      cantidad: cantidadTraspaso,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Esta funcion nos permite realizar una transferencia entre cuentas
export function transferenciaCuentas(
  jwt,
  remitenteObj,
  cantidad,
  destinatario
) {
  jwt = "Bearer " + jwt;
  return fetch(urlBackEnd + "/account/addMovimientoCuentaBancaria", {
    method: "POST",
    headers: {
      "Content-Type": "application/json", // Indica que esperas una respuesta en formato JSON
      Authorization: jwt,
      Accept: "application/json",
    }, // Convierte el objeto a JSON
    body: JSON.stringify({
      id_cuenta_bancaria: remitenteObj.id_cuenta_bancaria,
      tipo_movimiento: "TRANSFERENCIA",
      cantidad: cantidad,
      remitente_identificador: remitenteObj.numeroCuenta,
      destinatario_identificador: destinatario,
    }),
  })
    .then((response) => response.json())
    .then((data) => data)
    .catch((error) => 403);
}
// Crear una cookie
export function createdCookie(token) {
  const nombreCookie = "token";
  const fechaExpiracion = new Date();
  fechaExpiracion.setDate(fechaExpiracion.getDate() + 1); // 7 días

  document.cookie = `${nombreCookie}=${token}; expires=${fechaExpiracion.toUTCString()}; path=/; SameSite=Strict`;
}
// Este metodo abrira el container action de Tarjetas para mostrar funcionalidades
export function abrirVentanaAccionTarjetas() {
  document.getElementsByClassName("ventana-Accion")[0].classList.add("show");
  document
    .getElementsByClassName("container-tarjetas")[0]
    .classList.add("no-interactuable");
}
// Este metodo cerrara el container action de Tarjetas para mostrar funcionalidades
export function cerrarVentanaAccionTarjetas() {
  document.getElementsByClassName("ventana-Accion")[0].classList.remove("show");
  document
    .getElementsByClassName("container-tarjetas")[0]
    .classList.remove("no-interactuable");
}

// Este metodo abrira el container action de Cuentas para mostrar funcionalidades
export function abrirVentanaAccionCuentas() {
  document.getElementsByClassName("ventana-Accion")[0].classList.add("show");
  document
    .getElementsByClassName("container-cuentas")[0]
    .classList.add("no-interactuable");
}
// Este metodo cerrara el container action de Resumen para mostrar funcionalidades
export function cerrarVentanaAccionCuentas() {
  document.getElementsByClassName("ventana-Accion")[0].classList.remove("show");
  document
    .getElementsByClassName("container-cuentas")[0]
    .classList.remove("no-interactuable");
}

// Este metodo abrira el container action de Resumen para mostrar funcionalidades
export function abrirVentanaAccionResumen() {
  document.getElementsByClassName("ventana-Accion")[0].classList.add("show");
  document
    .getElementsByClassName("container-portal")[0]
    .classList.add("no-interactuable");
}
// Este metodo cerrara el container action de Resumen para mostrar funcionalidades
export function cerrarVentanaAccionResumen() {
  document.getElementsByClassName("ventana-Accion")[0].classList.remove("show");
  document
    .getElementsByClassName("container-portal")[0]
    .classList.remove("no-interactuable");
}

// Este metodo capitalizara las cadenas
export function capitalizeWord(cadena) {
  if (cadena.length === 0) return cadena;
  return cadena.charAt(0).toUpperCase() + cadena.slice(1).toLowerCase();
}
