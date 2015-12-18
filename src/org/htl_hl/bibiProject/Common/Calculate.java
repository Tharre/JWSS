package org.htl_hl.bibiProject.Common;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 17.12.2015.
 */
public class Calculate {

    public Calculate(){};

    public void calculate(List<Order> orders){
        int length=orders.size()-1;
        double readPrice;
        double highestPrice;
        double lowestPrice;
        double helper;
        double middlePrice;
        highestPrice=orders.get(0).getLimit();
        lowestPrice=orders.get(0).getLimit();
        for(int i=1;i<=length;i++){
            readPrice=orders.get(i).getLimit();
            if(readPrice > highestPrice)
                highestPrice=readPrice;
            if(readPrice < lowestPrice)
                lowestPrice=readPrice;
        }
        middlePrice=(highestPrice+lowestPrice)/2;
        System.out.println(middlePrice);
    }

}
//Scheiß Git