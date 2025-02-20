package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tipo_Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_tipo_token;
    private String tipo_nombre;
    @JsonIgnore

    @OneToMany(mappedBy = "tipo_token", fetch = FetchType.LAZY)
    List<Token> tokens;

    public Tipo_Token() {
    }

    public Tipo_Token(int id_tipo_token) {
        this.id_tipo_token = id_tipo_token;
    }

    public Tipo_Token(int id_tipo_token, String tipo_nombre, List<Token> tokens) {
        this.id_tipo_token = id_tipo_token;
        this.tipo_nombre = tipo_nombre;
        this.tokens = tokens;
    }

    public int getId_tipo_token() {
        return id_tipo_token;
    }

    public void setId_tipo_token(int id_tipo_token) {
        this.id_tipo_token = id_tipo_token;
    }

    public String getTipo_nombre() {
        return tipo_nombre;
    }

    public void setTipo_nombre(String tipo_nombre) {
        this.tipo_nombre = tipo_nombre;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "Tipo_Token{" +
                "id_tipo_token=" + id_tipo_token +
                ", tipo_nombre='" + tipo_nombre + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
