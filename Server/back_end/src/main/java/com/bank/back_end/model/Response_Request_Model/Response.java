package com.bank.back_end.model.Response_Request_Model;

public class Response <T>{
    /**
     * Códigos HTTP comunes:
     *
     * - 201 (Created): Se usa cuando un recurso se ha creado exitosamente.
     * - 200 (OK): Se usa cuando un recurso ha sido actualizado correctamente.
     * - 404 (Not Found): Se usa cuando el recurso solicitado no se encuentra en la base de datos (ej. cliente no encontrado).
     * - 400 (Bad Request): Se usa cuando la solicitud del cliente tiene datos inválidos o faltantes (ej. datos incorrectos o incompletos).
     * - 500 (Internal Server Error): Se usa cuando ocurre un error inesperado en el backend (por ejemplo, excepciones no manejadas).
     * - 401 (Unauthorized): Se usa cuando el cliente no está autenticado para realizar la operación.
     * - 403 (Forbidden): Se usa cuando el cliente está autenticado, pero no tiene permisos suficientes para realizar la operación.
     */
    private int codeResponse;
    private String message;
    private T body;

    public Response() {
    }

    public Response(int codeResponse, String message) {
        this();
        this.codeResponse = codeResponse;
        this.message = message;
    }

    public Response(T body, int codeResponse, String message) {
        this.body = body;
        this.message = message;
        this.codeResponse = codeResponse;
    }

    public int getCodeResponse() {
        return codeResponse;
    }

    public void setCodeResponse(int codeResponse) {
        this.codeResponse = codeResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }


}
