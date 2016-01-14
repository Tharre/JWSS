package org.htl_hl.bibiProject.ServerGUI;

import java.awt.*;
import javax.swing.*;

public class JSpieler extends JPanel {
    private JLabel lblName = new JLabel("Name:");
    private JLabel lblVermoegen = new JLabel("Vermögen:");

    public JSpieler() {
        setPreferredSize(new Dimension(120,90));

        setToolTipText("Spielerdetails");
        setLayout(new GridLayout(2,1));
        add(lblName);
        add(lblVermoegen);
    }//JSpieler

}//class Statistik
