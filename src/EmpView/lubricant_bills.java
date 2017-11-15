/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EmpView;

import FUEL.ProductDetails;
import FUEL.SalesDeatils;
import FUEL.SalesInputValidator;
import FUEL.SalesItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mycode.DBconnect;
import FUEL.SalesInputValidator;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author User 1
 */
public class lubricant_bills extends javax.swing.JInternalFrame {

    private ArrayList<SalesItem> salesItemList = new ArrayList<SalesItem>();
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Map<Integer, String> itemMap = null;
    SalesInputValidator validator = null;

    /**
     * Creates new form lubricant_bills
     */
    public lubricant_bills() {
        initComponents();
        con = DBconnect.connect();
        itemMap = new HashMap<Integer, String>();
        itemMap = (Map<Integer, String>) loadComboBox(itemMap, jPanel2, pname, "lubricants", "prCode", "prName");
        loadDate(datebill);
        validator = new SalesInputValidator();
        invoiceNum.setText(generateInvoiceId("APG"));
    }
    int val =0;
    public void loadDate(JLabel txtDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);
        txtDate.setText(dateString);
    }

    public Map<Integer, String> loadComboBox(Map<Integer, String> map, JPanel panel, JComboBox jComboBox, String table, String keyColumn, String valueColumn) {

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

                itemMap.put(id, key);
                jComboBox.addItem(value);
                id++;
            }

            panel.add(jComboBox);
            panel.revalidate();
            panel.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDeatils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemMap;
    }

    public String generateSalesId(String prefix) {
        String id = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);

        System.out.println(prefix + "-" + dateString);
        id = prefix + "-" + dateString;

        return id;
    }
    
    public String generateInvoiceId(String prefix) {
        String id = "";
        

        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);

        System.out.println(prefix + "-" + dateString);
        id = prefix + "-" + dateString;

        return id;
    }

    public void loadTable(JTable tab, ArrayList salesList) {

        try {

            String columns[] = {"Product Code", "Quantity", "Total" ,"Invoice No","Unit Price(Rs)"};
            int rows = salesList.size();

            Object data[][] = new Object[rows][columns.length];

            for (int i = 0; i < salesList.size(); i++) {
                SalesItem item = (SalesItem) salesList.get(i);
                data[i][0] = item.getProductID();
                data[i][1] = item.getQuantity();
                data[i][2] = item.getTotalValue();
                data[i][3] = item.getInvoiceNo();
                data[i][4] = item.getUnitPrice();
            }

            DefaultTableModel tabModel = new DefaultTableModel(data, columns);

            tab.setModel(tabModel);
        } catch (Exception ex) {
            Logger.getLogger(SalesDeatils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void clearAll(){
        qty.setText("");
        invoiceNum.setText("");
        pname.setSelectedIndex(0);
        //datebill.setText(loadDate(datebill));
        loadDate(datebill);
        
        invoiceNum.setText(generateInvoiceId("APG"));
        
        salesItemList.removeAll(salesItemList);
        loadTable(jTable1, salesItemList);
        
        total.setText("0.00");
               
        //jTable1.
        
    }

    public Double getGrandTotal(ArrayList<SalesItem> itList) {

        Double tot = 0d;
        for (int i = 0; i < itList.size(); i++) {
            SalesItem it = itList.get(i);
            tot = tot + it.getTotalValue();
        }
        return tot;
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
        String q = "SELECT quantity FROM lubricants WHERE prCode=\'" + productId + "\'";
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

        String q2 = "UPDATE lubricants SET quantity=" + remainingQunatity + " WHERE prCode='" + productId + "'";
        pst = con.prepareStatement(q2);
        pst.execute();
        System.out.println("==> " + q2);

        return remainingQunatity;
    }

    public int checkAvailability(String date, String productId, int quantityDiff) throws SQLException {
        int remainingQunatity = 0;
        String q = "SELECT quantity FROM lubricants WHERE prCode=\'" + productId + "\'";
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
        }

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pname = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        qty = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        invoiceNum = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        datebill = new javax.swing.JLabel();

        setBorder(null);
        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(1030, 630));
        setMinimumSize(new java.awt.Dimension(1030, 630));
        setPreferredSize(new java.awt.Dimension(1030, 630));

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Print Receipt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Black", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Product Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Quantity");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Invoice Number");

        pname.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("Total Value");

        total.setEditable(false);

        qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Date");

        invoiceNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invoiceNumActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Print");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("DELETE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        datebill.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                    .addComponent(total))
                                .addGap(10, 10, 10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel6))
                                        .addGap(52, 52, 52)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(pname, 0, 261, Short.MAX_VALUE)
                                            .addComponent(datebill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(invoiceNum, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                            .addComponent(qty)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(20, 20, 20)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(datebill, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(pname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(invoiceNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //if(validator.isNotEmpty("Invoice No", invoiceno) && validator.isNumber("Fuel Category", qty.getText())){
        //try {
        String productID = itemMap.get(pname.getSelectedIndex());
        String date = datebill.getText();
        // int quantity = Integer.parseInt(qty.getText());
        String invoiceno = invoiceNum.getText();
        //if(){
        if ( validator.isNumber("Quantity", qty.getText())) {
            try {
                int quantity = Integer.parseInt(qty.getText());
                if (checkAvailability(date, productID, quantity) >= 0) {

                    String query = "SELECT sellingPrice FROM lubricants WHERE prCode=\'" + productID + "\'";
                    System.out.print(query);
                    Statement selectStmt = con.createStatement();
                    ResultSet rs = selectStmt.executeQuery(query);

                    String price = "";
                    while (rs.next()) {
                        price = rs.getString(1);
                    }

                    Double sellingPrice = Double.parseDouble(price);
                    Double totalVal = sellingPrice * quantity;

                    SalesItem salesItem = new SalesItem();
                    salesItem.setInvoiceNo(invoiceno);
                   // System.out.println("+++++salesItem" + salesItem.getInvoiceNo());
                    salesItem.setProductID(productID);
                    salesItem.setProductName(pname.getSelectedItem().toString());
                    salesItem.setUnitPrice(sellingPrice);
                    salesItem.setQuantity(quantity);
                    salesItem.setSalesID(generateSalesId("LS"));
                    System.out.println("+++++salesItem" + salesItem.getSalesID());
                    salesItem.setTotalValue(totalVal);

                    salesItemList.add(salesItem);

                    loadTable(jTable1, salesItemList);
                    total.setText(getGrandTotal(salesItemList).toString());

                }

            } catch (SQLException ex) {
                Logger.getLogger(lubricant_bills.class.getName()).log(Level.SEVERE, null, ex);
            }
            //JOptionPane.showMessageDialog(null, "You have successfully print the Receipt");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void pnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnameActionPerformed

    private void qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyActionPerformed

    private void invoiceNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invoiceNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_invoiceNumActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        //buying.setText(Fbuying);
        //sell.setText(Fselling);
        //qty.setText(Fqty);// TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            String Gdate = datebill.getText();
            String Ginvoiceno = invoiceNum.getText();
//            String Ginvoiceno = generateInvoiceId("APG");
//            System.out.println("+++++it" + Ginvoiceno);
            
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("ReportFuel1.pdf"));
            document.open();
            document.add(new Paragraph("---------------- Lubricants Bill ----------------"));
            document.add(new Paragraph("A.P Gunawardhana Company(pvt)Limited"));
            document.add(new Paragraph("Jaela.."));
            document.add(new Paragraph("Telephone : 2393000  Hot Line : 072650934"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Date         : " + datebill.getText().toString()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Invioce no  : " + Ginvoiceno));
            document.add(new Paragraph(" "));
            // save in salesItem
            for (int i = 0; i < salesItemList.size(); i++) {
                SalesItem it = salesItemList.get(i);
                String productId = it.getProductID();
                String invoice = it.getInvoiceNo();
                String salesid = it.getSalesID();
                System.out.println("+++++it" + it.getSalesID());
                int qtylub = it.getQuantity();
                double tot = it.getTotalValue();

                String q1 = "INSERT INTO salesItems (lubSalesID,proId,quantity,total,invoiceNo) VALUES ('" + salesid + "','" + productId + "'," + qtylub + "," + tot + ",'" + invoice + "')";
                System.out.println(">>>" + q1);
                pst = con.prepareStatement(q1);
                pst.execute();

                // re-calculate stock
                recalculateStock(Gdate, productId, qtylub);
                
                
                document.add(new Paragraph(" " + it.getProductName() + "    " + it.getUnitPrice() + "    " + it.getQuantity() + "    " + it.getTotalValue()));
                document.add(new Paragraph(" "));
                
            }
            int value= val + 1;

            double Gtotal = Double.parseDouble(total.getText());

            String q2 = "INSERT INTO lubricantsales (grandTotal,date,invoiceNo) VALUES (" + Gtotal + ",'" + Gdate + "','" + Ginvoiceno + "')";
            pst = con.prepareStatement(q2);
            pst.execute();

            JOptionPane.showMessageDialog(null, "You have successfully print the Receipt");

            
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total price : " + total.getText().toString()));
            document.add(new Paragraph("Your Pay Has Been Made Up As Shown Above"));
            //document.add(new Paragraph("Deductions    "));
            //PdfWriter.getInstance(document, new FileOutputStream("ReportFuel2.pdf"));
            document.close();
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(lubricant_bills.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(lubricant_bills.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(lubricant_bills.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:'
        int row = jTable1.getSelectedRow();
        salesItemList.remove(row);
        loadTable(jTable1, salesItemList);
        total.setText(getGrandTotal(salesItemList).toString());
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel datebill;
    private javax.swing.JTextField invoiceNum;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox pname;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField total;
    // End of variables declaration//GEN-END:variables
}
