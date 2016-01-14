package org.htl_hl.bibiProject.ServerGUI;

import java.awt.*;
import javax.swing.*;

public class JSpieler extends JPanel {
    private JLabel lblName;
    private JLabel lblVermoegen;
    private double money;
    private String name;

    public JSpieler(String name, double money) {
        this.name=name;
        this.money=money;
        setPreferredSize(new Dimension(120,90));
        setToolTipText("Spielerdetails");
        setLayout(new GridLayout(2,1));
        lblName = new JLabel("Name: "+name);
        lblVermoegen = new JLabel("Vermögen: "+money);
        add(lblName);
        add(lblVermoegen);
    }//JSpieler

}//class Statistik
