package com.codigo.msariasantaurco.domain.ports.out;

import com.codigo.msariasantaurco.domain.aggregates.dto.PersonaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceOut {
    PersonaDTO crearPersonaOut(PersonaRequest personaRequest);
    Optional<PersonaDTO> buscarxIdOut(Long id);
    List<PersonaDTO> buscarTodosOut();
    PersonaDTO actualizarOut(Long id, PersonaRequest personaRequest);
    PersonaDTO eliminarOut(Long id);
}
