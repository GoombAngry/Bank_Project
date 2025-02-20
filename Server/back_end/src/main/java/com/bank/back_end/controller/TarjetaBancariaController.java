package com.bank.back_end.controller;

import com.bank.back_end.model.*;
import com.bank.back_end.model.Response_Request_Model.*;
import com.bank.back_end.service.Tarjeta_BancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarjeta")
public class TarjetaBancariaController {
    @Autowired
    private Tarjeta_BancariaService tarjetaBancariaService;


    @PostMapping("/altaTarjetaCliente")
    public ResponseEntity<Response<String>> altaCuentaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody AltaTarjetaBancariaRequest altaTarjetaBancariaRequest){
        return new ResponseEntity<>(this.tarjetaBancariaService.altaTarjetaBancaria(authHeader,altaTarjetaBancariaRequest), HttpStatus.OK);
    }

    @GetMapping("/getTarjetasBancarias")
    public ResponseEntity<Response<List<Tarjeta_Bancaria_Response>>> getTarjetasCliente(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.tarjetaBancariaService.getTarjetasCliente(authHeader),HttpStatus.OK);
    }


    @PostMapping("/addMovimientoTarjetaBancaria")
    private ResponseEntity<Response<String>> addMovimientoCuentaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody MovimientoTarjetaRequest movimientoTarjetaRequest){
        return new ResponseEntity<>(this.tarjetaBancariaService.agregarMovimientoTarjetaBancaria(authHeader,movimientoTarjetaRequest), HttpStatus.OK);
    }

    @PostMapping("/getMovimientosTarjeta")
    public ResponseEntity<Response<List<Movimiento_Tarjeta_Bancaria>>> getMovimientosTarjeta(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody TarjetaRequest tr){
        return new ResponseEntity<>(this.tarjetaBancariaService.getMovimientosTarjeta(authHeader,tr),HttpStatus.OK);
    }

    @GetMapping("/getTiposTarjeta")
    public ResponseEntity<Response<List<Tipo_Tarjeta>>> getTiposTarjeta(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.tarjetaBancariaService.getTiposTarjeta(authHeader),HttpStatus.OK);
    }

    @PostMapping("/changeLimiteTarjeta")
    public ResponseEntity<Response<String>> modificarLimiteTarjeta(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader,@RequestBody Tarjeta_Bancaria tarjetaRequest){
        return new ResponseEntity<>(this.tarjetaBancariaService.modificarLimiteTarjeta(authHeader,tarjetaRequest),HttpStatus.OK);
    }

    @PostMapping("/changeEstadoTarjeta")
    public ResponseEntity<Response<String>> modificarEstadoTarjeta(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader,@RequestBody Tarjeta_Bancaria tarjetaRequest){
        return new ResponseEntity<>(this.tarjetaBancariaService.modificarEstadoTarjeta(authHeader,tarjetaRequest),HttpStatus.OK);
    }

    @PostMapping("/exitsTarjetaBancaria")
    public ResponseEntity<Response<Boolean>> existTarjetaBancaria(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader,@RequestBody Tarjeta_Bancaria tarjeta_bancaria_request){
        return new ResponseEntity<>(this.tarjetaBancariaService.checkExistTarjetaBancaria(authHeader,tarjeta_bancaria_request.getNumeroTarjeta()),HttpStatus.OK);
    }

    @PostMapping("/addTraspaso")
    public ResponseEntity<Response<String>> procesarTraspaso(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader,@RequestBody TraspasoRequest traspasoRequest){
        return new ResponseEntity<>(this.tarjetaBancariaService.procesarTraspaso(authHeader,traspasoRequest),HttpStatus.OK);
    }


}
