package com.codigo.msariasantaurco.infrastructure.adapter;

import com.codigo.msariasantaurco.domain.aggregates.constants.Constant;
import com.codigo.msariasantaurco.domain.aggregates.dto.PersonaDTO;
import com.codigo.msariasantaurco.domain.aggregates.dto.ReniecDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.PersonaRequest;
import com.codigo.msariasantaurco.domain.ports.out.PersonaServiceOut;
import com.codigo.msariasantaurco.infrastructure.client.ClientReniec;
import com.codigo.msariasantaurco.infrastructure.dao.EmpresaRepository;
import com.codigo.msariasantaurco.infrastructure.dao.PersonaRepository;
import com.codigo.msariasantaurco.infrastructure.entity.PersonaEntity;
import com.codigo.msariasantaurco.infrastructure.mapper.PersonaMapper;
import com.codigo.msariasantaurco.infrastructure.redis.RedisService;
import com.codigo.msariasantaurco.infrastructure.util.Util;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaAdapter implements PersonaServiceOut {
    private final RedisService redisService;
    private final PersonaRepository personaRepository;
    private final ClientReniec clientReniec;
    private final EmpresaRepository empresaRepository;

    @Getter @Setter
    private Long currentCreateId;
    @Getter @Setter
    private Timestamp currentDateCreate;
    @Getter @Setter
    private String currentUsuaCreate;

    @Value("${token.apis.net}")
    private String tokenReniec;

    @Override
    public PersonaDTO crearPersonaOut(PersonaRequest personaRequest) {
        PersonaEntity personaEntity = getEntity(personaRequest, Constant.CREATE);
        return PersonaMapper.fromEntityToDto(personaRepository.save(personaEntity));
    }

    private PersonaEntity getEntity(PersonaRequest personaRequest, String action) {
        ReniecDTO dto = getFromReniec(personaRequest.getNumDoc());
        PersonaEntity entity = new PersonaEntity();
        entity.setNombre(dto.getNombres());
        entity.setApellido(String.format("%s %s", dto.getApellidoPaterno(), dto.getApellidoMaterno()));
        entity.setTipoDocumento(Constant.TIPO_DOCUMENTO_PERSONA);
        entity.setNumeroDocumento(dto.getNumeroDocumento());
        entity.setEstado(Constant.STATUS_ACTIVE);
        entity.setEmpresaId( empresaRepository.findByNumeroDocumento(personaRequest.getEmpresa()));
        entity.setEmail(generarEmail(dto));

        switch (action) {
            case "CREATE" -> {
                entity.setUsuaCrea(Constant.USU_ADMIN);
                entity.setDateCreate(getTimeStamp());
            }
            case "UPDATE" -> {
                entity.setId(getCurrentCreateId());
                entity.setUsuaModif(Constant.USU_ADMIN);
                entity.setDateModif(getTimeStamp());
                entity.setUsuaCrea(getCurrentUsuaCreate());
                entity.setDateCreate(getCurrentDateCreate());
            }
        }

        return entity;
    }

    private ReniecDTO getFromReniec(String numDocumento) {
        String authorization = String.format("Bearer %s", tokenReniec);
        return clientReniec.getInfoReniec(numDocumento, authorization);
    }

    private Timestamp getTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    // colocar de acuerdo a la empresa
    private String generarEmail(ReniecDTO persona) {
        String[] nombres = persona.getNombres().split(" ");
        String nombre = nombres[0].toLowerCase().substring(0, 3);
        String apellido = persona.getApellidoPaterno().toLowerCase().substring(0, 3);

        return String.format("%s.%s@gmail.com",nombre, apellido);
    }

    @Override
    public Optional<PersonaDTO> buscarxIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENERPERSONA+id);
        if (redisInfo != null) {
            PersonaDTO personaDTO = Util.stringToObject(redisInfo, PersonaDTO.class);
            return Optional.of(personaDTO);
        }
        else {
            PersonaDTO personaDTO = PersonaMapper.fromEntityToDto(personaRepository.findById(id).get());
            String dataToRedis = Util.dtoToString(personaDTO);
            redisService.saveInRedis(Constant.REDIS_KEY_OBTENERPERSONA+id, dataToRedis, 10);
            return Optional.of(personaDTO);
        }
    }

    @Override
    public List<PersonaDTO> buscarTodosOut() {
        List<PersonaEntity> entityList = personaRepository.findAll();
        return entityList.stream()
                .map(PersonaMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public PersonaDTO actualizarOut(Long id, PersonaRequest personaRequest) {
        Optional<PersonaEntity> old = personaRepository.findById(id);

        if (old.isPresent()) {
            setCurrentCreateId(old.get().getId());
            setCurrentUsuaCreate(old.get().getUsuaCrea());
            setCurrentDateCreate(old.get().getDateCreate());
            PersonaEntity entity = getEntity(personaRequest, Constant.UPDATE);
            return PersonaMapper.fromEntityToDto(personaRepository.save(entity));
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public PersonaDTO eliminarOut(Long id) {
        Optional<PersonaEntity> old = personaRepository.findById(id);
        if (old.isPresent()) {
            old.get().setEstado(Constant.STATUS_INACTIVE);
            old.get().setUsuaDelet(Constant.USU_ADMIN);
            old.get().setDateDelet(getTimeStamp());
        return PersonaMapper.fromEntityToDto(personaRepository.save(old.get()));
    }
    else {
        throw new RuntimeException();
        }
    }
}
