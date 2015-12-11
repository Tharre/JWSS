import java.awt.*;
import javax.swing.*;

public class JStatistik extends JPanel {

    int[] x = new int[20];
    int[] y = {40,100,60,300,30,49,40,100,60,300,30,49,40,100,60,300,30,49,300,400};
    int[] menge={20,100,20,100,20,100,20,100,20,100,20,100,20,100,20,100,20,100,20,200};
    private String[] waren = { "Holz","Wasser","Schnee","Stein","Diamant" };
    private JComboBox c = new JComboBox();



    public JStatistik() {
        setToolTipText("Statistik");
        for (int i = 0; i < waren.length; i++)
            c.addItem(waren[i]);
        add(c);
    }//JStatistik

    protected void paintComponent(Graphics g) {

		super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();
        int breite=(w-60)/x.length;

        g.drawLine(30,30,30,h-30);
        g.drawLine(30, h-30, w-30,h-30);

        for(int i=0; i<x.length; i++) {
            x[i] = 30 + breite*i ;
        }//for

        for(int j=0; j<menge.length; j++){
            g.setColor(Color.GREEN);
            g.drawRect(w-(w-(30+breite*j)), h-30-menge[j], breite, menge[j]);
            if(j%2 == 0)
            g.setColor(Color.RED);
            else
            g.setColor(Color.BLUE);
            g.fillRect(w-(w-(30+breite*j)), h-30-menge[j], breite, menge[j]);
        }//for

        g.setColor(Color.red);
        g.drawPolyline(x,y,x.length);
    }//paintComponent

}//class Statistik
