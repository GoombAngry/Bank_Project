package com.bank.back_end.model.Response_Request_Model;

import java.math.BigDecimal;

public class TraspasoRequest {
    private boolean tipoTraspaso;
    private String numero_cuenta;
    private String numeroTarjeta;
    private BigDecimal cantidad;

    public TraspasoRequest() {}

    public TraspasoRequest(boolean tipoTraspaso, String numero_cuenta, String numeroTarjeta, BigDecimal cantidad) {
        this.tipoTraspaso = tipoTraspaso;
        this.numero_cuenta = numero_cuenta;
        this.numeroTarjeta = numeroTarjeta;
        this.cantidad = cantidad;
    }

    public boolean isTipoTraspaso() {
        return tipoTraspaso;
    }

    public void setTipoTraspaso(boolean tipoTraspaso) {
        this.tipoTraspaso = tipoTraspaso;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    @Override
    public String toString() {
        return "TraspasoRequest{" +
                "tipoTraspaso=" + tipoTraspaso +
                ", numero_cuenta='" + numero_cuenta + '\'' +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
