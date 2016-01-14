package org.htl_hl.bibiProject.Client;

import java.util.Calendar;
import javax.swing.*;
/**
 * <p>Title: JUhr</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse JUhr.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class JUhr extends JLabel implements Runnable{
    /** millisekundenStart, millisekundenBisher - Private Eigenschaften der Klasse JUhr vom Typ long. <br>
     * millisekundenStart: Der Zeitpunkt in Millisekunden in dem die Uhr gestartet wurde. <br>
     * millisekundenBisher: Die Zeit in Millisekunden die seit dem Start der Uhr vergangen ist.
     */
    private long millisekundenStart, millisekundenBisher;
    /** uhrAktiv - Private Eigenschaft der Klasse JUhr vom Typ boolean.<br>
     * true = Uhr l&auml;uft gerade. <br>
     * false = Uhr l&auml;uft gerade nicht.
     */
    private boolean uhrAktiv = false;
    /** dauer - Private Eigenschaft der Klasse JUhr vom Typ Calender.<br>
     * Wird f&uuml;r die Ausgabe der Zeit in Minuten und Sekunden ben&ouml;tigt.
     */
    private Calendar dauer;
    /** min, sek, ms - Private Eigenschaften der Klasse JUhr vom Typ int.<br>
     * Hilfsvariablen f&uuml;r das Speichern von Minuten, Sekunden und Millisekunden.
     */
    private int min, sek, ms;
    public JUhr() {
        setText("Rundenzeit: ");
    }//JUhr

    /** Methode zum Starten des Threads f&uuml;r die Uhr.
     */
    public void start() {
        if (!uhrAktiv) {
            uhrAktiv = true;
            Thread th = new Thread(this); // Thread anlegen
            millisekundenStart = System.currentTimeMillis();
            th.start(); // Thread starten
        }//if
    }//start

    /** Methode zum Stoppen des Threads f&uuml;r die Uhr.
     */
    public void stop() {
        uhrAktiv = false;
    }//stop

    /** Methode zum sek&uuml;ndlichen Aktualisieren der Rundenzeit.
     */
    public void run() {
        StringBuffer ausgabe;
        while (uhrAktiv) {
            ausgabe=new StringBuffer();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//catch
            millisekundenBisher = System.currentTimeMillis() - millisekundenStart;
            dauer = Calendar.getInstance();
            dauer.setTimeInMillis(millisekundenBisher);
            min=dauer.get(Calendar.MINUTE);
            sek=dauer.get(Calendar.SECOND);

            if (min <= 9) {
                ausgabe.append("0"+min+":");
            } else {
                ausgabe.append(min+":");
            }//else
            if (sek <= 9) {
                ausgabe.append("0"+sek);
            } else {
                ausgabe.append(sek);
            }//else
            this.setText("Rundenzeit: "+(ausgabe.toString()));
            if(ausgabe.toString().equals("00:45")) {
                this.stop();
            }//if
        }//while
    }//run
}//class JUhr

