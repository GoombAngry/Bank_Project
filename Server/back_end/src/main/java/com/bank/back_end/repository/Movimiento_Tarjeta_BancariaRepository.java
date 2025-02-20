package com.bank.back_end.repository;

import com.bank.back_end.model.Movimiento_Cuenta_Bancaria;
import com.bank.back_end.model.Movimiento_Tarjeta_Bancaria;
import com.bank.back_end.model.Tarjeta_Bancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Movimiento_Tarjeta_BancariaRepository extends JpaRepository<Movimiento_Tarjeta_Bancaria,Integer> {
    @Query("SELECT m FROM Movimiento_Tarjeta_Bancaria m WHERE m.tarjetaBancaria.id_tarjeta_bancaria = :idiTaljeta ")
    List<Movimiento_Tarjeta_Bancaria> findMovimientosTarjetaBancariaByIdTarjeta(@Param("idiTaljeta")int id_tarjetaBancaria);
    // Esta consulta utiliza funciones del motor MYSQL (YEAR y MONTH)
    @Query("SELECT m FROM Movimiento_Tarjeta_Bancaria m WHERE m.tarjetaBancaria.id_tarjeta_bancaria = :iideTarjetaBancaria AND YEAR(m.fecha_movimiento) = :year AND MONTH(m.fecha_movimiento) = :month")
    List<Movimiento_Tarjeta_Bancaria> getResumenMensual(@Param("iideTarjetaBancaria") int id, @Param("year") int year, @Param("month") int month);
}
