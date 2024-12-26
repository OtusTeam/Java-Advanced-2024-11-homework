package edu.janeforjane.cache.entities;

import java.time.LocalDateTime;

public class CacheData {

    private String key;
    private byte[] bytes;
    private LocalDateTime lastUsageDateTime;

    public CacheData(String key, byte[] bytes, LocalDateTime lastUsageDateTime) {
        this.key = key;
        this.bytes = bytes;
        this.lastUsageDateTime = lastUsageDateTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public LocalDateTime getLastUsageDateTime() {
        return lastUsageDateTime;
    }

    public void setLastUsageDateTime(LocalDateTime lastUsageDateTime) {
        this.lastUsageDateTime = lastUsageDateTime;
    }

    @Override
    public String toString() {
        return "CacheData{" +
                "key='" + key + '\'' +
                ", lastUsageDateTime=" + lastUsageDateTime +
                '}';
    }
}
