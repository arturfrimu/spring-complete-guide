package com.arturfrimu.nomenclatures.second.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BugnomNomenclatureTypeDto {
    private Long id;
    private String code;
    private String description;
    private String entityName;
}
