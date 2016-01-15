package org.htl_hl.bibiProject.Common;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: ExchangeRate</p>
 * <p>Description: In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften der Klasse ExchangeRate.</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: HTL Hollabrunn</p>
 * <br><br>
 * Ein Netzwerkbasiertes B&ouml;rsensimulationsspiel
 * <br>
 * @author Michael Elpel, Daniel Gattringer, Daniel Krottendorfer, Thomas Gschwantner
 * @version 0.1
 */
public class ExchangeRate {
    /** rate - Private Eigenschaft von ExchangeRate vom Typ double.<br>
     * Aktueller B&ouml;rsenkurs.
     */
    private double rate;

    public ExchangeRate(double rate) {
        this.rate = rate;
    }

    public ExchangeRate() {
        this(0.0);
    }

    /** Methode zum Abrufen der aktuellen ExchangeRate (B&ouml;rsenkurs).
     * @return rate Aktuelle ExchangeRate (B&ouml;rsenkurs)
     */
    public double getRate() {
        return rate;
    }

    /** Methode zum Setzen der ExchangeRate (B&ouml;rsenkurs).
     * @param rate double - Neue ExchangeRate (B&ouml;rsenkurs)
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /** Methode zum Berechnen der ExchangeRate (B&ouml;rsenkurs).
     * @param orders List<Order> - Liste der Orders mit der selben Ware
     */
    public void recalculateRate(List<Order> orders) {
        List<Double> possibleRates = new LinkedList<>();
        double highestTurnover = 0;
        double bestRate = 0;
//
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

        // and select the best surviving rate
        rate = bestRate;
    }
}