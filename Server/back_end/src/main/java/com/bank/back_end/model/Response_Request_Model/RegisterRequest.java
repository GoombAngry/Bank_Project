package com.bank.back_end.model.Response_Request_Model;
// Clase para dar de alta a un cliente
public class RegisterRequest {
    private String dni_cliente;
    private String nombre;
    private String apellidos;
    private String correo_electronico;
    private String password;
    private String telefono;
    private String direccion;
    private String ciudad;


    public RegisterRequest() {
    }

    public RegisterRequest(String dni_cliente, String ciudad, String direccion, String telefono, String password, String correo_electronico, String apellidos, String nombre) {
        this.dni_cliente = dni_cliente;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.password = password;
        this.correo_electronico = correo_electronico;
        this.apellidos = apellidos;
        this.nombre = nombre;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "dni_cliente='" + dni_cliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
