package org.htl_hl.bibiProject.Common;

import java.util.Date;
import java.util.List;
/**
 * <p>Title: Round</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Round.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class Round {
    /** id- Private Eigenschaft der Klasse Round vom Typ int.<br>
     * Jede Runde hat eine eindeutige ID.
     */
    private int id;
    /** orders - Private Eigenschaft der Klasse Round vom Typ List<Order>.<br>
     * Jede Runde hat eine Liste mit Orders.
     */
    private List<Order> orders;
    /** endsAt - Private Eigenschaft der Klasse Round vom Typ Date.<br>
     * Jede Runde hat ein Datum mit Uhrzeit an dem die Runde endet.
     */
    private Date endsAt;

    public Round() {
        this(0, null, null);
    }
    public Round(int id, List<Order> orders, Date endsAt) {
        this.id = id;
        this.orders = orders;
        this.endsAt = endsAt;
    }

    /** Methode zum Abrufen der ID der Runde.
     * @return id ID der Runde
     */
    public int getId() {
        return id;
    }

    /** Methode zum Abrufen der Orders der Runde.
     * @return orders Liste der Orders der Runde
     */
    public List<Order> getOrders() {
        return orders;
    }
//
    /** Methode zum Abfragen von Datum & Uhrzeit an dem die Runde endet
     * @return endsAt Datum & Zeit an dem die Runde endet
     */
    public Date getEndsAt() {
        return endsAt;
    }
}
