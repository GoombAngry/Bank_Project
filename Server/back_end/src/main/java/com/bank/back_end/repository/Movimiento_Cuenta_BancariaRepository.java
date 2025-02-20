package com.bank.back_end.repository;

import com.bank.back_end.model.Cuenta_Bancaria;
import com.bank.back_end.model.Movimiento_Cuenta_Bancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface Movimiento_Cuenta_BancariaRepository extends JpaRepository<Movimiento_Cuenta_Bancaria,Integer> {
    @Query("SELECT m FROM Movimiento_Cuenta_Bancaria m WHERE m.cuentaBancaria.id_cuenta_bancaria = :iideCuentaBancaria")
    List<Movimiento_Cuenta_Bancaria> findMovimientosCuentaByIdCuentaBancaria(@Param("iideCuentaBancaria")int id_cuentaBancaria);
    // Esta consulta utiliza funciones del motor MYSQL (YEAR y MONTH)
    @Query("SELECT m FROM Movimiento_Cuenta_Bancaria m WHERE m.cuentaBancaria.id_cuenta_bancaria = :iideCuentaBancaria AND YEAR(m.fecha_movimiento) = :year AND MONTH(m.fecha_movimiento) = :month")
    List<Movimiento_Cuenta_Bancaria> getResumenMensual(@Param("iideCuentaBancaria") int id, @Param("year") int year, @Param("month") int month);
}
