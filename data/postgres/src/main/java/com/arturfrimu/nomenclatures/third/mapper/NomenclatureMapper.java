package com.arturfrimu.nomenclatures.third.mapper;

import com.arturfrimu.nomenclatures.third.types.INomenclatureType;
import com.arturfrimu.nomenclatures.third.types.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.types.NomenclatureDto;

public interface NomenclatureMapper<T extends NomenclatureEntity, D extends NomenclatureDto> extends INomenclatureType {
    D toDto(T entity);

    T toEntity(D dto);
}
