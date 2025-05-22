package com.arturfrimu.nomenclatures.third.repository;

import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.entity.UserEntity;

public interface UserEntityRepository extends NomenclatureRepository<UserEntity, Long> {

    @Override
    default NomenclatureType getType() {
        return NomenclatureType.USERS;
    }
}