package com.arturfrimu.nomenclatures.third.mapper;

import com.arturfrimu.nomenclatures.third.dto.ArticleView;
import com.arturfrimu.nomenclatures.third.entity.ArticleEntity;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends NomenclatureMapper<ArticleEntity, ArticleView> {

    @Override
    default NomenclatureType getType() {
        return NomenclatureType.ARTICLES;
    }
}
