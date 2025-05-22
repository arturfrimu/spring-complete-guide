package com.arturfrimu.nomenclatures.third.repository;

import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.entity.ArticleEntity;

public interface ArticleEntityRepository extends NomenclatureRepository<ArticleEntity, Long> {

    @Override
    default NomenclatureType getType() {
        return NomenclatureType.ARTICLES;
    }
}