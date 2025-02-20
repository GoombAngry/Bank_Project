package com.bank.back_end.service;

import com.bank.back_end.repository.Tipo_TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Tipo_TarjetaService {
    @Autowired
    private Tipo_TarjetaRepository repositoryTipo_Tarjeta;
}
