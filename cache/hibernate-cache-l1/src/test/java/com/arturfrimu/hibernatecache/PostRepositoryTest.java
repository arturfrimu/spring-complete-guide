package com.arturfrimu.hibernatecache;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private EntityManager entityManager;

    @Test
    void L1CacheFirstExample() {
        // 1. Unwrap the Hibernate SessionFactory and enable statistics
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        // 2. Persist & flush: insert into DB, but don't load it yet
        Post post = postRepository.save(
                Post.builder()
                        .name(RandomStringUtils.randomAlphabetic(10))
                        .build()
        );
        entityManager.flush();

        // 3. Clear the persistence context to evict everything from L1 cache
        entityManager.clear();

        // 4. Clear prior statistics so we only measure our two findById calls
        statistics.clear();

        // 5a. First load: should generate exactly one SELECT + one entity load
        Post firstLoad = postRepository.findById(post.getId()).orElseThrow();

        // 5b. Second load: same session, should come from L1 cache → no new SQL, no new load
        Post secondLoad = postRepository.findById(post.getId()).orElseThrow();

        // 6a. Verify Hibernate saw exactly one entity load
        assertEquals(1, statistics.getEntityLoadCount(), "Expected one entity load from DB");

        // 6b. Verify exactly one JDBC prepare (the SELECT)
        assertEquals(1, statistics.getPrepareStatementCount(), "Expected exactly one SQL select");

        // 6c. And the returned instances are identical (L1 cache hit)
        assertSame(firstLoad, secondLoad, "Both loads should return the same Java object");
    }

    @Test
    void L1CacheSecondExample() {
        // 1. Obtain the Hibernate SessionFactory from the EntityManagerFactory
        //    so we can access the built-in statistics API.
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();
        //    Enable collection of statistics (disabled by default).
        statistics.setStatisticsEnabled(true);

        // 2. Persist 10 new Post entities.
        //    They are saved to the database on flush but not yet loaded into the L1 cache.
        IntStream.range(0, 10).forEach(index -> {
            postRepository.save(
                    Post.builder()
                            .name(RandomStringUtils.randomAlphabetic(10))
                            .build()
            );
        });
        //    Force execution of all pending INSERTs.
        entityManager.flush();

        // 3. Evict all managed entities from the persistence context
        //    to clear the first-level cache.
        entityManager.clear();

        // 4. Reset statistics so we only measure the following operations.
        statistics.clear();

        // 5a. Bulk load: fetch all posts in one query.
        //     This should issue exactly one SQL SELECT and increment the entity-load count by 10.
        List<Post> posts = postRepository.findAll();

        // 5b. Verify that exactly 10 entities were loaded and only one statement was prepared.
        assertEquals(10, statistics.getEntityLoadCount(),
                "Expected 10 entity loads from DB after findAll()");
        assertEquals(1, statistics.getPrepareStatementCount(),
                "Expected exactly one SQL SELECT after findAll()");

        // 6. Individual lookups: each findById() should hit the L1 cache,
        //    so neither the entity-load count nor the statement count should increase.
        posts.stream()
                .map(Post::getId)
                .forEach(id -> {
                    postRepository.findById(id);

                    assertEquals(10, statistics.getEntityLoadCount(), "Entity load count should remain unchanged on cache hit");
                    assertEquals(1, statistics.getPrepareStatementCount(), "Statement count should remain unchanged on cache hit");
                });
    }

}
