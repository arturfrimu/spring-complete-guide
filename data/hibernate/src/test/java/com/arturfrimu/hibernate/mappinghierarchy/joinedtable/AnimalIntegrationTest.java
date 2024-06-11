package com.arturfrimu.hibernate.mappinghierarchy.joinedtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalIntegrationTest {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private WildAnimalRepository wildAnimalRepository;

    @Test
    void test() {
        savePet();
        saveWildAnimal();
    }

    void savePet() {
        Pet pet = Pet.builder()
//                .animalId(getRandomLong(9))
                .name("Pet Name - " + RandomStringUtils.randomAlphabetic(10))
                .species("Pet Specie - " + RandomStringUtils.randomAlphabetic(10))
                .build();
        petRepository.save(pet);
    }

    void saveWildAnimal() {
        WildAnimal book = WildAnimal.builder()
//                .animalId(getRandomLong(9))
                .habitat("WildAnimal Habitat - " + RandomStringUtils.randomAlphabetic(10))
                .species("WildAnimal Specie - " + RandomStringUtils.randomAlphabetic(10))
                .build();
        wildAnimalRepository.save(book);
    }

    private static long getRandomLong(final int count) {
        return Long.parseLong(RandomStringUtils.randomNumeric(count));
    }
}