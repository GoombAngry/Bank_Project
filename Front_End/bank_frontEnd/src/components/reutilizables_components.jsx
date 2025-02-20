import React, { useEffect, useState } from "react";
import IconCuentaBancaria from "../assets/images/icono-cuenta-bancaria.png";
import IconTarjetaBancaria from "../assets/images/icono-tarjeta-bancaria.png";
import IconCruz from "../assets/images/x-solid.png";
import IconCruzNegra from "../assets/images/x-solid-black.png";
import { useNavigate } from "react-router-dom";
import "./style.css";
import {
  cerrarVentanaAccionTarjetas,
  cerrarVentanaAccionCuentas,
  capitalizeWord,
  cerrarVentanaAccionResumen,
} from "../utilities/utilitie";
// Componente navegacion que genera los indices de navegacion para los contenedores de Tarjeta/Cuenta Bancaria
export function NavegationComponent({ min, max, actual, methodChange }) {
  let aux = [];
  if (max === 0) {
    // No hay paginas existentes
    aux.push(
      <button
        key="1"
        className="button-pages-controller-list-container-cards-accounts active"
      >
        1
      </button>
    );
  } else {
    if (max <= 5) {
      for (let i = min; i <= max; i++) {
        if (i === actual) {
          aux.push(
            <button
              key={i}
              className="button-pages-controller-list-container-cards-accounts active"
              onClick={() => methodChange(i)}
            >
              {i}
            </button>
          );
        } else {
          aux.push(
            <button
              key={i}
              className="button-pages-controller-list-container-cards-accounts"
              onClick={() => methodChange(i)}
            >
              {i}
            </button>
          );
        }
      }
    } else {
      if (actual >= min && actual <= min + 2) {
        // Entre los 3 primeros principio (Pero el 4 hay que ponerlo)
        for (let i = min; i <= min + 3; i++) {
          if (i === actual) {
            aux.push(
              <button
                key={i}
                className="button-pages-controller-list-container-cards-accounts active"
                onClick={() => methodChange(i)}
              >
                {i}
              </button>
            );
          } else {
            aux.push(
              <button
                key={i}
                className="button-pages-controller-list-container-cards-accounts"
                onClick={() => methodChange(i)}
              >
                {i}
              </button>
            );
          }
        }
        aux.push(<a key="puntos1">...</a>);
        aux.push(
          <button
            key={max}
            className="button-pages-controller-list-container-cards-accounts"
            onClick={() => methodChange(max)}
          >
            {max}
          </button>
        );
      } else if (actual >= max - 2 && actual <= max) {
        // Entre los 3 primeros final (Pero el 4 hay que ponerlo)
        aux.push(
          <button
            key={min}
            className="button-pages-controller-list-container-cards-accounts"
            onClick={() => methodChange(min)}
          >
            {min}
          </button>
        );
        aux.push(<a key="puntos2">...</a>);
        for (let i = max - 3; i <= max; i++) {
          if (i === actual) {
            aux.push(
              <button
                key={i}
                className="button-pages-controller-list-container-cards-accounts active"
                onClick={() => methodChange(i)}
              >
                {i}
              </button>
            );
          } else {
            aux.push(
              <button
                key={i}
                className="button-pages-controller-list-container-cards-accounts"
                onClick={() => methodChange(i)}
              >
                {i}
              </button>
            );
          }
        }
      } else {
        aux.push(
          <button
            key={min}
            className="button-pages-controller-list-container-cards-accounts"
            onClick={() => methodChange(min)}
          >
            {min}
          </button>
        );
        aux.push(<a key="puntos3">...</a>);
        for (let i = actual - 1; i <= actual + 1; i++) {
          if (i === actual) {
            aux.push(
              <button
                key={i}
                className="button-pages-controller-list-container-cards-accounts active"
                onClick={() => methodChange(i)}
              >
                {i}
              </button>
            );
          } else {
            aux.push(
              <button
                key={i}
                className="button-pages-controller-list-container-cards-accounts"
                onClick={() => methodChange(i)}
              >
                {i}
              </button>
            );
          }
        }
        aux.push(<a key="puntos4">...</a>);
        aux.push(
          <button
            key={max}
            className="button-pages-controller-list-container-cards-accounts"
            onClick={() => methodChange(max)}
          >
            {max}
          </button>
        );
      }
    }
  }
  return aux;
}
// Componente que indica un mensaje de que no se encontraron tarjetas
export function NotFoundCuentas_Tarjeta({ message }) {
  return (
    <tr className="row_data-notFoundCuentas_Tarjetas">
      <td className="message-notFound" colSpan={4}>
        {message}
      </td>
    </tr>
  );
}

// Componente listado cuentas bancarias (Se complementa con el componente de abajo)
export const CuentasBancarias = ({ cuentasBancarias, showMovimientos }) => {
  var [pageActual, setPageActual] = useState(1);
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
            <tr key="row_header_account">
              <th key="th_1_account" className="th_1_account">
                Numero cuenta
              </th>
              <th key="th_2_account" className="th_2_account">
                Tipo
              </th>
              <th key="th_3_account" className="th_3_account">
                Operaciones
              </th>
              <th key="th_4_account" className="th_4_account">
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
                    key={item.numeroCuenta}
                    id_CuentaBancaria={item.id_cuenta_bancaria}
                    numberAccount={item.numeroCuenta}
                    aliasAccount={item.descripcion}
                    amountAccount={item.saldo}
                    showMovimientos={showMovimientos}
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
// Componente Cuenta Bancaria para el listado de cuentas bancarias
const CardCuentaBancaria = ({
  id_CuentaBancaria,
  numberAccount,
  aliasAccount,
  amountAccount,
  showMovimientos,
}) => {
  return (
    <tr className="row_data">
      <td className="number-account">{numberAccount}</td>
      <td className="type-account">{aliasAccount}</td>
      <td className="operations-account">
        <a
          href="#"
          className="actionMovimiento-tipe-account"
          onClick={() => showMovimientos(id_CuentaBancaria, true)}
        >
          Consultar Movimientos
        </a>
      </td>
      <td className="amount-account">{amountAccount}€</td>
    </tr>
  );
};
// Componente que muestra el saldo total tanto total como individualmente (Cuenta y Tarjeta)
export const ContainerSaldoUser = ({ infoSaldoUser }) => {
  return (
    <div className="containerSaldoUser">
      <h2 className="tittle-containerSaldoUser">Saldo Total</h2>
      <div className="containerLinea">
        <div className="linea"></div>
      </div>
      <div className="info-containerSaldoUser">
        <div className="item-info-containerSaldoUser">
          <div className="header-item-info-containerSaldoUser">
            <img src={IconCuentaBancaria} alt="" />
            <h2>Cuentas Bancarias</h2>
          </div>
          <h4>{infoSaldoUser.Cuenta}&nbsp;€</h4>
        </div>
        <div className="item-info-containerSaldoUser">
          <div className="header-item-info-containerSaldoUser">
            <img src={IconTarjetaBancaria} alt="" />
            <h2>Tarjetas Bancarias</h2>
          </div>
          <h4>{infoSaldoUser.Tarjeta}&nbsp;€</h4>
        </div>
      </div>
    </div>
  );
};

// Componente listado tarjetas bancarias (Se complementa con el componente de abajo)
export const TarjetasBancarias = ({ tarjetasBancarias, showMovimientos }) => {
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
              <th key="th_3_tarjeta" className="th_3_tarjeta">
                Operaciones
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
                    id_Tarjeta={item.id_tarjeta_bancaria}
                    numberTarjeta={item.numeroTarjeta}
                    typeTarjeta={item.tipoTarjeta}
                    amountTarjeta={item.saldo}
                    showMovimientos={showMovimientos}
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

// Componente Tarjeta Bancaria para el listado de cuentas bancarias
const CardTarjetaBancaria = ({
  id_Tarjeta,
  numberTarjeta,
  typeTarjeta,
  amountTarjeta,
  showMovimientos,
}) => {
  return (
    <tr className="row_data">
      <td className="number-tarjeta">{numberTarjeta}</td>
      <td className="type-tarjeta">{typeTarjeta}</td>
      <td className="operations-tarjeta">
        <a
          href="#"
          className="actionMovimiento-tipe-tarjeta"
          onClick={() => showMovimientos(id_Tarjeta, false)}
        >
          Consultar Movimientos
        </a>
      </td>
      {typeTarjeta == "DEBITO" ? (
        <td className="amount-accout disable"></td>
      ) : (
        <td className="amount-accout">{amountTarjeta}€</td>
      )}
    </tr>
  );
};

// Componente que muestra un listado de los movimientos de una tarjeta o cuenta bancaria seleccionada (Se complemenenta con el de abajo)
export const MovimientosAction = ({
  movimientos,
  setAccion,
  tittle,
  iconSelected,
  typeMovimientos,
  isResumen,
}) => {
  var [pageActual, setPageActual] = useState(1);
  /*
    typeMovimientos [ Para cerrar la ventana y saber que abrir y que cerrar usamos el bool]
    false -> Tarjetas
    true -> Cuentas
  */
  return (
    <>
      <div className="header-movimiento-accion">
        <img
          src={IconCruzNegra}
          width={27}
          height={27}
          onClick={() => {
            if (isResumen) {
              cerrarVentanaAccionResumen();
            } else {
              if (typeMovimientos) {
                cerrarVentanaAccionCuentas();
              } else {
                cerrarVentanaAccionTarjetas();
              }
            }
            setAccion(false);
          }}
        />
      </div>
      <div className="container-movimientos">
        <div className="header-container-movimientos">
          <img
            src={iconSelected}
            alt=""
            className="icon-container-movimientos"
          />
          <h3 className="tittle-container-movimientos">{tittle}</h3>
        </div>
        <div className="list-container-movimientos">
          <table className="table-list-container-movimientos">
            <thead>
              <tr key="row_header">
                <th key="th_1_movimiento" className="th_1_fecha_movimiento">
                  Fecha Movimiento
                </th>
                <th
                  key="th_2_movimiento"
                  className="th_2_movimiento_descripcion"
                >
                  Descripcion
                </th>
                <th key="th_3_movimiento" className="th_3_movimiento_tipo">
                  Tipo
                </th>
                <th key="th_4_movimiento" className="th_4_movimiento_cantidad">
                  Cantidad
                </th>
              </tr>
            </thead>
            <tbody>
              {movimientos.length == 0 ? (
                <NotFoundCuentas_Tarjeta message={"No existen movimientos!"} />
              ) : (
                movimientos
                  .slice((pageActual - 1) * 6, pageActual * 6)
                  .map((item,index) => <CardMovimientoItem key={index} data_movimiento={item} />)
              )}
            </tbody>
          </table>
        </div>
        <div className="controller-list-container-movimientos">
          <h5 className="info-pages-controller-lists">
            Pagina {pageActual} de{" "}
            {movimientos.length == 0 ? 1 : Math.ceil(movimientos.length / 6)}
          </h5>
          <div className="pages-controller-list-container-movimientos">
            <NavegationComponent
              min={1}
              max={Math.ceil(movimientos.length / 6)}
              actual={pageActual}
              methodChange={setPageActual}
            />
          </div>
        </div>
      </div>
    </>
  );
};

// Componente pare representa los datos de un movimiento de tarjeta o cuenta bancaria
const CardMovimientoItem = ({ data_movimiento }) => {
  var [year, month, day] = data_movimiento.fecha_movimiento
    .split("T")[0]
    .split("-");
  return (
    <tr className="row_data_movimientos">
      <td className="fechaInicial-movimiento">
        {day}/{month}/{year}
      </td>
      <td className="descripcion-movimiento">{data_movimiento.descripcion}</td>
      <td className="tipo-movimiento">{data_movimiento.tipo_movimiento}</td>
      <td className="cantidad-movimiento">{data_movimiento.cantidad}</td>
    </tr>
  );
};
// Con este componente podremos seleccionar una cuenta bancaria para el apartado traspaso
export const SelectorCuentasBancarias = ({
  cuentasBancarias,
  selectCuenta,
}) => {
  var [pageActual, setPageActual] = useState(1);
  var [cuentaSeleccionada, setCuentaSeleccionada] = useState();
  return (
    <div className="container-select-general">
      <div className="account-selected">
        <h3>Cuenta Seleccionada:</h3>
        <p>
          &nbsp;
          {!cuentaSeleccionada
            ? "Seleccione una cuenta"
            : cuentaSeleccionada.numeroCuenta}
        </p>
      </div>
      <div className="container-select">
        <table className="table-container-select">
          <thead>
            <tr key="row_header_account">
              <th key="th_1_item" className="th_2_item_account-number">
                Numero cuenta
              </th>
              <th key="th_2_item" className="th_2_item_account-saldo">
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
                  <CardSelectedItem
                    key={item.id_cuenta_bancaria}
                    infoAccount={item}
                    seleccionarCuenta={setCuentaSeleccionada}
                    selectCuenta={selectCuenta}
                  />
                ))
            )}
          </tbody>
        </table>
        <div className="controller-list-container-select">
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
    </div>
  );
};

const CardSelectedItem = ({ infoAccount, seleccionarCuenta, selectCuenta }) => {
  return (
    <tr className="row_data">
      <td className="number-account-selection">
        <button
          className="btn-number-account"
          onClick={() => {
            seleccionarCuenta(infoAccount);
            selectCuenta(infoAccount);
          }}
        >
          {infoAccount.numeroCuenta}
        </button>
      </td>
      <td className="amount-account">{infoAccount.saldo}€</td>
    </tr>
  );
};

export const ComponentWaitingMesage = ({ icon, sizeIcon, tittle }) => {
  return (
    <div className="container-waitingMessage">
      <img
        src={icon}
        alt="Icono Waiting Message"
        height={sizeIcon.height}
        width={sizeIcon.width}
      />
      <h3>{tittle}</h3>
    </div>
  );
};
// Este componente mostrara la accion seleccionada dentro marco preview Tarjeta o Cuenta bancaria
export const VentanaAccion = ({ actionSelected }) => {
  return (
    <div className="ventana-Accion">
      {actionSelected ? actionSelected : <></>}
    </div>
  );
};

// Este componente contiene el marco que muestra un mensaje de estado relacionado con las operaciones dentro del Preview Tarjeta o Cuenta bancaria
export const VentanaMensajeAccion = ({ componentMessage }) => {
  return (
    <div className="ventana-mensaje-container-preview">{componentMessage}</div>
  );
};

// Componente para mostrar un mensaje provocado por una accion sobre la tarjeta o cuenta bancaria
export const MessageAction = ({ tittle, message }) => {
  return (
    <div className="content-message-action">
      <h2 className="style-content-message-action">{tittle}</h2>
      <p className="style-content-message-action">{message}</p>
    </div>
  );
};

// Componente para mostrar la lista de tipos de tarjeta disponibles
export const ListaTiposCuentaBancaria = ({
  tiposCuentaBancaria,
  setTipoCuentaSelected,
  tipoCuentaSelected,
}) => {
  function handleSelectList(event) {
    if (event.target.value !== "Select") {
      setTipoCuentaSelected(
        tiposCuentaBancaria.find(
          (item) => item.id_tipo_cuenta === parseInt(event.target.value)
        )
      );
    } else {
      setTipoCuentaSelected(false);
    }
  }
  return (
    <div className="list-tipos-container-Alta-Nueva-CuentaBancaria">
      <p>Tipo de cuenta bancaria: </p>{" "}
      <select
        name="tiposCuenta"
        id="tiposCuenta"
        value={
          tipoCuentaSelected ? tipoCuentaSelected.id_tipo_cuenta : "Select"
        }
        onChange={handleSelectList}
      >
        <option value="Select">Selecciona un tipo</option>
        {tiposCuentaBancaria.map((item) => (
          <option key={item.id_tipo_cuenta} value={item.id_tipo_cuenta}>
            {capitalizeWord(item.nombre)}
          </option>
        ))}
      </select>
    </div>
  );
};
// Componente para mostrar la lista de tipos de tarjeta disponibles
export const ListaTiposTarjeta = ({
  tiposTarjeta,
  setTipoTarjetaSelected,
  tipoTarjetaSelected,
}) => {
  function handleSelectList(event) {
    if (event.target.value !== "Select") {
      setTipoTarjetaSelected(
        tiposTarjeta.find(
          (item) => item.id_tipo_tarjeta === parseInt(event.target.value)
        )
      );
    } else {
      setTipoTarjetaSelected(false);
    }
  }
  return (
    <div className="list-tipos-container-Alta-Nueva-Tarjeta">
      <p>Tipo de tarjeta: </p>{" "}
      <select
        name="tiposTarjeta"
        id="tiposTarjeta"
        value={
          tipoTarjetaSelected ? tipoTarjetaSelected.id_tipo_tarjeta : "Select"
        }
        onChange={handleSelectList}
      >
        <option value="Select">Selecciona un tipo</option>
        {tiposTarjeta.map((item) => (
          <option key={item.id_tipo_tarjeta} value={item.id_tipo_tarjeta}>
            {capitalizeWord(item.nombre)}
          </option>
        ))}
      </select>
    </div>
  );
};
