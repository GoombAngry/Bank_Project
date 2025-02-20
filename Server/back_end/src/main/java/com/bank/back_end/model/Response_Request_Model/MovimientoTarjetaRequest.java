package com.bank.back_end.model.Response_Request_Model;

import java.math.BigDecimal;
import java.util.Date;

public class MovimientoTarjetaRequest {
    private int id_tarjeta_bancaria;
    private String tipo_movimiento;
    private BigDecimal cantidad;
    private Date fecha_movimiento;
    private String descripcion_movimiento;
    private String remitente_identificador;
    private String destinatario_identificador;

    public MovimientoTarjetaRequest() {
    }

    public MovimientoTarjetaRequest(int id_tarjetaBancaria, String tipo_movimiento, BigDecimal cantidad, Date fecha_movimiento, String descripcion_movimiento, String remitente_identificador, String destinatario_identificador) {
        this.id_tarjeta_bancaria = id_tarjetaBancaria;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha_movimiento = fecha_movimiento;
        this.descripcion_movimiento = descripcion_movimiento;
        this.remitente_identificador = remitente_identificador;
        this.destinatario_identificador = destinatario_identificador;
    }

    public int getId_tarjeta_bancaria() {
        return id_tarjeta_bancaria;
    }

    public void setId_tarjeta_bancaria(int id_tarjeta_bancaria) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public String getDescripcion_movimiento() {
        return descripcion_movimiento;
    }

    public void setDescripcion_movimiento(String descripcion_movimiento) {
        this.descripcion_movimiento = descripcion_movimiento;
    }

    public String getRemitente_identificador() {
        return remitente_identificador;
    }

    public void setRemitente_identificador(String remitente_identificador) {
        this.remitente_identificador = remitente_identificador;
    }

    public String getDestinatario_identificador() {
        return destinatario_identificador;
    }

    public void setDestinatario_identificador(String destinatario_identificador) {
        this.destinatario_identificador = destinatario_identificador;
    }

    @Override
    public String toString() {
        return "MovimientoTarjetaRequest{" +
                "id_tarjeta_bancaria=" + id_tarjeta_bancaria +
                ", tipo_movimiento='" + tipo_movimiento + '\'' +
                ", cantidad=" + cantidad +
                ", fecha_movimiento=" + fecha_movimiento +
                ", descripcion_movimiento='" + descripcion_movimiento + '\'' +
                ", remitente_identificador='" + remitente_identificador + '\'' +
                ", destinatario_identificador='" + destinatario_identificador + '\'' +
                '}';
    }
}
