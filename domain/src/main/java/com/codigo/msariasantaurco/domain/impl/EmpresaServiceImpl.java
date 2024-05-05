package com.codigo.msariasantaurco.domain.impl;

import com.codigo.msariasantaurco.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.EmpresaRequest;
import com.codigo.msariasantaurco.domain.ports.in.EmpresaServiceIn;
import com.codigo.msariasantaurco.domain.ports.out.EmpresaServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaServiceIn {

    private EmpresaServiceOut empresaServiceOut;

    @Override
    public EmpresaDTO crearEmpresaIn(EmpresaRequest empresaRequest) {
        return empresaServiceOut.crearEmpresaOut(empresaRequest);
    }

    @Override
    public Optional<EmpresaDTO> buscarxIdIn(Long id) {
        return empresaServiceOut.buscarxIdOut(id);
    }

    @Override
    public List<EmpresaDTO> buscarTodosIn() {
        return empresaServiceOut.buscarTodosOut();
    }

    @Override
    public EmpresaDTO actualizarIn(Long id, EmpresaRequest empresaRequest) {
        return empresaServiceOut.actualizarOut(id, empresaRequest);
    }

    @Override
    public EmpresaDTO eliminarIn(Long id) {
        return empresaServiceOut.eliminarOut(id);
    }
}
