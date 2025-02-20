# Bank_Project
Este proyecto es un **sistema bancario integral** que consta de varias aplicaciones interconectadas:

- **Servidor Backend**: Utiliza el framework **Spring Boot** para crear una **API REST** que gestiona y proporciona datos a diversas plataformas.
- **Autenticación segura**: Implementación de **JWT** para asegurar la comunicación entre el servidor y las aplicaciones.
- **Aplicación Web**: Desarrollada con **React** y **JavaScript**, esta aplicación está completamente adaptada para su uso en **dispositivos móviles**.
- **Software para Cajeros ATM**: Creado con **Swing (Java)**, gestionando las interacciones específicas con los cajeros automáticos.

## Requerimientos

Antes de comenzar con el uso de este proyecto, asegúrate de tener instalados los siguientes programas en tu entorno de desarrollo:

### 📄 MySQL
- **Versión requerida**: >= **Ver 8.0.41**

### 🟩 Node.js y NPM
- **Node.js**: v22.12.0
- **NPM**: v10.9.0

### ☕ Java
- **JDK**: v17

## Cómo Correr el Proyecto

Sigue estos pasos para ejecutar correctamente el sistema bancario:

1. **Verifica que cumples con los requerimientos**  
   Asegúrate de tener todas las herramientas y versiones necesarias instaladas (MySQL, Node.js, Java).

2. **Ejecuta el script SQL**  
   Antes de correr el servidor, debes ejecutar el script SQL que se encuentra en `Bank_Project/Server/Refact_BD_script.sql` para configurar la base de datos.

3. **Configura el archivo `application.properties`**  
   Dentro de la ruta `Bank_Project/Server/backend/src/main/resources/`, crea y configura el archivo `application.properties` con los datos necesarios para conectar la aplicación al servidor de base de datos. Tienes un archivo `.txt` que contiene la plantilla para completar la configuración requerida para este archivo.

4. **Instala dependencias del frontend**  
   Dirígete a la carpeta `Bank_Project/Front_End/bank_frontEnd/` y ejecuta el siguiente comando para resolver las dependencias:
   ```bash
   npm install
   # Para ejecutar la app
   npm run dev
   
5. **Verifica la configuración de la URL del servidor**  
   Asegúrate de que la URL del servidor esté correctamente configurada en los siguientes archivos:

   - Para la aplicación web en **React**, abre el archivo `Bank_Project/Front_End/bank_frontEnd/src/utilities/utilitie.js` y asegúrate de que la URL del servidor esté bien asignada.
   - Para la aplicación del **cajero ATM** hecha en **Swing (Java)**, abre el archivo `Bank_Project/ATM_SYSTEM/atm_bank/src/main/java/Utilidades/ControllerRequest.java` y verifica que la URL del servidor esté correctamente configurada.
   Estas configuraciones son necesarias para que ambas aplicaciones se conecten al servidor backend sin problemas.




