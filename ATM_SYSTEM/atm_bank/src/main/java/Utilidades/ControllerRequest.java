package Utilidades;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class ControllerRequest {

    public static String urlBackend = "http://localhost:8080";

    public static JSONObject loginAtm(String identificador, String password) {
        JSONObject body = new JSONObject();
        body.put("identification", identificador);
        body.put("password", password);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        return WebClient.sendRequest(header, urlBackend + "/auth/loginAtm", Http_Method.POST, body);
    }

    public static JSONObject getInformacionCliente(String jwt) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + jwt);
        return WebClient.sendRequest(header, urlBackend + "/client/getInformacion", Http_Method.GET, null);
    }

    public static JSONObject realizarOperacion(String jwt, boolean typeOpearation, float cantidad, String identificador, int id_identificador) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + jwt);
        header.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put("tipo_movimiento", (typeOpearation) ? "INGRESO" : "RETIRO");
        body.put("cantidad", (float) ((typeOpearation) ? cantidad : ((float) (-1) * (cantidad))));
        body.put("remitente_identificador", (typeOpearation) ? "Cajero/Caja" : identificador);
        body.put("destinatario_identificador", (typeOpearation) ? identificador : "Cajero/Caja");
        body.put(isBankId(identificador) ? "id_cuenta_bancaria" : "id_tarjeta_bancaria", id_identificador);
        return WebClient.sendRequest(header, urlBackend + (isBankId(identificador) ? "/account/addMovimientoCuentaBancaria" : "/tarjeta/addMovimientoTarjetaBancaria"), Http_Method.POST, body);
    }

    public static JSONObject getMovimientosByIdentificador(String jwt, String identificador, int id_identificador) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + jwt);
        header.put("Content-Type", "application/json");
        JSONObject body = new JSONObject();
        body.put((isBankId(identificador) ? "id_cuenta_bancaria" : "id_tarjeta_bancaria"), id_identificador);
        return WebClient.sendRequest(header, urlBackend + (isBankId(identificador) ? "/account/getMovimientosCuenta" : "/tarjeta/getMovimientosTarjeta"), Http_Method.POST, body);
    }

    // Devuelve si un identificador corresponde al de una cuenta bancaria
    public static boolean isBankId(String identificador) {
        /**
         * true -> Cuenta Bancaria false -> Tarjeta Bancaria
         */
        return Pattern.compile("^BANK-\\d{14}$").matcher(identificador).matches();
    }

    // Metodo para desacreditar un JWT
    public static boolean desacreditarJWT(String jwt) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            // Crear la petición GET con cabecera Authorization
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlBackend + "/auth/logout"))
                    .header("Authorization", "Bearer " + jwt) // Reemplaza con el token real
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
