package Utilidades;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import java.util.Map;
import org.json.JSONObject;


public class WebClient {
    public static JSONObject sendRequest(Map<String, String> headers, String url, Http_Method method,JSONObject jsonObject){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = buildRequest(headers, url, method, jsonObject);
            // Enviar la solicitud y esperar la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        } catch (Exception ex) {
            System.out.println("[!] - Ocurrio un error al realizar la request");
            System.out.println(ex);
            return null;
        }
    }
    // Metodo para setear la configuracion correspondientea a la requests
    private static HttpRequest buildRequest(Map<String, String> headers, String url, Http_Method method,JSONObject jsonObject) {
        Builder request = HttpRequest.newBuilder()
                .uri(URI.create(url));
        // Asignacion de los headers
        if (!headers.isEmpty() || headers != null) {
            for (Map.Entry<String, String> iter : headers.entrySet()) {
                request.header(iter.getKey(), iter.getValue());
            }
        }

        // Por predeterminado si no se indica el metodo es GET
        if (method == Http_Method.POST) {
            request.POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString(), StandardCharsets.UTF_8));  // Convertir el JSON a string
        }

        return request.build();
    }
}
