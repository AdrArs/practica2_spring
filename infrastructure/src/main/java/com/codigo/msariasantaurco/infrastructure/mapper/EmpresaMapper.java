package com.codigo.msariasantaurco.infrastructure.mapper;

import com.codigo.msariasantaurco.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msariasantaurco.infrastructure.entity.EmpresaEntity;
import org.springframework.stereotype.Service;

@Service
public class EmpresaMapper {
    public static EmpresaDTO fromEntityToDTO(EmpresaEntity empresa) {
        EmpresaDTO dto = new EmpresaDTO();

        dto.setId(empresa.getId());
        dto.setRazonSocial(empresa.getRazonSocial());
        dto.setTipoDocumento(empresa.getTipoDocumento());
        dto.setNumeroDocumento(empresa.getNumeroDocumento());
        dto.setEstado(empresa.getEstado());
        dto.setCondicion(empresa.getCondicion());
        dto.setDireccion(empresa.getDireccion());
        dto.setDistrito(empresa.getDistrito());
        dto.setProvincia(empresa.getProvincia());
        dto.setDepartamento(empresa.getDepartamento());
        dto.setEsAgenteRetencion(empresa.getEsAgenteRetencion());
        dto.setUsuaCrea(empresa.getUsuaCrea());
        dto.setDateCreate(empresa.getDateCreate());
        dto.setUsuaModif(empresa.getUsuaModif());
        dto.setDateModif(empresa.getDateModif());
        dto.setUsuaDelet(empresa.getUsuaDelet());
        dto.setDateDelet(empresa.getDateDelet());
        return dto;
    }
}