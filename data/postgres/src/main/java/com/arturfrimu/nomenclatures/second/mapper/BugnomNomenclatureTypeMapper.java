package com.arturfrimu.nomenclatures.second.mapper;

import com.arturfrimu.nomenclatures.second.dto.BugnomNomenclatureTypeDto;
import com.arturfrimu.nomenclatures.second.entity.BugnomNomenclatureTypeEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface BugnomNomenclatureTypeMapper extends GenericMapper<BugnomNomenclatureTypeEntity, BugnomNomenclatureTypeDto> {
}
