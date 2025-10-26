package org.example;

import java.util.*;

public class Prim {
    public static MSTResult run(Graph g) {
        long start = System.nanoTime();
        if (g.vertices.isEmpty()) return new MSTResult(Collections.emptyList(), 0, 0, g.edges.size(), 0, 0);

        Map<String, List<Edge>> adj = new HashMap<>();
        for (String v : g.vertices) adj.put(v, new ArrayList<>());
        for (Edge e : g.edges) {
            adj.get(e.u).add(e);
            adj.get(e.v).add(new Edge(e.v, e.u, e.w));
        }

        String startV = g.vertices.iterator().next();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        visited.add(startV);
        pq.addAll(adj.get(startV));

        List<Edge> mst = new ArrayList<>();
        long ops = 0;

        while (!pq.isEmpty() && mst.size() < g.vertices.size() - 1) {
            Edge e = pq.poll();
            ops++;
            if (visited.contains(e.v)) continue;
            visited.add(e.v);
            mst.add(e);
            for (Edge ne : adj.get(e.v)) {
                if (!visited.contains(ne.v)) {
                    pq.offer(ne);
                    ops++;
                }
            }
        }

        long total = 0;
        for (Edge e : mst) total += e.w;
        double timeMs = (System.nanoTime() - start) / 1_000_000.0;
        return new MSTResult(mst, total, g.vertices.size(), g.edges.size(), ops, timeMs);
    }
}
