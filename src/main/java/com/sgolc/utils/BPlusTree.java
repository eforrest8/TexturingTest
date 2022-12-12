package com.sgolc.utils;

import java.util.*;

public class BPlusTree<K, V> implements SortedMap<K, V> {
    private Collection<V> values = new LinkedList<>();
    private int order = 30;

    @Override
    public int size() {
        return values().size();
    }

    @Override
    public boolean isEmpty() {
        return values().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return null;
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return null;
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return null;
    }

    @Override
    public K firstKey() {
        return null;
    }

    @Override
    public K lastKey() {
        return null;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    private static class Node {

    }

    private static class Leaf extends Node {

    }
}
