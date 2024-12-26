package edu.janeforjane.cache.service;

import edu.janeforjane.cache.datasource.MyDataSource;
import edu.janeforjane.cache.datasource.MyFileDataSourceImpl;
import edu.janeforjane.cache.exceptions.CacheDirectoryException;
import edu.janeforjane.cache.exceptions.FileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    private MyDataSource dataSource;
    private CacheService cacheService;


    public FileService(String cacheDirectory) throws CacheDirectoryException {
        if(!isDirectoryAndExist(cacheDirectory)){
            throw new CacheDirectoryException("Such directory doesn't exist");
        }
        this.dataSource = new MyFileDataSourceImpl(cacheDirectory);
        this.cacheService = new CacheService(dataSource);
    }

    public byte[] getData(String key) throws FileException {
        return cacheService.getData(key).getBytes();

    }

    private boolean isDirectoryAndExist(String cacheDirectory){
        Path path = Paths.get(cacheDirectory);
        return Files.exists(path) && Files.isDirectory(path);
    }


}
