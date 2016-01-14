package org.htl_hl.bibiProject.ServerGUI;

import org.htl_hl.bibiProject.Common.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.swing.*;

import static org.htl_hl.bibiProject.Common.Item.loadItems;

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
    private JTextArea tfWaren = new JTextArea();

    private JStatistik statistik = new JStatistik();
    private JScrollPane scSpieler = new JScrollPane(pSpieler,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JScrollPane scWaren = new JScrollPane(tfWaren);
    private Game game;
    private String server;

    private Item item;
    private Thread runner = new Thread(this);

    private JSpieler spielerAr[];
    private String waren[]= new String [81];

    public Server (String server) throws IOException {
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

        jtp.addTab("Waren", scWaren);
        jtp.addTab("Spieler", scSpieler);
        jtp.addTab("Statistik", statistik);

        Map<Integer, Item> m = Item.loadItems(new File("res/Items.json"));
        for(int w=1; w<waren.length; w++){
            waren[w] = m.get(w).getName();
            tfWaren.setEditable(false);
            tfWaren.setText(tfWaren.getText() + waren[w] + "\n");
        }

        update();


    }//Server

    public void update() throws IOException {
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

    public static void main(String[] args)  {
        String ip = "http://127.0.0.1:8000";
        try{
        Server f = new Server(ip);
            f.setVisible(true);
        }catch(IOException e){
            e.printStackTrace();
        }

    }//main

    public void run(){
        Thread ich=Thread.currentThread();
        while(ich == runner){
            try{
                Thread.sleep(5000);
                update();
                System.out.println("Update ...");
            }catch(InterruptedException e){} catch (IOException e) {
                e.printStackTrace();
            }
        }//while
    }//run

    public void stop(){
        if (runner != null)
            runner=null;
    }//stop
}//class Server
