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
public class Addspare {
    
    
    public double calculateTotalPrice(String a, String b)
    {   int qtybox=Integer.parseInt(a);
        int purchasebox=Integer.parseInt(b);
        
    
    
    
      
    
        double total;
        
        total=((qtybox)*(purchasebox));
        
        return total;
    }

    double calculateTotalPrice(int qtybox1, int purchasebox1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
 }
