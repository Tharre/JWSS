public class Player {

    private int id;
    private String name;

    public Player() {
        this(0, "");
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "ID: " + id + ", name: " + name;
    }
}
