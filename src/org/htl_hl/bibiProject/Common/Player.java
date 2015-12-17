package org.htl_hl.bibiProject.Common;

import java.util.List;

public class Player {

    private int id;
    private String name;
    private List<Item> items;
    private double money;

    public Player() {
        this(0, "");
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
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
