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
public class Purches {
    int no;
    String date;
    float amount;
    String title;
    String curncy;
    
    Purches(){};
    Purches(String pdate,float pamount,String pname,String cur)
    {
       date = pdate;
       amount = pamount;
      title =pname;
      curncy=cur;
    }
    String InsertIntoPurches(String mn,String s)
    {
     String q5 ="insert into purchase1(Title,Date,Price,Currency,Machine,Supplier) values('"+title+"','"+date+"',"+amount+",'"+curncy+"','"+mn+"','"+s+"')";
     return q5;
    }
    
    String UpdatePurches(int pno){
    
      String q3;
      q3="UPDATE purchase1 SET Title='"+title+"',Date='"+date+"',Price="+amount+",Currency='"+curncy+"' where pno = "+ pno +"";
     return q3;
    
    }
    String DeletePurches(int mid){
    String q4;
      q4="delete from purchase1 where pno="+ mid+"";
     return q4;
    
    }
    
}
