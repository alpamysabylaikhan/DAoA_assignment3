package org.example;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import com.google.gson.*;


public class Main {
    public static void main(String[] args) throws Exception {
        String[] inputs = {
                "data/input_small.json",
                "data/input_medium.json",
                "data/input_large.json"
        };

        Files.createDirectories(Paths.get("results"));

        Map<String, Map<String, MSTResult>> allResults = new LinkedHashMap<>();
        StringBuilder csv = new StringBuilder();
        csv.append("Graph,Algorithm,TotalWeight,Vertices,Edges,Operations,TimeMs\n");

        for (String path : inputs) {
            String graphName = path.replace("data/", "").replace(".json", "");
            System.out.println("Processing graph: " + graphName);

            Graph g = JsonUtils.readGraph(path);

            MSTResult prim = Prim.run(g);
            MSTResult kruskal = Kruskal.run(g);

            Map<String, MSTResult> results = new LinkedHashMap<>();
            results.put("Prim", prim);
            results.put("Kruskal", kruskal);
            allResults.put(graphName, results);

            csv.append(String.format(Locale.US,
                    "%s,Prim,%d,%d,%d,%d,%.4f\n",
                    graphName, prim.totalWeight, prim.verticesCount, prim.edgesCount, prim.operations, prim.timeMs));
            csv.append(String.format(Locale.US,
                    "%s,Kruskal,%d,%d,%d,%d,%.4f\n",
                    graphName, kruskal.totalWeight, kruskal.verticesCount, kruskal.edgesCount, kruskal.operations, kruskal.timeMs));

            System.out.println("Prim total weight = " + prim.totalWeight + ", time = " + prim.timeMs + " ms");
            System.out.println("Kruskal total weight = " + kruskal.totalWeight + ", time = " + kruskal.timeMs + " ms");
            System.out.println("--------------------------------------------------");
        }

        JsonObject finalJson = new JsonObject();
        for (var entry : allResults.entrySet()) {
            JsonObject graphObj = new JsonObject();
            for (var algo : entry.getValue().entrySet()) {
                MSTResult r = algo.getValue();
                JsonObject obj = new JsonObject();
                obj.addProperty("totalWeight", r.totalWeight);
                obj.addProperty("verticesCount", r.verticesCount);
                obj.addProperty("edgesCount", r.edgesCount);
                obj.addProperty("operations", r.operations);
                obj.addProperty("timeMs", r.timeMs);
                graphObj.add(algo.getKey(), obj);
            }
            finalJson.add(entry.getKey(), graphObj);
        }

        Files.write(Paths.get("results/summary.json"), new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(finalJson).getBytes());
        Files.writeString(Paths.get("results/summary.csv"), csv.toString());

        System.out.println("\n✅ Все графы обработаны!");
        System.out.println("→ JSON: results/summary.json");
        System.out.println("→ CSV:  results/summary.csv");
    }
}
