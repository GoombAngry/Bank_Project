package com.bank.back_end.service;

import com.bank.back_end.repository.Tipo_CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Tipo_CuentaService {
    @Autowired
    private Tipo_CuentaRepository repositoryTipo_Cuenta;
}
