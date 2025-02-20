import { useState, useEffect } from "react";
import IconUser from "../../assets/images/icon-user.png";
import IconConfirmar from "../../assets/images/cambiar-datos.png";
import IconEditar from "../../assets/images/modificar-datos.png";

import "./style.css";
import { darDeBajaClienteBanco, logout, modifiedInformacionCliente } from "../../utilities/utilitie";
import {
  MessageAction,
  VentanaMensajeAccion,
} from "../reutilizables_components";
import { useNavigate } from "react-router-dom";

// Componente para la cabecera para volver al panel del Perfil
const ContenedorBackToPanelPerfil = ({ cambiarComponente }) => {
  return (
    <div className="containerBackToPanelPerfil">
      <button
        onClick={() => cambiarComponente(false)}
        className="btnBack-containerBackToPanelPerfil"
      ></button>
    </div>
  );
};
// Componente Datos Personales
const DatosPersonales = ({
  userInfo,
  cambiarComponente,
  changeUserInfo,
  goToLogin,
}) => {
  console.log(userInfo);
  const fechaContext = userInfo.fecha_alta.split("T")[0].split("-");
  var [isModifiedData, setModifiedData] = useState(false);
  var [componentMessage, setComponentMessage] = useState(false);

  var data = [
    [
      {
        id: 1,
        tittle: "Dni: ",
        value: userInfo.dni_cliente,
        isActiveInput: false,
        idInput: "campo_dni_datosPersonales",
      },
      {
        id: 2,
        tittle: "Nombre: ",
        value: userInfo.nombre,
        isActiveInput: isModifiedData,
        idInput: "campo_nombre_datosPersonales",
      },
      {
        id: 3,
        tittle: "Apellidos: ",
        value: userInfo.apellidos,
        isActiveInput: isModifiedData,
        idInput: "campo_apellidos_datosPersonales",
      },
    ],
    [
      {
        id: 4,
        tittle: "Calle: ",
        value: userInfo.direccion,
        isActiveInput: isModifiedData,
        idInput: "campo_calle_datosPersonales",
      },
      {
        id: 5,
        tittle: "Telefono: ",
        value: userInfo.telefono,
        isActiveInput: isModifiedData,
        idInput: "campo_telefono_datosPersonales",
      },
      {
        id: 6,
        tittle: "Correo: ",
        value: userInfo.correo_electronico,
        isActiveInput: isModifiedData,
        idInput: "campo_correo_datosPersonales",
      },
    ],
    [
      {
        id: 7,
        tittle: "Fecha Alta: ",
        value: fechaContext[2] + "-" + fechaContext[1] + "-" + fechaContext[0],
        isActiveInput: false,
        idInput: "campo_fecha_datosPersonales",
      },
      {
        id: 8,
        tittle: "Ciudad: ",
        value: userInfo.ciudad,
        isActiveInput: isModifiedData,
        idInput: "campo_ciudad_datosPersonales",
      },
    ],
  ];
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

  // Representa la data dentro del row
  const ItemData = ({ d }) => {
    return (
      <div className="itemData">
        <h3 className="tittle-itemData">{d.tittle}</h3>
        <input
          defaultValue={d.value}
          disabled={!d.isActiveInput}
          className="value-itemData"
          id={d.idInput}
        />
      </div>
    );
  };
  // Mostrar fila con los datos a representar
  const RowData = ({ data }) => {
    return (
      <div className="rowData">
        {data.map((item) => (
          <ItemData key={item.id} d={item} />
        ))}
      </div>
    );
  };
  // Metodo para modificar los datos del usuario
  async function cambiarDatosUsuario() {
    var oj = {};
    var sendChange = false;
    const nombre = document.getElementById(
      "campo_nombre_datosPersonales"
    ).value;
    if (nombre !== userInfo.nombre) {
      oj.nombre = nombre;
      sendChange = true;
    }
    const apellidos = document.getElementById(
      "campo_apellidos_datosPersonales"
    ).value;
    if (apellidos !== userInfo.apellidos) {
      oj.apellidos = apellidos;
      sendChange = true;
    }
    const calle = document.getElementById("campo_calle_datosPersonales").value;
    if (calle !== userInfo.direccion) {
      oj.calle = calle;
      sendChange = true;
    }
    const telefono = document.getElementById(
      "campo_telefono_datosPersonales"
    ).value;
    if (telefono !== userInfo.telefono) {
      oj.telefono = telefono;
      sendChange = true;
    }
    const correo = document.getElementById(
      "campo_correo_datosPersonales"
    ).value;
    if (correo !== userInfo.correo_electronico) {
      oj.correo_electronico = correo;
      sendChange = true;
    }
    const ciudad = document.getElementById(
      "campo_ciudad_datosPersonales"
    ).value;
    if (ciudad !== userInfo.ciudad) {
      oj.ciudad = ciudad;
      sendChange = true;
    }
    if (sendChange) {
      var response = await modifiedInformacionCliente(
        document.cookie.split("=")[1],
        oj
      );
      if (response.codeResponse) {
        if (response.codeResponse === 200) {
          if (oj.hasOwnProperty("nombre")) {
            userInfo.nombre = oj.nombre;
          }
          if (oj.hasOwnProperty("apellidos")) {
            userInfo.apellidos = oj.apellidos;
          }
          if (oj.hasOwnProperty("calle")) {
            userInfo.direccion = oj.calle;
          }
          if (oj.hasOwnProperty("telefono")) {
            userInfo.telefono = oj.telefono;
          }
          if (oj.hasOwnProperty("correo_electronico")) {
            userInfo.correo_electronico = oj.correo_electronico;
          }
          if (oj.hasOwnProperty("ciudad")) {
            userInfo.ciudad = oj.ciudad;
          }
          changeUserInfo(userInfo);
          showMessageEstadoAction(
            "Exito!",
            "Se han guardados los cambios",
            true
          );
        } else {
          showMessageEstadoAction("Error!", "Se ha producido un error", true);
        }
      } else {
        // Error pal lobby crack
        goToLogin();
      }
    }
    // Hay que hacer la peticion y si es validad remplazar los datos
    setModifiedData(false);
  }
  // Metodo para dar de baja una cuenta de usuario
  async function darDeBajaCuenta(){
    var response = await darDeBajaClienteBanco(document.cookie.split("=")[1]);
    if(response !== 404){
      console.log("Cliente dado de baja correctamente");
    }else{
      console.log("Hubo un problema al eliminar el cliente");
    }
    goToLogin();
  }

  return (
    <>
      <ContenedorBackToPanelPerfil cambiarComponente={cambiarComponente} />
      <div className="containerDatosPersonales">
        <VentanaMensajeAccion componentMessage={componentMessage} />
        <h3 className="tittle-containerDatosPersonales">Datos Personales</h3>
        <div className="content-containerDatosPersonales">
          <RowData data={data[0]} />
          <RowData data={data[1]} />
          <RowData data={data[2]} />
        </div>
        <h4 className="tittle-contentAcciones">Acciones Disponibles</h4>
        <div className="contentAcciones-containerDatosPersonales">
          {isModifiedData ? (
            <button
              className="btnAcciones-containerDatosPersonales confirmar"
              onClick={() => cambiarDatosUsuario()}
            >
              {" "}
              <img
                src={IconConfirmar}
                alt="Confirmar Icono"
                width={30}
                height={30}
                className="iconBtn-containerDatosPersonales"
              />
              Guardar Cambios
            </button>
          ) : (
            <button
              className="btnAcciones-containerDatosPersonales modificar"
              onClick={() => setModifiedData(true)}
            >
              <img
                src={IconEditar}
                alt="Editar Icono"
                width={30}
                height={30}
                className="iconBtn-containerDatosPersonales"
              />
              Modificar Datos
            </button>
          )}
          <button className="btnAcciones-containerDatosPersonales" onClick={() => darDeBajaCuenta()}>
            Dar de baja Cuenta
          </button>
        </div>
      </div>
    </>
  );
};
//  Componente que muestra el panel de perfil
const PanelPerfil = ({
  userInfo,
  cambiarComponente,
  changeUserInfo,
  goToLogin,
}) => {
  var opciones = [
    {
      nameOption: "Datos Personales",
      componente: (
        <DatosPersonales
          userInfo={userInfo}
          cambiarComponente={cambiarComponente}
          changeUserInfo={changeUserInfo}
          goToLogin={goToLogin}
        />
      ),
    },
    { nameOption: "Cerrar Session", componente: null },
  ];
  // Componente para representar cada accion disponible del panel de Perfil
  const ItemOptionPerfil = ({ option }) => {
    
    async function cerrarSesion(){
      var response = await logout(document.cookie.split("=")[1]);
      if(response !== 403){
        console.log("Se cerro correctamente la sesion");
      }else{
        console.log("Surgio un problema al cerrar sesion");
      }
      goToLogin();
    }

    return (
      <div
        className="item-content-container-perfil-main"
        onClick={() => {
          if (option.nameOption === "Cerrar Session") {
            cerrarSesion();
          } else {
            cambiarComponente(option.componente);
          }
        }}
      >
        <h3>{option.nameOption}</h3>
      </div>
    );
  };
  return (
    <>
      <div className="header-container-perfil-main">
        <img src={IconUser} alt="Icono User" />
        <h2>
          Hola, {userInfo.nombre} {userInfo.apellidos}
        </h2>
      </div>
      <div className="content-container-perfil-main">
        {opciones.map((item, index) => (
          <ItemOptionPerfil key={index} option={item} />
        ))}
      </div>
    </>
  );
};
const Perfil = ({ userInfo, changeUserInfo }) => {
  var [componente, setComponente] = useState(false);
  // Variable para redireccionar usando react-router
  const navigate = useNavigate();
  // Funcion para usar el redireccionamiento con react-router
  function goToLogin() {
    navigate("/");
  }
  return (
    <div className="container-perfil">
      <div className="container-perfil-main">
        {!componente ? (
          <PanelPerfil
            changeUserInfo={changeUserInfo}
            userInfo={userInfo}
            cambiarComponente={setComponente}
            goToLogin={goToLogin}
          />
        ) : (
          componente
        )}
      </div>
    </div>
  );
};

export default Perfil;
