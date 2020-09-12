package org.natera.test;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.natera.test.TestUtils.edge;
import static org.natera.test.TestUtils.getVertexesMap;

class DirectedGraphTest {

    @Test
    public void testAllMethods_nullSafe() {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        assertThrows(IllegalArgumentException.class, () -> graph.addVertex(null));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(null, null));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, null));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(null, 2));
        assertThrows(IllegalArgumentException.class, () -> graph.getPath(null, null));
        assertThrows(IllegalArgumentException.class, () -> graph.getPath(null, 2));
        assertThrows(IllegalArgumentException.class, () -> graph.getPath(1, null));
    }

    @Test
    public void testAddVertex() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(1));
        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), empty());
    }

    @Test
    public void testAddVertex_existedVertes() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(1);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(1));
        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), empty());
    }

    @Test
    public void testAddVertex_secodVertes() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(2));

        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), empty());

        assertThat(vertexesMap.get(2), notNullValue());
        assertThat(vertexesMap.get(2).getValue(), is(2));
        assertThat(vertexesMap.get(2).getEdgesTo(), empty());
    }

    @Test
    public void testAddEdge_notExistBothVertexes() throws Exception {
        Graph<Integer> graph = getGraph();
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    public void testAddEdge_notExistFromVertex() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(2);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    public void testAddEdge_notExistToVertex() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    public void testAddEdge_fromOneToTwo() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(2));

        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), contains(2));

        assertThat(vertexesMap.get(2), notNullValue());
        assertThat(vertexesMap.get(2).getValue(), is(2));
        assertThat(vertexesMap.get(2).getEdgesTo(), empty());
    }

    @Test
    public void testAddEdge_bothSideOneToTwo() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(2));

        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), contains(2));

        assertThat(vertexesMap.get(2), notNullValue());
        assertThat(vertexesMap.get(2).getValue(), is(2));
        assertThat(vertexesMap.get(2).getEdgesTo(), contains(1));
    }

    @Test
    public void testAddEdge_addTwoEdges() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(3));

        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), contains(2));

        assertThat(vertexesMap.get(2), notNullValue());
        assertThat(vertexesMap.get(2).getValue(), is(2));
        assertThat(vertexesMap.get(2).getEdgesTo(), contains(3));

        assertThat(vertexesMap.get(3), notNullValue());
        assertThat(vertexesMap.get(3).getValue(), is(3));
        assertThat(vertexesMap.get(3).getEdgesTo(), empty());
    }

    @Test
    public void testAddEdge_unboundGraph() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        Map<Integer, Graph.Vertex<Integer>> vertexesMap = getVertexesMap(graph);
        assertThat(vertexesMap.size(), is(4));

        assertThat(vertexesMap.get(1), notNullValue());
        assertThat(vertexesMap.get(1).getValue(), is(1));
        assertThat(vertexesMap.get(1).getEdgesTo(), contains(2));

        assertThat(vertexesMap.get(2), notNullValue());
        assertThat(vertexesMap.get(2).getValue(), is(2));
        assertThat(vertexesMap.get(2).getEdgesTo(), empty());

        assertThat(vertexesMap.get(3), notNullValue());
        assertThat(vertexesMap.get(3).getValue(), is(3));
        assertThat(vertexesMap.get(3).getEdgesTo(), contains(4));

        assertThat(vertexesMap.get(4), notNullValue());
        assertThat(vertexesMap.get(4).getValue(), is(4));
        assertThat(vertexesMap.get(4).getEdgesTo(), empty());
    }

    @Test
    public void testGetPath() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        List<Edge<Integer>> path = graph.getPath(1, 2);
        assertThat(path, contains(edge(1, 2)));
        List<Edge<Integer>> reversePath = graph.getPath(2, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_bothSide() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 2);

        List<Edge<Integer>> path = graph.getPath(1, 2);
        assertThat(path, contains(edge(1, 2)));
        List<Edge<Integer>> reversePath = graph.getPath(2, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_bothNotExist() throws Exception {
        Graph<Integer> graph = getGraph();
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    public void testGetPath_fromNotExist() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(2);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    public void testGetPath_toNotExist() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 2));
    }

    @Test
    public void testGetPath_edgeNotExist() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);

        List<Edge<Integer>> path = graph.getPath(1, 2);
        assertThat(path, empty());
        List<Edge<Integer>> reversePath = graph.getPath(2, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_threeVertexes_long() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        List<Edge<Integer>> path = graph.getPath(1, 3);
        assertThat(path, contains(edge(1, 2), edge(2, 3)));
        List<Edge<Integer>> reversePath = graph.getPath(3, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_unboundGraph_short() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);

        List<Edge<Integer>> path = graph.getPath(1, 3);
        assertThat(path, empty());
        List<Edge<Integer>> reversePath = graph.getPath(3, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_threeVertexes_long_bothSide() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 2);
        graph.addEdge(2, 1);

        List<Edge<Integer>> path = graph.getPath(1, 3);
        assertThat(path, contains(edge(1, 2), edge(2, 3)));
        List<Edge<Integer>> reversePath = graph.getPath(3, 1);
        assertThat(reversePath, contains(edge(3, 2), edge(2, 1)));
    }

    @Test
    public void testGetPath_unboundGraph_short_bothSide() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(1, 2);
        graph.addEdge(3, 2);

        List<Edge<Integer>> path = graph.getPath(1, 3);
        assertThat(path, empty());
        List<Edge<Integer>> reversePath = graph.getPath(3, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_unboundGraph_long() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);

        List<Edge<Integer>> path = graph.getPath(1, 4);
        assertThat(path, empty());
        List<Edge<Integer>> reversePath = graph.getPath(4, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_severalPaths() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 4);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);

        List<Edge<Integer>> path = graph.getPath(1, 4);
        if (path.get(0).equals(edge(1, 2))) {
            assertThat(path, contains(edge(1, 2), edge(2, 4)));
        } else {
            assertThat(path, contains(edge(1, 3), edge(3, 4)));
        }
        List<Edge<Integer>> reversePath = graph.getPath(4, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_severalPaths_differentDistance() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(1, 2);
        graph.addEdge(2, 5);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        List<Edge<Integer>> path = graph.getPath(1, 5);
        assertThat(path, contains(edge(1, 2), edge(2, 5)));
        List<Edge<Integer>> reversePath = graph.getPath(5, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_noPath_withCycle() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);

        List<Edge<Integer>> path = graph.getPath(1, 5);
        assertThat(path, empty());
        List<Edge<Integer>> reversePath = graph.getPath(5, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_withCycle() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);
        graph.addEdge(2, 5);

        List<Edge<Integer>> path = graph.getPath(1, 5);
        assertThat(path, contains(edge(1, 2), edge(2, 5)));

        List<Edge<Integer>> reversePath = graph.getPath(5, 1);
        assertThat(reversePath, empty());
    }

    @Test
    public void testGetPath_complex() throws Exception {
        Graph<Integer> graph = getGraph();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addVertex(7);
        graph.addVertex(8);
        graph.addVertex(9);
        graph.addVertex(10);
        graph.addVertex(11);
        graph.addVertex(12);
        graph.addVertex(13);
        graph.addVertex(14);
        graph.addVertex(15);

        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(2, 5);
        graph.addEdge(5, 1);
        graph.addEdge(3, 6);
        graph.addEdge(4, 6);
        graph.addEdge(6, 1);
        graph.addEdge(1, 7);
        graph.addEdge(7, 2);
        graph.addEdge(2, 8);
        graph.addEdge(8, 9);
        graph.addEdge(9, 10);
        graph.addEdge(9, 4);
        graph.addEdge(9, 11);
        graph.addEdge(10, 9);
        graph.addEdge(10, 2);
        graph.addEdge(12, 15);
        graph.addEdge(14, 15);
        graph.addEdge(12, 14);
        graph.addEdge(14, 13);
        graph.addEdge(13, 12);

        List<Edge<Integer>> path = graph.getPath(1, 4);
        assertThat(path, contains(edge(1, 2), edge(2, 3), edge(3, 4)));

        List<Edge<Integer>> reversePath = graph.getPath(4, 1);
        assertThat(reversePath, contains(edge(4, 6), edge(6, 1)));
    }

    private Graph<Integer> getGraph() {
        return new DirectedGraph<>();
    }
}