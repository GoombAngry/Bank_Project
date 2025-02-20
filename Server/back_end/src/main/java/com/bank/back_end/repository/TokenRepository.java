package com.bank.back_end.repository;

import com.bank.back_end.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("SELECT t FROM Token t WHERE t.cliente.id_cliente = :aidi_cliente AND t.tipo_token.id_tipo_token = :aidiTipoToken")
    List<Token> getAllTokensAppUberByIdCliente(@Param("aidi_cliente")int id_cliente,@Param("aidiTipoToken")int id_tipoToken);
    @Query("SELECT t FROM Token t WHERE t.token = :valueeeTok")
    Optional<Token> searchTokenByValueToken(@Param("valueeeTok")String valTok);
}
