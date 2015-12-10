public class Good{

  private int nr;
  private String name;
  private double curPrice;
  private int maxAmount;
  private int circAmount;

  public Good(int nr, String name, double curPrice, int maxAmount, int circAmount){
    this.nr=nr;
    this.name=name;
    this.curPrice=curPrice;
    this.maxAmount=maxAmount;
    this.circAmount=circAmount;
  }

  public void updateCircAmount(int amount){
    circAmount=amount;
    return;
  }

  public void updateCurPrice(double price){
    curPrice=price;
  }

}