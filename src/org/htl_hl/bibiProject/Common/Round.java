package org.htl_hl.bibiProject.Common;

import java.util.Date;
import java.util.List;

public class Round {

    private int id;
    private List<Order> orders;
    private Date endsAt;

    public Round() {
        this(0, null, null);
    }

    public Round(int id, List<Order> orders, Date endsAt) {
        this.id = id;
        this.orders = orders;
        this.endsAt = endsAt;
    }

    public int getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Date getEndsAt() {
        return endsAt;
    }
}
