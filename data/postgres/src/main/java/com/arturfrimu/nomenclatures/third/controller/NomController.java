package com.arturfrimu.nomenclatures.third.controller;

import com.arturfrimu.nomenclatures.third.service.CrudNomenclatureService;
import com.arturfrimu.nomenclatures.third.types.NomenclatureDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@JBossLog
@RestController
@RequestMapping("/nomenclature")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NomController {

    CrudNomenclatureService crudNomenclatureService;

    @PostMapping
    public ResponseEntity<NomenclatureDto> create(
            @RequestBody NomenclatureDto dto
    ) {
        NomenclatureDto created = crudNomenclatureService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}
