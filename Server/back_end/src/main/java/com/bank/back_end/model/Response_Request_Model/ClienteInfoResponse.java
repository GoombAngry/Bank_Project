package com.bank.back_end.model.Response_Request_Model;

import java.util.Date;

public class ClienteInfoResponse
{

    private String dni_cliente;

    private String nombre;

    private String apellidos;

    private String correo_electronico;

    private Date fecha_alta;

    private String telefono;

    private String direccion;

    private String ciudad;

    public ClienteInfoResponse() {
    }

    public ClienteInfoResponse(String dni_cliente, String nombre, String apellidos, String correo_electronico, Date fecha_alta, String telefono, String direccion,String ciudad) {
        this.dni_cliente = dni_cliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo_electronico = correo_electronico;
        this.fecha_alta = fecha_alta;
        this.telefono = telefono;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }


    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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


    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "ClienteInfoResponse{" +
                "dni_cliente='" + dni_cliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", fecha_alta=" + fecha_alta +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
