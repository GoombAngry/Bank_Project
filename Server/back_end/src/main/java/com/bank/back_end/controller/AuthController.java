package com.bank.back_end.controller;

import com.bank.back_end.model.Cliente;
import com.bank.back_end.model.Response_Request_Model.*;
import com.bank.back_end.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ClienteService clienteService;


    // Dar de alta/resgistrar a un cliente
    @PostMapping("/registerCliente")
    public ResponseEntity<Response<Cliente>> registerCliente(@RequestBody RegisterRequest request){
        System.out.println("request = " + request);
        return new ResponseEntity<Response<Cliente>>(this.clienteService.altaCliente(request), HttpStatus.OK);
    }

    // Login del usuario en la app bancaria
    @PostMapping("/login")
    public ResponseEntity<Response<TokenResponse>> loginCliente(@RequestBody LoginResquest request){
        return new ResponseEntity<>(this.clienteService.loginCliente(request),HttpStatus.OK);
    }

    // Login del usuario en la app bancaria
    @PostMapping("/loginAtm")
    public ResponseEntity<Response<AtmLoginResponse>> loginClienATM(@RequestBody AtmLoginRequest request){
        return new ResponseEntity<>(this.clienteService.loginClienteAtm(request),HttpStatus.OK);
    }

    // Refrescamos el token (Pasa de 1 Dia de validez a 7)
    @PostMapping("/refresh")
    public ResponseEntity<Response<TokenResponse>> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.clienteService.refreshToken(authHeader),HttpStatus.OK);
    }

}
