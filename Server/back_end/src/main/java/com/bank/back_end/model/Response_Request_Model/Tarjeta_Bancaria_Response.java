package com.bank.back_end.model.Response_Request_Model;

import java.math.BigDecimal;
import java.util.Date;

public class Tarjeta_Bancaria_Response {
    private int id_tarjeta_bancaria;
    private String numeroTarjeta;
    private String tipoTarjeta;
    private BigDecimal saldo;
    private String cuentaVinculada;
    private boolean isActiva;
    private BigDecimal limiteTarjeta;
    private Date fechaEmision;
    private Date fechaVencimiento;

    public Tarjeta_Bancaria_Response() {
    }

    public Tarjeta_Bancaria_Response(int id_tarjeta_bancaria, String numeroTarjeta, String tipoTarjeta, BigDecimal saldo, String cuentaVinculada, boolean isActiva, BigDecimal limiteTarjeta, Date fechaEmision, Date fechaVencimiento) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.saldo = saldo;
        this.cuentaVinculada = cuentaVinculada;
        this.isActiva = isActiva;
        this.limiteTarjeta = limiteTarjeta;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Tarjeta_Bancaria_Response(int id_tarjeta_bancaria, String numeroTarjeta, String tipoTarjeta, String cuentaVinculada, boolean isActiva, BigDecimal limiteTarjeta, Date fechaEmision, Date fechaVencimiento) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.cuentaVinculada = cuentaVinculada;
        this.isActiva = isActiva;
        this.limiteTarjeta = limiteTarjeta;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
    }


    public String getCuentaVinculada() {
        return cuentaVinculada;
    }

    public void setCuentaVinculada(String cuentaVinculada) {
        this.cuentaVinculada = cuentaVinculada;
    }

    public boolean isActiva() {
        return isActiva;
    }

    public void setActiva(boolean activa) {
        isActiva = activa;
    }

    public BigDecimal getLimiteTarjeta() {
        return limiteTarjeta;
    }

    public void setLimiteTarjeta(BigDecimal limiteTarjeta) {
        this.limiteTarjeta = limiteTarjeta;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId_tarjeta_bancaria() {
        return id_tarjeta_bancaria;
    }

    public void setId_tarjeta_bancaria(int id_tarjeta_bancaria) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    @Override
    public String toString() {
        return "Tarjeta_Bancaria_Response{" +
                "id_tarjeta_bancaria=" + id_tarjeta_bancaria +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", tipoTarjeta='" + tipoTarjeta + '\'' +
                ", saldo=" + saldo +
                ", cuentaVinculada='" + cuentaVinculada + '\'' +
                ", isActiva=" + isActiva +
                ", limiteTarjeta=" + limiteTarjeta +
                ", fechaEmision=" + fechaEmision +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }
}
