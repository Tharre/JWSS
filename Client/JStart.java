import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JStart extends JFrame implements ActionListener{
    private JLabel name= new JLabel("Name:");
    private JTextField tfName= new JTextField("");
            JButton btStart = new JButton("Start");

    public JStart(){
        super("Start JWSS");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,200);
        setLocationRelativeTo (null);
        Container c = getContentPane();
        c.setLayout(new GridLayout(2,2));

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
        c.add(btStart);
    }//JStart



    public void actionPerformed(ActionEvent e){
        Client f = new Client(tfName.getText());
        if(e.getSource() == btStart) {
            f.setVisible(true);
            setVisible(false);
        }//if
    }//actionPerformed

    public static void main(String[] args){
        JStart s = new JStart();
        s.setVisible(true);
    }//main
}//class JStart
