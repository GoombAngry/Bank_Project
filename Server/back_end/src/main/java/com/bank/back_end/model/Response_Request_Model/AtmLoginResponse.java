package com.bank.back_end.model.Response_Request_Model;

public class AtmLoginResponse {
    private String identificador;
    private int id_identificador;
    private String token;

    public AtmLoginResponse() {
    }

    public AtmLoginResponse(String identificador, int id_identificador, String token) {
        this.identificador = identificador;
        this.id_identificador = id_identificador;
        this.token = token;
    }

    public int getId_identificador() {
        return id_identificador;
    }

    public void setId_identificador(int id_identificador) {
        this.id_identificador = id_identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AtmLoginResponse{" +
                "identificador='" + identificador + '\'' +
                ", id_identificador=" + id_identificador +
                ", token='" + token + '\'' +
                '}';
    }
}
