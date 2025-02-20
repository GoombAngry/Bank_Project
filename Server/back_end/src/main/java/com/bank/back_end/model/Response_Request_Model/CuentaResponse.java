package com.bank.back_end.model.Response_Request_Model;

public class CuentaResponse
{
    private int id_cuenta_bancaria;
    private String numero_cuenta_bancaria;

    public CuentaResponse() {
    }

    public CuentaResponse(int id_cuenta_bancaria, String numero_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
        this.numero_cuenta_bancaria = numero_cuenta_bancaria;
    }

    public int getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public void setId_cuenta_bancaria(int id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public String getNumero_cuenta_bancaria() {
        return numero_cuenta_bancaria;
    }

    public void setNumero_cuenta_bancaria(String numero_cuenta_bancaria) {
        this.numero_cuenta_bancaria = numero_cuenta_bancaria;
    }

    @Override
    public String toString() {
        return "CuentaResponse{" +
                "id_cuenta_bancaria=" + id_cuenta_bancaria +
                ", numero_cuenta_bancaria='" + numero_cuenta_bancaria + '\'' +
                '}';
    }
}
