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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Lihini Avanthika
 */
public class ProductDetails extends javax.swing.JInternalFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SalesInputValidator validator = null;
    Map<Integer, String> itemMap = null;

    /**
     * Creates new form ProductDetails
     */
    public ProductDetails() {
        initComponents();
        con = DBconnect.connect();
        tableload(fueltbl, "fuel");
        tableloadLUB(lubtbl, "lubricants");
        loadComboBox(jComboBox2, "lubricants", "categary");
        loadComboBox(jComboBox1, "fuel", "categary");
        validator = new SalesInputValidator();
        itemMap = new HashMap<Integer, String>();
        
        itemMap = (Map<Integer, String>) loadComboBox(itemMap, jPanel5, supnamelub, "suplierDetails", "suplierNo", "suplierName");
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
           // panel.add(jComboBox);
           // panel.revalidate();
           // panel.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDeatils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemMap;
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
    public void tableloadLUB(JTable tbl, String tableName) {
        try {

            String sql1 = "SELECT * FROM " + tableName;
            pst = con.prepareStatement(sql1);
            rs = pst.executeQuery();

            tbl.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
        }
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

    public void updateStock(String productId, int quantity) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            String dateString = formatter.format(today);

            System.out.println("came here");

            String selQuery = "SELECT COUNT(*) AS tot FROM stock WHERE prID='" + productId + "' AND date='" + dateString + "'";
            Statement selectStmt1 = con.createStatement();
            ResultSet rst = selectStmt1.executeQuery(selQuery);
            rst.next();
            int count = rst.getInt("tot");

            System.out.println("========> " + selQuery + "  >>  " + count);

            if (count > 0) {
                String query = "UPDATE stock SET availableQty=" + quantity + " WHERE date='" + dateString + "' AND prID='" + productId + "'";
                pst = con.prepareStatement(query);
                pst.execute();
                System.out.println("==> " + query);
            } else {
                String query = "INSERT INTO stock (prID, availableQty, date) VALUES ('" + productId + "'," + quantity + ",'" + dateString + "')";
                pst = con.prepareStatement(query);
                pst.execute();
                System.out.println("==> " + query);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        jLabel17 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        fueltbl = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        procode = new javax.swing.JTextField();
        proname = new javax.swing.JTextField();
        sell = new javax.swing.JTextField();
        cate = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        buying = new javax.swing.JTextField();
        des = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        lubtbl = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lubcode = new javax.swing.JTextField();
        lubname = new javax.swing.JTextField();
        lubSelling = new javax.swing.JTextField();
        lubcat = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lubBuying = new javax.swing.JTextField();
        lubdes = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lubqty = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        supnamelub = new javax.swing.JComboBox();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(1030, 630));
        setMinimumSize(new java.awt.Dimension(1030, 630));
        setPreferredSize(new java.awt.Dimension(1030, 630));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setMaximumSize(new java.awt.Dimension(1030, 630));
        jPanel1.setMinimumSize(new java.awt.Dimension(1030, 630));
        jPanel1.setPreferredSize(new java.awt.Dimension(1030, 630));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Product Details");

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1030, 630));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1030, 630));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1030, 630));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 102, 255), null, null));
        jPanel2.setFocusable(false);
        jPanel2.setMaximumSize(new java.awt.Dimension(1030, 630));
        jPanel2.setMinimumSize(new java.awt.Dimension(1030, 630));
        jPanel2.setPreferredSize(new java.awt.Dimension(1030, 630));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        fueltbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Product Code", "Product name", "Categary", "Description", "Latest Quantity", "Buying Orice", "Selling Price"
            }
        ));
        fueltbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fueltblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(fueltbl);

        jButton3.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton3.setText("UPDATE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Product Code");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Product Name");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Categary");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Description");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Buying Price");

        proname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pronameActionPerformed(evt);
            }
        });

        sell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sellActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Selling Price");

        jButton1.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Quantity");

        qty.setEditable(false);
        qty.setText("0");
        qty.setToolTipText("");

        jButton9.setFont(new java.awt.Font("Segoe Marker", 1, 12)); // NOI18N
        jButton9.setText("CLEAR");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(procode, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proname, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sell, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(des, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addComponent(buying, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(90, 90, 90)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(35, 35, 35)
                        .addComponent(jButton9))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(70, 70, 70)
                        .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(174, 174, 174))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(des, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(procode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(proname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(buying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(sell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton9))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jButton5.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton5.setText("DELETE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton7.setText("SEARCH");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addGap(7, 7, 7)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(30, 30, 30)
                        .addComponent(jButton5)
                        .addGap(39, 39, 39))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton5)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("FUEL", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(102, 153, 255), null, null));
        jPanel3.setToolTipText("");
        jPanel3.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        lubtbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Categary", "Product Code", "Product Name", "Quantity", "Buying Price", "Selling Price", "Discount"
            }
        ));
        lubtbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lubtblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lubtbl);

        jButton4.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton4.setText("UPDATE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Product Code");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Product Name");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Categary");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Description");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("Buying Price");

        lubname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lubnameActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("Selling Price");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Quantity");

        lubqty.setEditable(false);
        lubqty.setText("0");

        jButton2.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Supplier");

        jButton10.setFont(new java.awt.Font("Segoe Marker", 1, 12)); // NOI18N
        jButton10.setText("CLEAR");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        supnamelub.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lubcode, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lubcat, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lubname, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13))
                .addGap(95, 95, 95)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lubBuying, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lubSelling, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lubdes, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(96, 96, 96)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel18))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lubqty, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                    .addComponent(supnamelub, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(lubcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lubdes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(lubqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lubcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(lubBuying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lubname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel16))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lubSelling, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(supnamelub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton10))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jButton6.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton8.setText("search");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addComponent(jScrollPane2)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton6)
                    .addComponent(jButton8))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("LUBRICANTS", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1045, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String productcode = procode.getText();
        String productname = proname.getText();
        String categary = cate.getText();
        String descrpt = des.getText();
        

        if (validator.isNotEmpty("Product Code", productcode) && validator.isNotEmpty("Product Name", productname) && validator.isNotEmpty("Category", categary) && validator.isNumber("Quantity", qty.getText()) && validator.isPriceValue("Buying Price", buying.getText()) && validator.isPriceValue("Selling price", sell.getText())) {
            //int a = JOptionPane.showMessageDialog(null, "You have successfully entered");
            
            try {
                
        String v="select * from fuel where proCode='"+productcode+"'";  
        pst=con.prepareStatement(v);
        rs =pst.executeQuery();
        if(rs.next())
        {
            JOptionPane.showMessageDialog(null, "Product ID already exists.");
        }
        else{
            if(productcode.startsWith("F")){
                int Qty = Integer.parseInt(qty.getText());
                double buyprice = Double.parseDouble(buying.getText());
                double sellprice = Double.parseDouble(sell.getText());
                String q3 = "INSERT INTO fuel (proCode,proName,description,categary,quantity,buyingPrice,sellingPrice) VALUES ('" + productcode + "','" + productname + "','" + descrpt + "','" + categary + "'," + Qty + "," + buyprice + "," + sellprice + " )";
                pst = con.prepareStatement(q3);
                pst.execute();

                updateStock(productcode, Qty);
                JOptionPane.showMessageDialog(null, "You have successfully entered");

            }else{JOptionPane.showMessageDialog(null, "Product ID must start with F.");}
            }} catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
            //JOptionPane.showMessageDialog(null, "You have successfully entered");
            tableload(fueltbl, "fuel");

        }
        //this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String Lubcat = lubcat.getText();
        String Lubname = lubname.getText();
        String Lubcode = lubcode.getText();
        String Lubdescrpt = lubdes.getText();
        
        
        if(validator.isNotEmpty("Product Code", Lubcode) && validator.isNotEmpty("Product Name", Lubname) && validator.isNotEmpty("Category", Lubcat) && validator.isNumber("Quantity", lubqty.getText()) && validator.isPriceValue("Buying Price", lubBuying.getText()) && validator.isPriceValue("Selling price", lubSelling.getText())){
        try {
            
        double LubBuying = Double.parseDouble(lubBuying.getText());
        double LubSelling = Double.parseDouble(lubSelling.getText());
        int LubQty = Integer.parseInt(lubqty.getText());
        int supcmpLub = Integer.parseInt(itemMap.get(supnamelub.getSelectedIndex()));
        String v="select * from lubricants where prCode='"+Lubcode+"'";  
        pst=con.prepareStatement(v);
        rs =pst.executeQuery();
        if(rs.next())
        {
            JOptionPane.showMessageDialog(null, "Product ID already exists.");
        }
        else{
            if(Lubcode.startsWith("L")){
            String q4 = "INSERT INTO lubricants (categary,prCode,prName,description,buyingPrice,sellingPrice,quantity,suplierCompany) VALUES ('" + Lubcat + "','" + Lubcode + "','" + Lubname + "','" + Lubdescrpt + "'," + LubBuying + "," + LubSelling + "," + LubQty + "," + supcmpLub + " )";
            System.out.println(q4);
            pst = con.prepareStatement(q4);
            pst.execute();

            updateStock(Lubcode, LubQty);
            JOptionPane.showMessageDialog(null, "You have successfully entered");
        }else{JOptionPane.showMessageDialog(null, "Product ID must be starts with L.");}
        }} catch (Exception e) {
            System.out.println(e.getMessage());
        }

       // JOptionPane.showMessageDialog(null, "You have successfully entered");
        tableload(lubtbl, "lubricants");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pronameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pronameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pronameActionPerformed

    private void lubnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lubnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lubnameActionPerformed

    private void fueltblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fueltblMouseClicked
                
        int r = fueltbl.getSelectedRow();

        String Fcode = fueltbl.getValueAt(r, 0).toString();
        String Fname = fueltbl.getValueAt(r, 1).toString();
        String Fdes = fueltbl.getValueAt(r, 2).toString();
        String Fcategary = fueltbl.getValueAt(r, 3).toString();
        String Fqty = fueltbl.getValueAt(r, 4).toString();
        String Fbuying = fueltbl.getValueAt(r, 5).toString();
        String Fselling = fueltbl.getValueAt(r, 6).toString();

        procode.setText(Fcode);
        proname.setText(Fname);
        cate.setText(Fcategary);
        des.setText(Fdes);
        buying.setText(Fbuying);
        sell.setText(Fselling);
        qty.setText(Fqty);        // TODO add your handling code here:
    }//GEN-LAST:event_fueltblMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //int s = JOptionPane.showConfirmDialog(null, "do you want to update");
        //if (s == 0) {
            String fprocode = procode.getText();
            String fproname = proname.getText();
            String fcat = cate.getText();
            String fdes = des.getText();
            String fbuy = buying.getText();
            String fsell = sell.getText();
            String fquan = qty.getText();
            
         if(validator.isNotEmpty("Product Code", fprocode) && validator.isNotEmpty("Product Name", fproname) && validator.isNotEmpty("Category", fcat) && validator.isNumber("Quantity", qty.getText()) && validator.isPriceValue("Buying Price", buying.getText()) && validator.isPriceValue("Selling price", sell.getText())){
         int s = JOptionPane.showConfirmDialog(null, "do you want to update");
    
         if (s == 0) {
            try {
                //String q2 = "update employee set Name='"+name+"' and Email='"+Email+"' and Contact='"+contact+"' and Address='"+address+"'and Age='"+age+"' where EmpID='"+empid+"' ";
                String q5 = "update fuel set  proName='" + fproname + "', description='" + fdes + "', categary='" + fcat + "', quantity=" + fquan + ", buyingPrice='" + fbuy + "', sellingPrice=" + fsell + " where proCode='" + fprocode + "' ";

                pst = con.prepareStatement(q5);
                pst.execute();
                tableload(fueltbl, "fuel");

                int uqty = Integer.parseInt(fquan);
                updateStock(fprocode, uqty);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        procode.setText("");
        proname.setText("");
        cate.setText("");
        des.setText("");
        buying.setText("");
        sell.setText("");
        qty.setText("");   
         }// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int s = JOptionPane.showConfirmDialog(null, "do you want to delete");

        if (s == 0) {
            String fdprocode = procode.getText();

            try {
                String q1 = "DELETE FROM fuel WHERE proCode = '" + fdprocode + "'";
                pst = con.prepareStatement(q1);
                pst.execute();
                tableload(fueltbl, "fuel");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        procode.setText("");
        proname.setText("");
        cate.setText("");
        des.setText("");
        buying.setText("");
        sell.setText("");
        qty.setText("");// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void lubtblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lubtblMouseClicked
        int r = lubtbl.getSelectedRow();

        String Lcategary = lubtbl.getValueAt(r, 0).toString();
        String Lprcode = lubtbl.getValueAt(r, 1).toString();
        String Lprname = lubtbl.getValueAt(r, 2).toString();
        String Ldescription = lubtbl.getValueAt(r, 3).toString();
        String Lbuying = lubtbl.getValueAt(r, 4).toString();
        String Lselling = lubtbl.getValueAt(r, 5).toString();
        String Lqty = lubtbl.getValueAt(r, 6).toString();
        String LsupId = lubtbl.getValueAt(r, 7).toString();
        
        System.out.println(">>>>>>>>>>>>>>>>>>>"+LsupId+" , "+itemMap.get(Integer.parseInt(LsupId)));

        lubcat.setText(Lcategary);
        lubcode.setText(Lprcode);
        lubname.setText(Lprname);
        lubdes.setText(Ldescription);
        lubBuying.setText(Lbuying);
        lubSelling.setText(Lselling);
        lubqty.setText(Lqty);        
        supnamelub.setSelectedIndex(getIndexOfValue(LsupId));
    }//GEN-LAST:event_lubtblMouseClicked

    private int getIndexOfValue(String value){
        int key = 0;
        for (Map.Entry<Integer, String> entrySet : itemMap.entrySet()) {            
            String val = entrySet.getValue();
            if(val.equalsIgnoreCase(value)){
                key = entrySet.getKey();
            }
        }
        return key;
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //int s = JOptionPane.showConfirmDialog(null, "do you want to update");
        //if (s == 0) {
            String lcategary = lubcat.getText();
            String lprcode = lubcode.getText();
            String lprname = lubname.getText();
            String ldes = lubdes.getText();
            String lbuying = lubBuying.getText();
            String lselling = lubSelling.getText();
            String lquan = lubqty.getText();
            String lsupcmp = (String) itemMap.get(supnamelub.getSelectedIndex());
            
            if(validator.isNotEmpty("Product Code", lprcode) && validator.isNotEmpty("Product Name", lprname) && validator.isNotEmpty("Category", lcategary) && validator.isNumber("Quantity", lubqty.getText()) && validator.isPriceValue("Buying Price", lubBuying.getText()) && validator.isPriceValue("Selling price", lubSelling.getText())){
            int s = JOptionPane.showConfirmDialog(null, "do you want to update");
            if (s == 0) {
            try {
                //String q2 = "update employee set Name='"+name+"' and Email='"+Email+"' and Contact='"+contact+"' and Address='"+address+"'and Age='"+age+"' where EmpID='"+empid+"' ";
                String q6 = "update lubricants set  categary='" + lcategary + "', prName='" + lprname + "', description='" + ldes + "', buyingPrice=" + lbuying + ", sellingPrice='" + lselling + "', quantity=" + lquan + ",suplierCompany="+lsupcmp+"  where prCode='" + lprcode + "' ";

                pst = con.prepareStatement(q6);
                pst.execute();
                tableload(lubtbl, "lubricants");
                int uqty = Integer.parseInt(lquan);
                updateStock(lprcode, uqty);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        lubcat.setText("");
        lubcode.setText("");
        lubname.setText("");
        lubdes.setText("");
        lubBuying.setText("");
        lubSelling.setText("");
        lubqty.setText("");
        supnamelub.setSelectedItem(""); 
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int s = JOptionPane.showConfirmDialog(null, "do you want to delete");

        if (s == 0) {
            String ldprcode = lubcode.getText();

            try {
                String q7 = "DELETE FROM lubricants WHERE prCode = '" + ldprcode + "'";
                pst = con.prepareStatement(q7);
                pst.execute();
                tableload(lubtbl, "lubricants");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        lubcat.setText("");
        lubcode.setText("");
        lubname.setText("");
        lubdes.setText("");
        lubBuying.setText("");
        lubSelling.setText("");
        lubqty.setText("");
        supnamelub.setSelectedItem("");// TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String category = jComboBox1.getSelectedItem().toString();
        loadTable(fueltbl, "fuel", "categary", category);// TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String category = jComboBox2.getSelectedItem().toString();
        loadTable(lubtbl, "lubricants", "categary", category);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
         JButton b=new JButton("CLEAR");
       
            //exDate.setText("");
            procode.setText("");
            proname.setText("");
            sell.setText(""); 
            des.setText(""); 
            cate.setText("");
            buying.setText("");
            qty.setText("0"); 
            //des.setText(""); // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void sellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sellActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sellActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
       JButton b=new JButton("CLEAR");
       
            //exDate.setText("");
            lubcat.setText("");
        lubcode.setText("");
        lubname.setText("");
        lubdes.setText("");
        lubBuying.setText("");
        lubSelling.setText("");
        lubqty.setText("0");
        supnamelub.setSelectedItem(""); // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField buying;
    private javax.swing.JTextField cate;
    private javax.swing.JTextField des;
    private javax.swing.JTable fueltbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lubBuying;
    private javax.swing.JTextField lubSelling;
    private javax.swing.JTextField lubcat;
    private javax.swing.JTextField lubcode;
    private javax.swing.JTextField lubdes;
    private javax.swing.JTextField lubname;
    private javax.swing.JTextField lubqty;
    private javax.swing.JTable lubtbl;
    private javax.swing.JTextField procode;
    private javax.swing.JTextField proname;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField sell;
    private javax.swing.JComboBox supnamelub;
    // End of variables declaration//GEN-END:variables
}
