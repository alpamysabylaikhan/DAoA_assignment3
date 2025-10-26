package org.example;

public class Edge implements Comparable<Edge> {
    public final String u;
    public final String v;
    public final long w;

    public Edge(String u, String v, long w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    @Override
    public int compareTo(Edge o) {
        return Long.compare(this.w, o.w);
    }

    @Override
    public String toString() {
        return String.format("%s - %s : %d", u, v, w);
    }
}
