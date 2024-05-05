package com.codigo.msariasantaurco.domain.ports.out;

import com.codigo.msariasantaurco.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceOut {
    EmpresaDTO crearEmpresaOut(EmpresaRequest empresaRequest);
    Optional<EmpresaDTO> buscarxIdOut(Long id);
    List<EmpresaDTO> buscarTodosOut();
    EmpresaDTO actualizarOut(Long id, EmpresaRequest empresaRequest);
    EmpresaDTO eliminarOut(Long id);
}
