import { useState, useEffect } from "react";
import "./style.css";
import {
  getCuentasBancarias,
  modificarEstadoCuentaBancaria,
  getMovimientosCuentaBancaria,
  transferenciaCuentas,
  cerrarVentanaAccionCuentas,
  abrirVentanaAccionCuentas,
  getTiposDeCuentaBancaria,
  capitalizeWord,
  solicitarAltaNuevaCuentaBancaria,
} from "../../utilities/utilitie";
import {
  ComponentWaitingMesage,
  NotFoundCuentas_Tarjeta,
  NavegationComponent,
  VentanaAccion,
  VentanaMensajeAccion,
  MessageAction,
  MovimientosAction,
  ListaTiposCuentaBancaria,
} from "../reutilizables_components";
import IconCuentaBancaria from "../../assets/images/icono-cuenta-bancaria.png";
import IconCruz from "../../assets/images/x-solid.png";
import IconCruzNegra from "../../assets/images/x-solid-black.png";
import { useNavigate } from "react-router-dom";

// Componente para dar de alta una nueva tarjeta bancaria
const DarAltaNuevaCuentaBancaria = ({
  setAccion,
  showMessage,
  refrescarCuentas,
  goToLogin,
}) => {
  var [tiposCuentasBancarias, setTiposCuentasBancarias] = useState([]);
  var [tipoCuentaSelected, setTipoCuentaSelected] = useState(false);
  useEffect(() => {
    getTiposCuentaBancaria(); // Obtenemos los diferentes tipos de cuentas bancarias disponibles
  }, []);

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
  async function confirmarSolicitudAltaCuentaBancaria() {
    if (tipoCuentaSelected) {
      var message = document.getElementsByClassName(
        "messageError-container-Alta-Nueva-CuentaBancaria"
      )[0];
      if (message.classList.contains("show")) {
        message.classList.remove("show");
      }
      console.log(tipoCuentaSelected);
      var response = await solicitarAltaNuevaCuentaBancaria(
        document.cookie.split("=")[1],
        tipoCuentaSelected.id_tipo_cuenta
      );
      if (response !== 403) {
        if (response.codeResponse === 200) {
          showMessage("Exito!", "Cuenta creada correctamente!", true);
        } else {
          showMessage("Error!", "Error al crear la cuenta", false);
        }
        setTimeout(() => {
          refrescarCuentas(); // Refrescamos el listado de cuentas
          cerrarVentanaAccionCuentas();
          setAccion(false);
        }, 2000);
      } else {
        // Falta definir redireccion "pal lobby crack"
        goToLogin();
      }
    } else {
      var message = document.getElementsByClassName(
        "messageError-container-Alta-Nueva-CuentaBancaria"
      );
      message[0].classList.add("show");
    }
  }
  return (
    <div className="container-Alta-Nueva-CuentaBancaria">
      <div className="header-container-Alta-Nueva-CuentaBancaria">
        <img
          src={IconCruzNegra}
          width={27}
          height={27}
          onClick={() => {
            cerrarVentanaAccionCuentas();
            setAccion(false);
          }}
        />
      </div>
      <h3 className="tittle-container-Alta-Nueva-CuentaBancaria">
        Solicitar nueva cuenta bancaria
      </h3>
      <div className="content-container-Alta-Nueva-CuentaBancaria">
        <ListaTiposCuentaBancaria
          tipoCuentaSelected={tipoCuentaSelected}
          setTipoCuentaSelected={setTipoCuentaSelected}
          tiposCuentaBancaria={tiposCuentasBancarias}
        />
        <p className="messageError-container-Alta-Nueva-CuentaBancaria">
          *Seleccione un tipo de cuenta
        </p>
      </div>
      <button
        className="btn-confirm-container-Alta-Nueva-CuentaBancaria"
        onClick={() => confirmarSolicitudAltaCuentaBancaria()}
      >
        Solicitar Cuenta
      </button>
    </div>
  );
};
// Componente listado tarjetas bancarias
const CuentasBancarias = ({
  cuentasBancarias,
  changeIsSelected,
  changeCuentaSelected,
}) => {
  var [pageActual, setPageActual] = useState(1);

  // Componente Tarjeta Bancaria para el listado de cuentas bancarias
  const CardCuentaBancaria = ({
    id_cuenta_bancaria,
    numberCuenta,
    typeCuenta,
    amountCuenta,
    cuentasBancarias,
    changeIsSelected,
    changeCuentaSelected,
  }) => {
    return (
      <tr className="row_data">
        <td className="number-tarjeta">
          <button
            onClick={() => {
              changeIsSelected(true);
              changeCuentaSelected(
                cuentasBancarias.find(
                  (item) => item.id_cuenta_bancaria === id_cuenta_bancaria
                )
              );
            }}
          >
            {numberCuenta}
          </button>
        </td>
        <td className="type-tarjeta">{typeCuenta}</td>
        <td className="amount-accout">{amountCuenta}€</td>
      </tr>
    );
  };
  return (
    <div className="container-cards-accounts">
      <div className="header-container-cards-accounts">
        <img
          src={IconCuentaBancaria}
          alt=""
          className="icon-container-cards-accounts"
        />
        <h3 className="tittle-container-cards-accounts">Cuentas Bancarias</h3>
      </div>
      <div className="list-container-cards-accounts">
        <table className="table-list-container-cards-accounts">
          <thead>
            <tr key="row_header">
              <th key="th_1_cuenta" className="th_1_tarjeta">
                Numero cuenta
              </th>
              <th key="th_2_cuenta" className="th_2_tarjeta">
                Tipo Cuenta
              </th>
              <th key="th_3_cuenta" className="th_4_tarjeta">
                Saldo
              </th>
            </tr>
          </thead>
          <tbody>
            {cuentasBancarias.length == 0 ? (
              <NotFoundCuentas_Tarjeta
                message={"No dispones de cuentas existentes!"}
              />
            ) : (
              cuentasBancarias
                .slice((pageActual - 1) * 6, pageActual * 6)
                .map((item) => (
                  <CardCuentaBancaria
                    amountCuenta={item.saldo}
                    changeIsSelected={changeIsSelected}
                    changeCuentaSelected={changeCuentaSelected}
                    cuentasBancarias={cuentasBancarias}
                    id_cuenta_bancaria={item.id_cuenta_bancaria}
                    numberCuenta={item.numeroCuenta}
                    typeCuenta={item.descripcion}
                    key={item.id_cuenta_bancaria}
                  />
                ))
            )}
          </tbody>
        </table>
      </div>
      <div className="controller-list-container-cards-accounts">
        <h5 className="info-pages-controller-lists">
          Pagina {pageActual} de{" "}
          {cuentasBancarias.length == 0
            ? 1
            : Math.ceil(cuentasBancarias.length / 6)}
        </h5>
        <div className="pages-controller-list-container-cards-accounts">
          <NavegationComponent
            min={1}
            max={Math.ceil(cuentasBancarias.length / 6)}
            actual={pageActual}
            methodChange={setPageActual}
          />
        </div>
      </div>
    </div>
  );
};
// Container principal para mostrar informacion de la cuenta bancaria seleccionada
const ContainerPreviewCuenta = ({
  cuentaInfo,
  nameOwner,
  isSelectedAccount,
  setIsSelectedAccount,
  changeCuentaSelected,
  refrescarCuentas,
  setAction,
  showMessage,
  goToLogin,
}) => {
  // Componente para mostrar
  const TransferenciaAction = ({
    cuentaRemitente,
    showMessage,
    refrescarCuentas,
    changeCuentaSelected,
    setAction,
    goToLogin,
  }) => {
    const InfoTransferencia = ({ tittle, data }) => {
      return (
        <div className="infoTransferencia-container-transferenciaCuenta-Action">
          <h4>{tittle}</h4>
          <p>{data}</p>
        </div>
      );
    };
    const RemitenteInfo = ({ nameTitular, cuentaInfo }) => {
      return (
        <div className="containerRemitente-container-transferenciaCuenta-Action">
          <InfoTransferencia
            tittle={"Titular:"}
            data={nameOwner.nombre + " " + nameOwner.apellidos}
          />
          <InfoTransferencia
            tittle={"BANK ID:"}
            data={cuentaInfo.numeroCuenta}
          />
          <InfoTransferencia
            tittle={"Descripcion:"}
            data={cuentaInfo.descripcion}
          />
          <InfoTransferencia tittle={"Saldo:"} data={cuentaInfo.saldo} />
        </div>
      );
    };
    const DestinatarioInfo = () => {
      function validarCuenta(event) {
        if (/\D/.test(event.target.value)) {
          event.target.value = event.target.value.slice(0, -1); // Elimina el último carácter no numérico
        }
      }
      return (
        <div className="containerDestinatario-container-transferenciaCuenta-Action">
          <h4>BANK ID:</h4>
          <div className="input-containerDestinatario-container-transferenciaCuenta-Action">
            <span>BANK-</span>
            <input
              type="text"
              id="inputDestinatarioCuentaBancaria"
              name="cuenta"
              pattern="\d{14}"
              maxLength="14"
              onChange={validarCuenta}
              required
            />
          </div>
        </div>
      );
    };
    const CantidadTransferencia = () => {
      var [valor, setValue] = useState(0);
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
        setValue(nuevoValor);
      };
      return (
        <div className="containerCantidad-container-transferenciaCuenta-Action">
          <h4>Cantidad: </h4>
          <input
            type="text"
            id="inputCantidad-containerCantidad-container-transferenciaCuenta-Action"
            onChange={controlarCampoNumerico}
            value={valor}
          />
        </div>
      );
    };
    async function realizarTransferencia() {
      if (cuentaRemitente.saldo === 0) {
        showMessage(
          "Error!",
          "No dispones de saldo para la transferencia",
          false
        );
      }
      const valorIdCuentaBancaria = document.getElementById(
        "inputDestinatarioCuentaBancaria"
      );
      if (!/^\d{14}$/.test(valorIdCuentaBancaria.value)) {
        showMessage("Error!", "El BANK-ID no es correcto, faltan numeros.");
      } else {
        let value = parseFloat(
          document.getElementById(
            "inputCantidad-containerCantidad-container-transferenciaCuenta-Action"
          ).value
        ).toFixed(2);
        if (value <= 0 || isNaN(value)) {
          showMessage(
            "Operacion fallida!",
            "Debes introducir una cantidad valida y superior a 0",
            false
          );
        } else {
          let check = String(value);
          if (check.includes(".") && check.split(".")[1] === "00") {
            check = parseInt(check);
          }
          // Realizar transferencia
          var response = await transferenciaCuentas(
            document.cookie.split("=")[1],
            cuentaRemitente,
            value * -1,
            "BANK-" + valorIdCuentaBancaria.value
          );

          if (response.codeResponse) {
            if (response.codeResponse === 200) {
              showMessage(
                "Exito!",
                "Se realizo la transferencia con exito!",
                true
              );
              cuentaRemitente.saldo -= value;
              changeCuentaSelected(cuentaRemitente);
              refrescarCuentas();
              setTimeout(() => {
                cerrarVentanaAccionCuentas();
                setAction(false);
              }, 2000);
            } else {
              showMessage("Error!", "Se ha producido un error!", false);
              setTimeout(() => {
                cerrarVentanaAccionCuentas();
                setAction(false);
              }, 2000);
            }
          } else {
            // Falta definir redireccion "pal lobby crack"
            goToLogin();
          }
        }
      }
    }
    return (
      <div className="container-transferenciaCuenta-Action">
        <div className="header-container-transferenciaCuenta-Action">
          <img
            src={IconCruzNegra}
            width={27}
            height={27}
            onClick={() => {
              cerrarVentanaAccionCuentas();
              setAction(false);
            }}
          />
        </div>
        <h3>Transferencia</h3>
        <h4 className="tittle-container-transferenciaCuenta-Action">
          Remitente
        </h4>
        <RemitenteInfo nameTitular={nameOwner} cuentaInfo={cuentaRemitente} />
        <h4 className="tittle-container-transferenciaCuenta-Action">
          Destinatario
        </h4>
        <DestinatarioInfo />
        <CantidadTransferencia />
        <div className="container-btn-container-transferenciaCuenta-Action">
          <button
            className="btn-container-transferenciaCuenta-Action"
            onClick={() => realizarTransferencia()}
          >
            Realizar
          </button>
        </div>
      </div>
    );
  };
  const ComponenteDatoBancaria = ({ tittleDato, dato }) => {
    return (
      <div className="cajaInfo_contenerInfoCuentaBancaria-previewCuenta">
        <h3>{tittleDato}</h3>
        <p>{dato}</p>
      </div>
    );
  };
  var year = "",
    month = "",
    day = "";
  if (isSelectedAccount) {
    var aux = cuentaInfo.fechaAlta.split("T")[0].split("-");
    year = aux[0];
    month = aux[1];
    day = aux[2];
  }
  // Metodo para desplegar la ventana emergente para mostrar los movimientos de la cuenta bancaria
  async function showMovimientosCuentaBancaria() {
    var response = await getMovimientosCuentaBancaria(
      document.cookie.split("=")[1],
      cuentaInfo.id_cuenta_bancaria
    );
    if (response.codeResponse === 200) {
      setAction(
        <MovimientosAction
          movimientos={response.body}
          iconSelected={IconCuentaBancaria}
          tittle={"Movimientos Cuenta Bancaria"}
          typeMovimientos={true}
          setAccion={setAction}
        />
      );
      console.log("[?] - Movimientos cuenta bancaria obtenidos correctamente!");
      abrirVentanaAccionCuentas();
    } else {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    }
  }

  // Metodo para mostrar el m
  function showMenuTransferencia() {
    setAction(
      <TransferenciaAction
        cuentaRemitente={cuentaInfo}
        showMessage={showMessage}
        refrescarCuentas={refrescarCuentas}
        changeCuentaSelected={changeCuentaSelected}
        setAction={setAction}
        goToLogin={goToLogin}
      />
    );
    abrirVentanaAccionCuentas();
  }

  async function activar_desactivarCuentaBancaria() {
    var response = await modificarEstadoCuentaBancaria(
      document.cookie.split("=")[1],
      cuentaInfo.id_cuenta_bancaria
    );
    console.log(cuentaInfo);
    if (response.codeResponse) {
      if (response.codeResponse === 200) {
        showMessage(
          response.body,
          cuentaInfo.activa
            ? "La cuenta se ha desactivado"
            : "La cuenta se ha activado",
          true
        );
        cuentaInfo.activa = !cuentaInfo.activa;
        changeCuentaSelected(cuentaInfo);
      } else {
        showMessage(response.body, response.message, false);
      }
    } else {
      // Falta definir redireccion "pal lobby crack"
      goToLogin();
    }
  }
  return (
    <div
      className={
        isSelectedAccount
          ? "containerPreview-cuenta show"
          : "containerPreview-cuenta"
      }
    >
      {isSelectedAccount ? (
        <div className="container-previewCuenta">
          <div className="contenerInfoCuentaBancaria-previewCuenta">
            <div className="containerIconBack-contenerInfoCuentaBancaria-previewCuenta">
              <img
                src={IconCruz}
                width={27}
                height={27}
                onClick={() => {
                  setIsSelectedAccount(false);
                }}
              />
            </div>
            <div className="header-contenerInfoCuentaBancaria-previewCuenta">
              <h3>Informacion Cuenta Bancaria</h3>
            </div>
            <div className="info-contenerInfoCuentaBancaria-previewCuenta">
              <ComponenteDatoBancaria
                tittleDato={"Titular:"}
                dato={nameOwner.nombre + " " + nameOwner.apellidos}
              />
              <ComponenteDatoBancaria
                tittleDato={"Bank Id:"}
                dato={cuentaInfo.numeroCuenta}
              />
              <ComponenteDatoBancaria
                tittleDato={"Tipo Cuenta:"}
                dato={cuentaInfo.descripcion}
              />
              <ComponenteDatoBancaria
                tittleDato={"Fecha Alta:"}
                dato={day + "/" + month + "/" + year}
              />
              <ComponenteDatoBancaria
                tittleDato={"Estado:"}
                dato={cuentaInfo.activa ? "Activa" : "Desactivada"}
              />
              <ComponenteDatoBancaria
                tittleDato={"Saldo:"}
                dato={cuentaInfo.saldo}
              />
            </div>
            <div className="container-acciones-contenerInfoCuentaBancaria-previewCuenta">
              <h2>Acciones</h2>
              <div className="acciones-contenerInfoCuentaBancaria-previewCuenta">
                <button onClick={() => showMenuTransferencia()}>
                  Transferencia
                </button>
                <button
                  onClick={() => {
                    activar_desactivarCuentaBancaria();
                  }}
                >
                  Activar/Desactiva
                </button>
                <button onClick={() => showMovimientosCuentaBancaria()}>
                  Movimientos
                </button>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <ComponentWaitingMesage
          icon={IconCuentaBancaria}
          sizeIcon={{ height: 100, width: 100 }}
          tittle={"Seleccione una cuenta para ver detalles"}
        />
      )}
    </div>
  );
};

const Cuentas = ({ userInfo }) => {
  var [cuentasBancarias, setCuentasBancarias] = useState([]);
  var [isSelectedCuenta, setIsSelectedCuenta] = useState(false);
  var [cuentaSelected, setCuentaSelected] = useState();
  var [actionSelected, setActionSelected] = useState(false);
  var [componentMessage, setComponentMessage] = useState(false);
  // Variable para redireccionar usando react-router
  const navigate = useNavigate();
  // Funcion para usar el redireccionamiento con react-router
  function goToLogin() {
    navigate("/");
  }

  useEffect(() => {
    obtenerCuentasBancarias();
  }, []);

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

  // Metodo para refrescar cuentas bancarias
  function refrescarCuentasBancarias() {
    obtenerCuentasBancarias();
  }

  const AccionesGeneralesCuentaBancaria = ({
    showMessage,
    refrescarCuentas,
    setAccion,
    goToLogin,
  }) => {
    return (
      <div className="acciones-generales-cuentas">
        <h2>Acciones Generales</h2>
        <button
          onClick={() => {
            setAccion(
              <DarAltaNuevaCuentaBancaria
                refrescarCuentas={refrescarCuentas}
                setAccion={setAccion}
                showMessage={showMessage}
                goToLogin={goToLogin}
              />
            );
            abrirVentanaAccionCuentas();
          }}
        >
          <img src={IconCuentaBancaria} width={50} height={50} /> Dar de alta
          nueva Cuenta Bancaria
        </button>
      </div>
    );
  };

  return (
    <div className="container-cuentas">
      <div className="container-cuentas-firts">
        <h3 className="tittle-container-portal-firts">Cuentas</h3>
        <VentanaAccion actionSelected={actionSelected} />
        <VentanaMensajeAccion componentMessage={componentMessage} />
        <ContainerPreviewCuenta
          changeCuentaSelected={setCuentaSelected}
          nameOwner={userInfo}
          setIsSelectedAccount={setIsSelectedCuenta}
          isSelectedAccount={isSelectedCuenta}
          cuentaInfo={cuentaSelected}
          refrescarCuentas={refrescarCuentasBancarias}
          setAction={setActionSelected}
          showMessage={showMessageEstadoAction}
          goToLogin={goToLogin}
        />
        <CuentasBancarias
          cuentasBancarias={cuentasBancarias}
          changeIsSelected={setIsSelectedCuenta}
          changeCuentaSelected={setCuentaSelected}
        />
        <AccionesGeneralesCuentaBancaria
          setAccion={setActionSelected}
          showMessage={showMessageEstadoAction}
          refrescarCuentas={refrescarCuentasBancarias}
          goToLogin={goToLogin}
        />
      </div>
    </div>
  );
};

export default Cuentas;
