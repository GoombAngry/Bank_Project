package com.bank.back_end.model.Response_Request_Model;

public class RegexRequest {
    private String input;

    public RegexRequest() {
    }

    public RegexRequest(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "RegexRequest{" +
                "input='" + input + '\'' +
                '}';
    }
}
