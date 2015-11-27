import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener{


	private JButton btSubmit = new JButton("Submit");
	private JTextArea taInput = new JTextArea();
	private Verkauf verkauf1 = new Verkauf();
	private Verkauf verkauf2 = new Verkauf();
	private Verkauf verkauf3 = new Verkauf();
	private Verkauf verkauf4 = new Verkauf();
	private JPanel pVerkauf = new JPanel(new GridLayout(2,2));
	private JScrollPane scrollpane = new JScrollPane(pVerkauf);

	public Client (){
		super("Sender");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),300);
		setLocationRelativeTo (null);

		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		addWindowListener (new WindowAdapter(){
			public void windowClosing(WindowEvent e1){
				btSubmit.setEnabled(false);
				taInput.setEnabled(false);
				dispose();
			}//windowClosing
		});//WindowListener
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		btSubmit.addActionListener(this);
		pVerkauf.add(verkauf1);
		pVerkauf.add(verkauf2);
		pVerkauf.add(verkauf3);
		pVerkauf.add(verkauf4);

		c.add(btSubmit, BorderLayout.SOUTH);
		c.add(taInput, BorderLayout.NORTH);
		c.add(scrollpane, BorderLayout.CENTER);
	}//JSender

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btSubmit)
		taInput.setText("funkt");
	}//actionPerformed

	public static void main(String[] args){
		Client f = new Client();
		f.setVisible(true);
	}//main

}//classJSender