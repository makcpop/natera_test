package org.natera.test;

import org.natera.test.Graph.Vertex;

import java.lang.reflect.Field;
import java.util.Map;

public class TestUtils {

    private TestUtils() {
    }

    public static <T> Map<T, Vertex<T>> getVertexesMap(Graph<T> graph) throws Exception{
        Field vertexes = Graph.class.getDeclaredField("vertexes");
        vertexes.setAccessible(true);
        return (Map<T, Vertex<T>>) vertexes.get(graph);
    }

    public static Edge<Integer> edge(Integer from, Integer to) {
        return new Edge<>(from, to);
    }
}
