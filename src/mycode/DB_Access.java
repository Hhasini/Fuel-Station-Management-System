/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Dissanayaka
 */
public class DB_Access {
    public static ResultSet getData(String query) throws SQLException, ClassNotFoundException{
        Connection conn = DBconnect.connect();
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(query);
        return rst;
    }
    
    public static void setData(String query) throws SQLException, ClassNotFoundException{
        Connection conn = DBconnect.connect();
        Statement stm = conn.createStatement();
        stm.executeUpdate(query);
        
    }
}
