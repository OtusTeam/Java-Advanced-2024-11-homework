package edu.janeforjane.cache.service;

import edu.janeforjane.cache.datasource.MyDataSource;
import edu.janeforjane.cache.entities.CacheData;
import edu.janeforjane.cache.exceptions.FileException;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheService {

    /**
     * SoftReference: Ссылка, которая предотвращает сборку мусора объекта до тех пор,
     * пока JVM не испытывает нехватку памяти. Объект, на который ссылается SoftReference,
     * будет собран GC только в случае нехватки памяти.
     */
    private Map<String, SoftReference<CacheData>> softCache = new ConcurrentHashMap<>();

    /**
     * WeakReference: Ссылка, которая не предотвращает сборку мусора объекта.
     * Объект, на который ссылается WeakReference, может быть собран GC в любое время,
     * если на него нет других сильных или мягких ссылок
     */
    private Map<String, WeakReference<CacheData>> weakCache = new ConcurrentHashMap<>();

    private MyDataSource myDataSource;

    private static final Integer maxAge_min = 1;

    public CacheService(MyDataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    public CacheData getData(String key) throws FileException {
        CacheData data;

        CacheData cacheData= getFromCache(key);
        if (cacheData != null) {
            cacheData.setLastUsageDateTime(LocalDateTime.now());
            data = cacheData;
        }else {
            data = restoreFile(key);
        }

        clean();
        inspect();

        System.out.println("* * * * * * * * * * * *");
        System.out.println("Вывод содержимого файла:");
        return data;
    }

    private void inspect(){

        System.out.println("------------------------");
        System.out.println("----Cache inspection----");
        System.out.println("------------------------");

        System.out.println("|     Soft cache:      |");
        softCache.entrySet().forEach(entry -> {
            System.out.println(String.format("| %-20s |", entry.getKey()));
        });
        System.out.println("------------------------");

        System.out.println("|     Weak cache:      |");

        weakCache.entrySet().forEach(entry -> {
            System.out.println(String.format("| %-20s |", entry.getKey()));
        });

        System.out.println("------------------------");


    }

    private void clean() {
        // remove nulls
        cleanSoftCache();
        cleanWeakCache();

        LocalDateTime expiringDateTime = LocalDateTime.now().minusMinutes(maxAge_min);

        //check age - move to weak
        softCache.entrySet().removeIf(entry -> {
            CacheData file = entry.getValue().get();
            if (file != null && file.getLastUsageDateTime().isBefore(expiringDateTime)) {
                putWeak(entry.getKey(), file);
                return true;
            }
            return false;
        });

        //check age - move to soft
        weakCache.entrySet().removeIf(entry -> {
            CacheData file = entry.getValue().get();
            if (file != null && file.getLastUsageDateTime().isAfter(expiringDateTime)) {
                putSoft(entry.getKey(), file);
                return true;
            }
            return false;
        });

    }

    private void putWeak(String key, CacheData value) {
        weakCache.put(key, new WeakReference<>(value));
    }

    private void putSoft(String key, CacheData value) {
        softCache.put(key, new SoftReference<>(value));
    }

    private CacheData getFromCache(String key) {
        CacheData cacheData = getWeak(key);
        if (cacheData != null) {
            return cacheData;
        }

        cacheData = getSoft(key);
        if (cacheData != null) {
            return cacheData;
        }

        return null;
    }

    private CacheData getWeak(String key) {
        WeakReference<CacheData> ref = weakCache.get(key);
        return (ref != null) ? ref.get() : null;
    }

    private CacheData getSoft(String key) {
        SoftReference<CacheData> ref = softCache.get(key);
        return (ref != null) ? ref.get() : null;
    }

    private void cleanWeakCache() {
        weakCache.entrySet().removeIf(entry -> entry.getValue().get() == null);
    }

    private void cleanSoftCache() {
        softCache.entrySet().removeIf(entry -> entry.getValue().get() == null);
    }

    private CacheData restoreFile(String key) throws FileException {
        CacheData cacheData = null;
        try {
            cacheData = myDataSource.getData(key);
        } catch (IOException e) {
            throw new FileException("Couldn't get file");
        }
        putSoft(key, cacheData);
        return cacheData;
    }


}
