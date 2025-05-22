package com.arturfrimu.nomenclatures.first;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NomenclatureServiceImpl implements NomenclatureService {

    private final JdbcTemplate jdbcTemplate;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private static final List<Nomenclature> NOMENCLATORS = List.of(
            new Nomenclature("AUTHORS", List.of("ID", "NAME")),
            new Nomenclature("BOOKS", List.of("ID", "TITLE", "ISBN"))
    );

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Nomenclature {
        private String tableName;
        private List<String> fields;
    }

    @Override
    public List<Nomenclature> getNomenclatures() {
        return List.copyOf(NOMENCLATORS);
    }

    private static final String COLUMN_QUERY = """
            SELECT COLUMN_NAME
            FROM INFORMATION_SCHEMA.COLUMNS
            WHERE TABLE_SCHEMA = SCHEMA()
              AND TABLE_NAME = ?
            """;

    @Override
    public List<Map<String, Object>> getData(String tableName, List<String> requestedColumns) {
        String validatedTable = validateTableName(tableName);
        List<String> availableColumns = fetchTableColumns(validatedTable);
        validateRequestedColumns(validatedTable, requestedColumns, availableColumns);
        String sql = buildSelectQuery(validatedTable, requestedColumns);
        return jdbcTemplate.queryForList(sql);
    }

    private String validateTableName(String name) {
        if (!name.matches("^[A-Za-z0-9_]+$")) {
            throw new BadRequestException("Nume tabel invalid: " + name);
        }
        return name.toUpperCase();
    }

    private List<String> fetchTableColumns(String tableName) {
        return jdbcTemplate.queryForList(COLUMN_QUERY, new Object[]{tableName}, String.class);
    }

    private void validateRequestedColumns(String tableName, List<String> requestedColumns, List<String> availableColumns) {
        List<String> upperRequested = requestedColumns.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        List<String> invalid = upperRequested.stream()
                .filter(column -> !availableColumns.contains(column))
                .collect(Collectors.toList());
        if (!invalid.isEmpty()) {
            throw new BadRequestException("Coloane invalide pentru tabelul " + tableName + ": " + invalid);
        }
    }

    private String buildSelectQuery(String tableName, List<String> columns) {
        String columnList = columns.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
        return String.format("SELECT %s FROM %s", columnList, tableName);
    }
}

