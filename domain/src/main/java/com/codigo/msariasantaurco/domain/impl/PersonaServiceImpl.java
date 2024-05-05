package com.codigo.msariasantaurco.domain.impl;

import com.codigo.msariasantaurco.domain.aggregates.dto.PersonaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.PersonaRequest;
import com.codigo.msariasantaurco.domain.ports.in.PersonaServiceIn;
import com.codigo.msariasantaurco.domain.ports.out.PersonaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonaServiceImpl implements PersonaServiceIn {


    private PersonaServiceOut personaServiceOut;

    @Override
    public PersonaDTO crearPersonaIn(PersonaRequest personaRequest) {
        return personaServiceOut.crearPersonaOut(personaRequest);
    }

    @Override
    public Optional<PersonaDTO> buscarxIdIn(Long id) {
        return personaServiceOut.buscarxIdOut(id);
    }

    @Override
    public List<PersonaDTO> buscarTodosIn() {
        return personaServiceOut.buscarTodosOut();
    }

    @Override
    public PersonaDTO actualizarIn(Long id, PersonaRequest personaRequest) {
        return personaServiceOut.actualizarOut(id, personaRequest);
    }

    @Override
    public PersonaDTO eliminarIn(Long id) {
        return personaServiceOut.eliminarOut(id);
    }
}
