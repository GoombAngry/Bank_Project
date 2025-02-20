package com.bank.back_end.service;

import com.bank.back_end.repository.Movimiento_Tarjeta_BancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Movimiento_Tarjeta_BancariaService {
    @Autowired
    private Movimiento_Tarjeta_BancariaRepository repositoryMovimiento_Tarjeta_Bancaria;
}
