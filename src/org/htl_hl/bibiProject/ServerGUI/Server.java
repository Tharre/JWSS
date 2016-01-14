package org.htl_hl.bibiProject.ServerGUI;

import org.htl_hl.bibiProject.Common.Game;
import org.htl_hl.bibiProject.Common.HttpUtil;
import org.htl_hl.bibiProject.Common.Player;
import org.htl_hl.bibiProject.Common.Stock;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

/**
 * <p>Title: Server</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes Börsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class Server extends JFrame implements Runnable{

    private JPanel pSpieler = new JPanel(new GridBagLayout());
    private JPanel pWaren = new JPanel();

    private JStatistik statistik = new JStatistik();
    private JScrollPane scSpieler = new JScrollPane(pSpieler,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private Game game;
    private String server;

    private Thread runner = new Thread(this);

    private JSpieler spielerAr[];

    public Server (String server){
        super("Client JWSS");
        this.server=server;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo (null);

        addWindowListener (new WindowAdapter(){
            public void windowClosing(WindowEvent e1){
                dispose();
                stop();
            }//windowClosing
            public void windowOpened(WindowEvent e1){
                runner.start();
            }
        });//WindowListener

        setTitle("Server");
        JTabbedPane jtp = new JTabbedPane();

        getContentPane().add(jtp);
        JPanel jp1 = new JPanel();
        JPanel jp3 = new JPanel();
        jtp.addTab("Waren", jp1);
        jtp.addTab("Spieler", scSpieler);
        jtp.addTab("Statistik", statistik);

        update();


    }//Server

    public void update(){
        try{
            GridBagConstraints gc1 = new GridBagConstraints();
            gc1.fill = GridBagConstraints.BOTH;
            gc1.insets = new Insets(5, 5, 5, 5);
            this.game = HttpUtil.sendGet(server, "games/0/", Game.class);
            Player[] players = HttpUtil.sendGet(server, "games/" + game.getId() + "/players", Player[].class);
            spielerAr = new JSpieler[players.length];

            for(int i=0; i<spielerAr.length; i++){
                if(i%2 == 0){
                    gc1.gridx = 0;
                    gc1.gridy = i;
                }else{
                    gc1.gridx = 1;
                    gc1.gridy = i-1;
                }//else

                spielerAr[i] = new JSpieler(players[i].getName(), players[i].getMoney());
                pSpieler.add(spielerAr[i], gc1);
            }//for
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }//update

    public static void main(String[] args){
        String ip = "http://127.0.0.1:8000";
        Server f = new Server(ip);
        f.setVisible(true);
    }//main

    public void run(){
        Thread ich=Thread.currentThread();
        while(ich == runner){
            try{
                Thread.sleep(5000);
                update();
                System.out.println("Update ...");
            }catch(InterruptedException e){}
        }//while
    }//run

    public void stop(){
        if (runner != null)
            runner=null;
    }//stop
}//class Server
