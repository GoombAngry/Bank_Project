package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
public class Tipo_Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipo_cuenta;

    private String nombre;

    private String descripcion;

    public Tipo_Cuenta() {
    }

    public Tipo_Cuenta(int id_tipo_cuenta) {
        this.id_tipo_cuenta = id_tipo_cuenta;
    }

    public Tipo_Cuenta(int id_tipo_cuenta, String descripcion, String nombre) {
        this.id_tipo_cuenta = id_tipo_cuenta;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "tipoCuenta", fetch = FetchType.LAZY)
    List<Cuenta_Bancaria> cuentasBancarias;

    public int getId_tipo_cuenta() {
        return id_tipo_cuenta;
    }

    public void setId_tipo_cuenta(int id_tipo_cuenta) {
        this.id_tipo_cuenta = id_tipo_cuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Cuenta_Bancaria> getCuentasBancarias() {
        return cuentasBancarias;
    }

    public void setCuentasBancarias(List<Cuenta_Bancaria> cuentasBancarias) {
        this.cuentasBancarias = cuentasBancarias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Tipo_Cuenta{" +
                "id_tipo_cuenta=" + id_tipo_cuenta +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
