package org.htl_hl.bibiProject.Common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {

    private int id;
    private Item item;
    private Player player;
    private boolean isBuy;
    private double limit;
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

    public int getQuantity() {
        return quantity;
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
