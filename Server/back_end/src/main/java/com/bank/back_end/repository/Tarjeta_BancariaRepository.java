package com.bank.back_end.repository;

import com.bank.back_end.model.Cuenta_Bancaria;
import com.bank.back_end.model.Tarjeta_Bancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface Tarjeta_BancariaRepository extends JpaRepository<Tarjeta_Bancaria,Integer> {
    @Query("SELECT t FROM Tarjeta_Bancaria t WHERE t.numeroTarjeta = :nuumeroTarjeta ")
    Optional<Tarjeta_Bancaria> findTarjetaBancariaByNumeroIdentificador(@Param("nuumeroTarjeta")String numeroTarjeta);
    @Query("SELECT t FROM Tarjeta_Bancaria t WHERE t.cliente.id_cliente = :idiiCliente ")
    List<Tarjeta_Bancaria> findTarjetasBancariasByIdCliente(@Param("idiiCliente")int id_cliente);
    @Query("SELECT t FROM Tarjeta_Bancaria t WHERE t.numeroTarjeta = :nuumeroTarjeta ")
    Optional<Tarjeta_Bancaria> existTarjetaBancariaByNumeroIdentificador(@Param("nuumeroTarjeta")String numeroTarjeta);
    @Query("SELECT SUM(t.saldo) FROM Tarjeta_Bancaria t where t.cliente.id_cliente = :iideCliente AND t.tipoTarjeta.id_tipo_tarjeta = 2")
    Optional<BigDecimal> getTotalSaldoTarjetasCliente(@Param("iideCliente")int idCliente);
}
