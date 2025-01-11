package edu.janeforjane.cache.datasource;

import edu.janeforjane.cache.entities.CacheData;

import java.io.IOException;
import java.util.List;

public interface MyDataSource {

    CacheData getData(String id) throws IOException;

}
