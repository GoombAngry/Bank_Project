package com.bank.back_end.model.Response_Request_Model;

public class AltaTarjetaBancariaRequest {
    private int id_tipo_tarjeta;
    private int id_tipo_cuenta;

    public AltaTarjetaBancariaRequest() {
    }

    public AltaTarjetaBancariaRequest(int id_tipo_tarjeta) {
        this.id_tipo_tarjeta = id_tipo_tarjeta;
    }


    public int getId_tipo_tarjeta() {
        return id_tipo_tarjeta;
    }

    public void setId_tipo_tarjeta(int id_tipo_tarjeta) {
        this.id_tipo_tarjeta = id_tipo_tarjeta;
    }

    public int getId_tipo_cuenta() {
        return id_tipo_cuenta;
    }

    public void setId_tipo_cuenta(int id_tipo_cuenta) {
        this.id_tipo_cuenta = id_tipo_cuenta;
    }

    @Override
    public String toString() {
        return "AltaTarjetaBancariaRequest{" +
                "id_tipo_tarjeta=" + id_tipo_tarjeta +
                ", id_tipo_cuenta=" + id_tipo_cuenta +
                '}';
    }
}
