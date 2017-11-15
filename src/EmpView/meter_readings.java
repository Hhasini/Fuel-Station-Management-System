/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmpView;

import FUEL.ProductDetails;
import FUEL.SalesDeatils;
import FUEL.SalesInputValidator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mycode.DBconnect;
import FUEL.SalesInputValidator;
import javax.swing.JLabel;

/**
 *
 * @author Lihini Avanthika
 */
public class meter_readings extends javax.swing.JInternalFrame {
     Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Map<String, String> itemMap = null;
     SalesInputValidator validator = null;
     String selectedProduct = null;
    /**
     * Creates new form meter_readings
     */
    public meter_readings() {
        initComponents();
        con = DBconnect.connect();
        String dateString = loadDate(date);
        itemMap = new HashMap<String, String>();
        itemMap = (Map<String, String>) loadItemMap(itemMap, jPanel2, jTextFuelProd, "fuel", "proCode", "proName");
        loadComboBox(jComboBox2, "pumpinfo", "pumpNo");
         validator = new SalesInputValidator();
    }
     public String loadDate(JLabel txtDate) {
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
    
    public void loadRelatedProduct(JTextField txt, String pumpId) {
        try {

            String query = "SELECT tankId FROM pumpinfo WHERE pumpNo='" + pumpId + "'";
            System.out.println(query);
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);

            String tankId = "";
            
            while (rs.next()) {
                String key = rs.getString(1);
                System.out.println(key);
                tankId = key;
            }
            
            String query2 = "SELECT productId FROM tankinfo WHERE tankNo='" + tankId + "'";
            System.out.println(query2);
            Statement selectStmt2 = con.createStatement();
            ResultSet rs2 = selectStmt2.executeQuery(query2);

            String productId = "";
            
            while (rs2.next()) {
                String key = rs2.getString(1);
                System.out.println(key);
                productId = key;
                selectedProduct = key;
            }
            
            txt.setText(itemMap.get(productId));
            jLabelTankId.setText("["+tankId+"]");

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

    public Map<String, String> loadItemMap(Map<String, String> map, JPanel panel, JTextField txt, String table, String keyColumn, String valueColumn) {
        
        try {

            String query = "SELECT " + keyColumn + "," + valueColumn + " FROM " + table;
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);

            int id = 0;
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                System.out.println(key + ":" + value);
                itemMap.put(key, value);
                if(id==0){
                    txt.setText(value);
                    selectedProduct = key;
                }                
                id++;
            }

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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        endM = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        shift = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        date = new javax.swing.JLabel();
        jTextFuelProd = new javax.swing.JTextField();
        jLabelTankId = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "MeterReadings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("End Meter Reading");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Date");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Shift");

        shift.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2" }));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Pump No");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Product Name");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        date.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jTextFuelProd.setEditable(false);

        jLabelTankId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

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
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 678, Short.MAX_VALUE)
                                .addComponent(endM, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(48, 48, 48)
                                        .addComponent(jLabelTankId, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 540, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(shift, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, 143, Short.MAX_VALUE)
                                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFuelProd))))))
                .addGap(35, 35, 35))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jTextFuelProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelTankId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(shift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
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

        String productcode = selectedProduct;

        if( validator.isNumber("End Meter Value",endM.getText())){
            try {
                int EndM = Integer.parseInt(endM.getText());
                String selQuery1 = "SELECT meterReading FROM fuelSales where productCode=\'"+productcode+"\' ORDER BY fuelSalesId DESC LIMIT 1";
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

                String selQuery2 = "SELECT SUM(accumilativeSales) AS previousTotal FROM fuelSales where productCode=\'"+productcode+"\'";
                Statement selectStmt2 = con.createStatement();
                ResultSet rst2 = selectStmt2.executeQuery(selQuery2);
                rst2.next();
                int previousTotal = rst2.getInt("previousTotal");
                int accumulativeTotal = previousTotal + currentSalesValue;
                
                //sales....
                String queryS = "SELECT sellingPrice FROM fuel WHERE proCode=\'" + productcode + "\'";
                    System.out.print(queryS);
                    Statement selectStmt = con.createStatement();
                    ResultSet rs = selectStmt.executeQuery(queryS);

                    String price = "";
                    while (rs.next()) {
                        price = rs.getString(1);
                    }

                    Double sellingPrice = Double.parseDouble(price);
                    Double totalPriceFuel = sellingPrice * currentSalesValue;
                
            if(lastReading<=EndM){
                JOptionPane.showMessageDialog(null, "Invalid Meter Reading.\n"+"Should be Lower than "+lastReading);
            }
            else{
                String q1 = "INSERT INTO fuelSales (date,productCode,pumpID,meterReading,salesValue,accumilativeSales,shift,totalPrice) VALUES ('" + DATE + "','" + productcode + "','" + pumpNo + "'," + EndM + "," + currentSalesValue + "," + accumulativeTotal + "," + Shift + ","+totalPriceFuel+" )";
                pst = con.prepareStatement(q1);
                pst.execute();

                recalculateStock(DATE, productcode, currentSalesValue);
                JOptionPane.showMessageDialog(null, "You have successfully entered");
            }
        } catch (Exception e) {
                System.out.println(e.getMessage());
            }

//            JOptionPane.showMessageDialog(null, "You have successfully entered");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        if (evt.getStateChange() == 1) {
            System.out.println(evt.getItem());
            loadShiftComboBox(shift, date.getText(), jComboBox2.getSelectedItem().toString());
            loadRelatedProduct(jTextFuelProd, jComboBox2.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel date;
    private javax.swing.JTextField endM;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelTankId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFuelProd;
    private javax.swing.JComboBox shift;
    // End of variables declaration//GEN-END:variables
}
