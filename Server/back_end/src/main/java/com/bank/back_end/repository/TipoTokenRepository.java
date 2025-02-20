package com.bank.back_end.repository;

import com.bank.back_end.model.Tipo_Token;
import com.bank.back_end.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoTokenRepository extends JpaRepository<Tipo_Token,Integer> {
}
