/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FUEL.iReport_Fuel;
import mycode.DBconnect;
import java.awt.Container;
import java.sql.Connection;
import java.sql.SQLException;
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
 * @author Lihini Avanthika
 */
public class reportGenF extends JFrame{
    public reportGenF(String filename)throws JRException{
       Connection conn = DBconnect.connect();
        JasperPrint print = JasperFillManager.fillReport(filename, null,conn);
        JRViewer jr = new JRViewer(print);
        Container contain = getContentPane();
        contain.add(jr);
        setVisible(true);
        setBounds(10,10,1200,690); 
    }
    
    public reportGenF(String filename,HashMap hash){
//        try{
//       Connection conn = DBconnect.connect();
//        JasperPrint print = JasperFillManager.fillReport(filename, hash,conn);
//        JRViewer jr = new JRViewer(print);
//        Container contain = getContentPane();
//        contain.add(jr);
//        setVisible(true);
//        setBounds(10,10,1200,690); 
//    }
//        catch(JRException e){
//            JOptionPane.showMessageDialog(this, "JRE Error");
//        }
//        catch(SQLException ex){
//            Logger.getLogger(reportGenF.class.getName()).log(Level.SEVERE,null,ex);
//        }
//        catch(ClassNotFoundException ex){
//            Logger.getLogger(reportGenF.class.getName()).log(Level.SEVERE,null,ex);
//        }
        
        Connection conn = DBconnect.connect();
        JasperPrint print = null;
        try {
            print = JasperFillManager.fillReport(filename, hash,conn);
        } catch (JRException ex) {
            Logger.getLogger(reportGenF.class.getName()).log(Level.SEVERE, null, ex);
        }
        JRViewer jr = new JRViewer(print);
        Container contain = getContentPane();
        contain.add(jr);
        setVisible(true);
        setBounds(10,10,1200,690); 
    }
}
