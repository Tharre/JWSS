package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

/**
 * <p>Title: Client</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes Börsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class Client extends JFrame implements ActionListener {

    private JButton btReady = new JButton("Bereit");

    private JLabel lblPlayer = new JLabel();
    private JLabel lblWealth = new JLabel();
    private JLabel lblRound = new JLabel();
    private JLabel lblCountdown = new JLabel();

    private Timer timer;
    private int countdown;

    private Player player;
    private Game game;
    private String server;

    private JPanel pVerkauf = new JPanel(new GridBagLayout());
    private JPanel pKauf = new JPanel(new GridBagLayout());
    private JPanel pSouth = new JPanel(new GridLayout(1, 2));
    private JPanel pNorth = new JPanel(new GridLayout(1, 4));

    private JScrollPane scVerkauf = new JScrollPane(pVerkauf);
    private JScrollPane scKauf = new JScrollPane(pKauf, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    /**
     * Erzeugen eines Clients auf Basis eines JFrames
     * @param player
     */
    public Client(Player player, Game game, String server) {
        super("Client JWSS");

        this.player = player;
        this.game = game;
        this.server = server;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;

                if (countdown < 0 ) {
                    update();
                    resyncTimer();
                }

                lblCountdown.setText("" + countdown);
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        btReady.addActionListener(this);

        lblPlayer.setText("Name: " + player.getName());
        update();

        pSouth.add(lblPlayer);
        pSouth.add(btReady);
        pNorth.add(lblWealth);
        pNorth.add(lblRound);
        pNorth.add(lblCountdown);
        c.add(pNorth, BorderLayout.NORTH);
        c.add(scVerkauf, BorderLayout.CENTER);
        c.add(scKauf, BorderLayout.EAST);
        c.add(pSouth, BorderLayout.SOUTH);
    }

    private void update() {
        try {
            game = HttpUtil.sendGet(server, "games/" + game.getId(), Game.class);
            player = game.getPlayers().get(player.getId());

            lblWealth.setText("Geld: " + player.getMoney());
        } catch (IOException e) {
            e.printStackTrace();
        }

        pVerkauf.removeAll();
        pKauf.removeAll();

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(5, 5, 5, 5);

        int helper = 0;
        for (Stock stock : player.getStocks()) {
            if (helper % 2 == 0) {
                gc.gridx = helper;
                gc.gridy = 0;
            } else {
                gc.gridx = helper - 1;
                gc.gridy = 1;
            }

            pVerkauf.add(new JSellPanel(server, game, player, stock), gc);
            helper++;
        }

        // get unique items that are in the game (excluding the player)
        List<Item> uniqItems = new LinkedList<>();
        for (Player p : game.getPlayers()) {
            if (p.equals(player))
                continue;

            for (Stock stock : p.getStocks()) {
                if (!uniqItems.contains(stock.getItem()))
                    uniqItems.add(stock.getItem());
            }
        }

        helper = 0;
        for (Item item : uniqItems) {
            gc.gridx = 0;
            gc.gridy = helper;
            pKauf.add(new JBuyPanel(server, game, player, item), gc);
            helper++;
        }

        pKauf.revalidate();
        pVerkauf.revalidate();
    }

    /**
     * Bearbeitung der Events der Buttons
     * @param e Das Actionevent der Buttons
     */
    public void actionPerformed(ActionEvent e) {
        update();
        resyncTimer();
    }

    public void resyncTimer() {
        try {
            Round currRound = HttpUtil.sendGet(server, "games/" + game.getId() + "/rounds", Round.class);
            lblRound.setText("Runde: " + currRound.getId());
            countdown = (int) ((currRound.getEndsAt().getTime() - System.currentTimeMillis()) / 1000);
            lblCountdown.setText("" + countdown);
            timer.setInitialDelay((int) ((currRound.getEndsAt().getTime() - System.currentTimeMillis()) % 1000));
            timer.restart();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
