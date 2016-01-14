package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Order</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Order.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class Order {
    /** id - Private Eigenschaft der Klasse Order vom Typ int.<br>
     * Jede Order hat eine eindeutige ID.
     */
    private int id;
    /** item - Private Eigenschaft der Klasse Order vom Typ Item.<br>
     * Jede Order beinhaltet ein Item.
     */
    private Item item;
    /** player - Private Eigenschaft der Klasse Order vom Typ Player.<br>
     * Jede Order wird von einem Player aufgegeben.
     */
    private Player player;
    /** isBuy - Private Eigenschaft der Klasse Order vom Typ boolean.<br>
     * true = Verkauf ; false = Kauf.
     * Jede Order ist ein Kauf oder Verkauf.
     */
    private boolean isBuy;
    /** limit - Private Eigenschaft der Klasse Order vom Typ double.<br>
     * Jede Order hat einen maximal/mindest Preis zu dem ge-/verkauft wird.
     */
    private double limit;

    /** quantity - Private Eigenschaft der Klasse Order vom Typ long.<br>
     * Jedes Item in der Order hat eine St&uuml;ckzahl die angeboten/ben&ouml;tigt wird.
     */
    private long quantity;
    /** fulfilled - Private Eigenschaft der Klasse Order vom Typ long.<br>
     * Da die Anzahl der zum Kauf und Verkauf angebotenen St&uuml;ck gleich sein muss wird in dieser Variable gespeichert wieviele gekauft/verkauft werden konnten.
     */
    private long fulfilled;


    public Order() {
        this(0, new Item(), new Player(), false, 0.0, 0);
    }

    public Order(int id, Item item, Player player, boolean isBuy, double limit, long quantity) {
        this.id = id;
        this.item = item;
        this.player = player;
        this.isBuy = isBuy;
        this.limit = limit;
        this.quantity = quantity;
        this.fulfilled = 0;
    }

    /** Methode zum Abrufen der ID der Order.
     * @return id ID der Order
     */
    public int getId() {
        return id;
    }

    /** Methode zum Abrufen des Spielers der die Order aufgegeben hat.
     * @return player Spieler der die Order aufgegeben hat
     */
    public Player getPlayer() {
        return player;
    }

    /** Methode zum Abrufen der Ware auf welche sich die Order bezieht.
     * @return item Die Ware auf welche sich die Order bezieht
     */
    public Item getItem() {
        return item;
    }

    /** Methode zum Abrufen ob die Order ein Kauf oder Verkauf ist.
     * true = Kauf ; false = Verkauf
     * @return isBuy Boolean-Variable anzeigt ob es ein Kauf oder Verkauf ist
     */
    public boolean getIsBuy() {
        return isBuy;
    }

    /** Methode zum Abrufen des Limits der Order.
     * Maximaler / Minimaler Preis zu welchem ge-/verkauf wird.
     * @return limit Limit der Order
     */
    public double getLimit() {
        return limit;
    }


    /** Methode zum Abrufen der St&uuml;ckzahl der zum Kauf/Verkauf angebotenen Ware.
     * @return quantity St&uuml;ckzahl der zum Kauf/Verkauf angebotenen Ware
     */
    public long getQuantity() {
        return quantity;
    }

    /** Methode zum Abrufen aller privaten Eigenschaften (Order-ID,Item-ID,isBuy,limit,quantity), welche als String zur&uuml;ckgegeben werden.
     * @return String
     */
    public String toString() {
        return "Order: " + id + ", itemID: " + item.getId() + ", isBuy: " + isBuy + ", limit: " + limit + ", quantity: " + quantity;
    }

    /** Methode zum Abfragen der erfolgreich verkauften St&uuml;ck der Order.
     * @return fulfilled Anzahl der erfolgreich verkauften/gekauften St&uuml;ck der Order
     */
    public long getFulfilled() {
        return fulfilled;
    }

    @JsonIgnore
    /** Methode zum Abfragen der St&uuml;ckzahl die nicht verkauft/gekauft werden konnten.
     * @return quantity-fulfilled Anzahl der St&uuml;ck die nicht verkauft/gekauft werden konnten
     */
    public long getRemaining() {
        return quantity - fulfilled;
    }

    /** Methode zum Setzen der verkauften/gekauften St&uuml;ckzahl.
     * @param fulfilled long Anzal der verkauften/gekauften St&uuml;ck
     */
    public void setFulfilled(long fulfilled) {
        this.fulfilled = fulfilled;
    }

    /** Methode zum Auslesen der Orders aus einer Datei.
     * @param file File Datei aus der die Orders gelesen werden.
     * @return m Eine Map die die Order mit der zugeh&ouml;rigen ID verkn&uuml;pft
     * @throws IOException
     */
    public static Map<Integer, Order> loadOrders(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<Integer, Order> m = new HashMap<>();

        for (Order order : mapper.readValue(file, Order[].class))
            m.put(order.getId(), order);

        return m;
    }

    public static void main(String[] args) throws IOException {
        Map<Integer, Order> m = Order.loadOrders(new File("res/Orders.json"));

        System.out.println(m.get(23));
    }
}
