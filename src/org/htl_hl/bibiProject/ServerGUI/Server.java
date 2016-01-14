package org.htl_hl.bibiProject.ServerGUI;

import java.awt.*;
import java.awt.event.*;
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
public class Server extends JFrame{

    private JPanel pSpieler = new JPanel(new GridBagLayout());
    private int anzSpieler = 10;
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
                dispose();
            }//windowClosing
        });//WindowListener

        setTitle("Server");
        JTabbedPane jtp = new JTabbedPane();

        getContentPane().add(jtp);
        JPanel jp1 = new JPanel();
        JPanel jp3 = new JPanel();
        jtp.addTab("Waren", jp1);
        jtp.addTab("Spieler", scSpieler);
        jtp.addTab("Log", jp3);
        jtp.addTab("Statistik", statistik);

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


    public static void main(String[] args){
        Server f = new Server();
        f.setVisible(true);
    }//main

}//class Server
