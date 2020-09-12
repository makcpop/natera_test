package org.natera.test;

import java.util.*;

/**
 * Abstract implementation of graph.
 *
 * Value of a vertext must be not null.
 * Not thread safe.
 *
 * @param <T>
 */
public abstract class Graph<T> {

    private final Map<T, Vertex<T>> vertexes = new HashMap<>();

    public Map<T, Vertex<T>> getVertexes() {
        return vertexes;
    }

    /**
     * Add new vertex to graph.
     * If the vertex already exists in the graph, nothing happens.
     *
     * @param value vertex value
     * @throws IllegalArgumentException if value is null
     */
    public void addVertex(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must be not null");
        }
        vertexes.computeIfAbsent(value, Vertex::new);
    }

    /**
     * Abstract method for adding new edge between specified vertexes.
     * {@link Graph#addPath(T, T)} must be used for adding new directed edge.
     *
     * @param vertexValueFrom vertex from
     * @param vertexValueTo vertex to
     */
    public abstract void addEdge(T vertexValueFrom, T vertexValueTo);

    /**
     * Return path between two vertexes.
     *
     * @param vertexValueFrom vertex from
     * @param vertexValueTo vertex to
     * @return list of edges. If path does not exist, empty list is returned.
     * @throws IllegalAccessException if from or to vertex is null or not in the graph
     */
    public List<Edge<T>> getPath(T vertexValueFrom, T vertexValueTo) {
        //check if in graph
        getVertex(vertexValueFrom);
        getVertex(vertexValueTo);

        Map<T, VisitedVertex<T>> paths = new HashMap<>();
        Set<T> shouldBeVisited = new HashSet<>();
        
        paths.put(vertexValueFrom, new VisitedVertex<>(null, 0));
        shouldBeVisited.add(vertexValueFrom);

        while (!shouldBeVisited.isEmpty()) {
            Set<T> vertexWithUpdatedDistance = new HashSet<>();
            for (T currentVertex : shouldBeVisited) {
                long currentDistance = paths.get(currentVertex).getDistance();
                for (T nextVertex : vertexes.get(currentVertex).getEdgesTo()) {
                    long nextDistance = Optional.ofNullable(paths.get(nextVertex))
                            .map(VisitedVertex::getDistance)
                            .orElse(Long.MAX_VALUE);
                    if (nextDistance > currentDistance + 1) {
                        paths.put(nextVertex, new VisitedVertex<>(currentVertex, currentDistance + 1));
                        vertexWithUpdatedDistance.add(nextVertex);
                    }
                }
            }
            shouldBeVisited = vertexWithUpdatedDistance;
        }

        return collectPath(vertexValueFrom, vertexValueTo, paths);
    }

    private List<Edge<T>> collectPath(T vertexValueFrom, T vertexValueTo, Map<T, VisitedVertex<T>> paths) {
        T currentVertex = vertexValueTo;
        VisitedVertex<T> pathToCurrent = paths.get(currentVertex);
        if (pathToCurrent == null) {
            return Collections.emptyList();
        }

        List<Edge<T>> fullPath = new ArrayList<>();
        fullPath.add(new Edge<>(pathToCurrent.getPreviousVertex(), currentVertex));
        while (!pathToCurrent.getPreviousVertex().equals(vertexValueFrom)) {
            currentVertex = pathToCurrent.getPreviousVertex();
            pathToCurrent = paths.get(currentVertex);
            fullPath.add(new Edge<>(pathToCurrent.getPreviousVertex(), currentVertex));
        }
        Collections.reverse(fullPath);
        return fullPath;
    }

    /**
     * Add directed edge between two vertexes.
     *
     * @param vertexValueFrom vertex from
     * @param vertexValueTo vertex to
     * @throws IllegalAccessException if from or to vertex is null or not in the graph
     */
    protected void addPath(T vertexValueFrom, T vertexValueTo) {
        Vertex<T> vertexFrom = getVertex(vertexValueFrom);
        Vertex<T> vertexTo = getVertex(vertexValueTo);
        vertexFrom.addEdgeTo(vertexTo.getValue());
    }

    private Vertex<T> getVertex(T vertexValue) {
        if (vertexValue == null) {
            throw new IllegalArgumentException("Value is null");
        }
        Vertex<T> vertex = vertexes.get(vertexValue);
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex" + vertexValue + " doesn't exist");
        }
        return vertex;
    }

    private class VisitedVertex<V> {
        private final V previousVertex;
        private final long distance;

        private VisitedVertex(V previousVertex, long distance) {
            this.previousVertex = previousVertex;
            this.distance = distance;
        }

        public V getPreviousVertex() {
            return previousVertex;
        }

        public long getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VisitedVertex<?> that = (VisitedVertex<?>) o;
            return distance == that.distance &&
                    Objects.equals(previousVertex, that.previousVertex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(previousVertex, distance);
        }

        @Override
        public String toString() {
            return "VisitedVertex{" +
                    "previousVertex=" + previousVertex +
                    ", distance=" + distance +
                    '}';
        }
    }

    static class Vertex<V> {
        private final V value;
        private final Set<V> edgesTo;

        private Vertex(V value) {
            this.value = value;
            this.edgesTo = new HashSet<>();
        }

        public V getValue() {
            return value;
        }

        public Set<V> getEdgesTo() {
            return edgesTo;
        }

        public void addEdgeTo(V value) {
            edgesTo.add(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return Objects.equals(value, vertex.value) &&
                    Objects.equals(edgesTo, vertex.edgesTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, edgesTo);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", edgesTo=" + edgesTo +
                    '}';
        }
    }
}
