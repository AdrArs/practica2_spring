package com.codigo.msariasantaurco.application.controller;

import com.codigo.msariasantaurco.domain.aggregates.dto.PersonaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.PersonaRequest;
import com.codigo.msariasantaurco.domain.ports.in.PersonaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ms-arias-antaurco/v1/persona")
@AllArgsConstructor
public class PersonaController {
    private PersonaServiceIn personaServiceIn;

    @PostMapping
    private ResponseEntity<PersonaDTO> crearPersona(@RequestBody PersonaRequest personaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.crearPersonaIn(personaRequest));
    }

    @GetMapping("/{id}")
    private ResponseEntity<PersonaDTO> buscarxId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.buscarxIdIn(id).get());
    }

    @GetMapping("/todos")
    private ResponseEntity<List<PersonaDTO>> buscartodos() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.buscarTodosIn());
    }

    @PutMapping("/{id}")
    private ResponseEntity<PersonaDTO> actualizar(@PathVariable Long id,
                                                  @RequestBody PersonaRequest personaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.actualizarIn(id, personaRequest));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<PersonaDTO> eliminar(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(personaServiceIn.eliminarIn(id));
    }
}
