package org.natera.test;

/**
 * Implementation of directed graph
 *
 * @param <T>
 */
public class DirectedGraph<T> extends Graph<T> {

    /**
     * Adds new directed edge between specified vertexes.
     *
     * @param vertexValueFrom vertex from
     * @param vertexValueTo vertex to
     */
    @Override
    public void addEdge(T vertexValueFrom, T vertexValueTo) {
        addPath(vertexValueFrom, vertexValueTo);
    }
}
