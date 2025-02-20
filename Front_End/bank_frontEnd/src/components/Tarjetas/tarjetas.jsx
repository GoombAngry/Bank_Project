import { useState, useEffect } from "react";
import IconBank from "../../assets/images/icon_bank.png";
import IconTarjeta from "../../assets/images/tarjeta-de-credito.png";
import IconTarjetaBancaria from "../../assets/images/icono-tarjeta-bancaria.png";
import IconCruz from "../../assets/images/x-solid.png";
import IconCruzBlack from "../../assets/images/x-solid-black.png";

import {
  NavegationComponent,
  NotFoundCuentas_Tarjeta,
  SelectorCuentasBancarias,
  ComponentWaitingMesage,
  MovimientosAction,
  VentanaAccion,
  VentanaMensajeAccion,
  MessageAction,
  ListaTiposCuentaBancaria,
  ListaTiposTarjeta,
} from "../reutilizables_components";
import {
  getTarjetasBancarias,
  getMovimientosTarjeta,
  modificarEstadoTarjeta,
  modificarLimiteTarjeta,
  getCuentasBancarias,
  traspasoTarjeta,
  getTiposDeTarjetaBancaria,
  abrirVentanaAccionTarjetas,
  cerrarVentanaAccionTarjetas,
  getTiposDeCuentaBancaria,
  solicitarAltaNuevaTarjeta,
} from "../../utilities/utilitie";
import "./style.css";
import { useNavigate } from "react-router-dom";
// Componente para mostrar la ventana de traspaso
const TraspasoAction = ({
  setAccion,
  setMessageStatus,
  typeTraspaso,
  tarjetaInfo,
  refrescarTarjetas,
  changeTarjetaSelected,
  goToLogin,
}) => {
  var [cuentasBancarias, setCuentasBancarias] = useState([]);
  var [cuentaSeleccionada, setCuentaSeleccionada] = useState();
  useEffect(() => {
    obtenerCuentasBancarias(); // Cargamos las cuentas Bancarias
  }, []);
  /*
      { - typeTraspaso value - }
      false = Tarjeta a Cuenta
      true = Cuenta a Tarjeta
    */
  // Metodo para cargar las cuentas bancarias del cliente
  async function obtenerCuentasBancarias() {
    var response = await getCuentasBancarias(document.cookie.split("=")[1]);
    if (response === 403) {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    } else {
      setCuentasBancarias(response.body);
      console.log("[?] - Cuentas Bancarias cargadas correctamente!");
    }
  }
  const CajaComponente = ({ titulo, texto }) => {
    return (
      <div className="cajaInfo-infoTarjeta-container-general-info-tarjeta-traspaso">
        <h4>{titulo}</h4>
        <p>{texto}</p>
      </div>
    );
  };
  const ContainerInfoTarjetaTraspaso = ({ tarjetaInfo, typeTraspaso }) => {
    return (
      <div className="container-general-info-tarjeta-traspaso">
        <h3 className="tittle-container-general-info-tarjeta-traspaso">
          {typeTraspaso ? "Tarjeta de Abono" : "Tarjeta de Cargo"}
        </h3>
        <div className="infoTarjeta-container-general-info-tarjeta-traspaso">
          <CajaComponente
            titulo={"Numero de tarjeta:"}
            texto={tarjetaInfo.numeroTarjeta}
          />
          <CajaComponente
            titulo={"Limite Tarjeta:"}
            texto={tarjetaInfo.limiteTarjeta}
          />
          <CajaComponente titulo={"Saldo Actual:"} texto={tarjetaInfo.saldo} />
        </div>
      </div>
    );
  };
  const controlarCampoNumerico = (event) => {
    // Permitir solo números y un único punto decimal
    let nuevoValor = event.target.value.replace(/[^0-9.]/g, ""); // Elimina caracteres no numéricos ni puntos
    // Asegura que no haya un punto al principio
    if (nuevoValor.startsWith(".")) {
      nuevoValor = "0.00"; // Si el valor comienza con un punto, lo reemplazamos por '0.'
    }
    // Permitir solo un punto decimal
    nuevoValor = nuevoValor.replace(/(\..*?)\..*/g, "$1");
    // Limitar a 2 decimales si hay decimales
    if (nuevoValor.includes(".")) {
      let [entero, decimales] = nuevoValor.split(".");
      decimales = decimales.slice(0, 2); // Cortamos a máximo 2 decimales
      if (decimales === "00") {
        nuevoValor = entero;
      } else {
        nuevoValor = entero + "." + decimales;
      }
    }
    document.getElementById("valueInputTraspasoTarjeta").value = nuevoValor;
  };
  async function realizarTraspaso() {
    if (cuentaSeleccionada) {
      var value = document.getElementById("valueInputTraspasoTarjeta").value;
      if (value.length != 0) {
        var flag = false;
        if (
          (value.includes(".") && value.split(".")[0].split("")[0] !== "0") ||
          value.split(".")[0].split("")[1] !== "0"
        ) {
          value = parseFloat(value);
          if (value > 0) {
            flag = true;
          }
        } else {
          value = parseInt(value);
          if (value > 0) {
            flag = true;
          }
        }
        if (flag) {
          var response = await traspasoTarjeta(
            document.cookie.split("=")[1],
            tarjetaInfo.numeroTarjeta,
            cuentaSeleccionada.numeroCuenta,
            typeTraspaso,
            value * -1
          );
          if (response.codeResponse && response.codeResponse === 200) {
            setMessageStatus(
              "Traspaso Realizado!",
              "Se realizo correctamente",
              true
            );
            if (!typeTraspaso) {
              tarjetaInfo.saldo -= value;
            } else {
              tarjetaInfo.saldo += value;
            }
            changeTarjetaSelected(tarjetaInfo);
          } else if (response.codeResponse && response.codeResponse === 404) {
            setMessageStatus("Error!", response.body, false);
          }
          if (response === 403) {
            // Falta definir redireccion "pal lobby crack"
            goToLogin();
          }
          setTimeout(() => {
            refrescarTarjetas();
            cerrarVentanaAccionTarjetas();
            setAccion(false);
          }, 2000);
        } else {
          setMessageStatus("Error!", "Debes indicar un importe valido", false);
        }
      } else {
        setMessageStatus("Error!", "Debes indicar el importe", false);
      }
    } else {
      setMessageStatus(
        "Error!",
        "Debes seleccionar una cuenta bancaria",
        false
      );
    }
  }
  return (
    <div className="container-traspaso-tarjeta-Accion">
      <div className="header-close-container-traspaso-tarjeta-Accion">
        <img
          src={IconCruzBlack}
          width={27}
          height={27}
          onClick={() => {
            cerrarVentanaAccionTarjetas();
            setAccion(false);
          }}
        />
      </div>
      <div className="header-tittle-container-traspaso-tarjeta-Accion">
        {!typeTraspaso ? (
          <h3>Traspaso Tarjeta a Cuenta</h3>
        ) : (
          <h3>Traspaso Cuenta a Tarjeta</h3>
        )}
      </div>
      {
        <SelectorCuentasBancarias
          cuentasBancarias={cuentasBancarias}
          selectCuenta={setCuentaSeleccionada}
        />
      }
      {
        <ContainerInfoTarjetaTraspaso
          tarjetaInfo={tarjetaInfo}
          typeTraspaso={typeTraspaso}
        />
      }
      <div className="container-cantidad-traspaso">
        <h2>Importe traspaso</h2>
        <input
          type="text"
          id="valueInputTraspasoTarjeta"
          className="valueInputTraspasoTarjeta"
          onChange={controlarCampoNumerico}
        />
      </div>
      <button
        className="btn-realizarTraspaso"
        onClick={() => {
          realizarTraspaso();
        }}
      >
        Realizar Traspaso
      </button>
    </div>
  );
};
// Componente para dar de alta una nueva tarjeta bancaria
const DarAltaNuevaTarjeta = ({
  goToLogin,
  setAccion,
  showMessageStatus,
  refrescarTarjetas,
}) => {
  var [tiposTarjetas, setTiposTarjetas] = useState([]);
  var [tiposCuentasBancarias, setTiposCuentasBancarias] = useState([]);
  var [tipoCuentaSelected, setTipoCuentaSelected] = useState(false);
  var [tipoTarjetaSelected, setTipoTarjetaSelected] = useState(false);

  useEffect(() => {
    getTiposTarjeta(); // Obtenemos los diferente tipos de tarjeta bancaria disponibles
    getTiposCuentaBancaria(); // Obtenemos los diferentes tipos de cuentas bancarias disponibles
  }, []);
  async function getTiposTarjeta() {
    var response = await getTiposDeTarjetaBancaria(
      document.cookie.split("=")[1]
    );
    if (response !== 403) {
      console.log(response.body);
      setTiposTarjetas(response.body);
    } else {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    }
  }
  async function getTiposCuentaBancaria() {
    var response = await getTiposDeCuentaBancaria(
      document.cookie.split("=")[1]
    );
    if (response !== 403) {
      console.log(response.body);
      setTiposCuentasBancarias(response.body);
    } else {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    }
  }
  async function confirmarSolicitudAltaTarjeta() {
    if (tipoTarjetaSelected && tipoCuentaSelected) {
      console.log(tipoTarjetaSelected);
      console.log(tipoCuentaSelected);
      var response = await solicitarAltaNuevaTarjeta(
        document.cookie.split("=")[1],
        tipoTarjetaSelected.id_tipo_tarjeta,
        tipoCuentaSelected.id_tipo_cuenta
      );
      if (response !== 403) {
        if (response.codeResponse === 200) {
          showMessageStatus(response.body, response.message, true);
        } else {
          showMessageStatus(response.body, response.message, false);
        }
        setTimeout(() => {
          refrescarTarjetas(); // Refrescamos el listado de tarjetas
          cerrarVentanaAccionTarjetas();
          setAccion(false);
        }, 2000);
      } else {
        // Falta definir redireccion "pal lobby crack"
        goToLogin();
      }
    } else {
      var message = document.getElementsByClassName(
        "messageError-container-Alta-Nueva-Tarjeta"
      );
      if (!tipoCuentaSelected) {

        message[1].classList.add("show");
      } else {
        message[1].classList.remove("show");
      }
      if (!tipoTarjetaSelected) {
        message[0].classList.add("show");
      } else {
        message[0].classList.remove("show");
      }
    }
  }
  return (
    <div className="container-Alta-Nueva-Tarjeta">
      <div className="header-container-Alta-Nueva-Tarjeta">
        <img
          src={IconCruzBlack}
          width={27}
          height={27}
          onClick={() => {
            cerrarVentanaAccionTarjetas();
            setAccion(false);
          }}
        />
      </div>
      <h3 className="tittle-container-Alta-Nueva-Tarjeta">
        Solicitar nueva tarjeta
      </h3>
      <div className="content-container-Alta-Nueva-Tarjeta">
        <ListaTiposTarjeta
          tiposTarjeta={tiposTarjetas}
          setTipoTarjetaSelected={setTipoTarjetaSelected}
          tipoTarjetaSelected={tipoTarjetaSelected}
        />
        <p className="messageError-container-Alta-Nueva-Tarjeta">
          *Seleccione un tipo de tarjeta
        </p>
        <ListaTiposCuentaBancaria
          tipoCuentaSelected={tipoCuentaSelected}
          setTipoCuentaSelected={setTipoCuentaSelected}
          tiposCuentaBancaria={tiposCuentasBancarias}
        />
        <p className="messageError-container-Alta-Nueva-Tarjeta">
          *Seleccione un tipo de cuenta
        </p>
      </div>
      <button
        className="btn-confirm-container-Alta-Nueva-Tarjeta"
        onClick={() => confirmarSolicitudAltaTarjeta()}
      >
        Solicitar Tarjeta
      </button>
    </div>
  );
};
// Componente para modificar el limite de la tarjeta
const ModificarLimiteTarjeta = ({
  tarjetaInfo,
  setAccion,
  setMessageStatus,
  changeTarjetaSelected,
  goToLogin,
}) => {
  var [valueLimite, setValueLimite] = useState(tarjetaInfo.limiteTarjeta);

  // Metodo para settear el limite de la tarjeta
  async function changeLimiteTarjeta() {
    let value = parseFloat(valueLimite).toFixed(2);
    if (value <= 0 || isNaN(value)) {
      setMessageStatus(
        "Operacion fallida!",
        "Debes introducir un valor superior a 0",
        false
      );
    } else {
      let check = String(value);
      if (check.includes(".") && check.split(".")[1] === "00") {
        value = parseInt(valueLimite);
      }
      var response = await modificarLimiteTarjeta(
        document.cookie.split("=")[1],
        tarjetaInfo.id_tarjeta_bancaria,
        value
      );
      if (response.codeResponse) {
        if (response.codeResponse === 200) {
          setMessageStatus(
            "Operacion exitosa!",
            "Limite establecido correctamente",
            true
          );
          tarjetaInfo.limiteTarjeta = value;
          cerrarVentanaAccionTarjetas();
          setAccion(false);
          changeTarjetaSelected(tarjetaInfo);
        }
      } else {
        // Falta definir redireccion "pal lobby crack"
        goToLogin();
      }
    }
  }
  const controlarCampoNumerico = (event) => {
    // Permitir solo números y un único punto decimal
    let nuevoValor = event.target.value.replace(/[^0-9.]/g, ""); // Elimina caracteres no numéricos ni puntos
    // Asegura que no haya un punto al principio
    if (nuevoValor.startsWith(".")) {
      nuevoValor = "0.00"; // Si el valor comienza con un punto, lo reemplazamos por '0.'
    }
    // Permitir solo un punto decimal
    nuevoValor = nuevoValor.replace(/(\..*?)\..*/g, "$1");
    // Limitar a 2 decimales si hay decimales
    if (nuevoValor.includes(".")) {
      let [entero, decimales] = nuevoValor.split(".");
      decimales = decimales.slice(0, 2); // Cortamos a máximo 2 decimales
      if (decimales === "00") {
        nuevoValor = entero;
      } else {
        nuevoValor = entero + "." + decimales;
      }
    }
    setValueLimite(nuevoValor);
  };
  return (
    <div className="container-modificarLimite-tarjeta-Accion">
      <div className="header-modificarLimite-tarjeta-Accion">
        <img
          src={IconCruzBlack}
          width={27}
          height={27}
          onClick={() => {
            cerrarVentanaAccionTarjetas();
            setAccion(false);
          }}
        />
      </div>
      <h3>Modificar Limite Tarjeta</h3>
      <div className="content-container-modificarLimite-tarjeta-Accion">
        <label htmlFor="limite">Limite Tarjeta</label>
        <input
          type="text"
          id="valueInputLimiteTarjeta"
          value={valueLimite}
          onChange={controlarCampoNumerico}
        />
      </div>
      <button
        className="btn-confirm-limite-tarjeta"
        onClick={() => changeLimiteTarjeta()}
      >
        Modificar Limite
      </button>
    </div>
  );
};
// Componente que dibuja una tarjeta con la informacion de la tarjeta
const Tarjeta = ({ tarjetaInfo, nameOwner }) => {
  var fecha = !tarjetaInfo
    ? "00/00"
    : tarjetaInfo.fechaVencimiento.split("T")[0].split("-");
  var formatedFecha =
    tarjetaInfo !== "00/00"
      ? fecha[1] + "/" + fecha[0].substring(4 - 2)
      : "00/00";
  return (
    <div className="viewtarjeta-tarjeta-container-previewTarjeta">
      <div className="header-viewTarjeta">
        <img src={IconBank} alt="Icono Banco" />
        <h2>Maze Bank</h2>
      </div>
      <div className="info-viewTarjeta">
        <h4 className="number-info-viewTarjeta">
          {!tarjetaInfo ? "Error" : tarjetaInfo.numeroTarjeta}
        </h4>
        <h4 className="expiration-info-viewTarjeta">
          <span className="text-expiration-info-viewTarjeta">Válida hasta</span>
          {formatedFecha}
        </h4>
        <h4 className="ownerName-info-viewTarjeta">
          {!nameOwner ? "Error" : nameOwner}
        </h4>
      </div>
    </div>
  );
};
// Container principal para mostrar informacion de la tarjeta que se seleccione
const ContainerPreviewTarjeta = ({
  tarjetaInfo,
  nameOwner,
  isSelectedCard,
  setIsSelectedCard,
  changeTarjetaSelected,
  refrescarTarjetas,
  setActionSelected,
  showMessageEstadoAction,
  goToLogin,
}) => {
  // Metodo para desplegar la ventana emergente para mostrar los movimientos de la tarjeta
  async function showMovimientosTarjeta() {
    var response = await getMovimientosTarjeta(
      document.cookie.split("=")[1],
      tarjetaInfo.id_tarjeta_bancaria
    );
    if (response === 403) {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    } else {
      setActionSelected(
        <MovimientosAction
          movimientos={response.body}
          setAccion={setActionSelected}
          iconSelected={IconTarjetaBancaria}
          tittle={"Movimientos Tarjeta Bancaria"}
          typeMovimientos={false}
        />
      );
      abrirVentanaAccionTarjetas();
    }
  }
  // Metodo para modificar el estado de la tarjeta
  async function actionModificarEstadoTarjeta() {
    var response = await modificarEstadoTarjeta(
      document.cookie.split("=")[1],
      tarjetaInfo.id_tarjeta_bancaria,
      !tarjetaInfo.activa
    );
    if (response.codeResponse) {
      if (response.codeResponse === 200) {
        tarjetaInfo.activa = !tarjetaInfo.activa;
        changeTarjetaSelected(tarjetaInfo); // Modificamos el valor del estado de la tarjeta para que se refresque el componente y muestre el nuevo valor asignado
        showMessageEstadoAction(
          "Operacion existosa!",
          tarjetaInfo.activa ? "Tarjeta activada" : "Tarjeta desactivada",
          true
        );
      }
    } else {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    }
  }

  // Metodo para mostrar el contenedor de modificar el limite de la tarjeta
  function actionModificarLimiteTarjeta() {
    setActionSelected(
      <ModificarLimiteTarjeta
        tarjetaInfo={tarjetaInfo}
        changeTarjetaSelected={changeTarjetaSelected}
        setAccion={setActionSelected}
        setMessageStatus={showMessageEstadoAction}
        goToLogin={goToLogin}
      />
    );
    abrirVentanaAccionTarjetas();
  }
  // Metodo para mostrar el contenedor de traspaso
  function showTraspaso(typeTraspaso) {
    /*
      { - typeTraspaso value - }
      false = Tarjeta a Cuenta
      true = Cuenta a Tarjeta
    */
    setActionSelected(
      <TraspasoAction
        tarjetaInfo={tarjetaInfo}
        changeTarjetaSelected={changeTarjetaSelected}
        setAccion={setActionSelected}
        setMessageStatus={showMessageEstadoAction}
        typeTraspaso={typeTraspaso}
        refrescarTarjetas={refrescarTarjetas}
        goToLogin={goToLogin}
      />
    );
    abrirVentanaAccionTarjetas();
  }
  return (
    <div
      className={
        !isSelectedCard
          ? "containerPreview-general"
          : tarjetaInfo.tipoTarjeta === "CREDITO"
          ? "containerPreview-general show credito"
          : "containerPreview-general show debito"
      }
    >
      {isSelectedCard ? (
        <div className="container-previewTarjeta">
          <div className="part1-container-previewTarjeta">
            <div className="tarjeta-container-previewTarjeta">
              <div className="headerIcon-tarjeta-container-previewTarjeta">
                <img
                  src={IconCruz}
                  width={27}
                  height={27}
                  onClick={() => {
                    setIsSelectedCard(false);
                  }}
                />
              </div>
              <Tarjeta tarjetaInfo={tarjetaInfo} nameOwner={nameOwner} />
            </div>
            <div className="infoTarjeta-container-previewTarjeta">
              <div>
                <h3>Nombre titular</h3>
                <img
                  src={IconCruzBlack}
                  width={27}
                  height={27}
                  onClick={() => {
                    setIsSelectedCard(false);
                  }}
                />
              </div>
              <p>{!nameOwner ? "Error" : nameOwner}</p>
              <h3>Cuenta bancaria asociada</h3>
              <p>{!tarjetaInfo ? "Error" : tarjetaInfo.cuentaVinculada}</p>
              <h3>Estado</h3>
              <p>
                {!tarjetaInfo
                  ? "Error"
                  : tarjetaInfo.activa
                  ? "Activa"
                  : "Desactivada"}
              </p>
              <h3>Tipo tarjeta</h3>
              <p>{!tarjetaInfo ? "Error" : tarjetaInfo.tipoTarjeta}</p>
              <h3>Limite Tarjeta</h3>
              <p>{!tarjetaInfo ? "Error" : tarjetaInfo.limiteTarjeta}</p>
            </div>
          </div>
          <div className="part2-container-previewTarjeta">
            <h2>Acciones</h2>
            <div className="acciones-container-previewTarjeta">
              <button onClick={() => showMovimientosTarjeta()}>
                Ver Movimientos
              </button>
              {tarjetaInfo.tipoTarjeta === "CREDITO" ? (
                <>
                  <button onClick={() => showTraspaso(false)}>
                    Traspaso Tarjeta a Cuenta
                  </button>
                  <button onClick={() => showTraspaso(true)}>
                    Traspaso Cuenta a Tarjeta
                  </button>
                </>
              ) : (
                <></>
              )}
              <button onClick={() => actionModificarEstadoTarjeta()}>
                Activa/Desactivar
              </button>
              <button onClick={() => actionModificarLimiteTarjeta()}>
                Modificar Limite
              </button>
            </div>
          </div>
        </div>
      ) : (
        <ComponentWaitingMesage
          icon={IconTarjeta}
          sizeIcon={{ height: 100, width: 100 }}
          tittle={"Seleccione una tarjeta para ver detalles"}
        />
      )}
    </div>
  );
};
// Componente Tarjeta Bancaria para el listado de cuentas bancarias
const CardTarjetaBancaria = ({
  id_tarjeta_bancaria,
  numberTarjeta,
  typeTarjeta,
  amountTarjeta,
  tarjetasBancarias,
  changeIsSelected,
  changeTarjetaSelected,
}) => {
  return (
    <tr className="row_data">
      <td className="number-tarjeta">
        <button
          onClick={() => {
            changeIsSelected(true);
            changeTarjetaSelected(
              tarjetasBancarias.find(
                (item) => item.id_tarjeta_bancaria === id_tarjeta_bancaria
              )
            );
          }}
        >
          {numberTarjeta}
        </button>
      </td>
      <td className="type-tarjeta">{typeTarjeta}</td>

      {typeTarjeta == "DEBITO" ? (
        <td className="amount-accout disable"></td>
      ) : (
        <td className="amount-accout">{amountTarjeta}€</td>
      )}
    </tr>
  );
};
// Componente listado tarjetas bancarias
const TarjetasBancarias = ({
  tarjetasBancarias,
  changeIsSelected,
  changeTarjetaSelected,
}) => {
  var [pageActual, setPageActual] = useState(1);

  // { numberTarjeta, typeTarjeta, saldoTarjeta }
  return (
    <div className="container-cards-accounts">
      <div className="header-container-cards-accounts">
        <img
          src={IconTarjetaBancaria}
          alt=""
          className="icon-container-cards-accounts"
        />
        <h3 className="tittle-container-cards-accounts">Tarjetas Bancarias</h3>
      </div>
      <div className="list-container-cards-accounts">
        <table className="table-list-container-cards-accounts">
          <thead>
            <tr key="row_header">
              <th key="th_1_tarjeta" className="th_1_tarjeta">
                Numero tarjeta
              </th>
              <th key="th_2_tarjeta" className="th_2_tarjeta">
                Tipo Tarjeta
              </th>
              <th key="th_4_tarjeta" className="th_4_tarjeta">
                Saldo
              </th>
            </tr>
          </thead>
          <tbody>
            {tarjetasBancarias.length == 0 ? (
              <NotFoundCuentas_Tarjeta
                message={"No dispones de tarjetas existentes!"}
              />
            ) : (
              tarjetasBancarias
                .slice((pageActual - 1) * 6, pageActual * 6)
                .map((item) => (
                  <CardTarjetaBancaria
                    key={item.numeroTarjeta}
                    id_tarjeta_bancaria={item.id_tarjeta_bancaria}
                    numberTarjeta={item.numeroTarjeta}
                    typeTarjeta={item.tipoTarjeta}
                    amountTarjeta={item.saldo}
                    tarjetasBancarias={tarjetasBancarias}
                    changeIsSelected={changeIsSelected}
                    changeTarjetaSelected={changeTarjetaSelected}
                  />
                ))
            )}
          </tbody>
        </table>
      </div>
      <div className="controller-list-container-cards-accounts">
        <h5 className="info-pages-controller-lists">
          Pagina {pageActual} de{" "}
          {tarjetasBancarias.length == 0
            ? 1
            : Math.ceil(tarjetasBancarias.length / 6)}
        </h5>
        <div className="pages-controller-list-container-cards-accounts">
          <NavegationComponent
            min={1}
            max={Math.ceil(tarjetasBancarias.length / 6)}
            actual={pageActual}
            methodChange={setPageActual}
          />
        </div>
      </div>
    </div>
  );
};
const Tarjetas = ({ userInfo }) => {
  var [isSelectedCard, setIsSelectedCard] = useState(false);
  var [tarjetasBancarias, setTarjetasBancarias] = useState([]);
  var [tarjetaSelected, setTarjetaSelected] = useState();
  var [actionSelected, setActionSelected] = useState(false);
  var [componentMessage, setComponentMessage] = useState(false);
  // Variable para redireccionar usando react-router
  const navigate = useNavigate();
  // Funcion para usar el redireccionamiento con react-router
  function goToLogin() {
    navigate("/");
  }

  useEffect(() => {
    obtenerTarjetasBancarias();
  }, []);
  function refrescarTarjetasBancarias() {
    obtenerTarjetasBancarias();
  }
  // Metodo para cargar las tarjetas bancarias del cliente
  async function obtenerTarjetasBancarias() {
    var response = await getTarjetasBancarias(document.cookie.split("=")[1]);
    if (response === 403) {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    } else {
      console.log(response.body);
      setTarjetasBancarias(response.body);
      console.log("[?] - Tarjetas Bancarias cargadas correctamente!");
    }
  }
  // Componente para crear una nueva tarjeta
  const AccionCrearTarjetaBancaria = ({
    setAccion,
    showMessageStatus,
    refrescarTarjetas,
    goToLogin,
  }) => {
    return (
      <div className="acciones-generales-tarjetas">
        <h2>Acciones Generales</h2>
        <button
          onClick={() => {
            setAccion(
              <DarAltaNuevaTarjeta
                goToLogin={goToLogin}
                setAccion={setAccion}
                showMessageStatus={showMessageStatus}
                refrescarTarjetas={refrescarTarjetas}
              />
            );
            abrirVentanaAccionTarjetas(); // Abrimos la ventana para mostrar el contenido
          }}
        >
          <img src={IconTarjeta} width={50} height={50} /> Dar de alta nueva
          Tarjeta
        </button>
      </div>
    );
  };
  // Metodo para mostrar un mensaje de estado al utilizar un boton del apartado de acciones
  function showMessageEstadoAction(tittle, message, isSuccess) {
    var divMessage = document.getElementsByClassName(
      "ventana-mensaje-container-preview"
    )[0];
    if (divMessage.classList.contains("error")) {
      divMessage.classList.remove("error");
    }
    if (divMessage.classList.contains("success")) {
      divMessage.classList.remove("success");
    }
    setComponentMessage(<MessageAction tittle={tittle} message={message} />);
    isSuccess
      ? divMessage.classList.add("success")
      : divMessage.classList.add("error");
    divMessage.classList.add("show");
    setTimeout(() => {
      divMessage.classList.remove("show");
    }, 3000);
  }

  return (
    <div className="container-tarjetas">
      <div className="container-tarjetas-firts">
        <h3 className="tittle-container-portal-firts">Tarjetas</h3>
        <VentanaAccion actionSelected={actionSelected} />
        <VentanaMensajeAccion componentMessage={componentMessage} />
        <ContainerPreviewTarjeta
          isSelectedCard={isSelectedCard}
          tarjetaInfo={tarjetaSelected}
          nameOwner={userInfo.nombre + " " + userInfo.apellidos}
          setIsSelectedCard={setIsSelectedCard}
          changeTarjetaSelected={setTarjetaSelected}
          refrescarTarjetas={refrescarTarjetasBancarias}
          setActionSelected={setActionSelected}
          showMessageEstadoAction={showMessageEstadoAction}
          goToLogin={goToLogin}
        />
        <TarjetasBancarias
          tarjetasBancarias={tarjetasBancarias}
          changeIsSelected={setIsSelectedCard}
          changeTarjetaSelected={setTarjetaSelected}
        />
        <AccionCrearTarjetaBancaria
          goToLogin={goToLogin}
          setAccion={setActionSelected}
          showMessageStatus={showMessageEstadoAction}
          refrescarTarjetas={refrescarTarjetasBancarias}
        />
      </div>
    </div>
  );
};

export default Tarjetas;
