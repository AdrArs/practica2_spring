package com.codigo.msariasantaurco.application.controller;

import com.codigo.msariasantaurco.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.EmpresaRequest;
import com.codigo.msariasantaurco.domain.ports.in.EmpresaServiceIn;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ms-arias-antaurco/v1/empresa")
@AllArgsConstructor
public class EmpresaController {
    private EmpresaServiceIn empresaServiceIn;

    @PostMapping
    private ResponseEntity<EmpresaDTO> crearEmpresa(@RequestBody EmpresaRequest empresaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.crearEmpresaIn(empresaRequest));
    }

    @GetMapping("/{id}")
    private ResponseEntity<EmpresaDTO> buscarxId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.buscarxIdIn(id).get());
    }

    @GetMapping("/todos")
    private ResponseEntity<List<EmpresaDTO>> buscartodos() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.buscarTodosIn());
    }

    @PutMapping("/{id}")
    private ResponseEntity<EmpresaDTO> actualizar(@PathVariable Long id,
                                                  @RequestBody EmpresaRequest empresaRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.actualizarIn(id, empresaRequest));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<EmpresaDTO> eliminar(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaServiceIn.eliminarIn(id));
    }
}