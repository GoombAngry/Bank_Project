package com.bank.back_end.repository;

import com.bank.back_end.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    @Query("SELECT c FROM Cliente c WHERE c.correo_electronico = :correo_param")
    Optional<Cliente> findByCorreoElectronico(@Param("correo_param")String email);
    @Query("SELECT c FROM Cliente c WHERE c.dni_cliente = :dinii_param")
    Optional<Cliente> findUserByDni(@Param("dinii_param")String dni_param);
    @Query("SELECT c FROM Cliente c WHERE c.correo_electronico = :imeil_param")
    Optional<Cliente> findUserByEmail(@Param("imeil_param")String emeil_param);
}
