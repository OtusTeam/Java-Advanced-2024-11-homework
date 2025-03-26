package org.example;

import java.io.File;

public class Util {
    public static void checkSize(String path, int bufferSize) {
        if(path==null || path.isEmpty()) {
            throw new IllegalArgumentException("Path is null or empty");
        }
        File f = new File(path);
        if(!f.exists()) {
            throw new IllegalArgumentException("File not exists");
        }
        if (f.isDirectory()) {
            throw new IllegalArgumentException("Path is a directory");
        }
        if(!f.canRead()){
            throw new IllegalArgumentException("Path is not readable");
        }
        if(f.length() > bufferSize){
            throw new IllegalArgumentException("File length > bufferSize");
        }
    }
}
