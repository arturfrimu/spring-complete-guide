package com.arturfrimu.nomenclatures.third;

import com.arturfrimu.nomenclatures.third.dto.UserView;
import com.arturfrimu.nomenclatures.third.service.CrudNomenclatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CrudNomenclatureEntityServiceTest {

    @Autowired
    private CrudNomenclatureService crudNomenclatureService;

    @Test
    void test() {
        UserView createdUser = crudNomenclatureService.create(new UserView(1L, "John", "Doe"));
        assertThat(createdUser.getName()).isEqualTo("John");
        assertThat(createdUser.getSurname()).isEqualTo("Doe");
    }

}