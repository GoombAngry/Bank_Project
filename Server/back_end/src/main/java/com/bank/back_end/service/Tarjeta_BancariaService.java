package com.bank.back_end.service;

import com.bank.back_end.model.*;
import com.bank.back_end.model.Response_Request_Model.*;
import com.bank.back_end.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class Tarjeta_BancariaService {
    @Autowired
    private Tarjeta_BancariaRepository repositoryTarjeta_Bancaria;
    @Autowired
    private Tipo_TarjetaRepository repositoryTipo_Tarjeta;
    @Autowired
    private ClienteRepository repositoryCliente;
    @Autowired
    private Cuenta_BancariaRepository cuentaBancariaRepository;
    @Autowired
    private Movimiento_Tarjeta_BancariaRepository repositoryMovimiento_Tarjeta_Bancaria;
    @Autowired
    private Movimiento_Cuenta_BancariaRepository repositoryMovimiento_Cuenta_Bancaria;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private Cuenta_BancariaService cuentaBancariaService;
    @Autowired
    private Cuenta_BancariaRepository repositoryCuentaBancaria;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Longitud de los digitos que tendran las Tarjetas Bancarias
    final int LENGTH_IDENTIFICATION_BANK = 12;

    /**
     * Dar de alta una Tarjeta Bancaria
     * @param authHeader Token JWT
     * @param altaTarjetaBancariaRequest Estructura con informacion para dar de alta una tarjeta bancaria
     * @return Estructura con informacion acerca de la tarjeta dada de alta
     */
    public Response<String> altaTarjetaBancaria(String authHeader, AltaTarjetaBancariaRequest altaTarjetaBancariaRequest) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        try{
            Cliente cl_find = this.repositoryCliente.findById(id_cliente).orElse(null);
            if(cl_find == null) return new Response<>("Error!",404,"El identificar de cliente no es correcto");
            // Creamos la cuenta bancaria primero que se asociara a la tarjeta (Generamos la cuenta primero)
            Cuenta_Bancaria cb_generated = this.cuentaBancariaService.generateBankAccount(id_cliente,new Tipo_Cuenta(altaTarjetaBancariaRequest.getId_tipo_cuenta(),"None","None"));
            if(cb_generated == null) return new Response<>("Error!",404,"Error al generar la cuenta asociada a la tarjeta");
            Tipo_Tarjeta tp_find = this.repositoryTipo_Tarjeta.findById(altaTarjetaBancariaRequest.getId_tipo_tarjeta()).orElse(null);
            if(tp_find == null) return new Response<>("Error",404,"El tipo de tarjeta es incorrecto!");
            // Procedemos a persistir las estructura de Cuenta bancaria y Tarjeta bancaria
            Tarjeta_Bancaria tj_generated = this.generarTarjetaBancaria(this.repositoryCuentaBancaria.save(cb_generated),cl_find,tp_find);
            this.repositoryTarjeta_Bancaria.save(tj_generated);
            return new Response<>("Exito!",200,"Tarjeta creado con exito");
        }catch (Exception ex){
            System.out.println("[!] - Se ha producido un error en el servicio altaTarjetaBancaria");
            return new Response<>("Error!",500,"Internal Error");
        }
    }

    public Response<List<Tarjeta_Bancaria_Response>> getTarjetasCliente(String authHeader) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        List<Tarjeta_Bancaria> tarjetas_find = this.repositoryTarjeta_Bancaria.findTarjetasBancariasByIdCliente(id_cliente);
        ArrayList<Tarjeta_Bancaria_Response> tarjetas = new ArrayList<>();
        for(Tarjeta_Bancaria t : tarjetas_find){
            if(t.getTipoTarjeta().getNombre().equalsIgnoreCase("credito")){
                tarjetas.add(new Tarjeta_Bancaria_Response(t.getId_tarjeta_bancaria(),t.getNumeroTarjeta(),t.getTipoTarjeta().getNombre(),t.getSaldo(),t.getCuentaBancaria().getNumero_cuenta(),t.isIs_activa(),t.getLimite_tarjeta(),t.getFecha_emision(),t.getFecha_vencimiento()));
            }else{
                tarjetas.add(new Tarjeta_Bancaria_Response(t.getId_tarjeta_bancaria(),t.getNumeroTarjeta(),t.getTipoTarjeta().getNombre(),t.getCuentaBancaria().getNumero_cuenta(),t.isIs_activa(),t.getLimite_tarjeta(),t.getFecha_emision(),t.getFecha_vencimiento()));
            }
        }
        return new Response<>(tarjetas,200,"Tarjetas obtenidas");
    }

    public Response<List<Movimiento_Tarjeta_Bancaria>> getMovimientosTarjeta(String authHeader, TarjetaRequest tr) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        Tarjeta_Bancaria tb_find = this.repositoryTarjeta_Bancaria.findById(tr.getId_tarjeta_bancaria()).orElse(null);
        if(tb_find == null || tb_find.getCliente().getId_cliente() != id_cliente) return new Response<>(null,404, "Error al obtener movimientos de la tarjeta");
        return new Response<>(this.repositoryMovimiento_Tarjeta_Bancaria.findMovimientosTarjetaBancariaByIdTarjeta(tb_find.getId_tarjeta_bancaria()),200,"Movimientos obtenidos");
    }


    /**
     * Metodo que devuelve todos los tipos de tarjetas disponibles
     * @param authHeader Token JWT
     * @return Lista con los tipos de tarjetas disponibles
     */
    public Response<List<Tipo_Tarjeta>> getTiposTarjeta(String authHeader) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        return new Response<>(this.repositoryTipo_Tarjeta.findAll(),200,"Tipos de tarjeta obtenidos");
    }

    public Response<String> agregarMovimientoTarjetaBancaria(String authHeader, MovimientoTarjetaRequest movimientoTarjetaRequest) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        // UPPERCASE -> Tipo Movimiento {}
        movimientoTarjetaRequest.setTipo_movimiento(movimientoTarjetaRequest.getTipo_movimiento().toUpperCase());
        Tarjeta_Bancaria tb_find = this.repositoryTarjeta_Bancaria.findById(movimientoTarjetaRequest.getId_tarjeta_bancaria()).orElse(null);
        if(tb_find == null || tb_find.getCliente().getId_cliente() != id_cliente) return new Response<>(null,404,"Error al registrar el movimiento");
        if(!this.validarTransaccion(tb_find,movimientoTarjetaRequest)) return new Response<>(null,404,"No se puede realizar la transaccion");
        return this.realizarTransaccion(tb_find,movimientoTarjetaRequest);

    }

    /**
     *
     * @param jwt Token JWT
     * @param tarjetaInfo Recogemos id_tarjeta_bancaria y el limite establecido
     * @return
     */
    public Response<String> modificarLimiteTarjeta(String jwt,Tarjeta_Bancaria tarjetaInfo) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(jwt);
        Tarjeta_Bancaria tb_find = this.repositoryTarjeta_Bancaria.findById(tarjetaInfo.getId_tarjeta_bancaria()).orElse(null);
        if(tb_find == null || tb_find.getCliente().getId_cliente() != id_cliente || tarjetaInfo.getLimite_tarjeta().compareTo(BigDecimal.ZERO) < 0) return new Response<>("Error al modificar el limite de la tarjeta",404,"Error");
        tb_find.setLimite_tarjeta(tarjetaInfo.getLimite_tarjeta());
        this.repositoryTarjeta_Bancaria.save(tb_find);
        return new Response<>("Correct!",200,"Se modifico el limite de la tarjeta correctamente");
    }

    /**
     *
     * @param jwt Token JWT
     * @param tarjetaInfo Recogemos id_tarjeta_bancaria y el estado de la tarjeta
     * @return
     */
    public Response<String> modificarEstadoTarjeta(String jwt, Tarjeta_Bancaria tarjetaInfo) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(jwt);
        Tarjeta_Bancaria tb_find = this.repositoryTarjeta_Bancaria.findById(tarjetaInfo.getId_tarjeta_bancaria()).orElse(null);
        if(tb_find == null || tb_find.getCliente().getId_cliente() != id_cliente) return new Response<>("Error al modificar el estado de la tarjeta",404,"Error");
        tb_find.setIs_activa(tarjetaInfo.isIs_activa());
        this.repositoryTarjeta_Bancaria.save(tb_find);
        return new Response<>("Correct!",200,"Se modifico el estado de la tarjeta correctamente");
    }

    private Response<String> realizarTransaccion(Tarjeta_Bancaria tbFind, MovimientoTarjetaRequest movimientoTarjetaRequest) {
        Movimiento_Tarjeta_Bancaria transaccionTarjeta;
        Movimiento_Tarjeta_Bancaria mtb_generated;
        Movimiento_Cuenta_Bancaria mcb_generated;
        Cuenta_Bancaria cb_associte_tarjeta;
        Cuenta_Bancaria cb_beneficiario;
        Tarjeta_Bancaria tb_beneficiaria;
        Date fechaTransaccion = new Date();
        switch(movimientoTarjetaRequest.getTipo_movimiento()) {
            case "REEMBOLSO":
            case "INGRESO":
                mtb_generated = new Movimiento_Tarjeta_Bancaria(
                        tbFind,
                        movimientoTarjetaRequest.getTipo_movimiento(),
                        movimientoTarjetaRequest.getCantidad(),
                        fechaTransaccion,
                        (movimientoTarjetaRequest.getTipo_movimiento().equalsIgnoreCase("REEMBOLSO"))?"REEMBOLSO "+movimientoTarjetaRequest.getRemitente_identificador().toUpperCase():"INGRESO DESDE CAJERO/CAJA",
                        movimientoTarjetaRequest.getRemitente_identificador().toUpperCase(),
                        movimientoTarjetaRequest.getDestinatario_identificador().toUpperCase()
                );
                if (tbFind.getTipoTarjeta().getNombre().equalsIgnoreCase("DEBITO")) {
                    cb_associte_tarjeta = tbFind.getCuentaBancaria(); // Obtenemos la cuenta bancaria asociada
                    cb_associte_tarjeta.setSaldo(cb_associte_tarjeta.getSaldo().add(movimientoTarjetaRequest.getCantidad()));
                    this.repositoryMovimiento_Tarjeta_Bancaria.save(mtb_generated);
                    mcb_generated = new Movimiento_Cuenta_Bancaria(
                            cb_associte_tarjeta,
                            mtb_generated.getTipo_movimiento(),
                            mtb_generated.getCantidad(),
                            fechaTransaccion,
                            (movimientoTarjetaRequest.getTipo_movimiento().equalsIgnoreCase("REEMBOLSO"))?"REEMBOLSO "+movimientoTarjetaRequest.getRemitente_identificador().toUpperCase()+" DESDE TARJETA "+tbFind.getNumeroTarjeta():"INGRESO DESDE CAJERO/CAJA DESDE TARJETA "+tbFind.getNumeroTarjeta(),
                            mtb_generated.getDestinatario_identificador(),
                            cb_associte_tarjeta.getNumero_cuenta()
                    );
                    this.repositoryMovimiento_Cuenta_Bancaria.save(mcb_generated);
                    this.cuentaBancariaRepository.save(cb_associte_tarjeta);
                    return new Response<>(movimientoTarjetaRequest.getTipo_movimiento()+" REALIZADO CON EXITO!", 200, "Movimiento registrado con exito!");
                }
                tbFind.setSaldo(tbFind.getSaldo().add(movimientoTarjetaRequest.getCantidad()));
                this.repositoryMovimiento_Tarjeta_Bancaria.save(mtb_generated);
                this.repositoryTarjeta_Bancaria.save(tbFind);
                return new Response<>(movimientoTarjetaRequest.getTipo_movimiento()+" REALIZADO CON EXITO!", 200, "Movimiento registrado con exito!");
            case "CARGO":
            case "RETIRO":
                mtb_generated = new Movimiento_Tarjeta_Bancaria(
                        tbFind,
                        movimientoTarjetaRequest.getTipo_movimiento().toUpperCase(),
                        movimientoTarjetaRequest.getCantidad(),
                        fechaTransaccion,
                        (movimientoTarjetaRequest.getTipo_movimiento().equalsIgnoreCase("CARGO"))?"CARGO "+movimientoTarjetaRequest.getRemitente_identificador().toUpperCase():"RETIRO DESDE CAJERO/CAJA",
                        movimientoTarjetaRequest.getRemitente_identificador().toUpperCase(),
                        movimientoTarjetaRequest.getDestinatario_identificador().toUpperCase()
                );
                if (tbFind.getTipoTarjeta().getNombre().equalsIgnoreCase("DEBITO")) {
                    cb_associte_tarjeta = tbFind.getCuentaBancaria();
                    cb_associte_tarjeta.setSaldo(cb_associte_tarjeta.getSaldo().add(movimientoTarjetaRequest.getCantidad()));
                    this.repositoryMovimiento_Tarjeta_Bancaria.save(mtb_generated);
                    mcb_generated = new Movimiento_Cuenta_Bancaria(
                            cb_associte_tarjeta,
                            movimientoTarjetaRequest.getTipo_movimiento(),
                            mtb_generated.getCantidad(),
                            fechaTransaccion,
                            (movimientoTarjetaRequest.getTipo_movimiento().equalsIgnoreCase("CARGO")?"CARGO "+movimientoTarjetaRequest.getRemitente_identificador().toUpperCase()+" DE TARJETA "+tbFind.getNumeroTarjeta():"RETIRO DESDE CAJERO/CAJA DESDE TARJETA "+tbFind.getNumeroTarjeta()),
                            cb_associte_tarjeta.getNumero_cuenta(),
                            mtb_generated.getDestinatario_identificador()
                    );
                    this.repositoryMovimiento_Cuenta_Bancaria.save(mcb_generated);
                    this.cuentaBancariaRepository.save(cb_associte_tarjeta);
                    return new Response<>(movimientoTarjetaRequest.getTipo_movimiento()+" REALIZADO CORRECTAMENTE!", 200, "Movimiento registrado con exito!");
                }
                tbFind.setSaldo(tbFind.getSaldo().add(movimientoTarjetaRequest.getCantidad()));
                this.repositoryMovimiento_Tarjeta_Bancaria.save(mtb_generated);
                this.repositoryTarjeta_Bancaria.save(tbFind);
                return new Response<>(movimientoTarjetaRequest.getTipo_movimiento()+" REALIZADO CORRECTAMENTE",200,"Movimiento registrado con exito!");
                default:
                return new Response<>(null,404,"Tipo de movimiento desconocido");
        }
    }

    /**
     * Este metodo devolvera si se puede realizar un movimiento en la tarjeta
     * @param tb_find Estructura que contiene datos de la tarjeta bancaria
     * @param movimientoTarjetaRequest Estructura que contiene informacion acerca de la transaccion a realizar con la tarjeta
     * @return Devuelve si es valida la transaccion
     */
    private boolean validarTransaccion(Tarjeta_Bancaria tb_find, MovimientoTarjetaRequest movimientoTarjetaRequest) {
        // Buscamos que tipo de movimiento es
        Cuenta_Bancaria cb_associte_tarjeta = tb_find.getCuentaBancaria();
        switch (movimientoTarjetaRequest.getTipo_movimiento()){
            case "REEMBOLSO":
                 /*
                        Valida (aparte de otros factores) -> "Entidad/Comercio" a {Tarjeta_Bancario_IdentificationNum}
                  */
                return ((movimientoTarjetaRequest.getCantidad().compareTo(BigDecimal.ZERO) > 0) && (!tb_find.isBloqueada()) && (tb_find.isIs_activa()) && (!movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta()) && (!movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase("Cajero/Caja"))  && (movimientoTarjetaRequest.getDestinatario_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta()))));
            case "INGRESO":
                 /*
                        Valida (aparte de otros factores) -> "Cajero/Caja" a {Tarjeta_Bancario_IdentificationNum}
                  */
                return ((movimientoTarjetaRequest.getCantidad().compareTo(BigDecimal.ZERO) > 0) && (!tb_find.isBloqueada()) && (tb_find.isIs_activa()) && (movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase("Cajero/Caja")  && (movimientoTarjetaRequest.getDestinatario_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta()))));
            case "RETIRO":
                switch(tb_find.getTipoTarjeta().getNombre().toUpperCase()){
                    case "DEBITO":
                        /*
                        Valida (aparte de otros factores) -> {Tarjeta_Bancario_IdentificationNum} a "Cajero/Caja"
                        */
                        return ((movimientoTarjetaRequest.getCantidad().compareTo(BigDecimal.ZERO) < 0) && (movimientoTarjetaRequest.getCantidad().abs().compareTo(tb_find.getLimite_tarjeta()) <= 0) && (movimientoTarjetaRequest.getCantidad().abs().compareTo(cb_associte_tarjeta.getSaldo()) <= 0) && (!tb_find.isBloqueada()) && (tb_find.isIs_activa()) && (movimientoTarjetaRequest.getDestinatario_identificador().equalsIgnoreCase("Cajero/Caja")) && (movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta())));
                    case "CREDITO":
                        /*
                        Valida (aparte de otros factores) -> {Tarjeta_Bancario_IdentificationNum} a "Cajero/Caja"
                        */
                        return ((movimientoTarjetaRequest.getCantidad().compareTo(BigDecimal.ZERO) < 0) && (movimientoTarjetaRequest.getCantidad().abs().compareTo(tb_find.getLimite_tarjeta()) <= 0) && (!tb_find.isBloqueada()) && (tb_find.isIs_activa()) && (movimientoTarjetaRequest.getDestinatario_identificador().equalsIgnoreCase("Cajero/Caja")) && (movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta())));
                }
            case "CARGO":
                switch(tb_find.getTipoTarjeta().getNombre().toUpperCase()){
                    case "DEBITO":
                        /*
                         Valida (aparte de otros factores) -> {Entidad/Comercio desconoido} a {Tarjeta_Bancario_IdentificationNum}
                        */
                        return ((movimientoTarjetaRequest.getCantidad().compareTo(BigDecimal.ZERO) < 0) && (movimientoTarjetaRequest.getCantidad().abs().compareTo(tb_find.getLimite_tarjeta()) <= 0) && (movimientoTarjetaRequest.getCantidad().abs().compareTo(cb_associte_tarjeta.getSaldo()) <= 0) && (!tb_find.isBloqueada()) && (tb_find.isIs_activa()) && (movimientoTarjetaRequest.getDestinatario_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta())) && (!movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta())));
                    case "CREDITO":
                        /*
                        Valida (aparte de otros factores) -> {Entidad/Comercio desconoido} a {Tarjeta_Bancario_IdentificationNum}
                        */
                        return ((movimientoTarjetaRequest.getCantidad().compareTo(BigDecimal.ZERO) < 0) && (movimientoTarjetaRequest.getCantidad().abs().compareTo(tb_find.getLimite_tarjeta()) <= 0) && (!tb_find.isBloqueada()) && (tb_find.isIs_activa()) && (movimientoTarjetaRequest.getDestinatario_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta())) && (!movimientoTarjetaRequest.getRemitente_identificador().equalsIgnoreCase(tb_find.getNumeroTarjeta())));
                }
            default:
                return false;
        }
    }

    /**
     * Metodo que generar el numero identificador de la tarjeta bancaria
     * @return
     */
    private String generateTarjeta_Id(){
        Random r = new Random();
        String identificador = "";
        for (int i = 0; i < LENGTH_IDENTIFICATION_BANK; i++) {
            identificador+=String.valueOf(r.nextInt(10));
        }
        return identificador;
    }

    /**
     * Este metodo generara nuestra tarjeta bancaria
     * @param cbFind Cuenta bancaria asociada a la tarjeta bancaria
     * @param clFind Cliente asociado a la tarjeta bancaria
     * @param tp_tarjeta Tipo de tarjeta que tendra nuestra tarjeta bancaria
     * @return
     */
    private Tarjeta_Bancaria generarTarjetaBancaria(Cuenta_Bancaria cbFind, Cliente clFind,Tipo_Tarjeta tp_tarjeta) {
        String numero_generated = this.generateTarjeta_Id();
        Tarjeta_Bancaria tj_find = this.repositoryTarjeta_Bancaria.findTarjetaBancariaByNumeroIdentificador(numero_generated).orElse(null);
        while(tj_find != null){
            numero_generated = this.generateTarjeta_Id();
            tj_find = this.repositoryTarjeta_Bancaria.findTarjetaBancariaByNumeroIdentificador(numero_generated).orElse(null);
        }
        Date date_now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date_now);
        calendar.add(Calendar.YEAR,3);
        StringBuilder cv_generated = new StringBuilder();
        for (int i = 0; i < 4;i++){
            cv_generated.append(String.valueOf(new Random().nextInt(10)));
        }
        System.out.println("Pin -> "+cv_generated);
        return new Tarjeta_Bancaria(
                clFind,
                cbFind,
                tp_tarjeta,
                numero_generated,
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(0),
                date_now,
                calendar.getTime(),
                false,
                true,
                this.passwordEncoder.encode(cv_generated.toString())
        );
    }
    /**
     * Metodo que devuelve el id del token y tambien realiza la verificacion de la integridad del mismo
     * @param authHeader
     * @return
     */
    private int getIdClienteAndVerifiedJwT(String authHeader){
        // Validamos si el token es correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw  new IllegalArgumentException("Token Bearer invalido");
        // Extraemos el token entero
        String token = authHeader.substring(7);
        // Extraemos a traves del token el id del cliente que contiene (Este metodo tambien verifica la alteracion del token)
        String id_cliente = this.jwtService.extractId_Client(token);
        return Integer.parseInt(id_cliente);
    }

    // Comprobar si un numero de tarjeta existe en nuestra bbdd
    public Response<Boolean> checkExistTarjetaBancaria(String authHeader, String numeroTarjeta) {
    int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
    if(this.repositoryTarjeta_Bancaria.existTarjetaBancariaByNumeroIdentificador(numeroTarjeta).isPresent()){
        return new Response<>(true,200,"El identificador corresponde a un cliente");
    }else{
        return new Response<>(false,200,"El identificador no corresponde a un cliente!");
    }
    }

    public Response<String> procesarTraspaso(String authHeader, TraspasoRequest traspasoRequest) {
        int id_Cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        Tarjeta_Bancaria tb_Traspaso = this.repositoryTarjeta_Bancaria.findTarjetaBancariaByNumeroIdentificador(traspasoRequest.getNumeroTarjeta()).orElse(null);
        Cuenta_Bancaria cb_traspaso = this.cuentaBancariaRepository.findCuentaByIdentificador(traspasoRequest.getNumero_cuenta()).orElse(null);
        Movimiento_Cuenta_Bancaria mv_cb;
        Movimiento_Tarjeta_Bancaria mv_tb;
        if( cb_traspaso == null || cb_traspaso.getCliente().getId_cliente() != id_Cliente || tb_Traspaso == null  || !tb_Traspaso.getTipoTarjeta().getNombre().equalsIgnoreCase("CREDITO") || tb_Traspaso.getCliente().getId_cliente() != id_Cliente || traspasoRequest.getCantidad().compareTo(BigDecimal.ZERO) > 0){
            return new Response<>("Error!",404,"No se puede procesar el traspaso!");
        }else{
            Date fechaTransaccion = new Date();
            if(!traspasoRequest.isTipoTraspaso()){
                if(traspasoRequest.getCantidad().abs().compareTo(tb_Traspaso.getLimite_tarjeta()) > 0){
                    // Se cuela del limite de la tarjeta
                 return new Response<>("La cantidad supera al limite de la tarjeta",404,"No se puede procesar el traspaso");
                }
                // Tarjeta a Cuenta
                tb_Traspaso.setSaldo(tb_Traspaso.getSaldo().add(traspasoRequest.getCantidad()));
                this.repositoryTarjeta_Bancaria.save(tb_Traspaso);
                mv_tb = new Movimiento_Tarjeta_Bancaria(
                        tb_Traspaso,
                        "TRASPASO",
                        traspasoRequest.getCantidad(),
                        fechaTransaccion,
                        "TRASPASO DE TARJETA A CUENTA "+traspasoRequest.getNumero_cuenta(),
                        traspasoRequest.getNumeroTarjeta(),
                        traspasoRequest.getNumero_cuenta()
                );
                this.repositoryMovimiento_Tarjeta_Bancaria.save(mv_tb);

                cb_traspaso.setSaldo(cb_traspaso.getSaldo().add(traspasoRequest.getCantidad().abs()));
                this.cuentaBancariaRepository.save(cb_traspaso);
                mv_cb = new Movimiento_Cuenta_Bancaria(
                        cb_traspaso,
                        "TRASPASO",
                        traspasoRequest.getCantidad().abs(),
                        fechaTransaccion,
                        "TRASPASO DE TARJETA "+traspasoRequest.getNumeroTarjeta(),
                        traspasoRequest.getNumeroTarjeta(),
                        traspasoRequest.getNumero_cuenta()
                );
                this.repositoryMovimiento_Cuenta_Bancaria.save(mv_cb);
                return new Response<>("TRASPASO TARJETA A CUENTA EXITOSO!",200,"El traspaso se realizo correctamente");
            }else{
                // Cuenta a Tarjeta
                if(traspasoRequest.getCantidad().abs().compareTo(cb_traspaso.getSaldo()) > 0){
                    // No dispone del saldo para realizar el traspaso
                    return new Response<>("La cantidad a traspasar excede a tu saldo disoponible",404,"No se puede procesar el traspaso");
                }
                mv_cb = new Movimiento_Cuenta_Bancaria(
                        cb_traspaso,
                        "TRASPASO",
                        traspasoRequest.getCantidad(),
                        fechaTransaccion,
                        "TRASPASO DE CUENTA A TARJETA "+traspasoRequest.getNumeroTarjeta(),
                        traspasoRequest.getNumero_cuenta(),
                        traspasoRequest.getNumeroTarjeta()
                );
                this.repositoryMovimiento_Cuenta_Bancaria.save(mv_cb);
                cb_traspaso.setSaldo(cb_traspaso.getSaldo().add(traspasoRequest.getCantidad()));
                this.cuentaBancariaRepository.save(cb_traspaso);
                mv_tb = new Movimiento_Tarjeta_Bancaria(
                        tb_Traspaso,
                        "TRASPASO",
                        traspasoRequest.getCantidad().abs(),
                        fechaTransaccion,
                        "TRASPASO DE CUENTA "+traspasoRequest.getNumero_cuenta(),
                        traspasoRequest.getNumero_cuenta(),
                        traspasoRequest.getNumeroTarjeta()
                );
                this.repositoryMovimiento_Tarjeta_Bancaria.save(mv_tb);
                tb_Traspaso.setSaldo(tb_Traspaso.getSaldo().add(traspasoRequest.getCantidad().abs()));
                this.repositoryTarjeta_Bancaria.save(tb_Traspaso);
                return new Response<>("TRASPASO CUENTA A TARJETA EXITOSO!",200,"El traspaso se realizo correctamente");
            }
        }

    }
}
