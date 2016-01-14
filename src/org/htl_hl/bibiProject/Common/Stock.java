package org.htl_hl.bibiProject.Common;

public class Stock {

    private final Item item;
    private long quantity;

    public Stock() {
        this(new Item(), 0);
    }

    public Stock(Item item, long quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
