package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {
    private Graph sampleGraph() {
        Graph g = new Graph();
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("A", "C", 3);
        return g;
    }

    @Test
    public void testPrimAndKruskalHaveSameWeight() {
        Graph g = sampleGraph();
        MSTResult p = Prim.run(g);
        MSTResult k = Kruskal.run(g);
        assertEquals(p.totalWeight, k.totalWeight);
        assertEquals(g.vertices.size() - 1, p.mstEdges.size());
    }
}

