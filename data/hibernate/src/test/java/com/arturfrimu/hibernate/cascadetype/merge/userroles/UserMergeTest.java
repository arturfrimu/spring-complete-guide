package com.arturfrimu.hibernate.cascadetype.merge.userroles;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserMergeTest {

    @Autowired
    private UserMergeRepository userMergeRepository;
    @Autowired
    private RoleMergeRepository roleMergeRepository;
    @Autowired
    private UserRoleMergeRepository userRoleMergeRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    /**
     * This test shows:
     * 1) When saving a new UserMerge (with no cascade=PERSIST), its UserRoleMerge children are NOT auto-persisted.
     * 2) When merging a detached UserMerge that has UserRoleMerge children (cascade=MERGE), those children ARE persisted.
     */
    @Test
    @Transactional
    void givenCascadeMerge_whenPersistingUser_thenChildrenNotSaved_butAfterMerge_thenChildrenAreSaved() {
        // 1) Create two standalone roles
        RoleMerge adminRole = roleMergeRepository.save(new RoleMerge(null, "ADMIN"));
        RoleMerge userRole  = roleMergeRepository.save(new RoleMerge(null, "USER"));

        // 2) Build a brand-new UserMerge with two UserRoleMerge children
        UserMerge user = UserMerge.builder()
                .name("Alice")
                .build();

        UserRoleMerge ur1 = new UserRoleMerge(user, adminRole);
        UserRoleMerge ur2 = new UserRoleMerge(user, userRole);
        user.getUserRoles().add(ur1);
        user.getUserRoles().add(ur2);

        // 3) Persist the user — because we only have cascade=MERGE (no PERSIST), the children are NOT saved
        user = userMergeRepository.saveAndFlush(user);
        Long userId = user.getId();

        // clear persistence context to force reload from DB
        entityManager.clear();

        // reload and assert no children were persisted
        UserMerge reloaded1 = userMergeRepository.findById(userId).orElseThrow();
        assertThat(reloaded1.getUserRoles()).isEmpty();

        // 4) Now simulate a detached update: reattach children to a fresh UserMerge instance
        UserMerge detached = new UserMerge();
        detached.setId(userId);
        detached.setName("Alice");  // name can remain same or be updated

        // re-add the two roles as children
        UserRoleMerge nur1 = new UserRoleMerge(detached, adminRole);
        UserRoleMerge nur2 = new UserRoleMerge(detached, userRole);
        detached.getUserRoles().add(nur1);
        detached.getUserRoles().add(nur2);

        // 5) MERGE the detached graph — cascade=MERGE will persist these new children
        UserMerge merged = entityManager.merge(detached);
        entityManager.flush();
        entityManager.clear();

        // 6) Reload and assert that the two UserRoleMerge entries now exist
        UserMerge reloaded2 = userMergeRepository.findById(userId).orElseThrow();
        assertThat(reloaded2.getUserRoles()).hasSize(2);

        // And each child points back to the same UserMerge
        reloaded2.getUserRoles().forEach(ur -> {
            assertEquals(userId, ur.getUser().getId());
            assertTrue(
                    ur.getRole().getName().equals("ADMIN") ||
                            ur.getRole().getName().equals("USER")
            );
        });
    }

    @Test
    void test() {
        RoleMerge roleMerge = roleMergeRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Role Merge not found"));

        UserMerge user = UserMerge.builder()
                .name("User")
                .build();

        UserRoleMerge userRole = new UserRoleMerge(user, roleMerge);

        user.setUserRoles(Set.of(
                userRole
        ));

        UserMerge userMerge = userService.create(user);

        assertThat(userMerge).isNotNull();
        assertThat(userMerge.getUserRoles()).isNotNull();
        assertThat(userMerge.getUserRoles()).isNotEmpty();
    }
}