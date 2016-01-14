package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

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

    private String ip;
    private Game game;
    private Player player;

    public JKaufen (String ip, Game game, Player player){
        setPreferredSize(new Dimension(200,90));

        setToolTipText("Kauffenster");
        setLayout(new GridLayout(4,2));
        add(lblWare); add(lblBlank);

        add(lblGewMenge); add(tfGewMenge);
        add(lblMaxPreis); add(tfMaxPreis);
        add(btKauf);

        btKauf.addActionListener(this);
        this.ip = ip;
        this.game = game;
        this.player = player;
    }//JKaufen
    /**
     * Bearbeitung der Events der Buttons
     * @param e Das Actionevent der Buttons
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource()== btKauf ) {
            // TODO(Tharre): Order an Server schicken

            String parameters = "itemId=2&playerId=" + player.getId() + "&isBuy=true&limit=" + tfMaxPreis.getText() +
                    "&quantity=" + tfGewMenge.getText();
            try {
                Round r = HttpUtil.sendGet(ip, "/games", Round.class);
                Order o = HttpUtil.sendPost(ip, "/games/0/rounds/" + r.getId() + "/orders", parameters, Order.class);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            btKauf.setText("gekauft");
        }//if
    }//actionPerformed

}//class JKaufen
