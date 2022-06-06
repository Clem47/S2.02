
import io.jbotsim.core.Node;
import java.util.Comparator;

public class NodesComparator implements Comparator<Node> {
    Node destination;
    public NodesComparator(Node destination) {
        this.destination = destination;
    }
    /**
     * Compare 2 neouds par rapport à la distance à la destination
     * @param n1 Node 1
     * @param n2 Node 2
     */
    @Override
    public int compare(Node n1, Node n2) {
        return (int)n1.distance(destination) - (int)n2.distance(destination);
    }

}
    

