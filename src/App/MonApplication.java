package App;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.ui.JTopology;
import io.jbotsim.ui.icons.Icons;

import javax.swing.*;
//import javax.xml.transform.Source;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MonApplication implements ActionListener , SelectionListener {
    Topology tp; // Objet qui contient le graphe
    JTopology jtp; // Composant graphique qui affiche le graphe
    Node source;
    Node destination;
    HashMap<Node,Node> parents;

    public MonApplication() {
        // Création du graphe
        tp = new Topology();

        // Création de l'interface graphique (ci-dessous)
        creerInterfaceGraphique();
    }

    private void creerInterfaceGraphique() {
        // Création d'une fenêtre
        JFrame window = new JFrame("Mon application");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du composant graphique qui affiche le graphe
        jtp = new JTopology(tp);
        window.add(jtp);

        // Création d'un bouton test
        JButton topButton = new JButton("Reset");
        JButton BFS = new JButton("BFS");

        window.add(topButton,BorderLayout.NORTH);
        window.add(BFS,BorderLayout.SOUTH);

        // Abonnement aux évènements
        topButton.addActionListener(this);
        BFS.addActionListener(this);
        tp.addSelectionListener(this);

        // Finalisation
        window.pack();
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Reset")) {
          destination.setIcon(null);
          destination = null;
          source.setColor(Node.DEFAULT_COLOR);
          source = null;
          resetPath();
        }
        if (e.getActionCommand().equals("BFS")) {
            parents = ParcoursEnLargeur(tp,source);
            for (Node n  : parents.keySet() ) {
                if(!n.equals(parents.get(n))){
                    n.getCommonLinkWith(parents.get(n)).setWidth(4);
                }
            }
        }
    }

    @Override
    public void onSelection(Node selectedNode) {
        if(source == null){
            source = selectedNode;
            source.setColor(Color.black);
            return;
        }
        if(destination == null ){
            destination = selectedNode;
            destination.setIcon(Icons.FLAG);
            return;
        }
    }

    public HashMap<Node,Node> ParcoursEnLargeur(Topology graph, Node startNode){
        HashMap<Node,Node> parent = new HashMap<>();
        parent = initMap(graph,parent,startNode);
        Queue <Node> queue = new LinkedList<>(); 
        queue.add(startNode);
        while (!queue.isEmpty()){
            Node tmp = queue.poll();
            for (Node neighbor : tmp.getNeighbors()) {
                if(parent.get(neighbor)== null){
                    parent.put(neighbor,tmp);
                    if(!queue.contains(neighbor)){ queue.add(neighbor);}
                }
            }
        }
        return parent;
    }

    private HashMap<Node,Node> initMap(Topology graph,HashMap<Node,Node> parent,Node startNode){
        for (Node n : graph.getNodes()) {
            if (n.equals(startNode)){ parent.put(n, n);}
            else{ parent.put(n, null);}
        }
        return parent;
    }

    private void resetPath(){
        if(parents != null){
            for (Node n  : parents.keySet() ) {
                if(!n.equals(parents.get(n))){
                    n.getCommonLinkWith(parents.get(n)).setWidth(1);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new MonApplication();
    }


}