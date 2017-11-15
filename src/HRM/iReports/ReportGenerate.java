/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HRM.iReports;

import iReport.reportGen;
import java.awt.Container;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import mycode.DBconnect;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author User 1
 */
public class ReportGenerate extends JFrame{
    
    Connection conn;
//    public ReportGenerate(String filename) throws JRException{
//        Connection conn = DBconnect.connect();
//        JasperPrint print = JasperFillManager.fillReport(filename, null,conn);
//        JRViewer jr = new JRViewer(print);
//        Container contain = getContentPane();
//        contain.add(jr);
//        setVisible(true);
//        setBounds(10,10,1200,690); 
//        
//    }
////     public  ReportGenerate(String filename,HashMap hash){
//     public  ReportGenerate(String filename,HashMap hash){
//        Connection conn = DBconnect.connect();
//        JasperPrint print = null;
//        try {
//            print = JasperFillManager.fillReport(filename, null,conn);
//        } catch (JRException ex) {
//            Logger.getLogger(reportGen.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        JRViewer jr = new JRViewer(print);
//        Container contain = getContentPane();
//        contain.add(jr);
//        setVisible(true);
//        setBounds(10,10,1200,690); 
//        
//    }
    
    
    
    
    
     public  ReportGenerate(String fileName)
    {
        this(fileName,null);
    }

     public  ReportGenerate(String fileName,HashMap para)
     {
        super("");

        try
        {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/servicestation","root","");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        try
        {
            JasperPrint print = JasperFillManager.fillReport(fileName,para,conn);
            JRViewer viewer = new JRViewer(print);
            Container c = getContentPane();
            c.add(viewer);
            conn.close();
        }
        catch(Exception e)
        {

        }

        setBounds(10,10,900,700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
     }
    
    
    
    
    
    
    
}
