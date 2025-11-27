package com.urlshortner.services.implementation;

import com.urlshortner.services.LeastRecentlyUsedService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class LeastRecentlyUsedImplementation implements LeastRecentlyUsedService {

    ConcurrentHashMap<String, Node> map;
    Node head, tail;
    @Value("${IN_MEMORY_CACHE_SIZE}")
    private Integer SIZE;

    LeastRecentlyUsedImplementation() {
        this.map = new ConcurrentHashMap<>();
        head = new Node("", "");
        tail = new Node("", "");
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public synchronized boolean add(String shortURL, String longURL) {
        if (map.containsKey(shortURL)) {
            Node node = map.get(shortURL);
            delete(node);
            node.longURL = longURL;
            add(node);
            return true;
        }

        if (map.size() == SIZE) {
            map.remove(tail.prev.shortURL);
            delete(tail.prev);
        }

        Node newNode = new Node(shortURL, longURL);
        add(newNode);
        map.put(shortURL, newNode);

//        System.out.println("InMemory Updated: "+map);
        return true;
    }

    @Override
    public synchronized String get(String shortURL) {
        if (!map.containsKey(shortURL)) {
//            System.out.println("Not in Inmemory cache");
            return null;
        }

//        long start = System.currentTimeMillis();
//        System.out.println("InMemory Cache Hit");
//        System.out.println(map);
        delete(map.get(shortURL));
        add(map.get(shortURL));
//        System.out.println("InMemory Cache Hit in: "+(System.currentTimeMillis()-start)+" ms");
        return map.get(shortURL).longURL;
    }

    @Override
    public synchronized void delete(String shortURL) {
        Node node = map.getOrDefault(shortURL, null);
        if (node == null) return;

        delete(node);
        map.remove(shortURL);
    }

    private void add(Node node) {
        head.next.prev = node;
        node.next = head.next;
        node.prev = head;
        head.next = node;
    }

    private void delete(Node node) {
        Node next = node.next;
        Node prev = node.prev;

        next.prev = prev;
        prev.next = next;
    }

    static class Node {
        String shortURL, longURL;
        Node prev, next;

        Node(String shortURL, String longURL) {
            this.shortURL = shortURL;
            this.longURL = longURL;

            prev = next = null;
        }

        @Override
        public String toString() {
            return "[" + shortURL + "," + longURL + "]";
        }
    }
}
