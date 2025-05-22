package com.arturfrimu.nomenclatures.third.entity;

import com.arturfrimu.nomenclatures.third.types.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements NomenclatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @Override
    public NomenclatureType getType() {
        return NomenclatureType.USERS;
    }
}
