package org.htl_hl.bibiProject.Common;

import org.htl_hl.bibiProject.Client.Client;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/** Objekt vom Typ Game.
 * In dieser Klasse befinden sich alle notwendigen Methoden und Klassen von Game.
 */
public class Game {

    private static final long ROUNDTIME = 5000;

    private int id;
    private final List<Round> rounds;
    private final List<Player> players;
    private final String name;
    private Round activeRound;

    public Game() {
        this(0, "", null);
    }

    public Game(int id, String name, List<Player> players) {
        this.id = id;
        this.name = name;
        this.players = players;
        this.rounds = new LinkedList<>();
    }

    /** Methode zum Abrufen der Game-ID.
     * @return id Game-ID
     */
    public int getId() {
        return id;
    }

    /** Methode zum Abrufen des Game-Namen.
     * @return name Game-Name
     */
    public String getName() {
        return name;
    }

    /** Methode zum Abrufen der Liste der Runden.
     * @return rounds Liste der Runden
     */
    public List<Round> getRounds() {
        return rounds;
    }

    /** Methode zum Abrufen der Liste der Player.
     * @return player Liste der Player
     */
    public List<Player> getPlayers() {
        return players;
    }

    /** Methode zum Abrufen der aktiven Runde.
     * @return activeRound Aktive Runde
     */
    public Round getActiveRound() {
        return activeRound;
    }

    /** Methode zum Setzen der aktiven Runde.
     * @param activeRound Round Runde die aktiv gesetzt werden soll
     * @return void
     */
    public void setActiveRound(Round activeRound) {
        this.activeRound = activeRound;
    }

    /** Methode zum Setzen der aktiven Runde mit Hilfe der Runden-ID.
     * @param activeRoundId int ID der Runde die aktiv gesetzt werden soll
     * @return void
     */
    private void setActiveRoundById(int activeRoundId) {
        if (activeRoundId < 0 || activeRoundId > rounds.size())
            throw new IllegalArgumentException("RoundId out of bounds");
        else
            this.activeRound = rounds.get(activeRoundId);
    }

    /** Methoden zum Abwickeln der Verkäufe.
     * Orders werden in Items getrennt; ExchangeRates werden berechnet; Verkäufe/Käufe werden augeteilt.
     * @param orders List<Order> Liste an Orders
     * @return void
     */
    private void exchange(List<Order> orders) {
        List<Item> items = new LinkedList<>();

        for (Order order : orders)
            if (!items.contains(order.getItem()))
                items.add(order.getItem());

        for (Item item : items) {
            // orders containing this item
            List<Order> orderToItem = new LinkedList<>();
            for (Order order : orders)
                if (order.getItem().equals(item))
                    orderToItem.add(order);

            // calculate exchange rates
            ExchangeRate rate = new ExchangeRate();
            rate.recalculateRate(orderToItem);

            List<Order> matchingOrders = new LinkedList<>();
            for (Order order : orderToItem) {
                if (order.getIsBuy() && order.getLimit() >= rate.getRate()
                        || !order.getIsBuy() && order.getLimit() <= rate.getRate())
                    matchingOrders.add(order);
            }

            if (matchingOrders.isEmpty())
                return;

            System.out.println("Matching orders:");
            for (Order order : matchingOrders) {
                System.out.println(order);
            }

            // calculate quantity distribution using largest remainder method
            long buyQuantity = 0;
            long sellQuantity = 0;
            for (Order order : matchingOrders) {
                if (order.getIsBuy())
                    buyQuantity += order.getRemaining();
                else
                    sellQuantity += order.getRemaining();
            }

            long turnover = Math.min(buyQuantity, sellQuantity);
            double fulfillmentRatio = Math.min(buyQuantity, sellQuantity) / turnover;
            long total = 0;
            List<Long> quantities = new LinkedList<>();
            List<Double> remainders = new LinkedList<>();

            for (Order order : matchingOrders) {
                if (order.getIsBuy() && turnover == buyQuantity || !order.getIsBuy() && turnover == sellQuantity) {
                    double q = order.getRemaining() * fulfillmentRatio;
                    long roundedDown = (long) q;
                    remainders.add(q - roundedDown);
                    quantities.add(roundedDown);
                    total += roundedDown;
                } else {
                    remainders.add(0.0);
                    quantities.add(order.getRemaining());
                }
            }

            for (; total < turnover; ++total) {
                int largest = 0;
                for (int i = 1; i < remainders.size(); ++i)
                    if (remainders.get(i) > remainders.get(largest))
                        largest = i;

                quantities.set(largest, quantities.get(largest) + 1);
                remainders.set(largest, 0.0);
            }

            for (int i = 0; i < matchingOrders.size(); ++i) {
                long quantity = quantities.get(i);
                Order order = matchingOrders.get(i);
                Player p = order.getPlayer();
                order.setFulfilled(order.getFulfilled() + quantity);
                if (order.getIsBuy()) {
                    p.setMoney(p.getMoney() - quantity * rate.getRate());
                    Stock s = p.getStockByItemId(order.getItem().getId());
                    if (s == null)
                        p.getStocks().add(new Stock(order.getItem(), quantity));
                    else
                        s.setQuantity(s.getQuantity() + quantity);
                } else {
                    p.setMoney(p.getMoney() + quantity * rate.getRate());
                    Stock s = p.getStockByItemId(order.getItem().getId());
                    s.setQuantity(s.getQuantity() - quantity);
                }
            }
        }
    }

    /** Methode zum wechseln in die nächste Runde.
     * @param endsAt Date Datum mit Uhrzeit an welcher die Runde endet
     * @return void
     */
    private void nextRound(Date endsAt) {
        List<Order> orders = new LinkedList<>();
        if (activeRound != null) {
            List<Order> prevOrders = activeRound.getOrders();
            exchange(prevOrders);

            // import partially or unfulfilled orders
            for (Order order : prevOrders) {
                if (order.getFulfilled() != order.getQuantity())
                    orders.add(order);
            }
        }

        int index = rounds.size();
        Round round = new Round(index, orders, endsAt);
        rounds.add(round);
        setActiveRoundById(index);
    }

    /** Methode zum Abschließen einer Runde.
     * @return void
     */
    public void updateRounds() {
        if (rounds.size() == 0)
            throw new IllegalStateException("Game hasn't been started yet");

        long curr = System.currentTimeMillis();

        for (long roundEnd = activeRound.getEndsAt().getTime(); curr >= roundEnd; roundEnd += ROUNDTIME) {
            // for each player who hasn't been putting up at least one order add a random one
            for (Player player : players) {
                boolean isEvil = true;

                for (Order order : activeRound.getOrders())
                    if (order.getPlayer().equals(player)) {
                        isEvil = false;
                        break;
                    }

                if (isEvil) {
                    List<Order> orders = activeRound.getOrders();
                    List<Stock> stocks = player.getStocks();
                    Item item = stocks.get((int) (Math.random()*stocks.size())).getItem();
                    boolean isBuy = false; // always sell stuff
                    double limit = Math.round(Math.random() * 500); // TODO(Tharre): how high should this be?
                    int quantity = (int) (Math.random() * player.getStockByItemId(item.getId()).getQuantity());
                    orders.add(new Order(orders.size(), item, player, isBuy, limit, quantity));
                }
            }

            nextRound(new Date(roundEnd + ROUNDTIME));

        }
    }

    /** Methode zum Starten einer Round.
     * @return void
     */
    public void start() {
        nextRound(new Date(System.currentTimeMillis() + ROUNDTIME));
    }

    public static void main(String []args) throws IOException {
        final String ip = "http://127.0.0.1:8000";
        Game g0 = HttpUtil.sendPost(ip, "games", "name=JGame", Game.class);
        Player p1 = HttpUtil.sendPost(ip, "games/0/players", "name=JGatto", Player.class);
        Player p2 = HttpUtil.sendPost(ip, "games/0/players", "name=JKartoffel", Player.class);

        HttpUtil.sendPost(ip, "games/0/rounds/0/orders", "itemId=" + p1.getStocks().get(0).getItem().getId() +
                "&playerId=" + p1.getId() + "&isBuy=false&limit=200&quantity=200", Order.class);
        HttpUtil.sendPost(ip, "games/0/rounds/0/orders", "itemId=" + p1.getStocks().get(0).getItem().getId() +
                "&playerId=" + p2.getId() + "&isBuy=true&limit=200&quantity=200", Order.class);
    }
}
