package org.example;

import java.util.*;

public class DSU {
    private final Map<String, String> parent = new HashMap<>();
    private final Map<String, Integer> rank = new HashMap<>();
    public long unions = 0;

    public DSU(Collection<String> items) {
        for (String s : items) {
            parent.put(s, s);
            rank.put(s, 0);
        }
    }

    public String find(String x) {
        if (parent.get(x).equals(x)) return x;
        String root = find(parent.get(x));
        parent.put(x, root);
        return root;
    }

    public boolean union(String a, String b) {
        String ra = find(a);
        String rb = find(b);
        if (ra.equals(rb)) return false;
        unions++;
        int rka = rank.get(ra);
        int rkb = rank.get(rb);
        if (rka < rkb) parent.put(ra, rb);
        else if (rka > rkb) parent.put(rb, ra);
        else {
            parent.put(rb, ra);
            rank.put(ra, rka + 1);
        }
        return true;
    }
}
