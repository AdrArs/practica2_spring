package com.codigo.msariasantaurco.domain.ports.in;

import com.codigo.msariasantaurco.domain.aggregates.dto.PersonaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.PersonaRequest;

import java.util.List;
import java.util.Optional;

public interface PersonaServiceIn {
    PersonaDTO crearPersonaIn(PersonaRequest personaRequest);
    Optional<PersonaDTO> buscarxIdIn(Long id);
    List<PersonaDTO> buscarTodosIn();
    PersonaDTO actualizarIn(Long id, PersonaRequest personaRequest);
    PersonaDTO eliminarIn(Long id);
}
