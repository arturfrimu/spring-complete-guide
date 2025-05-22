package com.arturfrimu.nomenclatures.first;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class NomenclatureRequest {

    @NotBlank
    private String tableName;

    @NotEmpty
    private List<@NotBlank String> fields;

    // getters & setters (sau @Data cu Lombok)
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public List<String> getFields() { return fields; }
    public void setFields(List<String> fields) { this.fields = fields; }
}

