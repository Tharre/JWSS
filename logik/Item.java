import java.io.*;

public class Item{

  private final String FILE="files/Waren.txt";
  private String[] nLine=new String[5];
  private int nr;
  private String name;
  private double curPrice;
  private int maxAmount;
  private int circAmount;

  public Item(){};

  public Item(int nr, String name, int maxAmount, int circAmount, double curPrice){
    this.nr=nr;
    this.name=name;
    this.maxAmount=maxAmount;
    this.circAmount=circAmount;
    this.curPrice=curPrice;
  }

  public void updateCircAmount(Item[][] item, int uItem, int amount){
    circAmount=amount;
  }

  public void updateCurPrice(Item item, int uItem, double price){
    curPrice=price;
  }
  public String toString(){
    return "Name: "+name+" | Nr: "+nr+" | maxAmount: "+maxAmount+" | circAmount: "+circAmount+" | curPrice: "+curPrice+" |";
  }

  public String getName(){
    return name;
  }

  public int getNr(){
    return nr;
  }

  public int getMaxAmount(){
    return maxAmount;
  }

  public int getCircAmount(){
    return circAmount;
  }

  public double getCurPrice(){
    return curPrice;
  }

  public void loadItems(){
    int num=0;
    int mAm=0;
    int cAm=0;
    double cPr=0.0;
    String na="";
    Item[] nItem=new Item[80];
    Item tItem;
      try{
        File f=new File(FILE);
        FileReader r=new FileReader(f);
        BufferedReader br=new BufferedReader(r);
        String z;
        int zeile=0;
        for(int i=0;(z=br.readLine())!=null;i++){
          nLine=z.split(";");
          for(int k=0; k<=79; k++){
            for(int j=0; j<=4; j++){
              switch(j){
                case 0: num=Integer.parseInt(nLine[j]);
                  break;
                case 1: na=nLine[j];
                  break;
                case 2: mAm=Integer.parseInt(nLine[j]);
                  break;
                case 3: cAm=Integer.parseInt(nLine[j]);
                  break;
                case 4: cPr=Double.parseDouble(nLine[j]);
                  break;
                default: System.out.println("Fehler");
                  break;
              }
            }
            nItem[k]=new Item(num,na,mAm,cAm,cPr);

          }
          System.out.println(nItem[i]);
          zeile++;
        }

        br.close();
      }catch(Exception e){

        //System.out.println(e.getMessage());
        e.printStackTrace();
      }

  }
}