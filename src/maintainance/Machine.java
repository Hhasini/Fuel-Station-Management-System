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
public class Machine {
     int id;
    String name;
    String catogory;
    float price;
    String version;
    String country;
    String brand;
    String year;
    String model;
    float capacity;
    int   warrant;
   String cur;
   String sp;
    Machine(){};
    
    Machine( String mname,String mcatogory,float mprice,String mversion,String mCountry,String mbrand,String myear,String mmodel,float mcapacity,int mwrr,String currency,String supplier){
    
     name = mname;
     catogory = mcatogory;
     price = mprice;
     version = mversion;
     country = mCountry;
     brand = mbrand;
     year = myear;
     model =mmodel;
     capacity = mcapacity;
     warrant = mwrr;
     cur=currency;
     sp=supplier;
    }
  
   String InsertIntoSmachines(float sweight,float sheight,float swidth,String sdesign)
           
   {         
              float weight = sweight;
              float height = sheight;
              float width = swidth;
              String design = sdesign;
              
              String q1 ="insert into smachine1(Name,Catogory,Price,Currency,Model,Version,Country,Year,Brand,Width,Height,Weight,Design,Capacity,Warranty,Supplier) values('"+name+"','"+catogory+"',"+price+",'"+cur+"','"+model+"','"+version+"','"+country+"','"+year+"','"+brand+"',"+width+","+height+","+weight+",'"+design+"',"+capacity+","+warrant+",'"+sp+"')";
              return q1;
               }
   String InsertIntoLmachines(String srtype)
           
   {         
              String stype = srtype;
              String q1 ="insert into fmachine1(Name,Catogory,Price,Currency,Model,Version,Country,Year,Brand,Syringe,Capacity,Warranty,Supplier) values('"+name+"','"+catogory+"',"+price+",'"+cur+"','"+model+"','"+version+"','"+country+"','"+year+"','"+brand+"','"+stype+"',"+capacity+","+warrant+",'"+sp+"')";
              return q1;
               }
    String UpdateSmachine(int mid,float wg ,float hg,float wd,String de ){
      String q2= null;
      
      
      
           
        q2="UPDATE smachine1 SET Name='"+name+"',Catogory='"+catogory+"',Price="+price+",Currency='"+cur+"',Model='"+model+"',Version='"+version+"',Country='"+country+"',Year='"+year+"',Brand='"+brand+"',Width="+wd+",Height="+hg+",Weight="+wg+",Design='"+de+"',Capacity="+capacity+",Warranty="+warrant+",Supplier='"+sp+"' where mno = "+mid +"";  
     
      return q2;
   }
    
    String UpdateLmachine(int lid,String st){
    String q2= null;
      
      
      
           
        q2="UPDATE fmachine1 SET Name='"+name+"',Catogory='"+catogory+"',Price="+price+",Currency='"+cur+"',Model='"+model+"',Version='"+version+"',Country='"+country+"',Year='"+year+"',Brand='"+brand+"',Syringe='"+st+"',Capacity="+capacity+",Warranty="+warrant+",Supplier='"+sp+"' where mno = "+lid +"";  
     
      
      return q2;
    
    }
    String Deletesmachine(int mid){
    String q4;
      q4="delete from smachine1 where mno="+mid+"";
     return q4;
    
    }
    String Deletelmachine(int mid){
    String q4;
      q4="delete from fmachine1 where mno="+ mid+"";
     return q4;
    
    }
    
}
