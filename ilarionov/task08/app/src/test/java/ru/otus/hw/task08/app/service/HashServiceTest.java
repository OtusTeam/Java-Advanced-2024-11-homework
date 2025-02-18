package ru.otus.hw.task08.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.task08.app.model.HashAlgorithm;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HashServiceTest {

    @Autowired
    private HashService hashService;

    @Test
    void cacheServiceShouldSaveUser() {
        byte[] pass = "password123123123".getBytes();
        Arrays.stream(HashAlgorithm.values())
                .forEach(algo -> {
                    String hash = hashService.getHash(pass, algo);
                    assertThat(hash).isNotNull();
                });
    }
}
