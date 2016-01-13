package org.htl_hl.bibiProject.Client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * <p>Title: Kaufen</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes Börsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JKaufen extends JPanel implements ActionListener {

     JButton btKauf = new JButton("Bestellen");



    JLabel lblWare = new JLabel("Name");
    private JLabel lblGewMenge= new JLabel("gew. Menge: ");
    private JLabel lblMaxPreis= new JLabel("max. Preis: ");
    private JLabel lblBlank = new JLabel("");

    private JTextField tfGewMenge= new JTextField("");
    private JTextField tfMaxPreis= new JTextField("");
    public JKaufen (){
        setPreferredSize(new Dimension(200,90));

        setToolTipText("Kauffenster");
        setLayout(new GridLayout(4,2));
        add(lblWare); add(lblBlank);

        add(lblGewMenge); add(tfGewMenge);
        add(lblMaxPreis); add(tfMaxPreis);
        add(btKauf);

        btKauf.addActionListener(this);
    }//JKaufen
    /**
     * Bearbeitung der Events der Buttons
     * @param e Das Actionevent der Buttons
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource()== btKauf ) {
            // TODO(Tharre): Order an Server schicken
            btKauf.setText("gekauft");
        }//if
    }//actionPerformed

}//class JKaufen
