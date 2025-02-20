package com.bank.back_end.controller;
import com.bank.back_end.model.Cliente;
import com.bank.back_end.model.Response_Request_Model.ClientEditRequest;
import com.bank.back_end.model.Response_Request_Model.ClienteInfoResponse;
import com.bank.back_end.model.Response_Request_Model.Response;
import com.bank.back_end.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/getInformacion")
    public ResponseEntity<Response<ClienteInfoResponse>> getInformacionCliente(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.clienteService.getInformacionCliente(authHeader),HttpStatus.OK);
    }
    @PostMapping("/editInformacion")
    public ResponseEntity<Response<String>> getInformacionCliente(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody ClientEditRequest clientInfo){
        return new ResponseEntity<>(this.clienteService.editarInformacionDelCliente(authHeader,clientInfo),HttpStatus.OK);
    }
    @GetMapping("/darDeBaja")
    public ResponseEntity<Response<String>> darDeBajaUnUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader){
        return new ResponseEntity<>(this.clienteService.darDeBajaCuentaCliente(authHeader),HttpStatus.OK);
    }
}
