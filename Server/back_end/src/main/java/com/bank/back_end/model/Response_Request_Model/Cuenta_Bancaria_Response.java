package com.bank.back_end.model.Response_Request_Model;

import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.util.Date;

public class Cuenta_Bancaria_Response {
    private int id_cuenta_bancaria;
    private String numeroCuenta;
    @Column(precision = 15, scale = 2)
    private BigDecimal saldo;
    private String descripcion;
    private Date fechaAlta;
    private boolean isActiva;

    public Cuenta_Bancaria_Response() {
    }

    public Cuenta_Bancaria_Response(int id_cuenta_bancaria, Date fechaAlta, String descripcion, BigDecimal saldo, String numeroCuenta,boolean isActiva) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
        this.fechaAlta = fechaAlta;
        this.descripcion = descripcion;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
        this.isActiva = isActiva;
    }

    public int getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public void setId_cuenta_bancaria(int id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isActiva() {
        return isActiva;
    }

    public void setActiva(boolean activa) {
        isActiva = activa;
    }

    @Override
    public String toString() {
        return "Cuenta_Bancaria_Response{" +
                "id_cuenta_bancaria=" + id_cuenta_bancaria +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                ", descripcion='" + descripcion + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", isActiva=" + isActiva +
                '}';
    }
}
