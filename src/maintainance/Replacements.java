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
public class Replacements {
    int id,mno;
    String status;
    float expenses;
    String name;
    String date;
    
    
     Replacements(){}
     Replacements(String rdate,int no,int tno){
     
      date=rdate;
      mno=no;
      id=tno;
     
     };
    
    Replacements(int rno,String rdate,float rexpn,String rname){
         
        id = rno;
       name = rname;
       date = rdate;
       expenses = rexpn;
      
       
    }
    String InsertIntoReplace(){
        String q5="insert into replacement1(Date,Machine,Maintained) values('"+date+"',"+mno+","+id+")";
        return q5;
    }
     String InsertIntoReplaced(){
        String q5="insert into replaced1(Rno,Date,Expenses,Supplier) values("+id+",'"+date+"',"+expenses+",'"+name+"')";
        return q5;
    }
    String UpdateReplace(int rid){
    
      String q4= null;
     
       q4="UPDATE replacement1 SET Date='"+date+"',Machine="+mno+" where Rno = "+ rid +"";
     
     return q4;
    
    }
    String UpdateReplaced(int rid){
    
      String q4= null;
     
       q4="UPDATE replaced1 SET Rno="+id+", Date='"+date+"',Expenses="+expenses+",Supplier='"+name+"' where RDno ="+rid+"";
     
     return q4;
    
    }
    String DeleteReplace(int mid){
    String q4;
      q4="delete from replacement1 where Rno="+ mid+"";
     return q4;
    
    }
     String DeleteReplaced(int mid){
    String q4;
      q4="delete from replaced1 where RDno="+ mid+"";
     return q4;
    
    }
    
    
}
