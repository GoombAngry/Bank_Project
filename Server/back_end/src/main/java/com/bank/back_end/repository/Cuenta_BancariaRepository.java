package com.bank.back_end.repository;

import com.bank.back_end.model.Cliente;
import com.bank.back_end.model.Cuenta_Bancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface Cuenta_BancariaRepository extends JpaRepository<Cuenta_Bancaria,Integer> {
    @Query("SELECT c FROM Cuenta_Bancaria c WHERE c.numero_cuenta = :iidentifiBank_param")
    Optional<Cuenta_Bancaria> findCuentaByIdentificador(@Param("iidentifiBank_param")String identificadorBancario);

    @Query("SELECT c FROM Cuenta_Bancaria c WHERE c.cliente.id_cliente = :iideCliente AND c.is_activa = true")
    List<Cuenta_Bancaria> findCuentasByIdCliente(@Param("iideCliente")int id_cliente);

    @Query("SELECT c FROM Cuenta_Bancaria c WHERE c.numero_cuenta = :iidentifiBank_param")
    Optional<Cuenta_Bancaria> existCuentaByIdentificador(@Param("iidentifiBank_param")String identificadorBancario);

    @Query("SELECT SUM(c.saldo) FROM Cuenta_Bancaria c where c.cliente.id_cliente = :iideCliente")
    Optional<BigDecimal> getTotalSaldoCuentasCliente(@Param("iideCliente")int idCliente);

}
