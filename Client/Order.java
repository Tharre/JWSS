
public class Order {
  private int runde;
  private char kv;
  private int spieler;
  private double limit;
  private int ware;
  private int stk;
  public Order(int runde, char kv, int spieler, double limit, int ware, int stk){
    this.runde=runde;
    this.kv=kv;
    this.spieler=spieler;
    this.limit=limit;
    this.ware=ware;
    this.stk=stk;
  }
}
