/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FUEL;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mycode.DBconnect;

/**
 *
 * @author Lihini Avanthika
 */
public class MeterReadings extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Map<Integer, String> itemMap = null;
     SalesInputValidator validator = null;
    /**
     * Creates new form MeterReadings
     */
    public MeterReadings() {
        initComponents();
        con = DBconnect.connect();
        String dateString = loadDate(date);
        itemMap = new HashMap<Integer, String>();
        itemMap = (Map<Integer, String>) loadComboBox(itemMap, jPanel2, jComboBox1, "fuel", "proCode", "proName");
        loadComboBox(jComboBox2, "pumpinfo", "pumpNo");
         validator = new SalesInputValidator();

    }

    public String loadDate(JTextField txtDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);
        txtDate.setText(dateString);
        return dateString;
    }

    public void loadShiftComboBox(JComboBox jComboBox, String date, String pumpId) {
        try {

            String query = "SELECT shift FROM fuelsales WHERE date='" + date + "' AND pumpID='" + pumpId + "'";
            System.out.println(query);
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);

            int rows = 0;

            if (rs.last()) {
                rows = rs.getRow();
                rs.beforeFirst();
            }
            
            System.out.println("rows -> " + rows);
            jComboBox.removeAllItems();

            if (rows < 2 && rows != 0) {                
                while (rs.next()) {
                    int key = rs.getInt(1);
                    System.out.println(key);
                    jComboBox.addItem(3 - key);
                }
            }else{
                jComboBox.addItem(1);
                jComboBox.addItem(2);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesDeatils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadComboBox(JComboBox jComboBox, String table, String keyColumn) {
        try {

            String query = "SELECT DISTINCT " + keyColumn + " FROM " + table;
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);

            jComboBox.removeAllItems();

            while (rs.next()) {
                String key = rs.getString(1);
                System.out.println(key);
                jComboBox.addItem(key);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesDeatils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<Integer, String> loadComboBox(Map<Integer, String> map, JPanel panel, JComboBox jComboBox, String table, String keyColumn, String valueColumn) {
        //JComboBox jComboBoxTemp = null;
        try {

            String query = "SELECT " + keyColumn + "," + valueColumn + " FROM " + table;
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);

            jComboBox.removeAllItems();

            int id = 0;
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                System.out.println(key + ":" + value);
                //model.addElement(new SalesItem(key, value));key
                itemMap.put(id, key);
                jComboBox.addItem(value);
                id++;
            }

            //jComboBoxTemp = new JComboBox(model);
            //jComboBoxTemp.setBounds(jComboBox.getBounds());            
            //jComboBoxTemp.setRenderer(new SalesItemRenderer());     
            //jComboBox.setVisible(false);
            //jComboBox = jComboBoxTemp;
            panel.add(jComboBox);
            panel.revalidate();
            panel.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDeatils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemMap;
    }

    public void updateStock(String date, String productId, int quantity) {
        try {
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //Date today = new Date();
            //String dateString = formatter.format(today);

            System.out.println("came here");

            String selQuery = "SELECT COUNT(*) AS tot FROM stock WHERE prID='" + productId + "' AND date='" + date + "'";
            Statement selectStmt1 = con.createStatement();
            ResultSet rst = selectStmt1.executeQuery(selQuery);
            rst.next();
            int count = rst.getInt("tot");

            System.out.println("========> " + selQuery + "  >>  " + count);

            if (count > 0) {
                String query = "UPDATE stock SET availableQty=" + quantity + " WHERE date='" + date + "' AND prID='" + productId + "'";
                pst = con.prepareStatement(query);
                pst.execute();
                System.out.println("==> " + query);
            } else {
                String query = "INSERT INTO stock (prID, availableQty, date) VALUES ('" + productId + "'," + quantity + ",'" + date + "')";
                pst = con.prepareStatement(query);
                pst.execute();
                System.out.println("==> " + query);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int recalculateStock(String date, String productId, int quantityDiff) throws SQLException {
        int remainingQunatity = 0;
        String q = "SELECT quantity FROM fuel WHERE proCode=\'" + productId + "\'";
        System.out.print(q);
        Statement selectStmt = con.createStatement();
        ResultSet rs = selectStmt.executeQuery(q);
        int lubQuantity = 0;

        while (rs.next()) {
            lubQuantity = rs.getInt(1);
        }

        remainingQunatity = lubQuantity - quantityDiff;

        System.out.print(lubQuantity + " - " + quantityDiff + " = " + remainingQunatity);

        if (remainingQunatity < 0) {
            JOptionPane.showMessageDialog(null, "Out of Stock " + remainingQunatity);
            remainingQunatity = 0;
        }

        updateStock(date, productId, remainingQunatity);

        String q2 = "UPDATE fuel SET quantity=" + remainingQunatity + " WHERE proCode='" + productId + "'";
        pst = con.prepareStatement(q2);
        pst.execute();
        System.out.println("==> " + q2);

        return remainingQunatity;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        endM = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        shift = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "MeterReadings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 153), new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204), new java.awt.Color(0, 255, 204)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("End Meter Reading");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Date");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Shift");

        shift.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Pump No");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Product Name");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 189, Short.MAX_VALUE)
                                .addComponent(endM, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(shift, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(35, 35, 35))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(shift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(endM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String pumpNo = jComboBox2.getSelectedItem().toString();
        int Shift = (int) shift.getSelectedItem();
        String DATE = date.getText();
        //int EndM = Integer.parseInt(endM.getText());

        String productcode = itemMap.get(jComboBox1.getSelectedIndex());
        
        if( validator.isNumber("End Meter Value",endM.getText())){
        try {
             int EndM = Integer.parseInt(endM.getText());
            String selQuery1 = "SELECT meterReading FROM fuelSales ORDER BY fuelSalesId DESC LIMIT 1";
            Statement selectStmt1 = con.createStatement();
            ResultSet rst1 = selectStmt1.executeQuery(selQuery1);
            rst1.next();
            int lastReading = rst1.getInt("meterReading");
            int currentSalesValue = 0;
            if (EndM < lastReading) {
                currentSalesValue = lastReading - EndM;
            } else {
                System.out.println("EndM is greater than lastReading");
            }
            System.out.println("Current Sales Value = " + currentSalesValue + " (" + lastReading + "-" + EndM + ")");

            String selQuery2 = "SELECT SUM(accumilativeSales) AS previousTotal FROM fuelSales";
            Statement selectStmt2 = con.createStatement();
            ResultSet rst2 = selectStmt2.executeQuery(selQuery2);
            rst2.next();
            int previousTotal = rst2.getInt("previousTotal");
            int accumulativeTotal = previousTotal + currentSalesValue;

            String q1 = "INSERT INTO fuelSales (date,productCode,pumpID,meterReading,salesValue,accumilativeSales,shift) VALUES ('" + DATE + "','" + productcode + "','" + pumpNo + "'," + EndM + "," + currentSalesValue + "," + accumulativeTotal + "," + Shift + " )";
            pst = con.prepareStatement(q1);
            pst.execute();

            recalculateStock(DATE, productcode, currentSalesValue);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "You have successfully entered");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        if (evt.getStateChange() == 1) {
            System.out.println(evt.getItem());
            loadShiftComboBox(shift, date.getText(), jComboBox2.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MeterReadings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MeterReadings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MeterReadings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MeterReadings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MeterReadings().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date;
    private javax.swing.JTextField endM;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox shift;
    // End of variables declaration//GEN-END:variables
}
