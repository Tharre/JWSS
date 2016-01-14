package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

/**
 * <p>Title: Client</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */

public class Client extends JFrame implements ActionListener, Runnable {

    private JButton btBereit = new JButton("Bereit");
    /** lblSpieler - Private Eigenschaft der Klasse Client vom Typ JLabel.<br>
     */
    private JLabel lblSpieler;
    /** lblVermoegen - Private Eigenschaft der Klasse Client vom Typ JLabel.<br>
     */
    private JLabel lblVermoegen;
    /** lblRang - Private Eigenschaft der Klasse Client vom Typ JLabel.<br>
     */
    private JLabel lblRang;
    /** lblRunde - Private Eigenschaft der Klasse Client vom Typ JLabel.<br>
     */
    private JLabel lblRunde;
    /** lblZeit - Private Eigenschaft der Klasse Client vom Typ JUhr.<br>
     * Zeigt die Rundenzeit an.
     */
    private JUhr lblZeit = new JUhr();
    /** runner - Private Eigenschaft der Klasse Client vom Typ Runner.<br>
     * Thread f&uuml;r Sitzung.
     */
    private Thread runner = new Thread(this);
    /** player - Private Eigenschaft der Klasse Client vom Typ Player.<br>
     * Referenz auf Player.
     */
    private Player player;
    /** game - Private Eigenschaft der Klasse Client vom Typ Game.<br>
     * Referenz auf Game.
      */
    private Game game;
    /** server - Private Eigenschaft der Klasse Client vom Typ String.<br>
     * Adresse des Servers.
     */
    private String server;
    /** verkauf - Private Eigenschaft der Klasse Client vom Typ List<JVerkauf>.<br>
     * Liste der Verk&auml;ufe des Clients.
     */
    private List<JVerkauf> verkauf = new LinkedList<>();
    /** kauf - Private Eigenschaft der Klasse Client vom Typ List<JKaufen>.<br>
     * Liste der K&auml;ufe des Clients.
     */
    private List<JKaufen> kauf = new LinkedList<>();
    /** pVerkauf - Private Eigenschaft der Klasse Client vom Typ JPanel.<br>
     * Panel f&uuml;r die Verk&auml;ufe.
     */
    private JPanel pVerkauf = new JPanel(new GridBagLayout());
    /** pKauf - Private Eigenschaft der Klasse Client vom Typ JPanel.<br>
     * Panel f&uuml;r die Kk&auml;ufe.
     */
    private JPanel pKauf = new JPanel(new GridBagLayout());
    /** pSOTUTH - Private Eigenschaft der Klasse Client vom Typ JPanel.<br>
     * Hilfspanel im S&uuml;den.
     */
    private JPanel pSOUTH = new JPanel(new GridLayout(1, 2));
    /** pNORTH - Private Eigenschaft der Klasse Client vom Typ JPanel.<br>
     * Hilfspanel Norden.
     */
    private JPanel pNORTH = new JPanel(new GridLayout(1, 4));
    /** scVerkauf - Private Eigenschaft der Klasse Client vom Typ JScrollPane.<br>
     * Scrollpane f&uuml;r die Verk&auml;ufe.
     */
    private JScrollPane scVerkauf = new JScrollPane(pVerkauf);
    /** scKauf - Private Eigenschaft der Klasse Client vom Typ JScrollPane.<br>
     * Scrollpane f&uuml;r die K&auml;ufe.
     */
    private JScrollPane scKauf = new JScrollPane(pKauf, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    public Client(Player player, Game game, String server) {
        super("Client JWSS");

        this.player = player;
        this.game = game;
        this.server = server;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        Round round = null;
        try {
            Player[] players = HttpUtil.sendGet(server, "games/" + game.getId() + "/players", Player[].class);
            lblSpieler = new JLabel("Spieler in Sitzung: " + players.length);

            round = HttpUtil.sendGet(server, "games/" + game.getId() + "/rounds", Round.class);
            lblRunde = new JLabel("Runde: " + round.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        lblVermoegen = new JLabel("Vermögen: " + player.getMoney());
        lblRang = new JLabel("Rang: ");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e1) {
                btBereit.setEnabled(false);
                lblZeit.stop();
                dispose();
                stop();
            }//windowClosing
        });//WindowListener

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        btBereit.addActionListener(this);
        btBereit.setFocusable(false);

        GridBagConstraints gc1 = new GridBagConstraints();
        gc1.fill = GridBagConstraints.BOTH;
        gc1.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < player.getStocks().size(); i++) {
            if (i % 2 == 0) {
                gc1.gridx = i;
                gc1.gridy = 0;
            }//if
            else {
                gc1.gridx = i - 1;
                gc1.gridy = 1;
            }//else
            verkauf.add(i, new JVerkauf());
            verkauf.get(i).setLblWare(player.getStocks().get(i).getItem().getName());
            verkauf.get(i).setLblVMenge(player.getStocks().get(i).getQuantity());
            pVerkauf.add(verkauf.get(i), gc1);
            verkauf.get(i).btVerk.addActionListener(this);
        }//for

        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = GridBagConstraints.BOTH;
        gc2.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < round.getOrders().size(); i++) {
            gc2.gridx = 0;
            gc2.gridy = i;
            kauf.add(i, new JKaufen(server, game, player));
            pKauf.add(kauf.get(i), gc2);
            kauf.get(i).btKauf.addActionListener(this);
        }//for

        pSOUTH.add(lblSpieler);
        pSOUTH.add(btBereit);
        pNORTH.add(lblVermoegen);
        pNORTH.add(lblRang);
        pNORTH.add(lblRunde);
        pNORTH.add(lblZeit);
        c.add(pNORTH, BorderLayout.NORTH);
        c.add(scVerkauf, BorderLayout.CENTER);
        c.add(scKauf, BorderLayout.EAST);
        c.add(pSOUTH, BorderLayout.SOUTH);
    }//Client

    /**
     * Bearbeitung der Events der Buttons
     * @param e Das Actionevent der Buttons
     */
    public void actionPerformed(ActionEvent e) {
        Round round = null;
        try {
            round = HttpUtil.sendGet(server, "games/" + game.getId() + "/rounds", Round.class);
        } catch (IOException e1) {
            e1.printStackTrace();
        }//catch

        if (e.getSource() == btBereit) {
            lblZeit.start();
            btBereit.setText("Spielername: " + player.getName());
            System.out.println();
            runner.start();
        }//if

        for (int i = 0; i <round.getOrders().size(); i++) { // TODO(Tharre): anzahl orders
            if (e.getSource() == kauf.get(i).btKauf)
                kauf.get(i).lblWare.setText("yes");
        }//for

        for (int i = 0; i < player.getStocks().size(); i++) {
            if (e.getSource() == verkauf.get(i).btVerk) {
                String parameters = "itemId=2&playerId=0&isBuy=false&limit=1000&quantity=3";
                try {
                    Order o = HttpUtil.sendPost(server, "/games/" + game.getId() + "/rounds/" + round.getId() +
                            "/orders/", parameters, Order.class);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }//for

    }//actionPerformed

    public void update(){
        Round round = null;
        try {
            this.game = HttpUtil.sendGet(server, "games/0/", Game.class);
            Player[] players = HttpUtil.sendGet(server, "games/" + game.getId() + "/players", Player[].class);
            lblSpieler.setText("Spieler in Sitzung: " + players.length);

            round = HttpUtil.sendGet(server, "games/" + game.getId() + "/rounds", Round.class);
            lblRunde.setText("Runde: " + round.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        lblVermoegen.setText("Vermögen: " + player.getMoney());
        lblRang.setText("Rang: ");
    }//update
    public void run(){
        Round round = null;
        try {
            round = HttpUtil.sendGet(server, "games/" + game.getId() + "/rounds", Round.class);
            Thread ich=Thread.currentThread();
            while(ich == runner){
                try{
                    System.out.println("Sleeping for " + (round.getEndsAt().getTime()-System.currentTimeMillis()));
                    Thread.sleep(/*round.getEndsAt().getTime()-System.currentTimeMillis()*/5000);
                    update();
                    System.out.println("Update ...");
                }catch(InterruptedException e){}
            }//while
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//run

    public void stop(){
        if (runner != null)
            runner=null;
    }//stop

}//class Client
