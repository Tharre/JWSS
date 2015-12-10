
public class Order {
	private int Runde;
	private char kv;
	private String Spieler;
	private double limit;
	private String Ware;
	private int stk;
	public Order(int Runde, char kv, String Spieler, double limit, String Ware, int stk){
		this.Runde=Runde;
		this.kv=kv;
		this.Spieler=Spieler;
		this.limit=limit;
		this.Ware=Ware;
		this.stk=stk;
	}
}
