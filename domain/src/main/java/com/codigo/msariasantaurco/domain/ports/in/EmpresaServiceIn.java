package com.codigo.msariasantaurco.domain.ports.in;

import com.codigo.msariasantaurco.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.EmpresaRequest;

import java.util.List;
import java.util.Optional;

public interface EmpresaServiceIn {
    EmpresaDTO crearEmpresaIn(EmpresaRequest empresaRequest);
    Optional<EmpresaDTO> buscarxIdIn(Long id);
    List<EmpresaDTO> buscarTodosIn();
    EmpresaDTO actualizarIn(Long id, EmpresaRequest empresaRequest);
    EmpresaDTO eliminarIn(Long id);

}
