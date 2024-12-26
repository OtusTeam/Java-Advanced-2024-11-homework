package edu.janeforjane.cache.datasource;

import edu.janeforjane.cache.entities.CacheData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;


public class MyFileDataSourceImpl implements MyDataSource {


    private String dirPath;

    public MyFileDataSourceImpl(String dirPath) {
        this.dirPath = dirPath;
    }

    @Override
    public CacheData getData(String key) throws IOException {

        String filePath = dirPath + "/" + key;

        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        return new CacheData(key, fileContent, LocalDateTime.now());
    }

}
