package org.htl_hl.bibiProject.Client;

import org.htl_hl.bibiProject.Common.Game;
import org.htl_hl.bibiProject.Common.HttpUtil;
import org.htl_hl.bibiProject.Common.Order;
import org.htl_hl.bibiProject.Common.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class JStart extends JFrame implements ActionListener{

    private JLabel name= new JLabel("Name:");
    private JLabel ipaddr= new JLabel("IP Adresse Server:");
    private JTextField tfIpaddr= new JTextField("");
    private JTextField tfName= new JTextField("");
    private JButton btStart = new JButton("Start");

    public JStart(){
        super("Start JWSS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        tfName.setPreferredSize(new Dimension(70,25));
        setLocationRelativeTo (null);
        Container c = getContentPane();
        c.setLayout(new FlowLayout());

        addWindowListener (new WindowAdapter(){
            public void windowClosing(WindowEvent e1){
                btStart.setEnabled(false);
                dispose();
            }//windowClosing
        });//WindowListener

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        btStart.addActionListener(this);

        c.add(name);
        c.add(tfName);
        c.add(ipaddr);
        c.add(tfIpaddr);
        c.add(btStart);
    }//JStart

    public void actionPerformed(ActionEvent e){
        try {
            Game g = HttpUtil.sendPost(tfIpaddr.getText(), "/games", "name=JGame", Game.class);
            Player p = HttpUtil.sendPost(tfIpaddr.getText(), "/games/" + g.getId() + "/players/",
                    "name=" + tfName.getText(), Player.class);
            Client f = new Client(p, tfIpaddr.getText());
            f.setVisible(true);
            dispose();
        } catch (IOException e1) {
            // TODO(Tharre): exception handling
            e1.printStackTrace();
        }
    }//actionPerformed

    public static void main(String[] args){
        JStart s = new JStart();
        s.setVisible(true);
    }//main
}//class JStart
