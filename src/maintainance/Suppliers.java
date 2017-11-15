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
public class Suppliers {
    int sid;
     String nic;
     String name;
     String address;
     String country;
     String company;
     String phone;
     String fax;
     String email;
     
     Suppliers(){};
     
     Suppliers( String snic,String sName,String saddress,String scountry,String scompany,String sphone, String sfax,String sEmail){
         nic=snic;
         name = sName;
         address= saddress;
         country = scountry;
         company =scompany;       
         phone = sphone;
         fax = sfax;
         email  =sEmail;    
         
     }
     
     
     String InsertIntoSupplire(){
     String q6="insert into supplier1(Nic,Name,Address,Country,Company,Phone,Email,Fax ) values('"+nic+"','"+name+"','"+address+"','"+country+"','"+company+"','"+phone+"','"+email+"','"+fax+"')";
     return q6;
     }
     
     String UpdateSupplier(int ssid,String nc,String nm,String adr,String cntr,String cmp,String phn,String eml,String fx){
             
             String q6 = null;
             
                 q6="UPDATE supplier1 SET Nic='"+nc+"', Name = '"+ nm +"',Address = '"+adr+"',Country='"+cntr+"',Company='"+cmp+"',Phone='"+phn+"',Email='"+eml+"',Fax='"+fx+"' where sid = "+ ssid +"";
             return q6;
     } 
     String Deletesupplier(int mid){
    String q4;
      q4="delete from supplier1 where sid="+ mid +"";
     return q4;
    
    }
     
}
