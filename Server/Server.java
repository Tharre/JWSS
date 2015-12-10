import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;


public class Server extends JFrame implements ActionListener{


    private JButton btBereit = new JButton("Bereit");
    private JStatistik statistik = new JStatistik();

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
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();
        JPanel jp4 = new JPanel();
        jp1.add(btBereit);
        jtp.addTab("Waren", jp1);
        jtp.addTab("Spieler", jp2);
        jtp.addTab("Log", jp3);
        jtp.addTab("Statistik", statistik);

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