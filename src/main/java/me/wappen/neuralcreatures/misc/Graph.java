package me.wappen.neuralcreatures.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T, U> {
    private final Map<T, Integer> indices;
    private final List<List<Vertex<T, U>>> adjacencyMatrix;

    private record Vertex<T, U>(T obj, U edge) { }

    public Graph() {
        indices = new HashMap<>();
        adjacencyMatrix = new ArrayList<>();
    }

    public Graph(Graph<T, U> graph) {
        indices = new HashMap<>(graph.indices);
        adjacencyMatrix = new ArrayList<>(graph.adjacencyMatrix);
    }

    public void addObj(T obj) {
        indices.put(obj, adjacencyMatrix.size()); // Store index of adjacency matrix in map

        for (List<Vertex<T, U>> column : adjacencyMatrix)
            column.add(new Vertex<>(obj, null));

        List<Vertex<T, U>> column = new ArrayList<>(adjacencyMatrix.size() + 1);
        for (int i = 0; i < adjacencyMatrix.size() + 1; i++)
            column.add(new Vertex<>(obj, null));

        adjacencyMatrix.add(column);
    }

    public void removeObj(T obj) {
        int index = indices.remove(obj);

        adjacencyMatrix.remove(index);

        for (List<Vertex<T, U>> column : adjacencyMatrix)
            column.remove(index);
    }

    public void addEdge(T from, T to, U edge) {
        int indexFrom = indices.get(from);
        int indexTo = indices.get(to);

        List<Vertex<T, U>> column = adjacencyMatrix.get(indexFrom);
        column.set(indexTo, new Vertex<>(column.get(indexTo).obj(), edge));
    }

    public void removeEdge(T from, T to) {
        int indexFrom = indices.get(from);
        int indexTo = indices.get(to);

        List<Vertex<T, U>> column = adjacencyMatrix.get(indexFrom);
        column.set(indexTo, new Vertex<>(column.get(indexTo).obj(), null));
    }

    public Map<U, T> getNext(T obj) {
        int index = indices.get(obj);

        Map<U, T> nextObjs = new HashMap<>();

        for (List<Vertex<T, U>> column : adjacencyMatrix) {
            Vertex<T, U> vertex = column.get(index);
            if (vertex.edge() != null)
                nextObjs.put(vertex.edge(), vertex.obj());
        }

        return nextObjs;
    }

    public Map<U, T> getPrevious(T obj) {
        int index = indices.get(obj);

        Map<U, T> prevObjs = new HashMap<>();

        for (Vertex<T, U> vertex : adjacencyMatrix.get(index)) {
            if (vertex.edge() != null)
                prevObjs.put(vertex.edge(), vertex.obj());
        }

        return prevObjs;
    }
}
