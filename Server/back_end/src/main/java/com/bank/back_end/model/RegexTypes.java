package com.bank.back_end.model;

import java.util.regex.*;

public class RegexTypes {
    private final static String regexIDCuentaBancaria = "^BANK-\\d{14}$";
    private final static String regexIDTarjetaBancaria = "^\\d{12}$";




    /**
     *  Este metodo validara si es un identificar de una cuenta bancaria
     * @param id identificador de cuenta bancaria
     * @return
     */
    public static boolean comprobarIDCuentaBancaria(String id){
        Pattern ptt = Pattern.compile(RegexTypes.regexIDCuentaBancaria);
        Matcher mach = ptt.matcher(id);
        return mach.matches();
    }

    /**
     * Este metodo validara si es un identificar de tarjeta bancaria
     * @param id identificador de tarjeta bancaria
     * @return
     */
    public static boolean comprobarIDTarjetaBancaria(String id){
        Pattern ptt = Pattern.compile(RegexTypes.regexIDTarjetaBancaria);
        Matcher mach = ptt.matcher(id);
        return mach.matches();
    }
}
