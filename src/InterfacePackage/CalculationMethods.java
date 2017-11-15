/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class CalculationMethods {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs=null;
    
    
    public double calculateGrossPay(String basic,String hra,String cea,String lta,String da,String med,String shift)
    {
        double grosspay=0;
        
        double a=Double.parseDouble(basic);
        double b=Double.parseDouble(hra);
        double c=Double.parseDouble(cea);
        double d=Double.parseDouble(lta);
        double e=Double.parseDouble(da);
        double f=Double.parseDouble(med);
        double g=Double.parseDouble(shift);
        grosspay=a+b+c+d+e+f+g;
        
        return grosspay;
        
    }
    




public double calculate_EPF_Employee(String gross)
{
    double EPFE_Rate = 0.08;
   
    double grosspay=Double.parseDouble(gross);
    
    double EPFE=grosspay*EPFE_Rate;
    
    return EPFE;
    
}

public double calculate_paytax(String gross)
{
    double taxRate=0.02;
   
    double grosspay=Double.parseDouble(gross);
    
    double tax=grosspay*taxRate;
    
    return tax;
    
}

public double calculate_EPF_Employer(String gross)
{
    double EPFE_Rate = 0.12;
   
    double grosspay=Double.parseDouble(gross);
    
    double EPFE=grosspay*EPFE_Rate;
    
    return EPFE;
    
}

public double calculate_ETF(String gross)
{
    double ETF_Rate = 0.03;
   
    double grosspay=Double.parseDouble(gross);
    
    double ETF=grosspay*ETF_Rate;
    
    return ETF;
    
}

public double calculate_netpay(String gross, String epf, String paytax, String ot,String loan){
    double a=Double.parseDouble(gross);
    double b=Double.parseDouble(epf);
    double c=Double.parseDouble(paytax);
    double d=Double.parseDouble(ot);
    double e=Double.parseDouble(loan);
    double net=(a-(b+c+e))+d;
    return net;
}



}