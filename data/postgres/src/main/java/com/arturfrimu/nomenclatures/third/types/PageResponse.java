package com.arturfrimu.nomenclatures.third.types;

import lombok.val;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(int pageNumber, int pageSize, long totalElements, List<T> content) {
    public static <T> PageResponse<T> of(Page<T> page, List<T> elements) {
        val pageNumber = page.getNumber();
        val pageSize = page.getSize();
        val totalElements = page.getTotalElements();
        return new PageResponse<T>(pageNumber, pageSize, totalElements, elements);
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        val pageNumber = page.getNumber();
        val pageSize = page.getSize();
        val totalElements = page.getTotalElements();
        return new PageResponse<T>(pageNumber, pageSize, totalElements, page.getContent());
    }
}
