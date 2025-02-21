import "./style.css";
import IconMenu from "../../assets/images/bars-solid.png";
import IconBank from "../../assets/images/icon_bank.png";
import IconCancel from "../../assets/images/x-solid.png";
import { logout } from "../../utilities/utilitie";

// Cabecera del usuario para navegar por los servicios
const Header = ({ apartados,setPageMethod,goToLogin }) => {
  // Funcion para mostrar/ocultar el menu
  function buttonMenu() {
    const menu = document.getElementById("menu");
    if (menu.classList.contains("hidde")) {
      menu.classList.remove("hidde");
      menu.classList.add("active");
      document.getElementById("icon-btn").src = IconCancel;
      document.body.style.overflow = "hidden";
    } else {
      menu.classList.remove("active");
      menu.classList.add("hidde");
      document.getElementById("icon-btn").src = IconMenu;
      document.body.style.overflow = "auto";
    }
  }
  function handleClickButton(id,component){
    if(id === 4){
      // Cerrar sesion
      logout(document.cookie.split("=")[1]);
      goToLogin();
    }else{
      setPageMethod(component);
    }
  }
  return (
    <header>
      <div className="header-tittle-icon">
        <img src={IconBank} className="header-image" />
        <h3 className="tittle-logo">Maze Bank</h3>
      </div>
      <nav id="desktop-nav">
        {apartados.map((item, index) => (
          <a key={item.name} href="#" onClick={()=>handleClickButton(item.id,item.componente)}>{item.name}</a>
        ))}
      </nav>
      <button id="menu-btn" className="menu-btn" onClick={() => buttonMenu()}>
        <img src={IconMenu} width={25} height={25} id="icon-btn" />
      </button>
      <div id="menu" className="menu hidde">
        {apartados.map((item, index) => (
           <a key={item.name} href="#" onClick={()=>handleClickButton(item.id,item.componente)}>{item.name}</a>
        ))}
      </div>
    </header>
  );
};

export default Header;
