package org.example;

import java.util.*;

public class Kruskal {
    public static MSTResult run(Graph g) {
        long start = System.nanoTime();
        List<Edge> edges = new ArrayList<>(g.edges);
        Collections.sort(edges);

        DSU dsu = new DSU(g.vertices);
        List<Edge> mst = new ArrayList<>();
        long ops = 0;

        for (Edge e : edges) {
            ops++;
            if (dsu.find(e.u).equals(dsu.find(e.v))) continue;
            if (dsu.union(e.u, e.v)) {
                mst.add(e);
            }
            if (mst.size() == g.vertices.size() - 1) break;
        }

        long total = 0;
        for (Edge e : mst) total += e.w;
        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        long totalOps = ops + dsu.unions;
        return new MSTResult(mst, total, g.vertices.size(), g.edges.size(), totalOps, timeMs);
    }
}

