package org.htl_hl.bibiProject.Common;

import java.util.LinkedList;
import java.util.List;

public class ExchangeRate {

    private final Item item;
    private double rate;

    public ExchangeRate() {
        this(new Item(), 0.0);
    }

    public ExchangeRate(Item item, double rate) {
        this.item = item;
        this.rate = rate;
    }

    public Item getItem() {
        return item;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void recalculateRate(List<Order> orders) {
        List<Double> possibleRates = new LinkedList<>();
        double highestTurnover = 0;
        double bestRate = 0;

        // first fetch all possible exchange rates
        for (Order order : orders) {
            if (!order.getIsBuy()) // ignore sell orders
                continue;

            if (order.getLimit() == 0) // ignore 'best possible' orders
                continue;

            possibleRates.add(order.getLimit());
        }

        // then calculate the possible turnover at this rate
        for (double possibleRate : possibleRates) {
            int possibleTurnover = 0;
            for (Order order : orders) {
                if (order.getIsBuy()) // ignore buy orders
                    continue;

                if (order.getLimit() <= possibleRate) // all orders below the limit are satisfied
                    possibleTurnover += order.getQuantity();
            }

            if (possibleTurnover > highestTurnover) {
                bestRate = possibleRate;
                highestTurnover = possibleTurnover;
            }
        }

        // and now set the surviving, best rate
        rate = bestRate;
    }
}