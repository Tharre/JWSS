import java.util.List;

public class Game {

    private int id;
    private List<Round> rounds;

    public Game() {
        this(0, null);
    }

    public Game(int id, List<Round> rounds) {
        this.id = id;
        this.rounds = rounds;
    }

    public int getId() {
        return id;
    }

    public List<Round> getRounds() {
        return rounds;
    }
}
