package org.htl_hl.bibiProject.Common;

import java.util.List;

public class Player {

    private int id;
    private String name;
    private List<Item> items;

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

    public String toString() {
        return "ID: " + id + ", name: " + name;
    }
}
