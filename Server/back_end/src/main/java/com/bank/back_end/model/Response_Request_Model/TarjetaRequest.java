package com.bank.back_end.model.Response_Request_Model;

public class TarjetaRequest
{
    private int id_tarjeta_bancaria;

    public TarjetaRequest() {
    }

    public TarjetaRequest(int id_tarjeta_bancaria) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
    }

    public int getId_tarjeta_bancaria() {
        return id_tarjeta_bancaria;
    }

    public void setId_tarjeta_bancaria(int id_tarjeta_bancaria) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
    }

    @Override
    public String toString() {
        return "TarjetaRequest{" +
                "id_tarjeta_bancaria=" + id_tarjeta_bancaria +
                '}';
    }
}
