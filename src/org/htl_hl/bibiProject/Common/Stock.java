package org.htl_hl.bibiProject.Common;

public class Stock {

    private final Item item;
    private int quantity;

    public Stock() {
        this(new Item(), 0);
    }

    public Stock(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
