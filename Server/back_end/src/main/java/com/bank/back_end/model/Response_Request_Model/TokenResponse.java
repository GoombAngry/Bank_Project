package com.bank.back_end.model.Response_Request_Model;

public class TokenResponse {
    private String tokenJwt;

    public TokenResponse() {
    }


    public TokenResponse(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    public String getTokenJwt() {
        return tokenJwt;
    }

    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }


    @Override
    public String toString() {
        return "TokenResponse{" +
                "tokenJwt='" + tokenJwt + '\'' +
                '}';
    }
}
