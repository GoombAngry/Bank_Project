package com.bank.back_end.model.Response_Request_Model;

public class CuentaRequest {
    private int id_cuenta_bancaria;

    public CuentaRequest() {
    }

    public CuentaRequest(int id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public int getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public void setId_cuenta_bancaria(int id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    @Override
    public String toString() {
        return "CuentaRequest{" +
                "id_cuenta_bancaria=" + id_cuenta_bancaria +
                '}';
    }
}
