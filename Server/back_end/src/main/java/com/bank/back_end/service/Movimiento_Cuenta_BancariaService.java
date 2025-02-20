package com.bank.back_end.service;

import com.bank.back_end.repository.Movimiento_Cuenta_BancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Movimiento_Cuenta_BancariaService {
@Autowired
    private Movimiento_Cuenta_BancariaRepository repositoryMoviento_Cuenta_Bancaria;
}
