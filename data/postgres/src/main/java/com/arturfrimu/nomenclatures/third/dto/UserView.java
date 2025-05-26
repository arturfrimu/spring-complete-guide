package com.arturfrimu.nomenclatures.third.dto;

import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserView implements NomenclatureView {
    private Long id;
    private String name;
    private String surname;

    @Override
    public NomenclatureType getType() {
        return NomenclatureType.USERS;
    }
}
