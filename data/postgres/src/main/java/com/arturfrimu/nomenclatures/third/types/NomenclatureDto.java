package com.arturfrimu.nomenclatures.third.types;

import com.arturfrimu.nomenclatures.third.dto.ArticleView;
import com.arturfrimu.nomenclatures.third.dto.UserView;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserView.class, name = "USERS"),
        @JsonSubTypes.Type(value = ArticleView.class, name = "ARTICLES"),
})
public interface NomenclatureDto {

    NomenclatureType getType();
}
