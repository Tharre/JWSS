package org.htl_hl.bibiProject.Client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * <p>Title: JVerkauf</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse JVerkauf.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JVerkauf extends JPanel {
    /** btVerk - Private Eigenschaft der Klasse JVerkauf vom Typ JButton.<br>
     * Button mit dem Text "Verkaufen".
     */
    public JButton btVerk = new JButton("Verkaufen");
    /** lblWare - Private Eigenschaft der Klasse JVerkaufen vom Typ JLabel.<br>
     * Label mit dem Text "Name von Ware".
     */
    private JLabel lblWare = new JLabel("Name von Ware");
    /** lblbVMenge - Private Eigenschaft der Klasse JVerkaufen vom Typ JLabel.<br>
     * Label mit dem Text "Vorhandene Menge".
     */
    private JLabel lblVMenge = new JLabel("Vorhandene Menge");
    /** lblBlank - Private Eigenschaft der Klasse JVerkaufen vom Typ JLabel.<br>
     * Leeres Label aus optischen Gr&uuml;nden.
     */
    private JLabel lblBlank = new JLabel("");
    /** lblBlank1 - Private Eigenschaft der Klasse JVerkaufen vom Typ JLabel.<br>
     * Leeres Label aus optischen Gr&uuml;nden.
     */
    private JLabel lblBlank1 = new JLabel("");
    /** lblMenge - Private Eigenschaft der Klasse JVerkaufen vom Typ JLabel.<br>
     * Label mit dem Text "Menge: ".
     */
    private JLabel lblMenge= new JLabel("Menge: ");
    /** lblMinPreis - Private Eigenschaft der Klasse JVerkaufen vom Typ JLabel.<br>
     * Label mit dem Text "Minimum Preis: ".
     */
    private JLabel lblMinPreis= new JLabel("Minimum Preis: ");
    /** tfMenge - Private Eigenschaft der Klasse JVerkaufen vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r die Menge.
     */
    private JTextField tfMenge= new JTextField("");
    /** tfPreis - Private Eigenschaft der Klasse JVerkaufen vom Typ JTextField.<br>
     * Eingabefeld f&uuml;r den Preis.
     */
    private JTextField tfPreis= new JTextField("");

    public JVerkauf (){
        setToolTipText("Verkaufsfenster");
        setLayout(new GridLayout(5,2));
        add(lblWare);
        add(lblBlank);
        add(lblVMenge);
        add(lblBlank1);
        add(lblMenge);
        add(tfMenge);
        add(lblMinPreis);
        add(tfPreis);
        add(btVerk);
        // TODO(Tharre): Items die der Spieler hat
    }//JVerkauf

    /** Methode zum Bearbeiten des Label-Textes bei einer Ware.
     * @param s String - Name der Ware
     */
    public void setLblWare(String s){
        lblWare.setText("Name von Ware : "+s);
    }
    /** Methode zum Bearbeiten des Label-Textes bei der Menge einer Ware.
     * @param s long - Menge der Ware
     */
    public void setLblVMenge(long s){
        lblVMenge.setText("Vorhandene Menge : "+s);
    }
}//class JVerkauf
