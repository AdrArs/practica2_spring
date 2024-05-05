package com.codigo.msariasantaurco.infrastructure.mapper;

import com.codigo.msariasantaurco.domain.aggregates.constants.Constant;
import com.codigo.msariasantaurco.domain.aggregates.dto.PersonaDTO;
import com.codigo.msariasantaurco.infrastructure.entity.PersonaEntity;
import org.springframework.stereotype.Service;

@Service
public class PersonaMapper {
    public static PersonaDTO fromEntityToDto(PersonaEntity personaEntity) {
        PersonaDTO dto = new PersonaDTO();

        dto.setId(personaEntity.getId());
        dto.setNombre(personaEntity.getNombre());
        dto.setApellido(personaEntity.getApellido());
        dto.setTipoDocumento(personaEntity.getTipoDocumento());
        dto.setNumeroDocumento(personaEntity.getNumeroDocumento());
        dto.setEmail(personaEntity.getEmail());
        dto.setTelefono(personaEntity.getTelefono());
        dto.setDireccion(personaEntity.getDireccion());
        dto.setEmpresaId(EmpresaMapper.fromEntityToDTO(personaEntity.getEmpresaId()));
        dto.setEstado(Constant.STATUS_ACTIVE);
        dto.setUsuaCrea(personaEntity.getUsuaCrea());
        dto.setDateCreate(personaEntity.getDateCreate());
        dto.setUsuaModif(personaEntity.getUsuaModif());
        dto.setDateModif(personaEntity.getDateModif());
        dto.setUsuaDelet(personaEntity.getUsuaDelet());
        dto.setDateDelet(personaEntity.getDateDelet());
        return dto;
    }
}
