package com.bank.back_end.model.Response_Request_Model;

public class AtmLoginRequest {
    private String identification;
    private String password;

    public AtmLoginRequest(String identification, String password) {
        this.identification = identification;
        this.password = password;
    }

    public AtmLoginRequest() {
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AtmLoginRequest{" +
                "identification='" + identification + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
