package org.htl_hl.bibiProject.Common;

import java.util.LinkedList;
import java.util.List;

/** Objekt vom Typ ExchangeRate.
 * In dieser Klasse befinden sich alle notwendigen Methoden und Eigenschaften einer ExchangeRate (Börsenkurs).
 */
public class ExchangeRate {
    /** rate - Private Eigenschaft von ExchangeRate.
     * Aktueller Börsenkurs.
     */
    private double rate;

    public ExchangeRate(double rate) {
        this.rate = rate;
    }

    public ExchangeRate() {
        this(0.0);
    }

    /** Methode zum Abrufen der aktuellen ExchangeRate (Börsenkurs).
     * @return rate Aktuelle ExchangeRate (Börsenkurs)
     */
    public double getRate() {
        return rate;
    }

    /** Methode zum Setzen der ExchangeRate (Börsenkurs).
     * @param rate double Neue ExchangeRate (Börsenkurs)
     * @return void
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /** Methode zum Berechnen der ExchangeRate (Börsenkurs).
     * @param orders List<Order> Liste der Orders mit der selben Ware
     */
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

        // and select the best surviving rate
        rate = bestRate;
    }
}