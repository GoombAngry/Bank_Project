package com.bank.back_end.service;

import com.bank.back_end.model.*;
import com.bank.back_end.model.Response_Request_Model.*;
import com.bank.back_end.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class Cuenta_BancariaService {
    @Autowired
    private Cuenta_BancariaRepository repositoryCuenta_Bancaria;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private Tipo_CuentaRepository tipoCuentaRepository;

    @Autowired
    private Tarjeta_BancariaRepository repositoryTarjeta_Bancaria;

    @Autowired
    private Movimiento_Cuenta_BancariaRepository repositoryMovimiento_Cuenta;

    @Autowired
    private Movimiento_Tarjeta_BancariaRepository repositoryMovimiento_Tarjeta;

    @Autowired
    private JwtService jwtService;

    /**
     * Dar de alta una cuenta bancaria
     * @param authHeader Token JWT
     * @param tipoCuenta Estructura que representa el tipo de cuenta bancaria que representa esta nueva cuenta
     * @return String
     */
    public Response<String> altaCuentaBancaria(String authHeader, Tipo_Cuenta tipoCuenta){
        try{
            // Verificamos integridad del token y obtenemos el id del cliente (En el caso de que no este comprometida la integridad)
            int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
            Tipo_Cuenta tipoCuentaFind = this.tipoCuentaRepository.findById(tipoCuenta.getId_tipo_cuenta()).orElse(null);
            if(tipoCuentaFind == null) return new Response<>("Error!",404,"EL identificador del tipo de cuenta bancaria no es correcto");
            // Generando informacion para la cuenta bancaria
            Cuenta_Bancaria cb_generated = this.generateBankAccount(id_cliente,tipoCuentaFind);
            if(cb_generated == null)  return new Response<>("Error!",404,"Se produjo un error al generar la cuenta bancaria");
            this.repositoryCuenta_Bancaria.save(cb_generated);
            return new Response<>("Exito!",200,"La Cuenta Bancaria ha sido creada correctamente");
        }catch (Exception e){
            System.out.println("[!] - Se ha producido un error en el servicio altaCuentaBancaria");
            return new Response<>("Error!",500,"Internal Error");
        }
    }
    public Response<String> agregarMovimientoCuentaBancaria(String authHeader, MovimientoCuentaRequest movimientoCuentaRequest){
        Response<Cuenta_Bancaria> r;
        try{
            // Verificamos integridad del token y obtenemos el id del cliente
            int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
            // Obtenemos el Id de la cuenta bancaria que realiza la transaccion
            Cuenta_Bancaria cuentaBancariaFind = this.repositoryCuenta_Bancaria.findById(movimientoCuentaRequest.getId_cuenta_bancaria()).orElse(null);
            // Comprobamos que la cuenta bancaria implicada en el movimiento pertenece al cliente del token
            if(cuentaBancariaFind == null || cuentaBancariaFind.getCliente().getId_cliente() != id_cliente) return new Response<>(null,404,"EL identificador de la cuenta bancaria no es correcto");
            // Construimos la estructura del movimiento bancaria
            Movimiento_Cuenta_Bancaria mvTransaccion = this.buildTransaccion(cuentaBancariaFind,movimientoCuentaRequest);
            if(this.realizarTransaccion(mvTransaccion)) return new Response<>("Exito!",200,"Transaccion realizada");
            return new Response<>("Error!",404,"Error en la operacion");
        }catch (Exception e){
            System.out.println(e);
            return new Response<>("Error!",500,"{ - Agregar Movimiento Cuenta Bancaria - } Internal Error");
        }
    }
    public Response<List<Cuenta_Bancaria_Response>> getCuentasCliente(String authHeader) {
         /*
            - Verificamos integridad del token y obtenemos el id del cliente (En el caso de que no este comprometida la integridad)
            * */
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        List<Cuenta_Bancaria> cuentas_find = this.repositoryCuenta_Bancaria.findCuentasByIdCliente(id_cliente);
        ArrayList<Cuenta_Bancaria_Response> cuentas = new ArrayList<>();
        for(Cuenta_Bancaria c : cuentas_find){
            cuentas.add(new Cuenta_Bancaria_Response(c.getId_cuenta_bancaria(),c.getFecha_creacion(),c.getTipoCuenta().getDescripcion(),c.getSaldo(),c.getNumero_cuenta(),c.isIs_activa()));
        }
        return new Response<>(cuentas,200,"Cuentas bancarias obtenidas");
    }
    public Response<List<Movimiento_Cuenta_Bancaria>> getMovimientosCuenta(String authHeader, CuentaRequest c_r){
        /*
          - Verificamos integridad del token y obtenemos el id del cliente (En el caso de que no este comprometida la integridad)
        * */
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        Cuenta_Bancaria cb_find = this.repositoryCuenta_Bancaria.findById(c_r.getId_cuenta_bancaria()).orElse(null);
        if(cb_find == null || cb_find.getCliente().getId_cliente() != id_cliente) return new Response<>(null,404,"EL identificador de la cuenta bancaria no es correcto");
        return new Response<>(this.repositoryMovimiento_Cuenta.findMovimientosCuentaByIdCuentaBancaria(cb_find.getId_cuenta_bancaria()),200,"Movimiento obtenidos");
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
    static private String generateBank_Id(){
        final int LENGTH_IDENTIFICATION_BANK = 14;
        Random r = new Random();
        String identificador = "";
        for (int i = 0; i < LENGTH_IDENTIFICATION_BANK; i++) {
            identificador+=String.valueOf(r.nextInt(10));
        }
        return "BANK-"+identificador;
    }
    public Cuenta_Bancaria generateBankAccount(int id_Cliente,Tipo_Cuenta tipoCuenta){
        String identificador = generateBank_Id();
        Cuenta_Bancaria cb = this.repositoryCuenta_Bancaria.findCuentaByIdentificador(identificador).orElse(null);
        while(cb != null){
            identificador = generateBank_Id();
            cb = this.repositoryCuenta_Bancaria.findCuentaByIdentificador(identificador).orElse(null);
        }
        cb = new Cuenta_Bancaria();
        cb.setFecha_creacion(new Date());
        cb.setIs_activa(true);
        cb.setSaldo(BigDecimal.valueOf(0));
        Cliente clienteFind = this.clienteRepository.findById(id_Cliente).orElse(null);
        if(clienteFind == null) return null;
        cb.setCliente(clienteFind);
        cb.setTipoCuenta(tipoCuenta);
        cb.setNumero_cuenta(identificador);
        return cb;
    }
    private Movimiento_Cuenta_Bancaria buildTransaccion(Cuenta_Bancaria cb, MovimientoCuentaRequest mv_cr){
        // Constructucion del Cuerpo de la transaccion
        return new Movimiento_Cuenta_Bancaria(
                cb,
                mv_cr.getTipo_movimiento().toUpperCase(),
                mv_cr.getCantidad(),
                new Date(),
                mv_cr.getDescripcion_movimiento(),
                mv_cr.getRemitente_identificador().toUpperCase(),
                mv_cr.getDestinatario_identificador().toUpperCase()
        );
    }
    private boolean realizarTransaccion(Movimiento_Cuenta_Bancaria mv_cb){
        // Detectar el tipo de movimiento
        /*
            Tipos de movimientos disponibles {"INGRESO", "RETIRO", "TRANSFERENCIA"}
         */
        Cuenta_Bancaria cb;
        mv_cb.setTipo_movimiento(mv_cb.getTipo_movimiento().toUpperCase());
        switch (mv_cb.getTipo_movimiento()){
            case "INGRESO": // {"Cajero/Caja" a Cuenta}
                cb = this.repositoryCuenta_Bancaria.findById(mv_cb.getCuentaBancaria().getId_cuenta_bancaria()).orElse(null);
                if(cb == null || mv_cb.getCantidad().compareTo(BigDecimal.ZERO) < 0 || !cb.getNumero_cuenta().equalsIgnoreCase(mv_cb.getDestinatario_identificador()) || (!mv_cb.getRemitente_identificador().equalsIgnoreCase("Cajero/Caja"))) return false; // Si es nulo o si el destinatario no coincide con la cuenta o si la cantidad es negativa
                mv_cb.setDescripcion("INGRESO DESDE CAJERO/CAJA");
                this.confirmarTransaccion(cb,mv_cb);
                return true;
            case "RETIRO": // {Cuenta a Cajero/Caja}
                cb = this.repositoryCuenta_Bancaria.findById(mv_cb.getCuentaBancaria().getId_cuenta_bancaria()).orElse(null);
                if(cb == null || mv_cb.getCantidad().compareTo(BigDecimal.ZERO) > 0 || mv_cb.getCantidad().abs().compareTo(cb.getSaldo()) > 0 || !cb.getNumero_cuenta().equalsIgnoreCase(mv_cb.getRemitente_identificador()) || (!mv_cb.getDestinatario_identificador().equalsIgnoreCase("Cajero/Caja"))) return false; // Si es nulo o si quiere retirar mas de lo que se tiene o si el remitente no coincide con la cuenta y ademas la cantidad es positiva
                mv_cb.setDescripcion("RETIRO DESDE CAJERO/CAJA");
                this.confirmarTransaccion(cb,mv_cb);
                return true;
            case "TRANSFERENCIA": // {Cuenta a Cuenta}   { -  Solo se podran hacer transferencias entre clientes (existentes) de nuestro BANK - }
                // Detectar el destinatario si es tarjeta o cuenta y ademas debe existir en nuestra base de datos
                if(RegexTypes.comprobarIDCuentaBancaria(mv_cb.getDestinatario_identificador()) && this.repositoryCuenta_Bancaria.existCuentaByIdentificador(mv_cb.getDestinatario_identificador()).isPresent()){
                    // Cuenta Bancaria - Destinatario
                    // { - Cuenta Bancaria emisora de la transferencia - }
                    cb = this.repositoryCuenta_Bancaria.findById(mv_cb.getCuentaBancaria().getId_cuenta_bancaria()).orElse(null);
                    if(cb == null || mv_cb.getCantidad().abs().compareTo(cb.getSaldo()) > 0 || mv_cb.getCantidad().compareTo(BigDecimal.ZERO) > 0 || !mv_cb.getRemitente_identificador().equalsIgnoreCase(cb.getNumero_cuenta()) || mv_cb.getDestinatario_identificador().equalsIgnoreCase(cb.getNumero_cuenta())) return false; // La cantidad supera al saldo de la cuenta remitente
                    mv_cb.setDescripcion(mv_cb.getTipo_movimiento()+" ENVIADA A "+mv_cb.getDestinatario_identificador());
                    this.confirmarTransaccion(cb,mv_cb);
                    // { - Cuenta Bancaria destinataria de la transferencia - }
                    cb = this.repositoryCuenta_Bancaria.findCuentaByIdentificador(mv_cb.getDestinatario_identificador()).orElse(null);
                    if(cb == null) return false;
                    Movimiento_Cuenta_Bancaria mv_cb_destinatario = new Movimiento_Cuenta_Bancaria(
                            cb,
                            mv_cb.getTipo_movimiento(),
                            mv_cb.getCantidad().abs(),
                            mv_cb.getFecha_movimiento(),
                            "TRANSFERENCIA RECIBIDA DE "+mv_cb.getRemitente_identificador(),
                            mv_cb.getRemitente_identificador(),
                            mv_cb.getDestinatario_identificador()
                    );
                    this.confirmarTransaccion(cb,mv_cb_destinatario);
                    return true;
                }
            default:
                return false;
        }
    }
    private void confirmarTransaccion(Cuenta_Bancaria cb,Movimiento_Cuenta_Bancaria mv_cb){
        // Registramos el movimiento
        this.repositoryMovimiento_Cuenta.save(mv_cb);
        // Actualizamos el saldo de la cuenta
        cb.setSaldo(cb.getSaldo().add(mv_cb.getCantidad()));
        // Guardamos cambios de la cuenta
        this.repositoryCuenta_Bancaria.save(cb);
    }

    public Response<List<Tipo_Cuenta>> getTiposCuenta(String authHeader) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        return new Response<>(this.tipoCuentaRepository.findAll(),200,"Tipos Obtenidos");
    }

    // Comprobar si un numero de cuenta bancaria existe en nuestra bbdd
    public Response<Boolean> checkExistCuentaBancaria(String jwt,String identificador){
        int id_Cliente = this.getIdClienteAndVerifiedJwT(jwt);
        if(this.repositoryCuenta_Bancaria.existCuentaByIdentificador(identificador).isPresent()){
            return new Response<>(true,200,"El identificador corresponde a un cliente");
        }else{
            return new Response<>(false,200,"El identificador no corresponde a un cliente!");
        }
    }

    public Response<String> darDeBajaCuenta(String jwt, CuentaRequest cuentaRequest) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(jwt);
        Cuenta_Bancaria cb_find = this.repositoryCuenta_Bancaria.findById(cuentaRequest.getId_cuenta_bancaria()).orElse(null);
        if(cb_find != null && cb_find.getCliente().getId_cliente() == id_cliente ){
            if(!cb_find.isIs_activa()){
                cb_find.setIs_activa(!cb_find.isIs_activa());
                cb_find.setFecha_cierre(null);
                this.repositoryCuenta_Bancaria.save(cb_find);
                return new Response<>("Exito!",200,"La cuenta ha sido activada!");
            }else{
                cb_find.setIs_activa(false);
                cb_find.setFecha_cierre(new Date());
                this.repositoryCuenta_Bancaria.save(cb_find);
                return new Response<>("Exito!",200,"La cuenta se desactivo correctamente!");
            }

        }else{
            return new Response<>("Error!",404,"Error al dar de baja la cuenta bancaria!");
        }
    }
}
