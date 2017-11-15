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
public class billing {
      public double calculateTotalPrice(String a, String b,   String c, String d,String e,String f, String g, String h, String i, String  j, String k ,String l)
    {   int fil =Integer.parseInt(a);
        int hub =Integer.parseInt(b);
        int brac =Integer.parseInt(c);
        int fue =Integer.parseInt(d);
        int bea =Integer.parseInt(e);
        int gea =Integer.parseInt(f);
        int oil =Integer.parseInt(g);
        int clu =Integer.parseInt(h);
        int hos =Integer.parseInt(i);
        int con =Integer.parseInt(j);
        int swi =Integer.parseInt(k);
        int bol =Integer.parseInt(l);

    
    
      
    
        double sum;
        
        sum=((fil*1500.0)+(hub*800.0)+(brac*16500.0)+(fue*1948.0)+(bea*5000.0)+(gea*12000.0)+(oil*9000.0)+(clu*10000.0)+(hos*500.0)+(con*20000.0)+(swi*30000.0)+(bol*27000.0));
        
        return sum;
    }

   
    
}
