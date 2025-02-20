package com.bank.back_end.model.Response_Request_Model;

import java.math.BigDecimal;
import java.util.Date;

public class MovimientoCuentaRequest
{
    private int id_cuenta_bancaria;
    private String tipo_movimiento;
    private BigDecimal cantidad;
    private Date fecha_movimiento;
    private String descripcion_movimiento;
    private String remitente_identificador;
    private String destinatario_identificador;

    public MovimientoCuentaRequest() {
    }

    public MovimientoCuentaRequest(int id_cuenta_bancaria, String tipo_movimiento, BigDecimal cantidad, String descripcion_movimiento, String remitente_identificador, String destinatario_identificador) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.descripcion_movimiento = descripcion_movimiento;
        this.remitente_identificador = remitente_identificador;
        this.destinatario_identificador = destinatario_identificador;
    }

    public MovimientoCuentaRequest(int id_cuenta_bancaria, String tipo_movimiento, BigDecimal cantidad, Date fecha_movimiento, String descripcion_movimiento, String remitente_identificador, String destinatario_identificador) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha_movimiento = fecha_movimiento;
        this.descripcion_movimiento = descripcion_movimiento;
        this.remitente_identificador = remitente_identificador;
        this.destinatario_identificador = destinatario_identificador;
    }

    public int getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public void setId_cuenta_bancaria(int id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public String getDestinatario_identificador() {
        return destinatario_identificador;
    }

    public void setDestinatario_identificador(String destinatario_identificador) {
        this.destinatario_identificador = destinatario_identificador;
    }

    public String getRemitente_identificador() {
        return remitente_identificador;
    }

    public void setRemitente_identificador(String remitente_identificador) {
        this.remitente_identificador = remitente_identificador;
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

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    @Override
    public String toString() {
        return "MovimientoCuentaRequest{" +
                "id_cuenta_bancaria=" + id_cuenta_bancaria +
                ", tipo_movimiento='" + tipo_movimiento + '\'' +
                ", cantidad=" + cantidad +
                ", fecha_movimiento=" + fecha_movimiento +
                ", descripcion_movimiento='" + descripcion_movimiento + '\'' +
                ", remitente_identificador='" + remitente_identificador + '\'' +
                ", destinatario_identificador='" + destinatario_identificador + '\'' +
                '}';
    }
}
