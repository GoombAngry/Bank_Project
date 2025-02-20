package com.bank.back_end.service;

import com.bank.back_end.model.Cuenta_Bancaria;
import com.bank.back_end.model.Movimiento_Cuenta_Bancaria;
import com.bank.back_end.model.Movimiento_Tarjeta_Bancaria;
import com.bank.back_end.model.Response_Request_Model.RequestDataMensual;
import com.bank.back_end.model.Response_Request_Model.Response;
import com.bank.back_end.model.Tarjeta_Bancaria;
import com.bank.back_end.repository.Cuenta_BancariaRepository;
import com.bank.back_end.repository.Movimiento_Cuenta_BancariaRepository;
import com.bank.back_end.repository.Movimiento_Tarjeta_BancariaRepository;
import com.bank.back_end.repository.Tarjeta_BancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class DataService {

    @Autowired
    private Movimiento_Cuenta_BancariaRepository movimientoCuentaBancariaRepository;

    @Autowired
    private Movimiento_Tarjeta_BancariaRepository movimientoTarjetaBancariaRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Cuenta_BancariaRepository cuentaBancariaRepository;

    @Autowired
    private Tarjeta_BancariaRepository tarjetaBancariaRepository;


    // Obtenemos un resumen general de los tipos de movimientos realizados tanto con las cuentas como tarjetas
    public Response<HashMap<String,Integer>> getResumenMensual(String jwt,RequestDataMensual requestDataMensual){
        int id_Cliente = this.getIdClienteAndVerifiedJwT(jwt);
        // Recogemos todos los movimientos de las cuentas bancarias del usuario de un mes en especifico
        List<Cuenta_Bancaria> cbs_cliente = this.cuentaBancariaRepository.findCuentasByIdCliente(id_Cliente);
        List<Movimiento_Cuenta_Bancaria> movimientosCuenta = new ArrayList<>();
        for(Cuenta_Bancaria c : cbs_cliente){
            movimientosCuenta.addAll(this.movimientoCuentaBancariaRepository.getResumenMensual(c.getId_cuenta_bancaria(),requestDataMensual.getYear(), requestDataMensual.getMonth()));
        }
        // Recogemos todos los movimientos de las tarjetas bancarias del usuario de un mes en especifico
        List<Tarjeta_Bancaria> tbs_cliente = this.tarjetaBancariaRepository.findTarjetasBancariasByIdCliente(id_Cliente);
        List<Movimiento_Tarjeta_Bancaria> movimientosTarjeta = new ArrayList<>();
        for(Tarjeta_Bancaria c : tbs_cliente){
            movimientosTarjeta.addAll(this.movimientoTarjetaBancariaRepository.getResumenMensual(c.getId_tarjeta_bancaria(),requestDataMensual.getYear(), requestDataMensual.getMonth()));
        }
        // Almacenamos el conteo en un HashMap
        HashMap<String,Integer> resumen = new HashMap<>();
        // Realizamos el conteo de los tipos de movimientos tanto de cuentas como tarjetas asociadas al cliente en un mes en concreto
        for(Movimiento_Cuenta_Bancaria mv_cb : movimientosCuenta){
            if(resumen.containsKey(mv_cb.getTipo_movimiento())){
                resumen.put(mv_cb.getTipo_movimiento(), resumen.get(mv_cb.getTipo_movimiento())+1);
            }else{
                resumen.put(mv_cb.getTipo_movimiento(),1);
            }
        }

        for(Movimiento_Tarjeta_Bancaria mv_tb : movimientosTarjeta){
            if(resumen.containsKey(mv_tb.getTipo_movimiento())){
                resumen.put(mv_tb.getTipo_movimiento(), resumen.get(mv_tb.getTipo_movimiento())+1);
            }else{
                resumen.put(mv_tb.getTipo_movimiento(),1);
            }
        }
        return new Response<>(resumen,200,"Operacion Exitosa!");
    }

    private int getIdClienteAndVerifiedJwT(String authHeader){
        // Validamos si el token es correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw  new IllegalArgumentException("Token Bearer invalido");
        // Extraemos el token entero
        String token = authHeader.substring(7);
        // Extraemos a traves del token el id del cliente que contiene (Este metodo tambien verifica la alteracion del token)
        String id_cliente = this.jwtService.extractId_Client(token);
        return Integer.parseInt(id_cliente);
    }
}
