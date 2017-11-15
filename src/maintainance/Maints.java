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
public class Maints {
     int mcno,emno,mtno;
    String title,name;
    String status;
    String date;
    float expense;
    Maints(){};
    Maints(String mtitle,String mdate,int mno,int eno){
      title = mtitle;
      date = mdate;
      mcno=mno;
      emno=eno;
    
    };
    
    Maints(int mno,String mdate,String mstatus,float mexpense,String mname ){
    mtno=mno;
     status = mstatus;
     date = mdate;
     expense = mexpense;
    name=mname;
    }
    String InsertIntomaintanance(){
    
   String q3= "insert into maintanance1(Title,Date,Machine,Employee) values('"+title+"','"+date+"',"+mcno+","+emno+")";
   return q3;
    
    }
    String InsertIntomaintained(){
    
   String q3= "insert into maintained1(Maintanance,Date,Status,Expenses,Maintainer) values("+mtno+",'"+date+"','"+status+"',"+expense+",'"+name+"')";
   return q3;
    
    }
       String UpdateMaintance(int mno){
    
      String q4 = null;
     
       q4="UPDATE maintanance1 SET Title='"+title+"',Date='"+date+"',Machine="+mcno+",Employee="+emno+" where mnNo = "+ mno +"";
     
     
     return q4;
    
    }
       String UpdateMaintaned(int mno){
    
      String q4 = null;
     
       q4="UPDATE maintained1 SET Maintanance="+title+",Date='"+date+"',Status='"+mcno+"',Expenses="+emno+",Maintainer='"+name+"' where mdNo = "+ mno +"";
     
     
     return q4;
    
    }
    String DeleteMaintanance(int mid){
    String q4;
      q4="delete from maintanance1 where mnNo="+ mid+"";
     return q4;
    
    }
    String DeleteMaintaned(int mid){
    String q4;
      q4="delete from maintained1 where mdNo="+ mid+"";
     return q4;}
}
