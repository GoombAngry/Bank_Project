import React, { useEffect, useState } from "react";

import CircularGraphic from "../Graphics/graphics";
import {
  CuentasBancarias,
  ContainerSaldoUser,
  TarjetasBancarias,
  VentanaAccion,
  MovimientosAction,
} from "../reutilizables_components";
import {
  abrirVentanaAccionCuentas,
  abrirVentanaAccionResumen,
  abrirVentanaAccionTarjetas,
  getCuentasBancarias,
  getMovimientosCuentaBancaria,
  getMovimientosTarjeta,
  getTarjetasBancarias,
  logout,
} from "../../utilities/utilitie";
import "./style.css";
import IconBank from "../../assets/images/icon_bank.png";
import IconCuentaBancaria from "../../assets/images/icono-cuenta-bancaria.png";
import IconTarjetaBancaria from "../../assets/images/icono-tarjeta-bancaria.png";
import { useNavigate } from "react-router-dom";

// Componente que muestra informacion acerca del usuario y opcion de des-logueo aparte de alguna opcion varia
const ContainerUser = ({ nameCliente, cambiarSectionPerfil, goToLogin }) => {
  async function cerrarSession() {
    var response = await logout(document.cookie.split("=")[1]);
    console.log(response);
    if (response !== 403) {
      console.log("Se ha cerrado correctamente la session");
    } else {
      console.log("Hubo un problema al cerrar la sesion");
    }
    goToLogin();
  }
  return (
    <div className="info-personal">
      <div className="header-info-personal">
        <img src={IconBank} className="image-header-info-personal" />
        <h5 className="tittle-header-info-personal">
          Hola,&nbsp;{nameCliente ? nameCliente : ""}
        </h5>
      </div>
      <div className="containerLinea">
        <div className="linea"></div>
      </div>
      <ul className="list-info-personal">
        <li>
          <a
            href="#"
            onClick={() => {
              cambiarSectionPerfil();
            }}
          >
            Ajustes
          </a>
        </li>
        <li>
          <a
            href="#"
            onClick={() => {
              cerrarSession();
            }}
          >
            Cerrar Session
          </a>
        </li>
      </ul>
    </div>
  );
};
// Componente que muestra un reloj indicando solo la hora
const ComponentReloj = () => {
  const [hora, setHora] = useState("");
  // Componente para representar la hora
  const HoraComponente = ({ hora }) => {
    var splitHora = hora.split(":");
    splitHora[0] =
      parseInt(splitHora[0]) < 10 ? "0" + splitHora[0] : splitHora[0];
    splitHora[1] =
      parseInt(splitHora[1]) < 10 ? "0" + splitHora[1] : splitHora[1];
    splitHora[2] =
      parseInt(splitHora[2]) < 10 ? "0" + splitHora[2] : splitHora[2];

    return (
      <div className="content-containerReloj">
        <h2 className="hour-content-containerReloj">{splitHora[0]}</h2>
        <span className="separator--content-containerReloj">:</span>
        <h2 className="minute-content-containerReloj">{splitHora[1]}</h2>
        <span className="separator--content-containerReloj">:</span>
        <h2 className="seconds-content-containerReloj">{splitHora[2]}</h2>
      </div>
    );
  };
  // Función para actualizar la hora
  const actualizarHora = () => {
    const fecha = new Date();
    const horaActual = fecha.getHours();
    const minutos = fecha.getMinutes();
    const segundos = fecha.getSeconds();

    // Formatear la hora en formato HH:MM:SS
    setHora(`${horaActual}:${minutos}:${segundos}`);
  };

  // useEffect para configurar el intervalo
  useEffect(() => {
    // Llamar a la función al montar el componente
    actualizarHora();
    // Actualizar la hora cada minuto (60000 ms)
    const intervaloHora = setInterval(actualizarHora, 1000);
    // Limpiar el intervalo cuando el componente se desmonte
    return () => clearInterval(intervaloHora);
  }, []);
  return (
    <div className="containerReloj">
      <h2 className="tittle-containerReloj">Hora actual</h2>
      <div className="containerLinea">
        <div className="linea"></div>
      </div>
      <HoraComponente hora={hora} />
    </div>
  );
};
// Componente Resumen (Muestra un resumen bancaria de nuestra cuenta)
const Resumen = ({ userInfo, cambiarSectionPerfil }) => {
  var [cuentasBancarias, setCuentasBancarias] = useState([]);
  var [tarjetasBancarias, setTarjetasBancarias] = useState([]);
  var [infoSaldoUser, setInfoSaldoUser] = useState({ Tarjeta: 0, Cuenta: 0 });
  var [actionSelected, setActionSelected] = useState(false);
  // Variable para redireccionar usando react-router
  const navigate = useNavigate();
  // Funcion para usar el redireccionamiento con react-router
  function goToLogin() {
    navigate("/");
  }
  // Primero cargamos las tarjetas y cuentas bancarias
  useEffect(() => {
    console.log("[useEffect] --> Cargo las cuentas y tarjetas bancarias");
    obtenerCuentasBancarias(); // Cargamos las cuentas bancarias
    obtenerTarjetasBancarias(); // Cargamos las tarjetas bancarias
  }, []);
  // Segundo una vez sean cargadas las varias cuentas y tarjetas se ejecutare el calculo del total de las cuentas y tarjetas
  useEffect(() => {
    var n = {
      Cuenta: 0,
      Tarjeta: 0,
    };
    cuentasBancarias.forEach((item) => {
      n.Cuenta += item.saldo;
    });
    tarjetasBancarias.forEach((item) => {
      n.Tarjeta += item.saldo;
    });
    n.Cuenta = n.Cuenta.toFixed(2);
    n.Tarjeta = n.Tarjeta.toFixed(2);
    setInfoSaldoUser(n);
  }, [cuentasBancarias, tarjetasBancarias]);

  // Metodo para cargar las cuentas bancarias del cliente
  async function obtenerCuentasBancarias() {
    var response = await getCuentasBancarias(document.cookie.split("=")[1]);
    if (response === 403) {
      // Redirijimos para el login por invalidez del JWT
      goToLogin();
    } else {
      setCuentasBancarias(response.body);
      console.log("[?] - Cuentas Bancarias cargadas correctamente!");
    }
  }
  // Metodo para cargar las tarjetas bancarias del cliente
  async function obtenerTarjetasBancarias() {
    var response = await getTarjetasBancarias(document.cookie.split("=")[1]);
    if (response === 403) {
      // Redirijimos para el login por invalidez del JWT
      goToLogin();
    } else {
      setTarjetasBancarias(response.body);
      console.log("[?] - Tarjetas Bancarias cargadas correctamente!");
    }
  }

  // Metodo para mostrar los movimientos tanto de cuentas bancarias como tarjeta bancarias
  async function showMovimientos(id_identificador, flag) {
    var response;
    if (flag) {
      // Cuenta Bancaria
      response = await getMovimientosCuentaBancaria(
        document.cookie.split("=")[1],
        id_identificador
      );
    } else {
      // Tarjeta Bancaria
      response = await getMovimientosTarjeta(
        document.cookie.split("=")[1],
        id_identificador
      );
    }
    if (response === 403) {
      // Redirijimos para el login por invalidez del JWT
      goToLogin();
    } else {
      setActionSelected(
        <MovimientosAction
          typeMovimientos={flag}
          setAccion={setActionSelected}
          movimientos={response.body}
          tittle={
            flag
              ? "Movimientos Cuenta Bancaria"
              : "Movimientos Tarjeta Bancaria"
          }
          iconSelected={flag ? IconCuentaBancaria : IconTarjetaBancaria}
          isResumen={true}
        />
      );
      abrirVentanaAccionResumen();
    }
  }

  return (
    <div className="container-portal">
      <VentanaAccion actionSelected={actionSelected} />
      <div className="container-portal-firts">
        <h3 className="tittle-container-portal-firts">Resumen</h3>
        <CuentasBancarias
          cuentasBancarias={cuentasBancarias}
          showMovimientos={showMovimientos}
        />
        <TarjetasBancarias
          tarjetasBancarias={tarjetasBancarias}
          showMovimientos={showMovimientos}
        />
      </div>
      <div className="container-portal-second">
        <ContainerUser
          nameCliente={userInfo.nombre}
          cambiarSectionPerfil={cambiarSectionPerfil}
          goToLogin={goToLogin}
        />
        <ContainerSaldoUser infoSaldoUser={infoSaldoUser} />
        <CircularGraphic goToLogin={goToLogin} />
        <ComponentReloj />
      </div>
    </div>
  );
};
export default Resumen;
