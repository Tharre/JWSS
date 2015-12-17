package org.htl_hl.bibiProject.Common;

import java.util.List;

public class Player {

    private int id;
    private String name;
    private List<Item> items;
    private double money;

    public Player() {
        this(0, "", null, 0.0);
    }

    public Player(int id, String name, List<Item> items, double money) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getMoney() {
        return money;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String toString() {
        return "ID: " + id + ", name: " + name;
    }
}
