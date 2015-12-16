package org.htl_hl.bibiProject.Common;

import java.util.List;

public class Round {

    private int id;
    private List<Order> orders;

    public Round() {
        this(0, null);
    }

    public Round(int id, List<Order> orders) {
        this.id = id;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
