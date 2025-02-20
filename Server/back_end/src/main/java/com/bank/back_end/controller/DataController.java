package com.bank.back_end.controller;

import com.bank.back_end.model.Response_Request_Model.RequestDataMensual;
import com.bank.back_end.model.Response_Request_Model.Response;
import com.bank.back_end.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    // Vamos a obtener el numero de movimientos tanto de la tarjeta como la cuenta
    @PostMapping("/getResumenMensual")
    public ResponseEntity<Response<HashMap<String,Integer>>> getResumenMensual(@RequestHeader(HttpHeaders.AUTHORIZATION)String authHeader, @RequestBody RequestDataMensual request){
       return new ResponseEntity<>(this.dataService.getResumenMensual(authHeader,request), HttpStatus.OK);
    }

}
