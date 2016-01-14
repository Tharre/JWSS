package org.htl_hl.bibiProject.Common;

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
    private int quantity;

    public Order() {
        this(0, new Item(), new Player(), false, 0.0, 0);
    }

    public Order(int id, Item item, Player player, boolean isBuy, double limit, int quantity) {
        this.id = id;
        this.item = item;
        this.player = player;
        this.isBuy = isBuy;
        this.limit = limit;
        this.quantity = quantity;
    }

    /** Methode zum Durchführen des Kauf-/Verkaufprozesses.
     * Diese Methode läuft jede Order in der Liste durch und aktualisiert beim Käufer und Verkäufer die Ware, Stückzahl der Ware und den Geldbetrag.
     * @param orders List<Order> Liste von Orders
     */
    public static void exchange(List<Order> orders) {
        for (Order order : orders) {
            Player player = order.getPlayer();
            int itemId = order.getId();
            int quantity = order.getQuantity();
            if (order.getIsBuy()) {
                player.setMoney(player.getMoney() - order.getLimit());
                if(order.getItem().getId()==itemId) {
                    player.getStockByItemId(itemId).setQuantity(player.getStockByItemId(itemId).getQuantity() + quantity);
                }else {
                    player.getStocks().add(new Stock(order.getItem(),quantity));
                }

            } else {
                player.setMoney(player.getMoney() + order.getLimit());
                player.getStockByItemId(itemId).setQuantity(player.getStockByItemId(itemId).getQuantity() - quantity);
            }
        }
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
    public int getQuantity() {
        return quantity;
    }

    /** Methode zum Abrufen aller privaten Eigenschaften (Order-ID,Item-ID,isBuy,limit,quantity), welche als String zurückgegeben werden.
     * @return String
     */
    public String toString() {
        return "Order: " + id + ", itemID: " + item.getId() + ", isBuy: " + isBuy + ", limit: " + limit + ", quantity: " + quantity;
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
