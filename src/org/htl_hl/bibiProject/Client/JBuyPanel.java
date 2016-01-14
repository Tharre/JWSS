package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JBuyPanel extends JPanel implements ActionListener {

    private String server;
    private Game game;
    private Player player;
    private Item item;

    private JTextField tfQuantity = new JTextField();
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

    /**
     * Bearbeitung der Events der Buttons
     * @param e Das Actionevent der Buttons
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
