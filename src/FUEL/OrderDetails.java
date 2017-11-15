/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FUEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Lihini Avanthika
 */
public class OrderDetails extends javax.swing.JInternalFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Map<Integer, String> itemMap = null;
    SalesInputValidator validator = null;
    int id = 0;
    int selectedSuppId = 0;

    /**
     * Creates new form OrderDetails
     */
    public OrderDetails() {
        initComponents();
        con = DBconnect.connect();
        tableload(suplier, "suplierdetails");
        loadComboBox(jComboBox1, "suplierdetails", "suplierName");
        loadDateL(odate);
        loadDateL(date);
        //loadComboBox(osupname, "suplierdetails", "suplierName");
        tableload(reqorder, "orderdetails");
        //loadComboBox(prc, "fuel", "proName");
        //loadComboBox(prc, "lubricants", "prName");
        tableload(reorder, "orderdetails");
        validator = new SalesInputValidator();
        ono.setText(generateOrderId("ODR"));
        in.setText(generateODRInvoice("APGR"));

        prc.removeAllItems();
        itemMap = new HashMap<Integer, String>();
        itemMap = (Map<Integer, String>) loadComboBoxWithId(itemMap, jPanel6, prc, "lubricants", "prCode", "prName");
        itemMap = (Map<Integer, String>) loadComboBoxWithId(itemMap, jPanel6, prc, "fuel", "proCode", "proName");
    }

    public String generateOrderId(String prefix) {
        String id = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);

        System.out.println(prefix + "-" + dateString);
        id = prefix + "-" + dateString;

        return id;
    }
    public void clearAllODR(){
        oqty.setText("");
        //ono.setText("");
        prc.setSelectedIndex(0);
        //datebill.setText(loadDate(datebill));
        loadDateL(odate);
        
        ono.setText(generateOrderId("ODR"));
        
        
    }
    

    public String generateODRInvoice(String prefix) {
        String id = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);

        System.out.println(prefix + "-" + dateString);
        id = prefix + "-" + dateString;

        return id;
    }
    public void clearAllRES(){
        ordernum.setText("");
        orderpname.setText("");
        recqty.setText("");
        //datebill.setText(loadDate(datebill));
        loadDateL(date);
        
        in.setText(generateODRInvoice("APGR"));
        reprice.setText("0.00");
        
    }

    public Map<Integer, String> loadComboBoxWithId(Map<Integer, String> map, JPanel panel, JComboBox jComboBox, String table, String keyColumn, String valueColumn) {

        try {
            String query = "SELECT " + keyColumn + "," + valueColumn + " FROM " + table;
            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);

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

    public void loadTable(JTable tab, String tableName, String columnName, String key) {

        try {

            String query = "SELECT * FROM " + tableName + " WHERE " + columnName + "=\'" + key + "\'";

            System.out.println(query);

            Statement selectStmt = con.createStatement();
            ResultSet rs = selectStmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            int cols = rsmd.getColumnCount();
            int rows = 0;

            if (rs.last()) {
                rows = rs.getRow();
                rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            String columns[] = new String[cols];
            Object data[][] = new Object[rows][cols];

            for (int i = 1; i <= cols; i++) {
                String name = rsmd.getColumnName(i);
                columns[i - 1] = name;
            }

            int j = 0;
            while (rs.next()) {
                for (int k = 1; k <= cols; k++) {
                    data[j][k - 1] = rs.getString(k);
                }
                j++;
            }

            DefaultTableModel tabModel = new DefaultTableModel(data, columns);

            tab.setModel(tabModel);
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

    public void tableload(JTable tbl, String tableName) {
        try {

            String sql1 = "SELECT * FROM " + tableName;
            pst = con.prepareStatement(sql1);
            rs = pst.executeQuery();

            tbl.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
        }
    }

    public void loadDate(JTextField txtDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);
        txtDate.setText(dateString);
    }

    public void loadDateL(JLabel txtDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String dateString = formatter.format(today);
        System.out.println(dateString);
        txtDate.setText(dateString);
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

    public int recalculateStock(String table, String date, String productIdKey, String productId, int quantityDiff) throws SQLException {
        int remainingQunatity = 0;
        String q = "SELECT quantity FROM " + table + " WHERE " + productIdKey + "=\'" + productId + "\'";
        System.out.print(q);
        Statement selectStmt = con.createStatement();
        ResultSet rs = selectStmt.executeQuery(q);
        int lubQuantity = 0;

        while (rs.next()) {
            lubQuantity = rs.getInt(1);
        }

        remainingQunatity = lubQuantity + quantityDiff;

        System.out.print(lubQuantity + " - " + quantityDiff + " = " + remainingQunatity);

        if (remainingQunatity < 0) {
            JOptionPane.showMessageDialog(null, "Out of Stock " + remainingQunatity);
            remainingQunatity = 0;
        }

        updateStock(date, productId, remainingQunatity);

        String q2 = "UPDATE " + table + " SET quantity=" + remainingQunatity + " WHERE " + productIdKey + "='" + productId + "'";
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
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        suplier = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        supName = new javax.swing.JTextField();
        loc = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        supid = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        reqorder = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        oqty = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ono = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        prc = new javax.swing.JComboBox();
        odate = new javax.swing.JLabel();
        osupname = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        reorder = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        ordernum = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        in = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        orderpname = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        recqty = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        reprice = new javax.swing.JTextField();
        date = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(1030, 630));
        setMinimumSize(new java.awt.Dimension(1030, 630));
        setPreferredSize(new java.awt.Dimension(1030, 630));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1006, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setFocusCycleRoot(true);

        jTabbedPane1.setBackground(new java.awt.Color(0, 153, 153));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1030, 630));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1030, 630));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1030, 630));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(1030, 630));
        jPanel2.setMinimumSize(new java.awt.Dimension(1030, 630));
        jPanel2.setPreferredSize(new java.awt.Dimension(1030, 630));

        suplier.setModel(new javax.swing.table.DefaultTableModel(
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
        suplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(suplier);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Suplier Name");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Location");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Email Address");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("EDIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("suplier id");

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("DELETE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe Marker", 1, 12)); // NOI18N
        jButton7.setText("CLEAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addGap(59, 59, 59)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(supName, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(supid))
                .addGap(50, 50, 50)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(37, 37, 37)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                    .addComponent(loc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(jButton7)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(loc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(supName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton7)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10))
                    .addComponent(supid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("SEARCH");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(29, 29, 29)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Supplier Details", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        reqorder.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(reqorder);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Date of order");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Product Code");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("quantity");

        jButton4.setText("EMAIL");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("orderNo");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("suplier Name");

        prc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        prc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                prcItemStateChanged(evt);
            }
        });
        prc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                prcPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        prc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prcMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                prcMousePressed(evt);
            }
        });

        odate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        osupname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                osupnameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(oqty, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(odate, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel6))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ono, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(prc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(osupname, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(odate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel15)
                            .addComponent(prc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(osupname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(oqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(ono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("request Order Details", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        reorder.setBackground(new java.awt.Color(204, 204, 204));
        reorder.setModel(new javax.swing.table.DefaultTableModel(
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
        reorder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reorderMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(reorder);

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("order No");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("invoiceNo");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Product name");

        jButton6.setText("SAVE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("return date");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("quantity");

        recqty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recqtyActionPerformed(evt);
            }
        });
        recqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                recqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                recqtyKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("price");

        date.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(in, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ordernum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderpname, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel14))
                .addGap(42, 42, 42)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reprice)
                    .addComponent(recqty, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addGap(34, 34, 34)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ordernum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(orderpname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(recqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton6)
                    .addComponent(jLabel14)
                    .addComponent(reprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Received Orders", jPanel5);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Purchase Order Management");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String supname = supName.getText();
        // int supno = Integer.parseInt(supid.getText());
        String location = loc.getText();

        String Email = email.getText();

        if (validator.isNotEmpty("SupPlier Name", supname) && validator.isNotEmpty("Location", location) && validator.isValidEmail(Email) && validator.isNumber("Supplier Id", supid.getText())) {
            try {
                int supno = Integer.parseInt(supid.getText());
                String v = "select * from suplierdetails where suplierNo='" + supno + "'";
                pst = con.prepareStatement(v);
                rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Suplier No already exists.");
                } else {

                    String q4 = "INSERT INTO suplierdetails (suplierNo,suplierName,location,email) VALUES (" + supno + ",'" + supname + "','" + location + "','" + Email + "' )";
                    pst = con.prepareStatement(q4);
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "You have successfully entered");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //JOptionPane.showMessageDialog(null, "You have successfully entered"); 
            //clearAllSPL();
            tableload(suplier, "suplierdetails");
            
        }
        //clearAllSPL();
        //this.dispose();
    }

    private void jButtonxActionPerformed(java.awt.event.ActionEvent evt) {

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String Sn = supName.getText();
        String Sl = loc.getText();
        String Se = email.getText();
        String Sno = supid.getText();

        if (validator.isNotEmpty("SupPlier Name", Sn) && validator.isNotEmpty("Location", Sl) && validator.isValidEmail(Se) && validator.isNumber("Supplier Id", supid.getText())) {
            int s = JOptionPane.showConfirmDialog(null, "do you want to update");
            if (s == 0) {
                try {
                    //String q2 = "update employee set Name='"+name+"' and Email='"+Email+"' and Contact='"+contact+"' and Address='"+address+"'and Age='"+age+"' where EmpID='"+empid+"' ";
                    String q5 = "update suplierdetails set  suplierName='" + Sn + "', location='" + Sl + "', email='" + Se + "' where suplierNo='" + Sno + "' ";

                    pst = con.prepareStatement(q5);
                    pst.execute();
                    tableload(suplier, "suplierdetails");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            supName.setText("");
            loc.setText("");
            email.setText("");
        }            // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void suplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suplierMouseClicked
        int r = suplier.getSelectedRow();

        String Sid = suplier.getValueAt(r, 0).toString();
        String Sname = suplier.getValueAt(r, 1).toString();
        String Sloc = suplier.getValueAt(r, 2).toString();
        String Semail = suplier.getValueAt(r, 3).toString();

        supid.setText(Sid);
        supName.setText(Sname);
        loc.setText(Sloc);
        email.setText(Semail);
        // TODO add your handling code here:
    }//GEN-LAST:event_suplierMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //int Oqty = Integer.parseInt(oqty.getText());
        String Ono = ono.getText();
        String Odate = odate.getText();
        String status="PENDING";
        // String Odate = odate.getText();
        String Oprname = itemMap.get(prc.getSelectedIndex());
        int Osup =  selectedSuppId;

        if (validator.isNotEmpty("Order No", Ono) && validator.isNotEmpty("Order Date", Odate) && validator.isNotEmpty("Product Name", Oprname) && validator.isNumber("SupplierId", String.valueOf(Osup)) && validator.isNumber("Quantity", oqty.getText())) {
            try {
                int Oqty = Integer.parseInt(oqty.getText());
                String q33 = "INSERT INTO orderdetails (orderNo,orderDate,productcode,quantity,suplierId,oderstatus) VALUES ('" + Ono + "','" + Odate + "','" + Oprname + "'," + Oqty + "," + Osup + ",'"+status+"')";
                pst = con.prepareStatement(q33);
                pst.execute();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "You have successfully entered order details");
            tableload(reqorder, "orderdetails");
            try {
                String url = "https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/h/1xrk3a4grkrk6/?zy%3Db%26f%3D1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1";
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));// TODO add your handling code here:
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
        clearAllODR();
//        try {
//            String url ="https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/h/1xrk3a4grkrk6/?zy%3Db%26f%3D1&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1";
//            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));// TODO add your handling code here:
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String ordn = ordernum.getText();
        String productname = orderpname.getText();
        String invoice = in.getText();
        String redate = date.getText();
        String rstatus="RECIEVED";

        if (validator.isNotEmpty("Order NO", ordn) && validator.isNotEmpty("Product Name", productname) && validator.isNotEmpty("Invoice no", invoice) && validator.isNotEmpty("Return Date", redate) && validator.isNumber("Quantity", recqty.getText()) && validator.isPriceValue("price", reprice.getText())) {
            try {
                int Qty = Integer.parseInt(recqty.getText());
                double price = Double.parseDouble(reprice.getText());
                String q33 = "INSERT INTO receiveorderdetails (orderNum,productcode,quantity,invoiceNo,returndate,payment) VALUES ('" + ordn + "','" + productname + "'," + Qty + ",'" + invoice + "','" + redate + "'," + price + ")";
                pst = con.prepareStatement(q33);
                pst.execute();
                
                String q44="UPDATE  orderdetails  set oderstatus='"+rstatus+"' where orderNo='"+ordn+"'";
                pst = con.prepareStatement(q44);
                pst.execute();
                

                if (productname.startsWith("F")) {
                    recalculateStock("fuel", redate, "proCode", productname, Qty);
                } else if (productname.startsWith("L")) {
                    recalculateStock("lubricants", redate, "prCode", productname, Qty);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "You have successfully entered and stock will be updated");
        }
        clearAllRES();
        //tableload(fueltbl,"fuel");// TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String category = jComboBox1.getSelectedItem().toString();
        loadTable(suplier, "suplierdetails", "suplierName", category); // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void reorderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reorderMouseClicked
        int r = reorder.getSelectedRow();
        String Roid = reorder.getValueAt(r, 0).toString();
        //String orname = reorder.getValueAt(r, 1).toString();
        String Rpname = reorder.getValueAt(r, 2).toString();
        //String oremail = reorder.getValueAt(r, 3).toString();

        ordernum.setText(Roid);
        orderpname.setText(Rpname);
        //loc.setText(Sloc);
        //email.setText(Semail);// TODO add your handling code here:
    }//GEN-LAST:event_reorderMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int s = JOptionPane.showConfirmDialog(null, "do you want to delete");

        if (s == 0) {
            String sid = supid.getText();

            try {
                String q1 = "DELETE FROM suplierdetails WHERE suplierNo = '" + sid + "'";
                pst = con.prepareStatement(q1);
                pst.execute();
                tableload(suplier, "suplierdetails");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        supName.setText("");
        supid.setText("");
        loc.setText("");
        email.setText("");
        //// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        JButton b = new JButton("CLEAR");
        supName.setText("");
        supid.setText("");
        loc.setText("");
        email.setText("");// TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void recqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recqtyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_recqtyKeyTyped

    private void recqtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recqtyActionPerformed

//        String productname = orderpname.getText();
//        try {
//            int quantity = Integer.parseInt(recqty.getText());
//            if (productname.startsWith("L")) {
//                String query = "SELECT buyingPrice FROM lubricants WHERE prCode=\'" + productname + "\'";
//
//                System.out.print(query);
//                Statement selectStmt = con.createStatement();
//                ResultSet rs = selectStmt.executeQuery(query);
//
//                String price = "";
//                while (rs.next()) {
//                    price = rs.getString(1);
//                }
//
//                Double bPrice = Double.parseDouble(price);
//                Double totalVal = bPrice * quantity;
//
//                reprice.setText(totalVal.toString());
//            } else {
//                String query = "SELECT buyingPrice FROM fuel WHERE prCode=\'" + productname + "\'";
//
//                System.out.print(query);
//                Statement selectStmt = con.createStatement();
//                ResultSet rs = selectStmt.executeQuery(query);
//
//                String price = "";
//                while (rs.next()) {
//                    price = rs.getString(1);
//                }
//
//                Double bPrice = Double.parseDouble(price);
//                Double totalVal = bPrice * quantity;
//
//                reprice.setText(totalVal.toString());
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
//        }// TODO add your handling code here:
    }//GEN-LAST:event_recqtyActionPerformed

    private void prcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prcMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_prcMouseClicked

    private void prcMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prcMousePressed
//        String productname = (String) prc.getSelectedItem();
//        if(productname.equals("F011")){
//            jTextField1.setText("Lanka oil pvt ltd");
//        }// TODO add your handling code here:
    }//GEN-LAST:event_prcMousePressed

    private void prcPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_prcPopupMenuWillBecomeInvisible
//        String productname = (String) prc.getSelectedItem();
//        if(productname.equals("F011")){
//            jTextField1.setText("Lanka oil pvt ltd");
//        }// TODO add your handling code here:
    }//GEN-LAST:event_prcPopupMenuWillBecomeInvisible

    private void osupnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_osupnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_osupnameActionPerformed

    private void prcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_prcItemStateChanged
        if (evt.getStateChange() == 1) {
            String productname = (String) prc.getSelectedItem();
            try{
                String v1 = "select * from fuel where proName='" + productname + "'";
                pst = con.prepareStatement(v1);
                rs = pst.executeQuery();
                if (rs.next()) {
                    
                osupname.setText("Lanka Oil Co-operation");
                }
                else{
            String v = "select s.suplierName,s.suplierNo from lubricants l,suplierDetails s where l.prName='" + productname + "' and l.suplierCompany=s.suplierNo";
                System.out.print(v);
                    Statement selectStmt = con.createStatement();
                    ResultSet rs = selectStmt.executeQuery(v);

                    String suplier = "";
                    int supplierNo = 0;
                    while (rs.next()) {
                        suplier = rs.getString(1);
                        supplierNo = rs.getInt(2);
                    }
                osupname.setText(suplier);
                selectedSuppId = supplierNo;
            }}
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

    }//GEN-LAST:event_prcItemStateChanged

    private void recqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_recqtyKeyReleased
        String productname = orderpname.getText();
        try {
            int quantity = Integer.parseInt(recqty.getText());
            if (productname.startsWith("L")) {
                String query = "SELECT buyingPrice FROM lubricants WHERE prCode=\'" + productname + "\'";

                System.out.print(query);
                Statement selectStmt = con.createStatement();
                ResultSet rs = selectStmt.executeQuery(query);

                String price = "";
                while (rs.next()) {
                    price = rs.getString(1);
                }

                Double bPrice = Double.parseDouble(price);
                Double totalVal = bPrice * quantity;

                reprice.setText(totalVal.toString());
            } else {
                String query = "SELECT buyingPrice FROM fuel WHERE proCode=\'" + productname + "\'";

                System.out.print(query);
                Statement selectStmt = con.createStatement();
                ResultSet rs = selectStmt.executeQuery(query);

                String price = "";
                while (rs.next()) {
                    price = rs.getString(1);
                }

                Double bPrice = Double.parseDouble(price);
                Double totalVal = bPrice * quantity;

                reprice.setText(totalVal.toString());
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex){
            reprice.setText("0.00");
        }
    }//GEN-LAST:event_recqtyKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel date;
    private javax.swing.JTextField email;
    private javax.swing.JTextField in;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField loc;
    private javax.swing.JLabel odate;
    private javax.swing.JTextField ono;
    private javax.swing.JTextField oqty;
    private javax.swing.JTextField ordernum;
    private javax.swing.JTextField orderpname;
    private javax.swing.JTextField osupname;
    private javax.swing.JComboBox prc;
    private javax.swing.JTextField recqty;
    private javax.swing.JTable reorder;
    private javax.swing.JTextField reprice;
    private javax.swing.JTable reqorder;
    private javax.swing.JTextField supName;
    private javax.swing.JTextField supid;
    private javax.swing.JTable suplier;
    // End of variables declaration//GEN-END:variables
}
