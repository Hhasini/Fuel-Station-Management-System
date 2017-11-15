/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itp;

/**
 *
 * @author Dissanayaka
 */
public class Sale {

    
    public double calculateTotalPrice(String a, String b)
    {   int qtybox1=Integer.parseInt(a);
        int pricebox1=Integer.parseInt(b);
        
    
    
      
    
        double total;
        
        total=((qtybox1)*(pricebox1));
        
        return total;
    }

  
}
