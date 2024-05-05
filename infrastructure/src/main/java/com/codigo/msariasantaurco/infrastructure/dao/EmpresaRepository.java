package com.codigo.msariasantaurco.infrastructure.dao;

import com.codigo.msariasantaurco.infrastructure.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Long> {
    EmpresaEntity findByNumeroDocumento(String numeroDocumento);
}
