package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Entity
public class Tarjeta_Bancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tarjeta_bancaria;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "id_cuenta_bancaria")
    private Cuenta_Bancaria cuentaBancaria;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tarjeta")
    private Tipo_Tarjeta tipoTarjeta;

    private String numeroTarjeta;

    @Column(precision = 15, scale = 2)
    private BigDecimal limite_tarjeta;

    @Column(precision = 15, scale = 2)
    private BigDecimal saldo;

    private Date fecha_emision;

    private Date fecha_vencimiento;

    private boolean bloqueada;

    private boolean is_activa;

    private String cv_tarjeta;

    @OneToMany(mappedBy = "tarjetaBancaria", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Movimiento_Tarjeta_Bancaria> movimientosTarjetaBancaria;

    public Tarjeta_Bancaria() {
    }

    public Tarjeta_Bancaria(int id_tarjeta_bancaria, Cliente cliente, Cuenta_Bancaria cuentaBancaria, Tipo_Tarjeta tipoTarjeta, String numeroTarjeta, BigDecimal limite_tarjeta, BigDecimal saldo, Date fecha_emision, Date fecha_vencimiento, boolean bloqueada, boolean is_activa, List<Movimiento_Tarjeta_Bancaria> movimientosTarjetaBancaria) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
        this.cliente = cliente;
        this.cuentaBancaria = cuentaBancaria;
        this.tipoTarjeta = tipoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.limite_tarjeta = limite_tarjeta;
        this.saldo = saldo;
        this.fecha_emision = fecha_emision;
        this.fecha_vencimiento = fecha_vencimiento;
        this.bloqueada = bloqueada;
        this.is_activa = is_activa;
        this.movimientosTarjetaBancaria = movimientosTarjetaBancaria;
    }

    public Tarjeta_Bancaria(Cliente cliente, Cuenta_Bancaria cuentaBancaria, Tipo_Tarjeta tipoTarjeta, String numeroTarjeta, BigDecimal limite_tarjeta, BigDecimal saldo, Date fecha_emision, Date fecha_vencimiento, boolean bloqueada, boolean is_activa) {
        this.cliente = cliente;
        this.cuentaBancaria = cuentaBancaria;
        this.tipoTarjeta = tipoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.limite_tarjeta = limite_tarjeta;
        this.saldo = saldo;
        this.fecha_emision = fecha_emision;
        this.fecha_vencimiento = fecha_vencimiento;
        this.bloqueada = bloqueada;
        this.is_activa = is_activa;
    }

    public Tarjeta_Bancaria(Cliente cliente, Cuenta_Bancaria cuentaBancaria, Tipo_Tarjeta tipoTarjeta, String numeroTarjeta, BigDecimal limite_tarjeta, BigDecimal saldo, Date fecha_emision, Date fecha_vencimiento, boolean bloqueada, boolean is_activa,String cv) {
        this.cliente = cliente;
        this.cuentaBancaria = cuentaBancaria;
        this.tipoTarjeta = tipoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.limite_tarjeta = limite_tarjeta;
        this.saldo = saldo;
        this.fecha_emision = fecha_emision;
        this.fecha_vencimiento = fecha_vencimiento;
        this.bloqueada = bloqueada;
        this.is_activa = is_activa;
        this.cv_tarjeta = cv;
    }

    public String getCv_tarjeta() {
        return cv_tarjeta;
    }

    public void setCv_tarjeta(String cv_tarjeta) {
        this.cv_tarjeta = cv_tarjeta;
    }

    public int getId_tarjeta_bancaria() {
        return id_tarjeta_bancaria;
    }

    public void setId_tarjeta_bancaria(int id_tarjeta_bancaria) {
        this.id_tarjeta_bancaria = id_tarjeta_bancaria;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cuenta_Bancaria getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(Cuenta_Bancaria cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public Tipo_Tarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(Tipo_Tarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public BigDecimal getLimite_tarjeta() {
        return limite_tarjeta;
    }

    public void setLimite_tarjeta(BigDecimal limite_tarjeta) {
        this.limite_tarjeta = limite_tarjeta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public boolean isIs_activa() {
        return is_activa;
    }

    public void setIs_activa(boolean is_activa) {
        this.is_activa = is_activa;
    }

    public List<Movimiento_Tarjeta_Bancaria> getMovimientosTarjetaBancaria() {
        return movimientosTarjetaBancaria;
    }

    public void setMovimientosTarjetaBancaria(List<Movimiento_Tarjeta_Bancaria> movimientosTarjetaBancaria) {
        this.movimientosTarjetaBancaria = movimientosTarjetaBancaria;
    }

    @Override
    public String toString() {
        return "Tarjeta_Bancaria{" +
                "id_tarjeta_bancaria=" + id_tarjeta_bancaria +
                ", cliente=" + cliente +
                ", cuentaBancaria=" + cuentaBancaria +
                ", tipoTarjeta=" + tipoTarjeta +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", limite_tarjeta=" + limite_tarjeta +
                ", saldo=" + saldo +
                ", fecha_emision=" + fecha_emision +
                ", fecha_vencimiento=" + fecha_vencimiento +
                ", bloqueada=" + bloqueada +
                ", is_activa=" + is_activa +
                ", cv_tarjeta='" + cv_tarjeta + '\'' +
                ", movimientosTarjetaBancaria=" + movimientosTarjetaBancaria +
                '}';
    }
}
