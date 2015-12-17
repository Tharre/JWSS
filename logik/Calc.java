    public void calculate(Order[] orders){
      int amount=orders.length;
      int highestQuantSell;
      int indBestPrice=-1;
      double price;
      int[] quantitySells=new int[amount];
      for(int i=0; i<=amount-1 ; i++){
        price=orders[i].getLimit();
        quantitySells[i]=orders[i].getQuantity();
        if(orders[i].getIsBuy()==false){
          for(int j=0; j<=amount-1; j++){
            if(i==j){
              continue;
            }
            if(orders[j].getLimit() > orders[i].getLimit() && orders[j].getIsBuy()==false){
                quantitySells[i]=quantitySells[i]+orders[j].getQuantity();
            }
          }
        }
      }
      highestQuantSell=quantitySells[0];
      indBestPrice=0;
      for(int k=0; k<=amount-1; k++){
          if(highestQuantSell<quantitySells[k]){
            highestQuantSell=quantitySells[k];
            indBestPrice=k;
          }
      }
      //Irgendwo bei irgendwas den Preis �ndern
      System.out.println("best: "+orders[indBestPrice].getLimit());
  }
