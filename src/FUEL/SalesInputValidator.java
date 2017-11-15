/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FUEL;

import javax.swing.JOptionPane;

/**
 *
 * @author Kavindu
 */
public class SalesInputValidator {
    
    /*private Validator validator;
    
    private Validator(){
    
    }
    
    public Validator getInstance(){
        if(validator==null){
            validator = new Validator();
        }
        return validator;
    }*/
    
    public boolean isValidEmail(String emailString){
        boolean isValidEmail = false;
        // TODO : should use regular expression pattern matching
        if(emailString != null){
            if(emailString.contains("@") && (emailString.indexOf("@") < emailString.length()-2)){
                isValidEmail = true;
            }
        }        
        if(!isValidEmail){
            JOptionPane.showMessageDialog(null, "Invalid email", "Invalid email", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(emailString + " > " + isValidEmail);
        
        return isValidEmail;
    }
    
    public boolean hasCorrectLength(String txtName, String string, int minLength, int maxLength){
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
            JOptionPane.showMessageDialog(null, txtName + " is not a number", "Invalid input", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, txtName + " is not a valid price value", "Invalid input", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, txtName + " is empty", "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
        return isNotEmpty;
    }
    
    public boolean isNotDoubleValue(String txtName, String string){
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
            JOptionPane.showMessageDialog(null, txtName + " is not a valid  value", "Invalid input", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(string + " > " + isNumber);
        return isNumber;
    }
    
}
