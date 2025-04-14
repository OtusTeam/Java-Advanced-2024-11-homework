package dev.korolz;

public class CacheFactory {
    public enum CacheType {
        WEAK, SOFT
    }

    public static Cache<String, String> createCache(CacheType type) {
        return switch (type) {
            case WEAK -> new WeakReferenceCache();
            case SOFT -> new SoftReferenceCache();
        };
    }
}