package org.htl_hl.bibiProject.Common;

import java.util.List;

public class Game {

    private int id;
    private List<Round> rounds;
    private List<Player> players;
    private String name;
    private Round activeRound;

    public Game() {
        this(0, "", null, null);
    }

    public Game(int id, String name, List<Round> rounds, List<Player> players) {
        this.id = id;
        this.name = name;
        this.rounds = rounds;
        this.players = players;
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

    public void setActiveRound(int activeRound) {
        try {
            this.activeRound = rounds.get(activeRound);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("RoundId out of bounds");
        }
    }
}
