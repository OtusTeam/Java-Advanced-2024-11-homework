package ru.otus.hw.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.*;

public class CacheControl {
    private final Cache<String, String> cache = new Cache<>(10);
    public void loadCache(String dir) throws IOException{

        try (Stream<Path> paths = Files.walk(Paths.get(dir))) {
            paths.filter(Files::isRegularFile).forEach(
                path ->{
                    try {
                        FileReader fr = new FileReader(path.toFile());
                        BufferedReader br = new BufferedReader(fr);
                        StringBuilder s = new StringBuilder();
                        while (br.ready()) {
                            s.append(br.readLine()).append("\n");
                        }
                        cache.put(path.getFileName().toFile().getName(),s.toString());
                    } catch( IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            );
        }
    }
    public String get(String fileName){
        return cache.get(fileName);
    }
}
