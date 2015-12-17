package org.htl_hl.bibiProject.Common;

import java.util.List;

public class Player {

    private final int id;
    private final String name;
    private final List<Stock> stocks;
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public double getMoney() {
        return money;
    }

    public Stock getStockByItemId(int itemId) {
        for (Stock stock : stocks) {
            if (stock.getItem().getId() == itemId)
                return stock;
        }

        return null;
    }

    public void updateStock(int index, int quantity) {
        stocks.get(index).setQuantity(quantity);
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String toString() {
        return "ID: " + id + ", name: " + name;
    }
}
