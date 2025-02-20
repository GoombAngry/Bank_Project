package com.bank.back_end.model.Response_Request_Model;

public class RegexResponse {
    /*
        - Code {200} - Correct Match
        - Code {400} - Incorrect Match
     */
    private int codeRegex;

    public RegexResponse() {
    }

    public RegexResponse(int codeRegex) {
        this.codeRegex = codeRegex;
    }

    public int getCodeRegex() {
        return codeRegex;
    }

    public void setCodeRegex(int codeRegex) {
        this.codeRegex = codeRegex;
    }

    @Override
    public String toString() {
        return "RegexResponse{" +
                "codeRegex=" + codeRegex +
                '}';
    }
}
