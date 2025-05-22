package com.arturfrimu.nomenclatures.third.dto;

import com.arturfrimu.nomenclatures.third.types.NomenclatureDto;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ArticleView implements NomenclatureDto {
    private Long id;
    private String title;

    @Override
    public NomenclatureType getType() {
        return NomenclatureType.ARTICLES;
    }
}
