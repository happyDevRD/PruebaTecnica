package com.happydev.accountmovementmanagementservice.repository;


import com.happydev.accountmovementmanagementservice.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);

    List<Movimiento> findByFechaBetween(Date fechaInicio, Date fechaFin);

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, Date fechaInicio, Date fechaFin);

}
