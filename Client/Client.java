import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener{


    private JButton btBereit = new JButton("Bereit");

    private JLabel lblSpieler = new JLabel("Spieler in Sitzung: ");

    private int anzWaren=10;
    private int anzAngebote=10;

    private JVerkauf verkauf[] = new JVerkauf[anzWaren];
    private JKaufen kauf[] = new JKaufen[anzAngebote];

    private JPanel pVerkauf = new JPanel(new GridBagLayout());
    private JPanel pKauf = new JPanel(new GridBagLayout());
    private JPanel pSOUTH = new JPanel();

    private JScrollPane scVerkauf = new JScrollPane(pVerkauf);
    private JScrollPane scKauf = new JScrollPane(pKauf);

    public Client (){
        super("Client JWSS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        setLocationRelativeTo (null);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        addWindowListener (new WindowAdapter(){
            public void windowClosing(WindowEvent e1){
                btBereit.setEnabled(false);
                dispose();
            }//windowClosing
        });//WindowListener

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        btBereit.addActionListener(this);

        GridBagConstraints gc1 = new GridBagConstraints();
        gc1.fill = GridBagConstraints.BOTH;
        gc1.insets = new Insets(5, 5, 5, 5);

        for(int i=0; i<verkauf.length; i++){
            if(i % 2 == 0) {
                gc1.gridx = i;
                gc1.gridy = 0;
            }//if
            else {
                gc1.gridx = i;
                gc1.gridy = 1;
            }//else
                verkauf[i] = new JVerkauf();
                pVerkauf.add(verkauf[i], gc1);
        }//for

        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.fill = GridBagConstraints.BOTH;
        gc2.insets = new Insets(5, 5, 5, 5);

        for(int i=0; i<kauf.length; i++){
                gc2.gridx = 0;
                gc2.gridy = i;
            kauf[i] = new JKaufen();
            pKauf.add(kauf[i], gc2);
        }//for

        pSOUTH.add(lblSpieler);
        pSOUTH.add(btBereit);
        c.add(scVerkauf, BorderLayout.CENTER);
        c.add(scKauf, BorderLayout.EAST);
        c.add(pSOUTH, BorderLayout.SOUTH);
    }//JSender

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == btBereit)
            btBereit.setText("funkt");
    }//actionPerformed

    public static void main(String[] args){
        Client f = new Client();
        f.setVisible(true);
    }//main

}//classJSender