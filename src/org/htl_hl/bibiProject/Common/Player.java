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

    public void updateStock(int index, int quantity) {
        stocks.get(index).setQuantity(quantity);
    }
    public void updateItems(Item item){
        if(this.items.contains(item))
           items.set(items.indexOf(item),items.get(items.indexOf(item)).)
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String toString() {
        return "ID: " + id + ", name: " + name;
    }
}
