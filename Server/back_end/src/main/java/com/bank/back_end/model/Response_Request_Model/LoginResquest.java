package com.bank.back_end.model.Response_Request_Model;
// Clase para enviar la peticion de login al servidor
public class LoginResquest {
    private String dni_cliente;
    private String password;

    public LoginResquest() {
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginResquest{" +
                "dni_cliente='" + dni_cliente + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
