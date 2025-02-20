package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
public class Tipo_Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipo_tarjeta;

    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "tipoTarjeta", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Tarjeta_Bancaria> tarjetasBancarias;

    public Tipo_Tarjeta() {
    }

    public Tipo_Tarjeta(int id_tipo_tarjeta, String nombre, String descripcion) {
        this.id_tipo_tarjeta = id_tipo_tarjeta;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_tipo_tarjeta() {
        return id_tipo_tarjeta;
    }

    public void setId_tipo_tarjeta(int id_tipo_tarjeta) {
        this.id_tipo_tarjeta = id_tipo_tarjeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Tarjeta_Bancaria> getTarjetasBancarias() {
        return tarjetasBancarias;
    }

    public void setTarjetasBancarias(List<Tarjeta_Bancaria> tarjetasBancarias) {
        this.tarjetasBancarias = tarjetasBancarias;
    }

    @Override
    public String toString() {
        return "Tipo_Tarjeta{" +
                "id_tipo_tarjeta=" + id_tipo_tarjeta +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tarjetasBancarias=" + tarjetasBancarias +
                '}';
    }
}
