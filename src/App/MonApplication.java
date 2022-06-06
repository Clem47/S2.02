import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.ui.JTopology;
import io.jbotsim.ui.icons.Icons;
import io.jbotsim.core.Link;


import javax.swing.*;
//import javax.xml.transform.Source;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MonApplication implements ActionListener , SelectionListener {
    Topology tp; // Objet qui contient le graphe
    JTopology jtp; // Composant graphique qui affiche le graphe
    Node source;
    Node destination;
    HashMap<Node,Node> parents;
    HashSet<Node> forbiddenNodes = new HashSet<>(); 

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
        JButton BFS = new JButton("Get path");
        JButton grille = new JButton("Grille");

        window.add(topButton,BorderLayout.NORTH);
        window.add(BFS,BorderLayout.SOUTH);
        window.add(grille,BorderLayout.EAST);

        // Abonnement aux évènements
        topButton.addActionListener(this);
        BFS.addActionListener(this);
        grille.addActionListener(this);
        tp.addSelectionListener(this);

        // Finalisation
        window.pack();
        window.setVisible(true);
    }
    /**
     * Gére les evenements
     * @param e evenement
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SearchAlgorithm algo = new SearchAlgorithm(source,destination);
        if (e.getActionCommand().equals("Reset")) {
          destination.setIcon(null);
          destination = null;
          source.setColor(Node.DEFAULT_COLOR);
          source = null;
          resetForbiddenNodes();
          resetPath();
        }
        if (e.getActionCommand().equals("Get path")) {
            String pathOption;
            do{
                pathOption = JOptionPane.showInputDialog(null, "1. Use BFS" + "\n" + "2. Use A* ","Algorithm",JOptionPane.QUESTION_MESSAGE);
            }
            while(pathOption !=null && !pathOption.equals("2") && !pathOption.equals("1"));
            if(pathOption.equals("1")){
                do{
                    pathOption = JOptionPane.showInputDialog(null, "1. Show all pathes " + "\n" + "2. Show the path between the start and the end","Path option",JOptionPane.QUESTION_MESSAGE);                }
                while(pathOption !=null && !pathOption.equals("2") && !pathOption.equals("1"));

                if(pathOption.equals("1")){
                    resetPath();
                    parents = algo.ParcoursEnLargeur(tp,source,forbiddenNodes);
                    for (Node n : parents.keySet()) {
                        if(!n.equals(parents.get(n))){
                        n.getCommonLinkWith(parents.get(n)).setWidth(4);
                        }
                    }
                }
                if(pathOption.equals("2")){
                    resetPath();
                    parents = algo.ParcoursEnLargeur(tp,source,forbiddenNodes);
                    ArrayList<Node> goodPath = extraireChemin(parents);
                    for(int i = 0  ; i<goodPath.size()-1 ; i++ ){
                        goodPath.get(i).getCommonLinkWith(goodPath.get(i+1)).setWidth(4);
                    }
                }
                pathOption = "null";
            }
            if(pathOption.equals("2")){
                do{
                    pathOption = JOptionPane.showInputDialog(null, "1. Show all pathes " + "\n" + "2. Show the path between the start and the end","Path option",JOptionPane.QUESTION_MESSAGE);                }
                while(pathOption !=null && !pathOption.equals("2") && !pathOption.equals("1"));
                if(pathOption.equals("1")){
                    resetPath();
                    parents = algo.AStar(tp,source,forbiddenNodes);
                    for (Node n : parents.keySet()) {
                        if(!n.equals(parents.get(n)) && parents.get(n)!=null){
                        n.getCommonLinkWith(parents.get(n)).setWidth(4);
                        }
                    }
                }
                if(pathOption.equals("2")){
                    resetPath();
                    parents =algo.AStar(tp,source,forbiddenNodes);
                    ArrayList<Node> goodPath = extraireChemin(parents);
                    for(int i = 0  ; i<goodPath.size()-1 ; i++ ){
                        goodPath.get(i).getCommonLinkWith(goodPath.get(i+1)).setWidth(4);
                    }
                }
            }
        }
        if(e.getActionCommand().equals("Grille")){
            String gridOption;
            do{
                gridOption = JOptionPane.showInputDialog(null, "Wich length for the grid (between 2 and 100)","Grid Option",JOptionPane.QUESTION_MESSAGE);
            }
            while(gridOption != null && gridOption.compareTo("2") >= 0 && gridOption.compareTo("100") <= 0);
            genererGrille(tp,Integer.parseInt(gridOption));
        }
    }

    /**
     * Gere les evenements de selection
     * @param node node selectionné
     */
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
        if(!selectedNode.equals(source) && !selectedNode.equals(destination)){
            forbiddenNodes.add(selectedNode);
            selectedNode.setColor(Color.red);
        }
    } 

    /**
     * géneration d'une grille
     * @param tp une topologie 
     * @param nbRows taille de la grille
     */
    public static void genererGrille(Topology tp, int nbRows){
        int stepX = (tp.getWidth() - 100) / (nbRows - 1);
        int stepY = (tp.getHeight() - 100) / (nbRows - 1);
        if (Math.max(stepX, stepY) >= 2 * Math.min(stepX, stepY)){
            String s = "les proportions de la topologie sont inadaptées";
            JOptionPane.showMessageDialog(null, s);
            return;
        }
        tp.setCommunicationRange(Math.max(stepX, stepY)+1);
        for (int i = 50; i <= tp.getWidth() - 50; i += stepX){
            for (int j = 50; j <= tp.getHeight() - 50; j += stepY) {
                tp.addNode(i, j, new Node());
            }
        }
    }

    /**
     * Récupaire le chemin entre la destination et la source
     * @param parents un dictionnaire de parents
     */
    public ArrayList<Node> extraireChemin(HashMap<Node,Node> graph){
        ArrayList<Node> goodPath = new ArrayList<>();
        goodPath.add(destination);
        while(!graph.get(goodPath.get(0)).equals(goodPath.get(0))){
            goodPath.add(0,graph.get(goodPath.get(0)));
        }
        return goodPath;
    }

    /**
     * Réinitialise les noeuds interdits
     */
    private void resetForbiddenNodes(){
        for (Node n : forbiddenNodes) {
            n.setColor(Node.DEFAULT_COLOR);
        }
        forbiddenNodes.clear();
    }

    /**
     * Réinitialise les liens
     */
    private void resetPath(){
        for (Link l : tp.getLinks()) {
            l.setWidth(1);
        }
    }

    public static void main(String[] args) {
        new MonApplication();
    }
}