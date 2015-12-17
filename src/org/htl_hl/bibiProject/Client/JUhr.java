package org.htl_hl.bibiProject.Client;

import java.util.Calendar;
import javax.swing.*;

public class JUhr extends JLabel implements Runnable{
    private long millisekundenStart, millisekundenBisher;
    private boolean uhrAktiv = false;
    private Calendar dauer;
    private int min, sek, ms;
    public JUhr() {
        setText("Rundenzeit: ");
    }//JUhr


    public void start() {
        if (!uhrAktiv) {
            uhrAktiv = true;
            Thread th = new Thread(this); // Thread anlegen
            millisekundenStart = System.currentTimeMillis();
            th.start(); // Thread starten
        }//if
    }//start

    public void stop() {
        uhrAktiv = false;
    }//sto

    public void run() {
        StringBuffer ausgabe;
        while (uhrAktiv) {
            ausgabe=new StringBuffer();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//catch
            millisekundenBisher = System.currentTimeMillis()
                    - millisekundenStart;
            dauer = Calendar.getInstance();
            dauer.setTimeInMillis(millisekundenBisher);
            min=dauer.get(Calendar.MINUTE);
            sek=dauer.get(Calendar.SECOND);
            ms=dauer.get(Calendar.MILLISECOND);

            if (min <= 1) {
                ausgabe.append("0"+min+":");
            } else {
                ausgabe.append(min+":");
            }//else
            if (sek <= 9) {
                ausgabe.append("0"+sek+":");
            } else {
                ausgabe.append(sek+":");
            }//else
            if (ms <= 9) {
                ausgabe.append("00"+ms);
            } else if (ms <= 99) {
                ausgabe.append("0"+ms);
            } else {
                ausgabe.append(ms);
            }//else
            this.setText("Rundenzeit: "+(ausgabe.toString()));
        }//while
    }//run
}//class JUhr

