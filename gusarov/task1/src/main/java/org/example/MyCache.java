package org.example;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class MyCache {
    public MyCache() {
    }

    HashMap<String, SoftReference<String>> map  = new HashMap<>();

    public String get(String key){
        String res = null;
        SoftReference<String> stringSoftReference = map.get(key);
        if(stringSoftReference!= null)
        {
            String s = stringSoftReference.get();
            if(s!= null) {
                res = s;
            }
        }
        return res;
    }

    public void put(String key, String data){
        SoftReference<String> dataRef = new SoftReference<>(data);
        map.put(key, dataRef);
    }
}
