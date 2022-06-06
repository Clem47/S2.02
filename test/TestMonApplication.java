package test;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;

import App.MonApplication;
import App.SearchAlgorithm;

public class TestSearchAlgorithm {
    public static void main(String[] args) {
        testAstar();
        testBFS();
    }

    @Test
    public static void testAstar(){
        Topology tp = new Topology();
        MonApplication.genererGrille(tp, 5);
        HashMap<Node,Node> parent = new HashMap<>();
        Node source = tp.getNodes().get(1);
        Node destination = tp.getNodes().get(4);

        parent = new SearchAlgorithm().AStar(tp, source, destination);
        assertTrue(!parent.get(destination).equals(null));
        assertTrue(destination.getNeighbors().contains(parent.get(destination)));
        assertTrue(parent.get(source).equals(source));
    }

    @Test
    public static void testBFS(){
        Topology tp = new Topology();
        MonApplication.genererGrille(tp, 5);
        HashMap<Node,Node> parent = new HashMap<>();
        HashSet<Node> forbiddenNodes = new HashSet<>();
        forbiddenNodes.add(tp.getNodes().get(3));
        Node source = tp.getNodes().get(1);
        Node destination = tp.getNodes().get(4);
        
        parent = new SearchAlgorithm().ParcoursEnLargeur(tp, source, destination, forbiddenNodes);
        assertTrue(!parent.get(destination).equals(null));
        assertTrue(destination.getNeighbors().contains(parent.get(destination)));
        assertTrue(parent.get(source).equals(source));
    }
}
