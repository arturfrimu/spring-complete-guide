package com.arturfrimu.nomenclatures.first;


import java.util.List;
import java.util.Map;

public interface NomenclatureService {
    List<NomenclatureServiceImpl.Nomenclature> getNomenclatures();

    /**
     * Returnează lista de rânduri, fiecare rând fiind map<columnName,value>,
     * din tabela specificată și doar cu coloanele indicate.
     */
    List<Map<String, Object>> getData(String tableName, List<String> fields);
}
