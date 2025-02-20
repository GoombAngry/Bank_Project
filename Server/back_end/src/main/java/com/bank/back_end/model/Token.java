package com.bank.back_end.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Token {
    public enum TokenType {
        BEARER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_token;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    private String token;

    private boolean revoked;

    private boolean expired;

    @Enumerated(EnumType.STRING)
    private TokenType token_type = TokenType.BEARER;

    @ManyToOne
    @JoinColumn(name = "id_tipo_token")
    private Tipo_Token tipo_token;


    public Token() {

    }

    public int getId_token() {
        return id_token;
    }

    public void setId_token(int id_token) {
        this.id_token = id_token;
    }

    public TokenType getToken_Type() {
        return token_type;
    }

    public void setToken_Type(TokenType token_Type) {
        this.token_type = token_Type;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tipo_Token getTipo_token() {
        return tipo_token;
    }

    public void setTipo_token(Tipo_Token tipo_token) {
        this.tipo_token = tipo_token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", revoked=" + revoked +
                ", expired=" + expired +
                ", tokenType=" + token_type +
                ", id_token=" + id_token +
                '}';
    }
}
