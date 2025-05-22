package com.arturfrimu.nomenclatures.second.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BUGNOM_NOMENCLATOR_TIP", uniqueConstraints = @UniqueConstraint(columnNames = "NNT_COD"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BugnomNomenclatureTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;
    @NotBlank
    @Column(name = "NNT_COD", nullable = false, unique = true)
    private String code;
    @Column(name = "NNT_DESCRIERE")
    private String description;
    @Column(name = "NNT_ENTITATE")
    private String entityName;
}

