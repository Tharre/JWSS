package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * <p>Title: JBuyPanel</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse JBuyPanel.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JBuyPanel extends JPanel implements ActionListener {
    /** server - Private Eigenschaft der Klasse Client vom Typ String.<br>
     * Referenz auf Server.
     */
    private String server;
    /** game - Private Eigenschaft der Klasse Client vom Typ Game.<br>
     * Referenz auf Game.
     */
    private Game game;
    /** player - Private Eigenschaft der Klasse Client vom Typ Player.<br>
     * Referenz auf Player.
     */
    private Player player;
    /** item - Private Eigenschaft der Klasse Client vom Typ Item.<br>
     * Referenz auf Item.
     */
    private Item item;
    /** tfQuantity - Private Eigenschaft der Klasse Client vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r die Menge.
     */
    private JTextField tfQuantity = new JTextField();
    /** tfLimit - Private Eigenschaft der Klasse Client vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r den maximalen/minimalen Preis.
     */
    private JTextField tfLimit = new JTextField();

    public JBuyPanel(String server, Game game, Player player, Item item) {
        this.server = server;
        this.game = game;
        this.player = player;
        this.item = item;

        setPreferredSize(new Dimension(200, 90));
        setToolTipText("Kauffenster");
        setLayout(new GridLayout(4, 2));
        add(new JLabel("Name"));
        add(new JLabel(item.getName()));
        add(new JLabel("gew. Menge: "));
        add(tfQuantity);
        add(new JLabel("max. Preis"));
        add(tfLimit);
        JButton btBuy = new JButton("Bestellen");
        add(btBuy);

        btBuy.addActionListener(this);
    }

    /** Methode zum Abarbeiten der Klick-Events.<br>
     * @param e ActionEvent - Klick-Event des Button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (tfQuantity.getText().equals("") || tfLimit.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Werte fehlen", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double limit;
        long quantity;
        try {
            limit = Double.parseDouble(tfLimit.getText());
            quantity = Long.parseLong(tfQuantity.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Werte sind ungültig", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Buying item " + item.getName() + " x" + quantity + " at "
                + limit + " Money a piece");

        String parameters = "itemId=" + item.getId() + "&playerId=" + player.getId() + "&isBuy=true&limit="
                + limit + "&quantity=" + quantity;
        try {
            Order o = HttpUtil.sendPost(server, "games/" + game.getId() + "/orders",
                    parameters, Order.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        tfQuantity.setText("");
        tfLimit.setText("");
    }
}
