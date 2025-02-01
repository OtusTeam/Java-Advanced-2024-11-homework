package ru.otus.hw.task05.provider;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class DataProvider {

    public String generateAdditionalText() {
        return RandomStringUtils.insecure().nextAlphabetic(10);
    }
}
