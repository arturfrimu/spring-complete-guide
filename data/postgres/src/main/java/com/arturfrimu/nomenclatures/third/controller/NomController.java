package com.arturfrimu.nomenclatures.third.controller;

import com.arturfrimu.nomenclatures.third.service.CrudNomenclatureService;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import com.arturfrimu.nomenclatures.third.types.NomenclatureView;
import com.arturfrimu.nomenclatures.third.types.PageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@JBossLog
@RestController
@RequestMapping("/nomenclature")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NomController {

    CrudNomenclatureService crudNomenclatureService;

    @PostMapping
    public ResponseEntity<NomenclatureView> create(@RequestBody NomenclatureView dto) {
        NomenclatureView created = crudNomenclatureService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping
    public ResponseEntity<NomenclatureView> update(@RequestBody NomenclatureView dto) {
        NomenclatureView updated = crudNomenclatureService.update(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NomenclatureView> findById(@PathVariable Long id, @RequestParam NomenclatureType type) {
        NomenclatureView found = crudNomenclatureService.findById(type, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(found);
    }

    @GetMapping
    public ResponseEntity<PageResponse<NomenclatureView>> findPage(
            @RequestParam NomenclatureType type,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
    ) {
        Page<NomenclatureView> page = crudNomenclatureService.findPage(type, PageRequest.of(pageNumber, pageSize));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PageResponse.of(page));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestParam NomenclatureType type) {
        crudNomenclatureService.delete(type, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(@RequestParam NomenclatureType type) {
        crudNomenclatureService.deleteAll(type);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
