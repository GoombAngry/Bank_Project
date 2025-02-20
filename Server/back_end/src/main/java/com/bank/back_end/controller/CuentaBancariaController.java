package com.bank.back_end.controller;

import com.bank.back_end.model.Cuenta_Bancaria;
import com.bank.back_end.model.Movimiento_Cuenta_Bancaria;
import com.bank.back_end.model.Response_Request_Model.*;
import com.bank.back_end.model.Tipo_Cuenta;
import com.bank.back_end.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class CuentaBancariaController {
    @Autowired
    private Cuenta_BancariaService cuentaBancariaService;

    @PostMapping("/altaCuentaCliente")
    public ResponseEntity<Response<String>> altaCuentaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody Tipo_Cuenta tipoCuenta){
        return new ResponseEntity<>(this.cuentaBancariaService.altaCuentaBancaria(authHeader,tipoCuenta), HttpStatus.OK);
    }
    @GetMapping("/getCuentasBancarias")
    public ResponseEntity<Response<List<Cuenta_Bancaria_Response>>> getCuentasCliente(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.cuentaBancariaService.getCuentasCliente(authHeader),HttpStatus.OK);
    }

    @PostMapping("/addMovimientoCuentaBancaria")
    private ResponseEntity<Response<String>> addMovimientoCuentaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody MovimientoCuentaRequest movimientoCuentaRequest){
        return new ResponseEntity<>(this.cuentaBancariaService.agregarMovimientoCuentaBancaria(authHeader,movimientoCuentaRequest), HttpStatus.OK);
    }

    @PostMapping("/getMovimientosCuenta")
    public ResponseEntity<Response<List<Movimiento_Cuenta_Bancaria>>> getMovimientosCuenta(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody CuentaRequest cr){
        return new ResponseEntity<>(this.cuentaBancariaService.getMovimientosCuenta(authHeader,cr),HttpStatus.OK);
    }

    @GetMapping("/getTiposCuenta")
    public ResponseEntity<Response<List<Tipo_Cuenta>>> getTiposCuenta(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.cuentaBancariaService.getTiposCuenta(authHeader),HttpStatus.OK);
    }

    @PostMapping("/existCuentaBancaria")
    public ResponseEntity<Response<Boolean>> existCuentaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader,@RequestBody Cuenta_Bancaria cuentaBancariaRequest){
        return new ResponseEntity<>(this.cuentaBancariaService.checkExistCuentaBancaria(authHeader,cuentaBancariaRequest.getNumero_cuenta()),HttpStatus.OK);
    }

    @PostMapping("/cambiarEstadoCuentaBancaria")
    public ResponseEntity<Response<String>> cambiarEstadoCuentaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader,@RequestBody CuentaRequest cuentaRequest){
        return new ResponseEntity<>(this.cuentaBancariaService.darDeBajaCuenta(authHeader,cuentaRequest),HttpStatus.OK);
    }


}
