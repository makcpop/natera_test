package org.natera.test;

/**
 * Implementation of directed graph
 *
 * Value of a vertex must be not null.
 * Not thread safe.
 *
 * @param <T>
 */
public class DirectedGraph<T> extends Graph<T> {

    /**
     * Adds new directed edge between specified vertexes.
     *
     * @param vertexValueFrom vertex from
     * @param vertexValueTo vertex to
     * @throws IllegalAccessException if from or to vertex is null or not in the graph
     */
    @Override
    public void addEdge(T vertexValueFrom, T vertexValueTo) {
        addPath(vertexValueFrom, vertexValueTo);
    }
}
