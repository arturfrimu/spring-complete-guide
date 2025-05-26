package com.arturfrimu.nomenclatures.third.mapper;

import com.arturfrimu.nomenclatures.third.types.INomenclatureType;
import com.arturfrimu.nomenclatures.third.entity.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.dto.NomenclatureView;

public interface NomenclatureMapper<T extends NomenclatureEntity, D extends NomenclatureView> extends INomenclatureType {
    D toDto(T entity);

    T toEntity(D dto);
}
