package org.example;

import java.util.*;

public class MSTResult {
    public final List<Edge> mstEdges;
    public final long totalWeight;
    public final int verticesCount;
    public final int edgesCount;
    public final long operations;
    public final double timeMs;

    public MSTResult(List<Edge> mstEdges, long totalWeight, int verticesCount, int edgesCount, long operations, double timeMs) {
        this.mstEdges = mstEdges;
        this.totalWeight = totalWeight;
        this.verticesCount = verticesCount;
        this.edgesCount = edgesCount;
        this.operations = operations;
        this.timeMs = timeMs;
    }
}
