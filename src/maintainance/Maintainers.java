/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintainance;

/**
 *
 * @author HOME
 */
public class Maintainers {
    
    int id;
    String nic;
    String name;
    String address;
    String phone;
    
    Maintainers(){};
    Maintainers(String mnic,String mname,String maddr,String mphn){
        
        
        name = mname;
        nic = mnic;
        address =maddr;
        phone = mphn;
        
    }
    String InsertIntoMaintainer(){
        String q2="insert into maitainer1(Nic,Name,Address,Phone ) values('"+nic+"','"+name+"','"+address+"','"+phone+"')";
        return q2;
    }
   
    String UpdateMaintainer(int mid,String nic,String nm,String addr, String phn){
    
    String q3;
      
      
          q3="UPDATE maitainer1 SET Nic='"+nic+"', Name = '"+ nm +"',Address = '"+addr+"',phone = '"+ phn +"' where mtId = "+ mid +"";  
     
      return q3;
    
    }
    
    String DeleteMaintatiner(int mid){
    String q4;
      q4="delete from maitainer1 where mtId="+ mid+"";
     return q4;
    
    }
    
}


    

