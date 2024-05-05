package com.codigo.msariasantaurco.infrastructure.dao;

import com.codigo.msariasantaurco.infrastructure.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {

}
