package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * <p>Title: JSellPanel</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse JSellPanel.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JSellPanel extends JPanel implements ActionListener {
    /** server - Private Eigenschaft der Klasse Client vom Typ Server.<br>
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
    /** stock - Private Eigenschaft der Klasse Client vom Typ Stock.<br>
     * Referenz auf Stock.
     */
    private Stock stock;
    /** tfQuantity - Private Eigenschaft der Klasse Client vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r die Menge.
     */
    private JTextField tfQuantity = new JTextField();
    /** tfLimit - Private Eigenschaft der Klasse Client vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r den minimalen Preis.
     */
    private JTextField tfLimit = new JTextField();

    /**
     * Erzeugen eines Verkauffensters auf Basis eines JPanel
     */
    public JSellPanel(String server, Game game, Player player, Stock stock){
        this.server = server;
        this.game = game;
        this.player = player;
        this.stock = stock;
//
        setToolTipText("Verkaufsfenster");
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Name der Ware: "));
        add(new JLabel(stock.getItem().getName()));
        add(new JLabel("Vorhandene Menge: "));
        add(new JLabel(stock.getQuantity() + ""));
        add(new JLabel("Menge: "));
        add(tfQuantity);
        add(new JLabel("Minimum Preis: "));
        add(tfLimit);
        JButton btSell = new JButton("Verkaufen");
        add(btSell);

        btSell.addActionListener(this);
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

        System.out.println("Selling item " + stock.getItem().getName() + " x" + quantity + " at "
                + limit + " Money a piece");

        String parameters = "itemId=" + stock.getItem().getId() + "&playerId=" + player.getId() + "&isBuy=false&limit="
                + limit + "&quantity=" + quantity;
        try {
            Order o = HttpUtil.sendPost(server, "games/" + game.getId() + "/orders",
                    parameters, Order.class);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        tfQuantity.setText("");
        tfLimit.setText("");
    }
}
