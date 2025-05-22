package com.arturfrimu.nomenclatures.second.controller;

import com.arturfrimu.nomenclatures.second.dto.BugnomNomenclatureTypeDto;
import com.arturfrimu.nomenclatures.second.service.BugnomNomenclatureTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bugnom-nomenclature-types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BugnomNomenclatureTypeController {

    BugnomNomenclatureTypeService bugnomNomenclatureTypeService;

//    @GetMapping
//    public List<BugnomNomenclatureTypeDto> list() {
//        return bugnomNomenclatureTypeService.list();
//    }

    @GetMapping
    public List<BugnomNomenclatureTypeDto> list() {
        return bugnomNomenclatureTypeService.list();
    }
}
