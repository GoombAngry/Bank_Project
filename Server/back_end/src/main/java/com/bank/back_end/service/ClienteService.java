package com.bank.back_end.service;

import ch.qos.logback.core.net.server.Client;
import com.bank.back_end.model.*;
import com.bank.back_end.model.Response_Request_Model.*;
import com.bank.back_end.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repositoryCliente;
    @Autowired
    private Tarjeta_BancariaRepository repositoryTarjetaBancaria;
    @Autowired
    private Cuenta_BancariaRepository repositoryCuentaBancaria;
    @Autowired
    private TokenRepository repositoryToken;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TipoTokenRepository tipoTokenRepository;

    // [ Public Methods ]
    public Response<Cliente> altaCliente(RegisterRequest request){
        Cliente cliente = new Cliente();
        request.setPassword(this.passwordEncoder.encode(request.getPassword()));
        cliente.parserRegisterRequest(request);
        try{
            // Guardamos el cliente insertado en la base de datos
            cliente = this.repositoryCliente.save(cliente);
            return new Response<>(cliente,201,"Cliente Creado correctamente");
        }catch (Exception e){
            System.out.println(e);
            return new Response<>(null,500,"Error al crear el cliente");
        }
    }
    public Response<List<Cliente>> getAllClientes(){
        try{
            return new Response<>(this.repositoryCliente.findAll(),200,"Consulta realizada correctamente");
        }catch (Exception e){
            return new Response<>(null,500,"Internal Error");
        }
    };
    public Response<Cliente> updateCliente(Cliente cliente){
        try{
            Cliente c = this.repositoryCliente.findById(cliente.getId_cliente()).orElse(null);
            if(c != null){
                if(cliente.getDni_cliente() != null) c.setDni_cliente(cliente.getDni_cliente());
                if(cliente.getNombre() != null) c.setNombre(cliente.getNombre());
                if(cliente.getApellidos() != null) c.setApellidos(cliente.getApellidos());
                if(cliente.getCorreo_electronico() != null) c.setCorreo_electronico(cliente.getCorreo_electronico());
                if(cliente.getPassword() != null) c.setPassword(cliente.getPassword());
                if(cliente.getTelefono() != null) c.setTelefono(cliente.getTelefono());
                if(cliente.getDireccion() != null) c.setDireccion(cliente.getDireccion());
            }
            // Algo va mal = No encontro a un cliente con ese id
            return new Response<>((c != null)?this.repositoryCliente.save(c):null,200,(c != null)?"Se actualizo correctamente":"Algo va mal...");
        } catch (Exception e) {
            System.out.println(e);
            return new Response<>(null,500,"Internal Error");
        }
    }
    // Login a traves de los cajeros ATM
    public Response<AtmLoginResponse> loginClienteAtm(AtmLoginRequest request){

        Cliente c_associate = null;
        Tipo_Token t = this.tipoTokenRepository.findById(2).orElse(null);
        int id_indentificador;
        if(RegexTypes.comprobarIDTarjetaBancaria(request.getIdentification())){
            System.out.println("[!] - Solicitud de login desde cajero ATM con Tarjeta Bancaria");
            Tarjeta_Bancaria tb_find = this.repositoryTarjetaBancaria.findTarjetaBancariaByNumeroIdentificador(request.getIdentification()).orElse(null);
            if(tb_find == null) return new Response<>(null,404,"Tarjeta Bancaria desconocida!");
            if(this.passwordEncoder.matches(request.getPassword(),tb_find.getCv_tarjeta())){
                id_indentificador = tb_find.getId_tarjeta_bancaria();
                c_associate = tb_find.getCliente();
            }else{
                return new Response<>(null,404,"Pin incorrecto!");
            }
        }else{
            System.out.println("[!] - Solicitud de login desde cajero ATM con Cartilla Bancaria");
            Cuenta_Bancaria cb_find = this.repositoryCuentaBancaria.findCuentaByIdentificador(request.getIdentification()).orElse(null);
            if(cb_find == null) return new Response<>(null,404,"Cartilla Bancaria desconocida!");
            c_associate = cb_find.getCliente();
            id_indentificador = cb_find.getId_cuenta_bancaria();
        }
        if(t == null) return new Response<>(null,404,"Error al obtener el tipo de token!");
        // Generamos y guardamos el token que devolveremos al cajero ATM
        String token_atm_generated = this.jwtService.generateToken(c_associate);
        this.revocarAllTokensByTypeTokeCliente(c_associate,t);
        this.saveUserToken(c_associate,token_atm_generated,t);
        return new Response<>(new AtmLoginResponse(request.getIdentification(),id_indentificador,token_atm_generated),201,"Login existoso!");
    }

    // Login a traves de app front-end
    public Response<TokenResponse> loginCliente(LoginResquest request){
        /**
         * - Authentication Manager maneja el proceso de Autenticacion
         * - Este requiere el uso de 3 funciones para funcionar, definidas en AppConfig(userDetailsService(),authenticationProvider(),authenticationManager())
         * - UsernamePasswordAuthenticationToken es una clase de Spring Security que almacena los datos de autenticacion a comprobar (dni y password en mi caso)
         */
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getDni_cliente(),request.getPassword()));
        /**
         * - Si se da el caso que las creedenciales de login son validas se ejecutara el resto de codigo que se encuentra abajo sino no.
         */
        /*
            Comprobamos que la cuenta no este dada de baja
         */
        Cliente c = this.repositoryCliente.findUserByDni(request.getDni_cliente()).orElseThrow();
        String jwt = this.jwtService.generateToken(c);
        //String refresh_jwt = this.jwtService.generateRefreshToken(c);
        Tipo_Token tipo_token = this.tipoTokenRepository.findById(1).orElse(null);
        if(tipo_token == null)return new Response<>(null,404,"Error al obtener el tipo de token");
        this.revocarAllTokensByTypeTokeCliente(c,tipo_token); // Revocamos o invalidamos todos los token de APP del cliente que se va a loguear (Para que solo tenga valida 1 sesion APP max)
        this.saveUserToken(c,jwt,tipo_token); // Guardamos el nuevo token que se le asignara al usuario que se esta logeando
        return new Response<>(new TokenResponse(jwt),201,"Login existoso!");
    }
    // [ Private Methods ]
    /**
     * Metodo para guardar el nuevo token que se le asigna a un usuario
     * @param cliente Estructura con los datos del cliente
     * @param tokenJwt El nuevo token que se le asignara
     */
    private void saveUserToken(Cliente cliente, String tokenJwt,Tipo_Token t) {
        Token token = new Token();
        token.setTipo_token(t);
        token.setCliente(cliente);
        token.setToken(tokenJwt);
        token.setToken_Type(Token.TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        this.repositoryToken.save(token);
    }

    /**
     * Este metodo revocara todos los token de un usuario concreto
     * @param cliente Estructura con los datos del cliente
     */
    private void revocarAllTokensByTypeTokeCliente(Cliente cliente,Tipo_Token tipoToken){
        List<Token> tokens_Cliente = this.repositoryToken.getAllTokensAppUberByIdCliente(cliente.getId_cliente(), tipoToken.getId_tipo_token());
        if(!tokens_Cliente.isEmpty()){
            for(Token t : tokens_Cliente){
                t.setRevoked(true);
                t.setExpired(true);
            }
            this.repositoryToken.saveAll(tokens_Cliente);
        }

    }

    // Nos renovara el token para 7 dias (El token devuelto )
    public Response<TokenResponse> refreshToken(String authHeader){
        /*
        // Validamos si el token es correcto
        if (authHeader == null || !authHeader.startsWith("Bearer ")) throw  new IllegalArgumentException("Token Bearer invalido");
        // Extraemos el token entero
        String token = authHeader.substring(7);
        // Extraemos a traves del token el correo electronico que contiene (Este metodo tambien verifica la alteracion del token)
        //String clientEmail = this.jwtService.extractEmailClient(token);
        //if (clientEmail == null) throw  new IllegalArgumentException("Token invalid");
        // Buscamos el cliente que tenga ese correo asociado
        //Cliente clientSearch = this.repositoryCliente.findUserByEmail(clientEmail).orElseThrow(()-> new UsernameNotFoundException(clientEmail));
        // Validamos el token asociado a ese cliente
        if(!this.jwtService.isValidToken(token,clientSearch)) throw new IllegalArgumentException("Token invalid");
        // Generamos un nuevo token que se asignara al usuario de validad de 7 dias
        String newToken = this.jwtService.generateRefreshToken(clientSearch);
        // Revocamos todos los token existente del cliente
        revocarAllTokensCliente(clientSearch);
        // Agregamos el nuevo token para el cliente
        saveUserToken(clientSearch,newToken);
        return new Response<>(new TokenResponse(newToken),201,"Token Refresh");
         */
        return null;
    }

    public Response<ClienteInfoResponse> getInformacionCliente(String authHeader) {
        int id_Cliente = this.getIdClienteAndVerifiedJwT(authHeader);
        System.out.println("id_Cliente = " + id_Cliente);

        Cliente clienteFind = this.repositoryCliente.findById(id_Cliente).orElse(null);
        if(clienteFind == null) return new Response<>(null,404,"Error en la opearcion");
        ClienteInfoResponse ci_r = new ClienteInfoResponse(
                clienteFind.getDni_cliente(),
                clienteFind.getNombre(),
                clienteFind.getApellidos(),
                clienteFind.getCorreo_electronico(),
                clienteFind.getFecha_alta(),
                clienteFind.getTelefono(),
                clienteFind.getDireccion(),
                clienteFind.getCiudad()
        );
        return new Response<>(ci_r,200,"Operacion Existosa");

    }

    public Response<String> editarInformacionDelCliente(String jwt,ClientEditRequest clientInfo){
        int id_cliente = this.getIdClienteAndVerifiedJwT(jwt);
        Cliente find;
        System.out.println(clientInfo);
        try{
            if(clientInfo.getCorreo_electronico() != null){
                find = this.repositoryCliente.findUserByEmail(clientInfo.getCorreo_electronico()).orElse(null);
                // El email no existe en ningun usuario existente en la base de datos y podemos cambiarlo correctamente
                if(find == null){
                    find = this.repositoryCliente.findById(id_cliente).orElse(null);
                    if(find != null){
                        // Procedemos a realizar el cambio de datos que desea cambiar el usuario
                        this.repositoryCliente.save(this.setChangeDetectedByCliente(find,clientInfo));
                        return new Response<>("Exito!",200,"Cambios guardados con exito!");
                    }else{
                        return new Response<>("Error!",404,"Error al encontrar el cliente por id");
                    }
                }else{
                    return new Response<>("Error!",404,"El correo esta asociado a un cliente existente!");
                }
            }else{
                find = this.repositoryCliente.findById(id_cliente).orElse(null);
                if(find != null){
                    this.repositoryCliente.save(this.setChangeDetectedByCliente(find,clientInfo));
                    return new Response<>("Exito!",200,"Cambios guardados con exito!");
                }else{
                    return new Response<>("Error!",404,"Error al encontrar el cliente por id");
                }
            }
        }catch(Exception e){
            return new Response<>(null,501,"Error del servidor!");
        }
    }

    private Cliente setChangeDetectedByCliente(Cliente c_db,ClientEditRequest clientInfo){
        if(clientInfo.getCorreo_electronico() != null){
            c_db.setCorreo_electronico(clientInfo.getCorreo_electronico());
        }
        if(clientInfo.getApellidos() != null){
            c_db.setApellidos(clientInfo.getApellidos());
        }
        if(clientInfo.getDireccion() != null){
            c_db.setDireccion(clientInfo.getDireccion());
        }
        if(clientInfo.getNombre() != null){
            c_db.setNombre(clientInfo.getNombre());
        }
        if(clientInfo.getCiudad() != null){
            c_db.setCiudad(clientInfo.getCiudad());
        }
        if(clientInfo.getTelefono() != null){
            c_db.setTelefono(clientInfo.getTelefono());
        }
        if(clientInfo.getDireccion() != null){
            c_db.setDireccion(clientInfo.getDireccion());
        }
        return c_db;
    }

    /**
     * Este metodo generara un numero identificador para una cuenta bancaria
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


    public Response<String> darDeBajaCuentaCliente(String jwt) {
        int id_cliente = this.getIdClienteAndVerifiedJwT(jwt);
        Cliente c_find = this.repositoryCliente.findById(id_cliente).orElse(null);
        Token t = this.repositoryToken.searchTokenByValueToken(jwt.split(" ")[1]).orElse(null);
        System.out.println(jwt);
        if(c_find == null || t == null) return new Response<>(null,404,"Error al dar de baja al cliente!");
        c_find.setIs_baja(true);
        this.repositoryCliente.save(c_find);
        t.setRevoked(true);
        t.setExpired(true);
        System.out.println(this.repositoryToken.save(t));
        return new Response<>(null,200,"Cliente dado de baja correctamente!");
    }
}
