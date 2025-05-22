package com.arturfrimu.nomenclatures.first;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nomenclatoare")
public class NomenclatureController {

    private final NomenclatureService nomenclatureService;

    @GetMapping
    public ResponseEntity<List<NomenclatureServiceImpl.Nomenclature>> fetch() {
        return ResponseEntity.ok(nomenclatureService.getNomenclatures());
    }

    @PostMapping
    public ResponseEntity<List<Map<String, Object>>> fetch(@Valid @RequestBody NomenclatureRequest request) {
        return ResponseEntity.ok(
                nomenclatureService.getData(
                        request.getTableName(),
                        request.getFields()
                )
        );
    }
}
