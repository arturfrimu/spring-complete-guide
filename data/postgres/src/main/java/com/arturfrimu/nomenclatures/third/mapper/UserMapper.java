package com.arturfrimu.nomenclatures.third.mapper;

import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.dto.UserView;
import com.arturfrimu.nomenclatures.third.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends NomenclatureMapper<UserEntity, UserView> {

    @Override
    default NomenclatureType getType() {
        return NomenclatureType.USERS;
    }
}
