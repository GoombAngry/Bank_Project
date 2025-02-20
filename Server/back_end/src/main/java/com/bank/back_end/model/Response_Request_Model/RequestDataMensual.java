package com.bank.back_end.model.Response_Request_Model;

public class RequestDataMensual {
    private int year;
    private int month;

    public RequestDataMensual() {
    }

    public RequestDataMensual(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    @Override
    public String toString() {
        return "RequestDataMensual{" +
                "year=" + year +
                ", month=" + month +
                '}';
    }
}
