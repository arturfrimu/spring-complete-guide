package com.arturfrimu.nomenclatures.third;

import com.arturfrimu.nomenclatures.third.dto.UserView;
import com.arturfrimu.nomenclatures.third.service.CrudNomenclatureService;
import com.arturfrimu.nomenclatures.third.types.NomenclatureType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CrudNomenclatureEntityServiceTest {

    @Autowired
    private CrudNomenclatureService crudNomenclatureService;

    @BeforeEach
    public void cleanup() {
        crudNomenclatureService.deleteAll(NomenclatureType.USERS);
    }

    @Test
    void create() {
        UserView createdUser = crudNomenclatureService.create(new UserView(null, "John", "Doe"));
        assertThat(createdUser.getId()).isNotNull().isNotNegative();
        assertThat(createdUser.getName()).isEqualTo("John");
        assertThat(createdUser.getSurname()).isEqualTo("Doe");
    }

    @Test
    void update() {
        // create user
        UserView createdUser = crudNomenclatureService.create(new UserView(null, "John", "Doe"));

        // update user
        UserView updatedUser = crudNomenclatureService.update(new UserView(createdUser.getId(), "Arnold", "Beam"));
        assertThat(updatedUser.getId()).isNotNull().isNotNegative();
        assertThat(updatedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(updatedUser.getName()).isEqualTo("Arnold");
        assertThat(updatedUser.getSurname()).isEqualTo("Beam");
    }

    @Test
    void findById() {
        UserView createdUser = crudNomenclatureService.create(new UserView(null, "John", "Doe"));

        UserView user = crudNomenclatureService.findById(NomenclatureType.USERS, createdUser.getId());
        assertThat(user.getId()).isEqualTo(createdUser.getId());
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getSurname()).isEqualTo("Doe");
    }

    @Test
    void findPage() {
        // create user1
        crudNomenclatureService.create(new UserView(null, "John", "Doe"));
        // create user2
        crudNomenclatureService.create(new UserView(null, "Andrew", "Black"));

        Page<UserView> page1 = crudNomenclatureService.findPage(NomenclatureType.USERS, PageRequest.of(0, 1));
        assertThat(page1.getContent()).isNotNull().isNotEmpty();
        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page1.getTotalElements()).isEqualTo(2);
        assertThat(page1.getContent().getFirst().getId()).isNotNull().isNotNegative();
        assertThat(page1.getContent().getFirst().getName()).isEqualTo("John");
        assertThat(page1.getContent().getFirst().getSurname()).isEqualTo("Doe");

        Page<UserView> page2 = crudNomenclatureService.findPage(NomenclatureType.USERS, PageRequest.of(1, 1));
        assertThat(page2.getContent()).isNotNull().isNotEmpty();
        assertThat(page2.getTotalPages()).isEqualTo(2);
        assertThat(page2.getTotalElements()).isEqualTo(2);
        assertThat(page2.getContent().getFirst().getId()).isNotNull().isNotNegative();
        assertThat(page2.getContent().getFirst().getName()).isEqualTo("Andrew");
        assertThat(page2.getContent().getFirst().getSurname()).isEqualTo("Black");
    }

    @Test
    void delete() {
        UserView createdUser = crudNomenclatureService.create(new UserView(null, "John", "Doe"));

        Page<UserView> page = crudNomenclatureService.findPage(NomenclatureType.USERS, PageRequest.of(0, 1));
        assertThat(page.getContent()).isNotNull().isNotEmpty();
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().getFirst().getId()).isNotNull().isNotNegative();
        assertThat(page.getContent().getFirst().getName()).isEqualTo("John");
        assertThat(page.getContent().getFirst().getSurname()).isEqualTo("Doe");

        crudNomenclatureService.delete(NomenclatureType.USERS, createdUser.getId());

        Page<UserView> pageAfterDelete = crudNomenclatureService.findPage(NomenclatureType.USERS, PageRequest.of(0, 1));
        assertThat(pageAfterDelete.getContent()).isEmpty();
        assertThat(pageAfterDelete.getTotalPages()).isEqualTo(0);
        assertThat(pageAfterDelete.getTotalElements()).isEqualTo(0);
    }

    @Test
    void deleteAll() {
        crudNomenclatureService.create(new UserView(null, "John", "Doe"));
        crudNomenclatureService.create(new UserView(null, "Andrew", "Black"));

        Page<UserView> pageBeforDelete = crudNomenclatureService.findPage(NomenclatureType.USERS, PageRequest.of(0, 1));
        assertThat(pageBeforDelete.getContent()).isNotEmpty();
        assertThat(pageBeforDelete.getTotalPages()).isEqualTo(2);
        assertThat(pageBeforDelete.getTotalElements()).isEqualTo(2);

        crudNomenclatureService.deleteAll(NomenclatureType.USERS);

        Page<UserView> pageAfterDelete = crudNomenclatureService.findPage(NomenclatureType.USERS, PageRequest.of(0, 1));
        assertThat(pageAfterDelete.getContent()).isEmpty();
        assertThat(pageAfterDelete.getTotalPages()).isEqualTo(0);
        assertThat(pageAfterDelete.getTotalElements()).isEqualTo(0);
    }
}