package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private int id;
    private Item item;
    private Player player;
    private boolean isBuy;
    private double limit;
    private long quantity;
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

    public int getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getItem() {
        return item;
    }

    public boolean getIsBuy() {
        return isBuy;
    }

    public double getLimit() {
        return limit;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getFulfilled() {
        return fulfilled;
    }

    @JsonIgnore
    public long getRemaining() {
        return quantity - fulfilled;
    }

    public void setFulfilled(long fulfilled) {
        this.fulfilled = fulfilled;
    }

    public String toString() {
        return "Order: " + id + ", itemID: " + item.getId() + ", isBuy: " + isBuy + ", limit: " + limit + ", quantity: " + quantity;
    }

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
