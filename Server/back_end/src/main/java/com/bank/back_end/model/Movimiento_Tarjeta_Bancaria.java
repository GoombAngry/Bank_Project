package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Entity
public class Movimiento_Tarjeta_Bancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_movimiento_tarjeta_bancaria;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_tarjeta_bancaria")
    private Tarjeta_Bancaria tarjetaBancaria;

    private String tipo_movimiento;

    @Column(precision = 15, scale = 2)
    private BigDecimal cantidad;

    private Date fecha_movimiento;

    private String descripcion;

    private String remitente_identificador;

    private String destinatario_identificador;

    public Movimiento_Tarjeta_Bancaria() {
    }

    public Movimiento_Tarjeta_Bancaria(int id_movimiento_tarjeta_bancaria, String destinatario_identificador, String remitente_identificador, String descripcion, Date fecha_movimiento, BigDecimal cantidad, String tipo_movimiento, Tarjeta_Bancaria tarjetaBancaria) {
        this.id_movimiento_tarjeta_bancaria = id_movimiento_tarjeta_bancaria;
        this.destinatario_identificador = destinatario_identificador;
        this.remitente_identificador = remitente_identificador;
        this.descripcion = descripcion;
        this.fecha_movimiento = fecha_movimiento;
        this.cantidad = cantidad;
        this.tipo_movimiento = tipo_movimiento;
        this.tarjetaBancaria = tarjetaBancaria;
    }

    public Movimiento_Tarjeta_Bancaria(Tarjeta_Bancaria tarjetaBancaria, String tipo_movimiento, BigDecimal cantidad, Date fecha_movimiento, String descripcion, String remitente_identificador, String destinatario_identificador) {
        this.tarjetaBancaria = tarjetaBancaria;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha_movimiento = fecha_movimiento;
        this.descripcion = descripcion;
        this.remitente_identificador = remitente_identificador;
        this.destinatario_identificador = destinatario_identificador;
    }

    public int getId_movimiento_tarjeta_bancaria() {
        return id_movimiento_tarjeta_bancaria;
    }

    public void setId_movimiento_tarjeta_bancaria(int id_movimiento_tarjeta_bancaria) {
        this.id_movimiento_tarjeta_bancaria = id_movimiento_tarjeta_bancaria;
    }

    public Tarjeta_Bancaria getTarjetaBancaria() {
        return tarjetaBancaria;
    }

    public void setTarjetaBancaria(Tarjeta_Bancaria tarjetaBancaria) {
        this.tarjetaBancaria = tarjetaBancaria;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        return "Movimiento_Tarjeta_Bancaria{" +
                "id_movimiento_tarjeta_bancaria=" + id_movimiento_tarjeta_bancaria +
                ", tarjetaBancaria=" + tarjetaBancaria +
                ", tipo_movimiento='" + tipo_movimiento + '\'' +
                ", cantidad=" + cantidad +
                ", fecha_movimiento=" + fecha_movimiento +
                ", descripcion='" + descripcion + '\'' +
                ", remitente_identificador='" + remitente_identificador + '\'' +
                ", destinatario_identificador='" + destinatario_identificador + '\'' +
                '}';
    }
}
