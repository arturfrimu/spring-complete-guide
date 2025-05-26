package com.arturfrimu.nomenclatures.third.repository;

import com.arturfrimu.nomenclatures.third.types.INomenclatureType;
import com.arturfrimu.nomenclatures.third.entity.NomenclatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NomenclatureRepository<T extends NomenclatureEntity, ID> extends JpaRepository<T, ID>, INomenclatureType {
}
