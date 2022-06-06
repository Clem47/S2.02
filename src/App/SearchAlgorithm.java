package App;
import io.jbotsim.core.Topology;
import io.jbotsim.core.Node;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

public class SearchAlgorithm  {
    Node source;
    Node destination;

    public SearchAlgorithm(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public HashMap<Node,Node> ParcoursEnLargeur(Topology graph, Node startNode, HashSet<Node> forbiddenNodes) {
        HashMap<Node,Node> parent = new HashMap<>();
        parent = initMap(graph,parent,startNode, forbiddenNodes);
        Queue <Node> queue = new LinkedList<>(); 
        queue.add(startNode);
        while (!queue.isEmpty()){
            Node tmp = queue.poll();
            for (Node neighbor : tmp.getNeighbors()) {
                if(!forbiddenNodes.contains(neighbor)){
                    if(parent.get(neighbor)== null ){
                        parent.put(neighbor,tmp);
                        if(!queue.contains(neighbor)){ queue.add(neighbor);}
                    }
                }
            }
        }
        return parent;
    }

    public HashMap<Node,Node> AStar(Topology graph, Node startNode,  HashSet<Node> forbiddenNodes) {
        HashMap<Node,Node> parent = new HashMap<>();
        parent = initMap(graph,parent,startNode, forbiddenNodes);
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodesComparator(destination));
        queue.add(startNode);
        while (!queue.isEmpty()){
            Node tmp = queue.poll();
            for (Node neighbor : tmp.getNeighbors()) {
                if(!forbiddenNodes.contains(neighbor)){
                    if(parent.get(neighbor)== null ){
                        parent.put(neighbor,tmp);
                        if(!queue.contains(neighbor)){ queue.add(neighbor);}
                        if (tmp.equals(destination)){
                            return parent;
                        }
                    }
                }
            }
        }
        return parent;
    }

    private HashMap<Node,Node> initMap(Topology graph,HashMap<Node,Node> parent,Node startNode, HashSet<Node> forbiddenNodes){
        for (Node n : graph.getNodes()) {
            if(!forbiddenNodes.contains(n)){
                if (n.equals(startNode)){ parent.put(n, n);}
                else{ parent.put(n, null);}
            }
        }
        return parent;
    }    
}