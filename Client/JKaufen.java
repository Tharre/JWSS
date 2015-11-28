import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JKaufen extends JPanel implements ActionListener {

    private JButton btKauf = new JButton("Kaufen");

    private int menge;
    private int preis;

    private JLabel lblWare = new JLabel("Name von Ware");
    private JLabel lblMenge= new JLabel("Menge: " + menge);
    private JLabel lblPreis= new JLabel("Preis: " + preis);

    public JKaufen (){
        setPreferredSize(new Dimension(140,90));
        setToolTipText("Kauffenster");
        setLayout(new GridLayout(4,1));
        add(lblWare);
        add(lblMenge);
        add(lblPreis);
        add(btKauf);

        btKauf.addActionListener(this);
    }//JKaufen

    public void actionPerformed(ActionEvent e){
        if (e.getSource()== btKauf )
            btKauf.setText("gekauft");
    }//actionPerformed

}//class JKaufen
