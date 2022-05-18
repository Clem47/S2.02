package App;
import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.SelectionListener;
import io.jbotsim.ui.JTopology;

import javax.swing.*;
import javax.xml.transform.Source;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonApplication implements ActionListener , SelectionListener {
    Topology tp; // Objet qui contient le graphe
    JTopology jtp; // Composant graphique qui affiche le graphe
    Node source , destination = new Node();

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
        JButton topButton = new JButton("Top");
        window.add(topButton,BorderLayout.NORTH);

        JButton downButton = new JButton("Down");
        window.add(downButton,BorderLayout.SOUTH);

        // Abonnement aux évènements du bouton (clic, etc.)
        topButton.addActionListener(this);
        downButton.addActionListener(this);

        //
        tp.addSelectionListener(this);

        // Finalisation
        window.pack();
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Top")) {
            JOptionPane.showMessageDialog(null, "Bouton haut cliqué");
        }
        if (e.getActionCommand().equals("Down")) {
            JOptionPane.showMessageDialog(null, "Bouton bas cliqué");
        }
    }

    @Override
    public void onSelection(Node selectedNode) {
        if(destination == null && source!=null ){
            destination = selectedNode;
            System.out.println("Destination");
        }
        if(source == null){
            source = selectedNode;
            source.setColor(Color.black);
            System.out.println("Source");
        }
    }

    public static void main(String[] args) {
        new MonApplication();
    }
}