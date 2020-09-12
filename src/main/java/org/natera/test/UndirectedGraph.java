package org.natera.test;

/**
 * Implementation of directed graph
 *
 * @param <T>
 */
public class UndirectedGraph<T> extends Graph<T> {

    /**
     * Adds new undirected edge between specified vertexes.
     *
     * @param vertexValueFrom vertex from
     * @param vertexValueTo vertex to
     */
    @Override
    public void addEdge(T vertexValueFrom, T vertexValueTo) {
        addPath(vertexValueFrom, vertexValueTo);
        addPath(vertexValueTo, vertexValueFrom);
    }
}
