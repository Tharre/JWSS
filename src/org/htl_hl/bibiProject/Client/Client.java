package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.Player;
import org.htl_hl.bibiProject.Common.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.LinkedList;

public class Client extends JFrame implements ActionListener {

    private JButton btBereit = new JButton("Bereit");

    private JLabel lblSpieler;
    private JLabel lblVermoegen;
    private JLabel lblRang;
    private JLabel lblRunde;

    private JUhr lblZeit = new JUhr();

    private Player player;

    private List<JVerkauf> verkauf = new LinkedList<>();
    private List<JKaufen> kauf = new LinkedList<>();

    private JPanel pVerkauf = new JPanel(new GridBagLayout());
    private JPanel pKauf = new JPanel(new GridBagLayout());
    private JPanel pSOUTH = new JPanel(new GridLayout(1, 2));
    private JPanel pNORTH = new JPanel(new GridLayout(1, 4));

    private JScrollPane scVerkauf = new JScrollPane(pVerkauf);
    private JScrollPane scKauf = new JScrollPane(pKauf, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    public Client(Player player) {
        super("Client JWSS");

        this.player = player;
        // TODO(Tharre): Spieleranzahl (fix), Rang (fix), Runde vom Server holen

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        lblSpieler = new JLabel("Spieler in Sitzung: "); // TODO(Tharre): implement API endpoint
        lblVermoegen = new JLabel("Vermögen: " + player.getMoney());
        lblRang = new JLabel("Rang: "); // TODO(Tharre): implement API endpoint
        lblRunde = new JLabel("Runde: "); // TODO(Tharre): implement feature

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e1) {
                btBereit.setEnabled(false);
                lblZeit.stop();
                dispose();
            }//windowClosing
        });//WindowListener

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        btBereit.addActionListener(this);
        btBereit.setFocusable(false);

        GridBagConstraints gc1 = new GridBagConstraints();
        gc1.fill = GridBagConstraints.BOTH;
        gc1.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < 4; i++) {
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

        for (int i = 0; i < 4; i++) {
            gc2.gridx = 0;
            gc2.gridy = i;
            kauf.add(i, new JKaufen());
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btBereit) {
            lblZeit.start();
            btBereit.setText("Spielername: " + player.getName());
            System.out.println();
        }//if

        for (int i = 0; i < 4; i++) {
            if (e.getSource() == kauf.get(i).btKauf)
                kauf.get(i).lblWare.setText("yes");
        }//for

        for (int i = 0; i < 4; i++) {
            if (e.getSource() == verkauf.get(i).btVerk)
                verkauf.get(i).tfPreis.setText("yes");
        }//for


    }//actionPerformed

}//class Client
