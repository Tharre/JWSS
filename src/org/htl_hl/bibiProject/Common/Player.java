package org.htl_hl.bibiProject.Common;

import java.util.List;
/**
 * <p>Title: Player</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Player.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class Player {
    /** id - private Eigenschaft von Player vom Typ int.<br>
     * Jeder Spieler hat eine eindeutige ID */
    private final int id;
    /** name - private Eigenschaft von Player vom Typ String.<br>
     * Jeder Spieler hat einen Namen */
    private final String name;
    /** stocks - private Eigenschaft von Player vom Typ List<Stock>.<br>
     * Jeder Spieler hat die gleiche Anzahl an Waren, in dieser Liste wird jede Ware die der Spieler besitzt gespeichert. */
    private final List<Stock> stocks;
    /** money - private Eigenschaft von Player vom Typ double.<br>
     * Jeder Spieler hat einen gewissen Geldbetrag, welcher zu Beginn des Spiels bei jedem Spieler XXX Euro betr&auml;gt. */
    private double money;

    public Player() {
        this(0, "", null, 0.0);
    }

    public Player(int id, String name, List<Stock> stocks, double money) {
        this.id = id;
        this.name = name;
        this.stocks = stocks;
        this.money = money;
    }

    /** Methode zum Abfragen der Spieler-ID.
     * @return id ID des Spielers
     */
    public int getId() {
        return id;
    }
    /** Methode zum Abfragen des Spielernamens.
     * @return name Name des Spielers
     */
    public String getName() {
        return name;
    }
    /** Methode zum Abfragen der Waren im Besitz von Spieler.
     * @return stocks List von Waren im Besitz des Spielers
     */
    public List<Stock> getStocks() {
        return stocks;
    }
    /** Methode zum Abfragen des aktuellen Geldbetrages von Spieler.
     * @return money Aktueller Geldbetrag des Spielers
     */
    public double getMoney() {
        return money;
    }
    /** Methode zum Abfragen einer Ware welche im Besitz des Spielers ist mittels der ID der Ware.
     * @param itemId int - eindeutige ID einer Ware
     * @return stock Ware im Besitz des Spielers
     */
    public Stock getStockByItemId(int itemId) {
        for (Stock stock : stocks) {
            if (stock.getItem().getId() == itemId)
                return stock;
        }
        return null;
    }
    /** Methode zum Bearbeiten der Waren im Besitz des Spielers.
     * @param index int - Index der zu bearbeitenden Ware in der Liste
     * @param quantity int - Neue St&uuml;ckzahl der zu bearbeitenden Ware
     */
    public void updateStock(int index, int quantity) {
        stocks.get(index).setQuantity(quantity);
    }
    /** Methode zum Bearbeiten des Geldbetrages des Spielers.
     * @param money double - Aktueller Geldbetrag
     */
    public void setMoney(double money) {
        this.money = money;
    }
    /** Methode zum Ausgeben der ID und des Namen des Spielers.
     * @return String
     */
    public String toString() {
        return "ID: " + id + ", name: " + name;
    }

    public boolean equals(Object other) {
        return other instanceof Player && ((Player) other).getId() == id;
    }
}
