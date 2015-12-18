package org.htl_hl.bibiProject.Client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JVerkauf extends JPanel {

    public JButton btVerk = new JButton("Verkaufen");
    private JLabel lblWare = new JLabel("Name von Ware");
    private JLabel lblVMenge = new JLabel("Vorhandene Menge");
    private JLabel lblBlank = new JLabel("");
    private JLabel lblBlank1 = new JLabel("");
    private JLabel lblMenge= new JLabel("Menge: ");
    private JLabel lblMinPreis= new JLabel("Minimum Preis: ");

    private JTextField tfMenge= new JTextField("");
    	    JTextField tfPreis= new JTextField("");

    public JVerkauf (){
        setToolTipText("Verkaufsfenster");
        setLayout(new GridLayout(5,2));
        add(lblWare);
        add(lblBlank);
        add(lblVMenge);
        add(lblBlank1);
        add(lblMenge);
        add(tfMenge);
        add(lblMinPreis);
        add(tfPreis);
        add(btVerk);
        // TODO(Tharre): Items die der Spieler hat
    }//JVerkauf

    public void setLblWare(String s){
        lblWare.setText("Name von Ware : "+s);
    }
    public void setLblVMenge(int s){
        lblVMenge.setText("Vorhandene Menge : "+s);
    }
}//class JVerkauf
