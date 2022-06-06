import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;

public class TestMonApplication {
    public static void main(String[] args) {
        testAstar();
        testBFS();
    }

    /**
     * Test de l'algorithme A*
     */
    @Test
    public static void testAstar(){
        Topology tp = new Topology();
        MonApplication.genererGrille(tp, 5);
        HashMap<Node,Node> parent = new HashMap<>();
        Node source = tp.getNodes().get(1);
        Node destination = tp.getNodes().get(4);
        HashSet<Node> forbiddenNodes = new HashSet<>();
        forbiddenNodes.add(tp.getNodes().get(3));
        
        parent = new SearchAlgorithm(source,destination).AStar(tp, source, forbiddenNodes);
        assertTrue(!parent.get(destination).equals(null));
        assertTrue(destination.getNeighbors().contains(parent.get(destination)));
        assertTrue(parent.get(source).equals(source));
    }

    /**
     * Test de l'algorithme BFS
     */
    @Test
    public static void testBFS(){
        Topology tp = new Topology();
        MonApplication.genererGrille(tp, 5);
        HashMap<Node,Node> parent = new HashMap<>();
        HashSet<Node> forbiddenNodes = new HashSet<>();
        forbiddenNodes.add(tp.getNodes().get(3));
        Node source = tp.getNodes().get(1);
        Node destination = tp.getNodes().get(4);
        
        parent = new SearchAlgorithm(source,destination).ParcoursEnLargeur(tp, source, forbiddenNodes);
        assertTrue(!parent.get(destination).equals(null));
        assertTrue(destination.getNeighbors().contains(parent.get(destination)));
        assertTrue(parent.get(source).equals(source));
        for (Node node : forbiddenNodes) {
            assertFalse(parent.containsKey(node));
        }            
        }
}
