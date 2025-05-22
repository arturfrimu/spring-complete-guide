package com.arturfrimu.nomenclatures.third.service;

import com.arturfrimu.nomenclatures.third.types.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.types.NomenclatureDto;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.mapper.NomenclatureMapper;
import com.arturfrimu.nomenclatures.third.repository.NomenclatureRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CrudNomenclatureService {

    List<NomenclatureMapper<?, ?>> mappers;
    List<NomenclatureRepository<?, ?>> repositories;

    public <E extends NomenclatureEntity, D extends NomenclatureDto> D create(D dto) {
        NomenclatureType type = dto.getType();

        NomenclatureMapper<E, D> mapper = findMapper(type);
        NomenclatureRepository<E, ?> repo = findRepository(type);

        E entity = mapper.toEntity(dto);

        E savedEntity = repo.save(entity);

        D response = mapper.toDto(savedEntity);

        return response;
    }

    @SuppressWarnings("unchecked")
    private <E extends NomenclatureEntity> NomenclatureRepository<E, ?> findRepository(NomenclatureType type) {
        return (NomenclatureRepository<E, ?>) repositories.stream()
                .filter(r -> r.isFit(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Repository absent pentru tipul " + type));
    }

    @SuppressWarnings("unchecked")
    private <E extends NomenclatureEntity, D extends NomenclatureDto> NomenclatureMapper<E, D> findMapper(NomenclatureType type) {
        return (NomenclatureMapper<E, D>) mappers.stream()
                .filter(m -> m.isFit(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Mapper absent pentru tipul " + type));
    }
}
