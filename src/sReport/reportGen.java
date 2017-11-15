/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sReport;
import mycode.DBconnect;
import java.awt.Container;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import mycode.DBconnect;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Dissanayaka
 */
public class reportGen extends JFrame {
    public reportGen(String filename) throws JRException{
        Connection conn = DBconnect.connect();
        JasperPrint print = JasperFillManager.fillReport(filename, null,conn);
        JRViewer jr = new JRViewer(print);
        Container contain = getContentPane();
        contain.add(jr);
        setVisible(true);
        setBounds(10,10,1200,690); 
        
    }
    
    
     
     
      public reportGen(String filename,HashMap hash)throws JRException {
        Connection conn = DBconnect.connect();
        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport(filename, null,conn);
        } catch (JRException ex) {
            Logger.getLogger(reportGen.class.getName()).log(Level.SEVERE, null, ex);
        }
        JRViewer jr = new JRViewer(print);
        Container contain = getContentPane();
        contain.add(jr);
        setVisible(true);
        setBounds(10,10,1200,690); 
        
    }
    

     
    
}
