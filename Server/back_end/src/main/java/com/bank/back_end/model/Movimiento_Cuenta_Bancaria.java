package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Movimiento_Cuenta_Bancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_movimiento_cuenta_bancaria;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cuenta_bancaria")
    private Cuenta_Bancaria cuentaBancaria;

    private String tipo_movimiento;

    @Column(precision = 15, scale = 2)
    private BigDecimal cantidad;

    private Date fecha_movimiento;

    private String descripcion;

    private String remitente_identificador;

    private String destinatario_identificador;

    public Movimiento_Cuenta_Bancaria() {
    }

    public Movimiento_Cuenta_Bancaria(int id_movimiento_cuenta_bancaria, Cuenta_Bancaria cuentaBancaria, String tipo_movimiento, BigDecimal cantidad, Date fecha_movimiento, String descripcion, String remitente_identificador, String destinatario_identificador) {
        this.id_movimiento_cuenta_bancaria = id_movimiento_cuenta_bancaria;
        this.cuentaBancaria = cuentaBancaria;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha_movimiento = fecha_movimiento;
        this.descripcion = descripcion;
        this.remitente_identificador = remitente_identificador;
        this.destinatario_identificador = destinatario_identificador;
    }

    public Movimiento_Cuenta_Bancaria(Cuenta_Bancaria cuentaBancaria, String tipo_movimiento, BigDecimal cantidad, Date fecha_movimiento, String descripcion, String remitente_identificador, String destinatario_identificador) {
        this.cuentaBancaria = cuentaBancaria;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha_movimiento = fecha_movimiento;
        this.descripcion = descripcion;
        this.remitente_identificador = remitente_identificador;
        this.destinatario_identificador = destinatario_identificador;
    }

    public int getId_movimiento_cuenta_bancaria() {
        return id_movimiento_cuenta_bancaria;
    }

    public void setId_movimiento_cuenta_bancaria(int id_movimiento_cuenta_bancaria) {
        this.id_movimiento_cuenta_bancaria = id_movimiento_cuenta_bancaria;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public Cuenta_Bancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(Cuenta_Bancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    @Override
    public String toString() {
        return "Movimiento_Cuenta_Bancaria{" +
                "id_movimiento_cuenta_bancaria=" + id_movimiento_cuenta_bancaria +
                ", cuentaBancaria=" + cuentaBancaria +
                ", tipo_movimiento='" + tipo_movimiento + '\'' +
                ", cantidad=" + cantidad +
                ", fecha_movimiento=" + fecha_movimiento +
                ", descripcion='" + descripcion + '\'' +
                ", remitente_identificador='" + remitente_identificador + '\'' +
                ", destinatario_identificador='" + destinatario_identificador + '\'' +
                '}';
    }
}
