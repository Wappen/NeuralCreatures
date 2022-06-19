package me.wappen.neuralcreatures.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T, U> {
    private final Map<T, Integer> indices;
    private final List<T> objs;
    private final List<List<U>> adjacencyMatrix;

    public Graph() {
        indices = new HashMap<>();
        objs = new ArrayList<>();
        adjacencyMatrix = new ArrayList<>();
    }

    public Graph(Graph<T, U> graph) {
        indices = new HashMap<>(graph.indices);
        objs = new ArrayList<>(graph.objs);
        adjacencyMatrix = new ArrayList<>(graph.adjacencyMatrix);
    }

    public void addObj(T obj) {
        indices.put(obj, adjacencyMatrix.size()); // Store index for adjacency matrix in map
        objs.add(obj);

        for (List<U> column : adjacencyMatrix)
            column.add(null);

        List<U> column = new ArrayList<>(adjacencyMatrix.size() + 1);
        for (int i = 0; i < adjacencyMatrix.size() + 1; i++)
            column.add(null);

        adjacencyMatrix.add(column);
    }

    public void removeObj(T obj) {
        int index = indices.remove(obj);
        objs.remove(obj);

        adjacencyMatrix.remove(index);

        for (List<U> column : adjacencyMatrix)
            column.remove(index);
    }

    public void addEdge(T from, T to, U edge) {
        int indexFrom = indices.get(from);
        int indexTo = indices.get(to);

        List<U> column = adjacencyMatrix.get(indexFrom);
        column.set(indexTo, edge);
    }

    public void removeEdge(T from, T to) {
        int indexFrom = indices.get(from);
        int indexTo = indices.get(to);

        List<U> column = adjacencyMatrix.get(indexFrom);
        column.set(indexTo, null);
    }

    public Map<U, T> getNext(T obj) {
        int fromIndex = indices.get(obj);

        Map<U, T> nextObjs = new HashMap<>();

        int toIndex = 0;
        for (U vertex : adjacencyMatrix.get(fromIndex)) {
            if (vertex != null)
                nextObjs.put(vertex, objs.get(toIndex));
            toIndex++;
        }

        return nextObjs;
    }

    public Map<U, T> getPrevious(T obj) {
        int toIndex = indices.get(obj);

        Map<U, T> prevObjs = new HashMap<>();

        int fromIndex = 0;
        for (List<U> column : adjacencyMatrix) {
            U vertex = column.get(toIndex);
            if (vertex != null)
                prevObjs.put(vertex, objs.get(fromIndex));
            fromIndex++;
        }

        return prevObjs;
    }
}
