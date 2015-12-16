package org.htl_hl.bibiProject.ServerGUI;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;


public class Server extends JFrame implements ActionListener{

    private JPanel pSpieler = new JPanel(new GridBagLayout());
    private int anzSpieler = 8;
    private JButton btBereit = new JButton("Bereit");
    private JStatistik statistik = new JStatistik();
    private JScrollPane scSpieler = new JScrollPane(pSpieler,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JSpieler spielerAr[] = new JSpieler[anzSpieler];

    public Server (){
        super("Client JWSS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo (null);

        addWindowListener (new WindowAdapter(){
            public void windowClosing(WindowEvent e1){
                btBereit.setEnabled(false);
                dispose();
            }//windowClosing
        });//WindowListener


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        btBereit.addActionListener(this);

        setTitle("Server");
        JTabbedPane jtp = new JTabbedPane();

        getContentPane().add(jtp);
        JPanel jp1 = new JPanel();
        JPanel jp3 = new JPanel();
        jp1.add(btBereit);
        jtp.addTab("Waren", jp1);
        jtp.addTab("Spieler", scSpieler);
        jtp.addTab("Log", jp3);
        jtp.addTab("Statistik", statistik);

        btBereit.addActionListener(this);
        btBereit.setFocusable(false);

        GridBagConstraints gc1 = new GridBagConstraints();
        gc1.fill = GridBagConstraints.BOTH;
        gc1.insets = new Insets(5, 5, 5, 5);

        for(int i=0; i<spielerAr.length; i++){
			if(i%2 == 0){
            gc1.gridx = 0;
            gc1.gridy = i;
            }else{
            gc1.gridx = 1;
            gc1.gridy = i-1;
		    }//else

            spielerAr[i] = new JSpieler();
            pSpieler.add(spielerAr[i], gc1);
        }//for

    }//Server

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btBereit)
            btBereit.setText("ja");
    }//actionPerformed

    public static void main(String[] args){
        Server f = new Server();
        f.setVisible(true);
    }//main

}//class Server
