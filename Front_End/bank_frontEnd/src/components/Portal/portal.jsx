import React, { useEffect, useState } from "react";
import "./style.css";
import Header from "../Header/header";
import Footer from "../Footer/footer";
import Resumen from "../Resumen/resumen";
import Cuentas from "../Cuentas/cuentas";
import Tarjetas from "../Tarjetas/tarjetas";
import Perfil from "../Perfil/perfil";
import { getInformacionUsuario } from "../../utilities/utilitie";
import { useNavigate } from "react-router-dom";

// Componente principal
const Portal = () => {
  var [actualPage, setPage] = useState();
  var [userInfo, setUserInfo] = useState({});
  var [headerRoutes,setHeaderRoutes] = useState([
    { name: "Resumen", componente: <Resumen/> },
    { name: "Cuentas", componente: <Cuentas/> },
    { name: "Tarjetas", componente: <Tarjetas /> },
    { name: "Perfil", componente: <Perfil /> },
    { name: "Cerrar session" },
  ]);
  // Variable para redireccionar usando react-router
  const navigate = useNavigate();
  // Funcion para usar el redireccionamiento con react-router
  function goToLogin(){
    navigate('/');
  }
  useEffect(()=>{
    obtenerInformacionUsuario(); // Cargamos informacion del cliente
  },[]);
  // Metodo para cargar datos del cliente
  async function obtenerInformacionUsuario() {
    var response = await getInformacionUsuario(document.cookie.split("=")[1]);
    if (response === 403) {
      // Redirijimos para el login por invalidez del JWT
      goToLogin();
    } else {
      setUserInfo(response.body);
    }
  }
  // Cuando se detecte un cambio en la variable userInfo
  useEffect(()=>{
    setHeaderRoutes([
      { id:0,name: "Resumen", componente: <Resumen userInfo={userInfo} cambiarSectionPerfil={changeSectionPerfil}/> },
      { id:1,name: "Cuentas", componente: <Cuentas userInfo={userInfo}/> },
      { id:2,name: "Tarjetas", componente: <Tarjetas userInfo={userInfo}/> },
      { id:3,name: "Perfil", componente: <Perfil userInfo={userInfo} changeUserInfo={setUserInfo}/> },
      { id:4,name: "Cerrar session" },
    ]);
  },[userInfo])

  // Metodo para cambiar al apartado perfil desde el apartado user desde Resumen
  function changeSectionPerfil(){
    setPage(headerRoutes[3].componente);
  }
  return (
    <div className="container_main">
      <Header apartados={headerRoutes} setPageMethod={setPage} goToLogin={goToLogin}/>
      {!actualPage ? <Resumen userInfo={userInfo} cambiarSectionPerfil={changeSectionPerfil}/> : actualPage}
      <Footer />
    </div>
  );
};

export default Portal;
