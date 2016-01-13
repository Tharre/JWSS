package org.htl_hl.bibiProject.Common;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Round getActiveRound() {
        return activeRound;
    }

    private void setActiveRound(int activeRoundId) {
        try {
            this.activeRound = rounds.get(activeRoundId);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("RoundId out of bounds");
        }
    }

    private void nextRound(Date endsAt) {
        List<Order> orders = new LinkedList<>();
        // TODO: import unfulfilled orders from the last round, calulate exchange rates and exchange items
        int index = rounds.size();
        Round round = new Round(index, orders, endsAt);
        rounds.add(round);
        setActiveRound(index);
    }

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

    public void start() {
        nextRound(new Date(System.currentTimeMillis() + ROUNDTIME));
    }
}
