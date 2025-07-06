package ru.otus.client;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class ClientRest {

    public Integer callApi() {
        return ThreadLocalRandom.current().nextInt(0, 101);
    }
}
