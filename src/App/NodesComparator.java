package App;
import io.jbotsim.core.Node;
import java.util.Comparator;

public class NodesComparator implements Comparator<Node> {
    Node destination;
    public NodesComparator(Node destination) {
        this.destination = destination;
    }
    @Override
    public int compare(Node n1, Node n2) {
        return (int)n1.distance(destination) - (int)n2.distance(destination);
    }

}
    

