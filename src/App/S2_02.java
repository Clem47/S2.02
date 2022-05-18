/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package App;

import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author etd
 */
public class S2_02 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Topology tp = new Topology();
        new JViewer(tp);
        tp.start();
    }
    
}
