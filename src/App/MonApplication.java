package App;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JTopology;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonApplication implements ActionListener {
    Topology tp; // Objet qui contient le graphe
    JTopology jtp; // Composant graphique qui affiche le graphe

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
        JButton button = new JButton("Test");
        window.add(button,BorderLayout.NORTH);

        // Abonnement aux évènements du bouton (clic, etc.)
        button.addActionListener(this);

        // Finalisation
        window.pack();
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Test")) {
            JOptionPane.showMessageDialog(null, "Bouton cliqué");
        }
    }

    public static void main(String[] args) {
        new MonApplication();
    }
}