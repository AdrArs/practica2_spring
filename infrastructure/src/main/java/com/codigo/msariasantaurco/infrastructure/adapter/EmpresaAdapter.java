package com.codigo.msariasantaurco.infrastructure.adapter;

import com.codigo.msariasantaurco.domain.aggregates.constants.Constant;
import com.codigo.msariasantaurco.domain.aggregates.dto.EmpresaDTO;
import com.codigo.msariasantaurco.domain.aggregates.dto.SunatDTO;
import com.codigo.msariasantaurco.domain.aggregates.request.EmpresaRequest;
import com.codigo.msariasantaurco.domain.ports.out.EmpresaServiceOut;
import com.codigo.msariasantaurco.infrastructure.client.ClientSunat;
import com.codigo.msariasantaurco.infrastructure.dao.EmpresaRepository;
import com.codigo.msariasantaurco.infrastructure.entity.EmpresaEntity;
import com.codigo.msariasantaurco.infrastructure.mapper.EmpresaMapper;
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
public class EmpresaAdapter implements EmpresaServiceOut {
    private final EmpresaRepository empresaRepository;
    private final ClientSunat clientSunat;
    private final RedisService redisService;

    @Getter @Setter
    private Long currentCreateId;
    @Getter @Setter
    private Timestamp currentDateCreate;
    @Getter @Setter
    private String currentUsuaCreate;

    @Value("${token.apis.net}")
    private String tokenSunat;

    @Override
    public EmpresaDTO crearEmpresaOut(EmpresaRequest empresaRequest) {
        EmpresaEntity empresaEntity = getEntity(empresaRequest, Constant.CREATE);
        return EmpresaMapper.fromEntityToDTO(empresaRepository.save(empresaEntity));
    }
    private EmpresaEntity getEntity(EmpresaRequest empresaRequest, String action) {
        SunatDTO sunatDTO = getExecSunat(empresaRequest.getNumDoc());
        EmpresaEntity empresa = new EmpresaEntity();

        empresa.setRazonSocial(sunatDTO.getRazonSocial());
        empresa.setTipoDocumento(Constant.TIPO_DOCUMENTO_EMPRESA);
        empresa.setNumeroDocumento(sunatDTO.getNumeroDocumento());
        empresa.setCondicion(sunatDTO.getCondicion());
        empresa.setDireccion(sunatDTO.getDireccion());
        empresa.setDistrito(sunatDTO.getDistrito());
        empresa.setProvincia(sunatDTO.getProvincia());
        empresa.setEstado(Constant.STATUS_ACTIVE);
        empresa.setDepartamento(sunatDTO.getDepartamento());
        empresa.setEsAgenteRetencion(sunatDTO.isEsAgenteRetencion());

        switch (action) {
            case "CREATE" -> {
                empresa.setUsuaCrea(Constant.USU_ADMIN);
                empresa.setDateCreate(getTimeStamp());
            }
            case "UPDATE" -> {
                empresa.setId(getCurrentCreateId());
                empresa.setUsuaModif(Constant.USU_ADMIN);
                empresa.setDateModif(getTimeStamp());
                empresa.setUsuaCrea(getCurrentUsuaCreate());
                empresa.setDateCreate(getCurrentDateCreate());
            }
        }

        return empresa;
    }

    private SunatDTO getExecSunat(String numeroDocumento) {
        String authorization = String.format("Bearer %s", tokenSunat);
        return clientSunat.getInfoSunat(numeroDocumento, authorization);
    }

    private Timestamp getTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    public Optional<EmpresaDTO> buscarxIdOut(Long id) {
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_OBTENEREMPRESA+id);
        if (redisInfo != null) {
            EmpresaDTO empresaDTO = Util.stringToObject(redisInfo, EmpresaDTO.class);
            return Optional.of(empresaDTO);
        }
        else {
            EmpresaDTO empresaDTO = EmpresaMapper.fromEntityToDTO(empresaRepository.findById(id).get());
            String dataToRedis = Util.dtoToString(empresaDTO);
            redisService.saveInRedis(Constant.REDIS_KEY_OBTENEREMPRESA+id, dataToRedis, 10);
            return Optional.of(empresaDTO);
        }
    }

    @Override
    public List<EmpresaDTO> buscarTodosOut() {
        //List<EmpresaDTO> dtoList = new ArrayList<>();
        List<EmpresaEntity> entityList = empresaRepository.findAll();
        return entityList.stream()
                .map(EmpresaMapper::fromEntityToDTO)
                .toList();
    }

    @Override
    public EmpresaDTO actualizarOut(Long id, EmpresaRequest empresaRequest) {
        Optional<EmpresaEntity> old = empresaRepository.findById(id);

        if (old.isPresent()) {
            setCurrentCreateId(old.get().getId());
            setCurrentUsuaCreate(old.get().getUsuaCrea());
            setCurrentDateCreate(old.get().getDateCreate());
            EmpresaEntity entity = getEntity(empresaRequest, Constant.UPDATE);
            return EmpresaMapper.fromEntityToDTO(empresaRepository.save(entity));
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public EmpresaDTO eliminarOut(Long id) {
        Optional<EmpresaEntity> old = empresaRepository.findById(id);
        if (old.isPresent()) {
            old.get().setEstado(Constant.STATUS_INACTIVE);
            old.get().setUsuaDelet(Constant.USU_ADMIN);
            old.get().setDateDelet(getTimeStamp());
            return EmpresaMapper.fromEntityToDTO(empresaRepository.save(old.get()));
        }
        else {
            throw new RuntimeException();
        }
    }
}