import java.awt.*;
import javax.swing.*;

public class Verkauf extends JPanel {

	private JButton btVerk = new JButton("Verkaufen");

	private JLabel lblWare = new JLabel("Name von Ware");
	private JLabel lblVMenge = new JLabel("Vorhandene Menge");
	private JLabel lblBlank = new JLabel("");
	private JLabel lblBlank1 = new JLabel("");
	private JLabel lblMenge= new JLabel("Menge: ");
	private JLabel lblPreis= new JLabel("Preis: ");

	private JTextField tfMenge= new JTextField("");
	private JTextField tfPreis= new JTextField("");

	public Verkauf (){
		setToolTipText("Verkaufsfenster");
		setLayout(new GridLayout(5,2));
		add(lblWare);
		add(lblBlank);
		add(lblVMenge);
		add(lblBlank1);
		add(lblMenge);
		add(tfMenge);
		add(lblPreis);
		add(tfPreis);
		add(btVerk);





	}//Verkauf
}//JSchachbrett