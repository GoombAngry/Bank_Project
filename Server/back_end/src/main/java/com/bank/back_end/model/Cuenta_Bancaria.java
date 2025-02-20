package com.bank.back_end.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
public class Cuenta_Bancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cuenta_bancaria;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cuenta")
    private Tipo_Cuenta tipoCuenta;

    private String numero_cuenta;

    @Column(precision = 15, scale = 2)
    private BigDecimal saldo;

    private Date fecha_creacion;
    private Date fecha_cierre;
    private boolean is_activa;

    @JsonIgnore
    @OneToMany(mappedBy = "cuentaBancaria", fetch = FetchType.LAZY)
    private List<Movimiento_Cuenta_Bancaria> movimientosCuentaBancaria;

    @JsonIgnore
    @OneToOne(mappedBy = "cuentaBancaria")
    private Tarjeta_Bancaria tarjetaBancaria;

    public Cuenta_Bancaria() {
    }

    public int getId_cuenta_bancaria() {
        return id_cuenta_bancaria;
    }

    public void setId_cuenta_bancaria(int id_cuenta_bancaria) {
        this.id_cuenta_bancaria = id_cuenta_bancaria;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tipo_Cuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(Tipo_Cuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Date fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public boolean isIs_activa() {
        return is_activa;
    }

    public void setIs_activa(boolean is_activa) {
        this.is_activa = is_activa;
    }

    public List<Movimiento_Cuenta_Bancaria> getMovimientosCuentaBancaria() {
        return movimientosCuentaBancaria;
    }

    public void setMovimientosCuentaBancaria(List<Movimiento_Cuenta_Bancaria> movimientosCuentaBancaria) {
        this.movimientosCuentaBancaria = movimientosCuentaBancaria;
    }

    public Tarjeta_Bancaria getTarjetaBancaria() {
        return tarjetaBancaria;
    }

    public void setTarjetaBancaria(Tarjeta_Bancaria tarjetaBancaria) {
        this.tarjetaBancaria = tarjetaBancaria;
    }

    @Override
    public String toString() {
        return "Cuenta_Bancaria{" +
                "id_cuenta_bancaria=" + id_cuenta_bancaria +
                ", tipoCuenta=" + tipoCuenta +
                ", numero_cuenta='" + numero_cuenta + '\'' +
                ", saldo=" + saldo +
                ", fecha_creacion=" + fecha_creacion +
                ", fecha_cierre=" + fecha_cierre +
                ", is_activa=" + is_activa +
                '}';
    }
}
