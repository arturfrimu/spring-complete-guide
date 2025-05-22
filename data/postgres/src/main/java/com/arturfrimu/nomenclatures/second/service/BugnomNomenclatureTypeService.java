package com.arturfrimu.nomenclatures.second.service;


import com.arturfrimu.nomenclatures.second.dto.BugnomNomenclatureTypeDto;
import com.arturfrimu.nomenclatures.second.mapper.BugnomNomenclatureTypeMapper;
import com.arturfrimu.nomenclatures.second.repository.BugnomNomenclatureTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BugnomNomenclatureTypeService {

    BugnomNomenclatureTypeMapper bugnomNomenclatureTypeMapper;
    BugnomNomenclatureTypeRepository bugnomNomenclatureTypeRepository;

    public List<BugnomNomenclatureTypeDto> list() {
        return bugnomNomenclatureTypeRepository.findAll()
                .stream()
                .map(bugnomNomenclatureTypeMapper::toDto)
                .toList();
    }
}
