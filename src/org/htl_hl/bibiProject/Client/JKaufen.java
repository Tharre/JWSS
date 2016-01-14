package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * <p>Title: JKaufen</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse JKaufen.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JKaufen extends JPanel implements ActionListener {
    /** btKauf - Private Eigenschaft der Klasse JKaufen vom Typ JButton.<br>
     * Button mit dem Text "Bestellen".
     */
    JButton btKauf = new JButton("Bestellen");
    /** lblWare - Private Eigenschaft der Klasse JKaufen vom Typ JLabel.<br>
     * Label mit dem Text "Name".
     */
    JLabel lblWare = new JLabel("Name");
    /** lblGewMenge - Private Eigenschaft der Klasse JKaufen vom Typ JLabel.<br>
     * Label mit dem Text "gew. Menge: ".
     */
    private JLabel lblGewMenge= new JLabel("gew. Menge: ");
    /** lblMaxPreis - Private Eigenschaft der Klasse JKaufen vom Typ JLabel.<br>
     * Label mit dem Text "max. Preis: ".
     */
    private JLabel lblMaxPreis= new JLabel("max. Preis: ");
    /** lblBlank - Private Eigenschaft der Klasse JKaufen vom Typ JLabel.<br>
     *  Leeres Label aus optischen Gr&uuml;nden.
     */
    private JLabel lblBlank = new JLabel("");
    /** tfPreis - Private Eigenschaft der Klasse JVerkaufen vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r die gew&uuml;nschte Menge.
     */
    private JTextField tfGewMenge= new JTextField("");
    /** tfPreis - Private Eigenschaft der Klasse JVerkaufen vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r den maximalen Preis.
     */
    private JTextField tfMaxPreis= new JTextField("");
    /** ip - Private Eigenschaft der Klasse JKaufen vom Typ String.<br>
     * IP-Adresse des Servers.
     */
    private String ip;
    /** game - Private Eigenschaft der Klasse JKaufen vom Typ Game.<br>
     * Referenz auf das Game.
     */
    private Game game;
    /** player - Private Eigenschaft der Klasse JKaufen vom Typ Player.<br>
     * Referenz auf den Player.
     */
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

    /** Methode zum Abarbeiten der Klick-Events von btKauf.<br>
     * Eine Order wird an den Server gesendet und der Button-Text ge&auml;ndert.
     * @param e ActionEvent - Klick-Event von btKauf
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
