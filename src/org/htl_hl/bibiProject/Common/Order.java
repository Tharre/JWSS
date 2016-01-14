package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Objekt vom Typ Order.
 * In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse Order.
 */
public class Order {
    /** id - Private Eigenschaft der Klasse Order.
     * Jede Order hat eine eindeutige ID.
     */
    private int id;
    /** item - Private Eigenschaft der Klasse Order.
     * Jede Order beinhaltet ein Item.
     */
    private Item item;
    /** player - Private Eigenschaft der Klasse Order.
     * Jede Order wird von einem Player aufgegeben.
     */
    private Player player;
    /** isBuy - Private Eigenschaft der Klasse Order.
     * Jede Order ist ein Kauf oder Verkauf.
     */
    private boolean isBuy;
    /** limit - Private Eigenschaft der Klasse Order.
     * Jede Order hat einen maximal/mindest Preis zu dem ge-/verkauft wird.
     */
    private double limit;

    /** quantity - Private Eigenschaft der Klasse Order.
     * Jedes Item in der Order hat eine Stückzahl die angeboten/benötigt wird.
     */
    private long quantity;
    /** fulfilled - Private Eigenschaft der Klasse Order.
     * Da die Anzahl der zum Kauf und Verkauf angebotenen Stück gleich sein muss wird in dieser Variable gespeichert wieviele gekauft/verkauft werden konnten.
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


    /** Methode zum Abrufen der Stückzahl der zum Kauf/Verkauf angebotenen Ware.
     * @return quantity Stückzahl der zum Kauf/Verkauf angebotenen Ware
     */
    public long getQuantity() {
        return quantity;
    }

    /** Methode zum Abrufen aller privaten Eigenschaften (Order-ID,Item-ID,isBuy,limit,quantity), welche als String zurückgegeben werden.
     * @return String
     */
    public String toString() {
        return "Order: " + id + ", itemID: " + item.getId() + ", isBuy: " + isBuy + ", limit: " + limit + ", quantity: " + quantity;
    }

    /** Methode zum Abfragen der erfolgreich verkauften Stück der Order.
     * @return fulfilled Anzahl der erfolgreich verkauften/gekauften Stück der Order
     */
    public long getFulfilled() {
        return fulfilled;
    }

    @JsonIgnore
    /** Methode zum Abfragen der Stückzahl die nicht verkauft/gekauft werden konnten.
     * @return quantity-fulfilled Anzahl der Stück die nicht verkauft/gekauft werden konnten
     */
    public long getRemaining() {
        return quantity - fulfilled;
    }

    /** Methode zum Setzen der verkauften/gekauften Stückzahl.
     * @param fulfilled long Anzal der verkauften/gekauften Stück
     * @return void
     */
    public void setFulfilled(long fulfilled) {
        this.fulfilled = fulfilled;
    }

    /** Methode zum Auslesen der Orders aus einer Datei.
     * @param file File Datei aus der die Orders gelesen werden.
     * @return m Eine Map die die Order mit der zugehörigen ID verknüpft
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
