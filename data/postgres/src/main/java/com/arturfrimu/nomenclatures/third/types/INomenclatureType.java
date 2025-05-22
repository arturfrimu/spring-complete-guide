package com.arturfrimu.nomenclatures.third.types;

public interface INomenclatureType {

    NomenclatureType getType();

    default boolean isFit(NomenclatureType type) {
        return getType() == type;
    }
}
