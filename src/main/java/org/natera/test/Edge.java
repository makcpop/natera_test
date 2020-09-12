package org.natera.test;

import java.util.Objects;

public class Edge<T> {

    private final T vertexFrom;
    private final T vertexTo;

    public Edge(T vertexFrom, T vertexTo) {
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        return Objects.equals(vertexFrom, edge.vertexFrom) &&
                Objects.equals(vertexTo, edge.vertexTo);
    }

    public T getVertexFrom() {
        return vertexFrom;
    }

    public T getVertexTo() {
        return vertexTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexFrom, vertexTo);
    }

    @Override
    public String

    toString() {
        return "Edge{" +
                "vertexFrom=" + vertexFrom +
                ", vertexTo=" + vertexTo +
                '}';
    }
}
