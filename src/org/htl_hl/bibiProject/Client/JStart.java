package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.Game;
import org.htl_hl.bibiProject.Common.HttpUtil;
import org.htl_hl.bibiProject.Common.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

/**
 * <p>Title: Start</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JStart extends JFrame implements ActionListener{

    private JLabel name = new JLabel("Name:");
    private JLabel server = new JLabel("Server:");
    private JTextField tfServer = new JTextField();
    private JTextField tfName = new JTextField();
    private JButton btStart = new JButton("Start");

    /**
     * Erzeugen eines Startfensters auf Basis eines JFrames
     */
    public JStart() {
        super("Start JWSS");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300,200);
        tfName.setPreferredSize(new Dimension(70,25));
        setLocationRelativeTo(null);
        Container c = getContentPane();
        c.setLayout(new GridLayout(3,2));

        btStart.addActionListener(this);

        c.add(name);
        c.add(tfName);
        c.add(server);
        c.add(tfServer);
        c.add(btStart);
    }

    /**
     *Erzeugt einen neuen Client
     * @param e
     */
    public void actionPerformed(ActionEvent e){
        if (tfName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Bitte Spielernamen eingeben", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String serverUrl = "http://" + tfServer.getText() + ":8000";
        try {
            Game[] games = HttpUtil.sendGet(serverUrl, "/games", Game[].class);
            Game g;
            // TODO(Tharre): race condition
            if (games.length == 0)
                g = HttpUtil.sendPost(serverUrl, "/games", "name=" + tfName.getText(), Game.class);
            else
                g = HttpUtil.sendGet(serverUrl, "/games/0/", Game.class);

            Player p = HttpUtil.sendPost(serverUrl, "/games/" + g.getId() + "/players/",
                    "name=" + tfName.getText(), Player.class);
            Client f = new Client(p, g, serverUrl);
            f.setVisible(true);
            dispose();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unbekannter Hostname", "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Keine g&uuml;ltige IP oder Hostname", "Fehler", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Verbindung zum Server fehlgeschlagen", "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Erzeugen eines JFrame-Fensters
     * @param args nicht verwendet
     */
    public static void main(String[] args){
        JStart s = new JStart();
        s.setVisible(true);
    }
}
