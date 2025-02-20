package com.bank.back_end.model.Response_Request_Model;

import com.bank.back_end.model.Tipo_Cuenta;

public class AltaCuentaBancariaRequest {
    private int id_Cliente;
    private Tipo_Cuenta tipoCuenta;


    public AltaCuentaBancariaRequest() {
    }

    public AltaCuentaBancariaRequest(int id_Cliente, Tipo_Cuenta tipoCuenta) {
        this.id_Cliente = id_Cliente;
        this.tipoCuenta = tipoCuenta;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public Tipo_Cuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(Tipo_Cuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public String toString() {
        return "AltaCuentaBancariaRequest{" +
                "id_Cliente=" + id_Cliente +
                ", tipoCuenta=" + tipoCuenta +
                '}';
    }
}
