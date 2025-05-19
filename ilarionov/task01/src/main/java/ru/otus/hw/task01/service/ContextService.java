package ru.otus.hw.task01.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.task01.dao.ContextDao;

import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ContextService {

    private final ContextDao contextDao;

    public void setPath(String uri) {
        var path = Paths.get(uri);
        contextDao.setCatalog(path);
    }
}
