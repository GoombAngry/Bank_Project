import "./style.css";
import icono from "../../assets/images/icon_bank.png";
import {
  dniChecker,
  sendRequestRegister,
  createdCookie,
  check_password,
  sendRequestLogin,
} from "../../utilities/utilitie";
import React from "react";
import { useNavigate } from "react-router-dom";

const Login = () => {
  // Navegacion por los diferentes Paths de nuestra app
  const navigate = useNavigate(); 
  const goToPortal = ()=>{
    navigate("/portal");
  };
  function showModal(data) {
    /*

    Explicacion entradas:

    {
                flag:false or true,
                tittle:<titulo modal>,
                message:<mensajeModal>
    }   
    
    Modelos fijos:
    {
                flag:false,
                tittle:"Registro fallido!",
                message:"¡No se a podido registrar el cliente correctamente!"
    }

    {
                flag:true,
                tittle:"Registro exitoso",
                message:"¡Tu registro se ha realizado correctamente!"
    }    

    */
    const modal = document.getElementById("success-modal");
    const closeModal = document.getElementById("close-modal");
    // Mostrar modal (aplicar transición de deslizamiento desde arriba)
    modal.style.display = "flex"; // Hace que el modal sea visible
    setTimeout(() => {
      console.log(data);
      modal.querySelector(".modal-content").classList.add("active"); // Desliza el modal hacia abajo
      data.flag
        ? modal.querySelector(".modal-content").classList.add("succes")
        : modal.querySelector(".modal-content").classList.add("error");
      document.getElementById("tittle_modal").textContent = data.tittle;
      document.getElementById("content_modal").textContent = data.message;
    }, 10); // Añadimos un pequeño retardo para que se aplique la transición correctamente

    // Cerrar el modal cuando se hace clic en la cruz
    closeModal.onclick = function () {
      modal.querySelector(".modal-content").classList.remove("active"); // El modal se desliza hacia arriba
      setTimeout(() => {
        modal.style.display = "none"; // Después de la animación, ocultamos el modal completamente
      }, 500); // Este tiempo debe coincidir con la duración de la animación
      data.flag
        ? modal.querySelector(".modal-content").classList.remove("succes")
        : modal.querySelector(".modal-content").classList.remove("error");
    };

    // Cerrar el modal si el usuario hace clic fuera del contenido del modal
    window.onclick = function (event) {
      if (event.target === modal) {
        closeModal.onclick(); // Si se hace clic fuera, cerramos el modal
      }
    };
  }
  function warning_MessageInputs(id, message) {
    document.getElementById(id).textContent = message;
    document.getElementById(id).style.display = "block";
  }
  async function handle_submit_form_register(event) {
    event.preventDefault();
    // Creamos la estructura formulario
    var form = new FormData(event.target);
    var data_form = {};
    var counterCheck = 0;
    form.forEach((value, key) => {
      data_form[key] = value;
    });
    // Validaciones datos form
    // Validamos el dni
    if (!dniChecker(data_form["dni"])) {
      // Mensaje warnign en input
      warning_MessageInputs(
        "register_dni_input",
        "Introduzca un dni correcto!"
      );
    } else {
      document.getElementById("register_dni_input").style.display = "none";
      counterCheck += 1;
    }
    // Comprobamos passwords
    if (
      !check_password(data_form["confirm_password"], data_form[["password"]])
    ) {
      // Mensaje warnign en input
      warning_MessageInputs(
        "register_p1_input",
        "Las contraseñas no coinciden!"
      );
      warning_MessageInputs(
        "register_p2_input",
        "Las contraseñas no coinciden!"
      );
    } else {
      document.getElementById("register_p1_input").style.display = "none";
      document.getElementById("register_p2_input").style.display = "none";
      counterCheck += 1;
    }
    // Comprobamos numero de telefono
    if (!new RegExp("^\\d{9}$").test(data_form["telefono"])) {
      // Mensaje warnign en input
      warning_MessageInputs(
        "register_telefono_input",
        "Formato de numero incorrecto"
      );
    } else {
      document.getElementById("register_telefono_input").style.display = "none";
      counterCheck += 1;
    }

    if (counterCheck === 3) {
      var api_response = await sendRequestRegister(data_form);
      if (api_response.codeResponse === 201) {
        // Cliente creado correctamente
        showModal({
          flag: true,
          tittle: "Registro exitoso",
          message: "¡Tu registro se ha realizado correctamente!",
        });
        // Tiempo para quitar el modal
        setTimeout(() => {
          document
            .getElementsByClassName("modal-content")[0]
            .classList.remove("active");
          // Recargar la página desde el servidor (sin caché)
          location.reload(true);
        }, 3000);
      } else if (api_response.codeResponse === 500) {
        // Error interno al crear el cliente
        showModal({
          flag: false,
          tittle: "Registro fallido!",
          message: "¡No se a podido registrar el cliente correctamente!",
        });
        setTimeout(() => {
          document
            .getElementsByClassName("modal-content")[0]
            .classList.remove("active");
          // Recargar la página desde el servidor (sin caché)
          location.reload(true);
        }, 3000);
      }
    } else {
      alert("Existen datos incorectos, comprueba el formulario de registro");
    }
  }
  async function handle_submit_form_login(event) {
    event.preventDefault();
    // Creamos la estructura formulario
    var form = new FormData(event.target);
    var data_form = {};
    var counterCheck = 0;
    form.forEach((value, key) => {
      data_form[key] = value;
    });
      var api_response = await sendRequestLogin(data_form);
      console.log(api_response);
      if (api_response.codeResponse === 201) {
        // Cliente logueado correctamente
        showModal({
          flag: true,
          tittle: "Login exitoso",
          message: "Redirigiendo...",
        });
        // Vamos a guardar el jwt como cookie
        createdCookie(api_response.body.tokenJwt);
        // Tiempo para quitar el modal
        setTimeout(() => {
          document
            .getElementsByClassName("modal-content")[0]
            .classList.remove("active");
          // Recargar la página desde el servidor (sin caché)
        }, 2000);
        setTimeout(() => {
          goToPortal();
        }, 2500);
        
      } else {
        // Error interno por el servidor 
        showModal({
          flag: false,
          tittle: "Login Fallido!",
          message: "¡No se a podido loguear!",
        });
        setTimeout(() => {
          document
            .getElementsByClassName("modal-content")[0]
            .classList.remove("active");
        }, 3000);
      }
      
  }

  return (
    <div className="container">
      <div className="form-icon">
        <img src={icono} alt="Formulario Icono" width="90" height="90" />
      </div>
      <div className="form-tittle">
        <h3>Maze Bank</h3>
      </div>
      <div className="form-container">
        <div className="form-toggle">
          <button
            id="login-btn"
            className="toggle-btn active"
            onClick={() => {
              document.getElementById("login-form").classList.add("active");
              document
                .getElementById("register-form")
                .classList.remove("active");
              document.getElementById("login-btn").classList.add("active");
              document
                .getElementById("register-btn")
                .classList.remove("active");
            }}
          >
            Login
          </button>
          <button
            id="register-btn"
            className="toggle-btn"
            onClick={() => {
              document.getElementById("register-form").classList.add("active");
              document.getElementById("login-form").classList.remove("active");
              document.getElementById("register-btn").classList.add("active");
              document.getElementById("login-btn").classList.remove("active");
            }}
          >
            Registrar
          </button>
        </div>

        <div className="form-box">
          <form id="login-form" className="form active" onSubmit={(event) => handle_submit_form_login(event)}>
            <h2>Iniciar sesión</h2>
            <div className="textbox">
              <label htmlFor="dni_usuario">Dni usuario</label>
              <input
                type="text"
                placeholder="Dni usuario"
                name="dni_usuario"
                id="dni_usuario"
                required
              />
              <p className="warning_input" id="register_dni_input"></p>
            </div>
            <div className="textbox">
              <label htmlFor="contraseña">Contraseña</label>
              <input
                type="password"
                placeholder="Contraseña"
                name="password"
                id="contraseña"
                required
              />
            </div>
            <div className="checkbox">
              <label>
                <input type="checkbox" name="remember" /> Recordarme
              </label>
            </div>
            <input type="submit" value="Iniciar sesión" className="btn"/>
          </form>

          <form
            id="register-form"
            className="form"
            onSubmit={(event) => handle_submit_form_register(event)}
          >
            <h2>Registrarse</h2>

            <div className="textbox">
              <label htmlFor="dni">DNI</label>
              <input
                type="text"
                placeholder="DNI"
                name="dni"
                id="dni"
                required
              />
              <p className="warning_input" id="register_dni_input"></p>
            </div>

            <div className="textbox">
              <label htmlFor="nombre">Nombre</label>
              <input
                type="text"
                placeholder="Nombre"
                name="nombre"
                id="nombre"
                required
              />
            </div>

            <div className="textbox">
              <label htmlFor="apellidos">Apellidos</label>
              <input
                type="text"
                placeholder="Apellidos"
                name="apellidos"
                id="apellidos"
                required
              />
            </div>

            <div className="textbox">
              <label htmlFor="email">Correo electrónico</label>
              <input
                type="email"
                placeholder="Correo electrónico"
                name="email"
                id="email"
                required
              />
            </div>

            <div className="textbox">
              <label htmlFor="contraseña">Contraseña</label>
              <input
                type="password"
                placeholder="Contraseña"
                name="password"
                id="contraseña"
                required
              />
              <p className="warning_input" id="register_p1_input"></p>
            </div>

            <div className="textbox">
              <label htmlFor="confirmar-contraseña">Confirmar Contraseña</label>
              <input
                type="password"
                placeholder="Confirmar Contraseña"
                name="confirm_password"
                id="confirmar-contraseña"
                required
              />
              <p className="warning_input" id="register_p2_input"></p>
            </div>

            <div className="textbox">
              <label htmlFor="telefono">Teléfono</label>
              <input
                type="tel"
                placeholder="Teléfono"
                name="telefono"
                id="telefono"
                required
              />
              <p className="warning_input" id="register_telefono_input"></p>
            </div>

            <div className="textbox">
              <label htmlFor="direccion">Dirección</label>
              <input
                type="text"
                placeholder="Dirección"
                name="direccion"
                id="direccion"
                required
              />
            </div>

            <div className="textbox">
              <label htmlFor="ciudad">Ciudad</label>
              <input
                type="text"
                placeholder="Ciudad"
                name="ciudad"
                id="ciudad"
                required
              />
            </div>

            <input type="submit" value="Registrarse" className="btn" />
          </form>
        </div>
      </div>

      <div id="success-modal" className="modal">
        <div className="modal-content">
          <div className="close-container">
            <span id="close-modal" className="close">
              &times;
            </span>
          </div>
          <h2 id="tittle_modal"></h2>
          <p id="content_modal"></p>
        </div>
      </div>
    </div>
  );
};
export default Login;
