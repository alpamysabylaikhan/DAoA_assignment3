package org.example;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JsonUtils {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Graph readGraph(String path) throws IOException {
        String text = Files.readString(Path.of(path));
        JsonObject obj = JsonParser.parseString(text).getAsJsonObject();
        Graph g = new Graph();

        JsonArray edges = obj.getAsJsonArray("edges");
        for (JsonElement e : edges) {
            JsonObject o = e.getAsJsonObject();
            g.addEdge(o.get("u").getAsString(), o.get("v").getAsString(), o.get("w").getAsLong());
        }
        return g;
    }

    public static void writeResults(Map<String, MSTResult> map, String path) throws IOException {
        JsonObject root = new JsonObject();
        for (Map.Entry<String, MSTResult> entry : map.entrySet()) {
            MSTResult r = entry.getValue();
            JsonObject obj = new JsonObject();
            obj.addProperty("totalWeight", r.totalWeight);
            obj.addProperty("verticesCount", r.verticesCount);
            obj.addProperty("edgesCount", r.edgesCount);
            obj.addProperty("operations", r.operations);
            obj.addProperty("timeMs", r.timeMs);
            JsonArray arr = new JsonArray();
            for (Edge e : r.mstEdges) {
                JsonObject eo = new JsonObject();
                eo.addProperty("u", e.u);
                eo.addProperty("v", e.v);
                eo.addProperty("w", e.w);
                arr.add(eo);
            }
            obj.add("mstEdges", arr);
            root.add(entry.getKey(), obj);
        }
        Files.writeString(Path.of(path), gson.toJson(root));
    }
}
