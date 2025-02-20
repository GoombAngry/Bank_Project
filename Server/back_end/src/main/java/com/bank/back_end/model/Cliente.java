package com.bank.back_end.model;

import com.bank.back_end.model.Response_Request_Model.RegisterRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;

    private String dni_cliente;

    private String nombre;

    private String apellidos;

    private String correo_electronico;

    @JsonIgnore
    private String password;

    private Date fecha_alta;
    @JsonIgnore
    private boolean is_baja;

    private String telefono;

    private String direccion;

    private String ciudad;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cuenta_Bancaria> cuentasBancarias;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Tarjeta_Bancaria> tarjetasBancarias;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Token> tokensCliente;

    public Cliente() {
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public List<Tarjeta_Bancaria> getTarjetasBancarias() {
        return tarjetasBancarias;
    }

    public void setTarjetasBancarias(List<Tarjeta_Bancaria> tarjetasBancarias) {
        this.tarjetasBancarias = tarjetasBancarias;
    }

    public List<Cuenta_Bancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(List<Cuenta_Bancaria> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isIs_baja() {
        return is_baja;
    }

    public void setIs_baja(boolean is_baja) {
        this.is_baja = is_baja;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
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

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public List<Token> getTokensCliente() {
        return tokensCliente;
    }

    public void setTokensCliente(List<Token> tokensCliente) {
        this.tokensCliente = tokensCliente;
    }

    public void parserRegisterRequest(RegisterRequest request){
        // Pasara la info del Object RegisterRequest a Cliente
        this.dni_cliente = request.getDni_cliente();
        this.nombre = request.getNombre();
        this.apellidos = request.getApellidos();
        this.correo_electronico = request.getCorreo_electronico();
        this.password = request.getPassword();
        this.telefono = request.getTelefono();
        this.direccion = request.getDireccion();
        this.fecha_alta = new Date();
        this.ciudad = request.getCiudad();
        this.ciudad = request.getCiudad();
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "ciudad='" + ciudad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fecha_alta=" + fecha_alta +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dni_cliente='" + dni_cliente + '\'' +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
