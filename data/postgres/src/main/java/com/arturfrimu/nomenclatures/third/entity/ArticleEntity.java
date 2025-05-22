package com.arturfrimu.nomenclatures.third.entity;

import com.arturfrimu.nomenclatures.third.types.NomenclatureEntity;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "articles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity implements NomenclatureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Override
    public NomenclatureType getType() {
        return NomenclatureType.ARTICLES;
    }
}
