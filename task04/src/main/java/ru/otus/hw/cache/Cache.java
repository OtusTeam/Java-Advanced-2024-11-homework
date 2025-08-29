package ru.otus.hw.cache;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;

public class Cache<K,V>  {
    private final Map<K, SoftReference<Pair<K,V>>> hash = new HashMap<>();
    private final int HARD_SIZE;
    private final LinkedList<V> hardCache = new LinkedList<>();
    private final ReferenceQueue<Pair<K,V>> queue = new ReferenceQueue<>();

    public Cache() { this(100); }
    public Cache(int hardSize) { HARD_SIZE = hardSize; }

    public V get(K key) {
        V result = null;
        SoftReference<Pair<K,V>> softRef = hash.get(key);
        if (softRef != null) {
            result = Objects.requireNonNull(softRef.get()).value;
            if (result == null) {
                hash.remove(key);
            } else {
                hardCache.addFirst(result);
                if (hardCache.size() > HARD_SIZE) {
                    hardCache.removeLast();
                }
            }
        }
        return result;
    }

  record  Pair<K, V>(K key, V value ){
  }


    private void processQueue() {
        Reference<? extends Pair<K,V>> sv = null;
        while ((sv = queue.poll()) != null) {
            hash.remove(Objects.requireNonNull(sv.get()).key());
        }
    }
    public K put(K key, V value) {
        processQueue();
        var pair = new Pair<K,V>(key, value);
        var ref= hash.put(key, new SoftReference<>(pair, queue) );
        return ref == null? null : Objects.requireNonNull(Objects.requireNonNull(ref).get()).key;
    }
    public Object remove(K key) {
        processQueue();
        return hash.remove(key);
    }
    public void clear() {
        hardCache.clear();
        processQueue();
        hash.clear();
    }
    public int size() {
        processQueue();
        return hash.size();
    }
}