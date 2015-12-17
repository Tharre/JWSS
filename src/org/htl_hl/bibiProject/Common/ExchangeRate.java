package org.htl_hl.bibiProject.Common;

/**
 * Created by Daniel on 17.12.2015.
 */
public class ExchangeRate {

    private Item item;
    private double exchangeRate;

    public ExchangeRate(Item item, double exchangeRate){
        this.item=item;
        this.exchangeRate=exchangeRate;
    }

    public Item getItem(){
        return item;
    }

    public double getExchangeRate(){
        return exchangeRate;
    }

    public void setExchangeRate(double exchRate){
        exchangeRate=exchRate;
    }
}
