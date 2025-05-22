package com.arturfrimu.nomenclatures.singletable;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductIntegrationTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PenRepository penRepository;

    @Test
    void test() {
        saveBook();
        savePen();
    }

    void saveBook() {
        Book book = Book.builder()
                .productId(getRandomLong(9))
                .name("Book Name - " + RandomStringUtils.randomAlphabetic(10))
                .author("Book Author - " + RandomStringUtils.randomAlphabetic(10))
                .build();
        bookRepository.save(book);
    }

    void savePen() {
        Pen book = Pen.builder()
                .productId(getRandomLong(9))
                .name("Pen Name - " + RandomStringUtils.randomAlphabetic(10))
                .color("Pen Color - " + RandomStringUtils.randomAlphabetic(10))
                .build();
        penRepository.save(book);
    }

    private static long getRandomLong(final int count) {
        return Long.parseLong(RandomStringUtils.randomNumeric(count));
    }
}