package org.htl_hl.bibiProject.ServerGUI;

import java.awt.*;
import javax.swing.*;
/**
 * <p>Title: JSpieler</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse JSpieler.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JSpieler extends JPanel {
    /** lblName - Private Eigenschaft der Klasse JSpieler vom Typ JLabel.<br>
     */
    private JLabel lblName;
    /** lblVermoegen - Private Eigenschaft der Klasse JSpieler vom Typ JLabel.<br>
     */
    private JLabel lblVermoegen;
    /** money - Private Eigenschaft der Klasse JSpieler vom Typ double.<br>
     * Verm&ouml;gen des Spielers.
     */
    private double money;
    /** name - Private Eigenschaft der Klasse JSpieler vom Typ String.<br>
     * Name des Spielers.
     */
    private String name;
//
    public JSpieler(String name, double money) {
        this.name=name;
        this.money=money;
        setPreferredSize(new Dimension(160,90));
        setToolTipText("Spielerdetails");
        setLayout(new GridLayout(2,1));
        lblName = new JLabel("Name: "+name);
        lblVermoegen = new JLabel("Vermögen: "+money);
        add(lblName);
        add(lblVermoegen);
    }//JSpieler

}//class Statistik
