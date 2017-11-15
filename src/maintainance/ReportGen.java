/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintainance;
import java.sql.Connection;
import java.awt.Container;
import java.util.HashMap;
import javax.swing.JFrame;
import java.sql.*;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;





/**
 *
 * @author Induni
 */
public class ReportGen extends JFrame{
Connection conn;

    /**
     *
     * @param fileName
     * @throws JRException
     */
    public ReportGen(String fileName)
    {
        this(fileName,null);
    }

     public ReportGen(String fileName,HashMap para)
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
    
