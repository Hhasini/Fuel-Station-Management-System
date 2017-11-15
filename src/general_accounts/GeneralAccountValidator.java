/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general_accounts;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Induni
 */
public class GeneralAccountValidator {
   
    
    
    public boolean checkCorrectLength(String txtName, String string, int minLength, int maxLength){
        boolean hasCorrectLength = false;
        if(string != null){
            if((string.length() <= maxLength) && (string.length() >= minLength)){
                hasCorrectLength = true;
            }
        }
        if(!hasCorrectLength){
            JOptionPane.showMessageDialog(null, txtName + " exceeds expexted length", "Invalid length", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(string + " > " + hasCorrectLength);
        return hasCorrectLength;
    }
    
    
    public boolean isNumber(String txtName, String string){
        boolean isNumber = false;        
        if(string != null){
            try{
                Integer.parseInt(string);
                isNumber = true;
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        if(!isNumber){
            JOptionPane.showMessageDialog(null, txtName +/* " is not a number", */"Invalid input" /*, JOptionPane.ERROR_MESSAGE*/);
        }
        System.out.println(string + " > " + isNumber);
        return isNumber;
    }
    
    public boolean isPriceValue(String txtName, String string){
        boolean isNumber = false;        
        if(string != null){
            try{
                Double.parseDouble(string);
                isNumber = true;
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        if(!isNumber){
            JOptionPane.showMessageDialog(null, txtName + /*" is not a valid price value", */"Invalid input"/*, JOptionPane.ERROR_MESSAGE*/);
        }
        System.out.println(string + " > " + isNumber);
        return isNumber;
    }
    
    public boolean isNotEmpty(String txtName, String string){
        boolean isNotEmpty = false;
        if(string != null){
            string = string.trim();
            if(!string.isEmpty()){
                isNotEmpty = true;
            }
        }
        System.out.println("'" + string + "' > " + isNotEmpty);
        if(!isNotEmpty){
            JOptionPane.showMessageDialog(null, txtName + " Field cannot be empty");
        }
        return isNotEmpty;
    }
    
   
   /* public boolean dateIsNotEmpty(String txtName,String string){
        boolean dateIsNotEmpty=false;
        
        if(((JTextField)string.getDateEditor().getUiComponent()).getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Date should be filled");
        
        return dateIsNotEmpty;
        }
        return false;
        
    }
    
*/
    
}
