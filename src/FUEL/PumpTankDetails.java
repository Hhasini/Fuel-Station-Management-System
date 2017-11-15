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
public class PumpTankDetails extends javax.swing.JInternalFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SalesInputValidator validator = null;
    Map<Integer, String> itemMap = null;

    /**
     * Creates new form PumpTankDetails
     */
    public PumpTankDetails() {
        initComponents();
        con = DBconnect.connect();

        tableload(tanktbl, "tankInfo");
        tableload(pumptbl, "pumpInfo");
        //loadComboBox(jComboBox2, "pumpinfo", "fuelcategary");
        //loadComboBox(Stank, "tankinfo", "fuelcategary");
        loadComboBox(pumpCat, "tankinfo", "tankNo");
        loadComboBox(tankCat, "fuel", "categary");
        itemMap = new HashMap<Integer, String>();
        itemMap = (Map<Integer, String>) loadComboBox(itemMap, jPanel4, tankCat, "fuel", "proCode", "proName");
        validator = new SalesInputValidator();
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
                itemMap.put(id, key);
                jComboBox.addItem(value);
                id++;
            }

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pumptbl = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pumpNo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        pumpVol = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        pumpPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        pPres = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        pumpCat = new javax.swing.JComboBox();
        jButton9 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tanktbl = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tankNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tankCap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tankPrice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tankLevel = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        tankCat = new javax.swing.JComboBox();
        jButton10 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1030, 630));
        setMinimumSize(new java.awt.Dimension(1030, 630));
        setPreferredSize(new java.awt.Dimension(1030, 630));

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Pump Tank Details");

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(1030, 630));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(1030, 630));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1030, 630));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        pumptbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Pump No", "Volume", "Presure", "Retail Price"
            }
        ));
        pumptbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pumptblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(pumptbl);

        jButton4.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton4.setText("UPDATE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Pump No");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Tank ID");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("volume");

        pumpVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pumpVolActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Cost Price");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Pressure");

        jButton3.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton3.setText("ADD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        pumpCat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton9.setFont(new java.awt.Font("Segoe Marker", 1, 12)); // NOI18N
        jButton9.setText("CLEAR");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(22, 22, 22)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pumpNo, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(pumpCat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(78, 78, 78)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pumpPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(pumpVol, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(pPres))
                .addGap(100, 100, 100)
                .addComponent(jButton3)
                .addGap(36, 36, 36)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pumpNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(pumpVol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(pumpCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(pPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(pumpPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jButton9))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jButton6.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(46, 46, 46)
                        .addComponent(jButton6)
                        .addGap(143, 143, 143))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pump", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tanktbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tank No", "Critical Level", "Capacity", "Retail Price"
            }
        ));
        tanktbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tanktblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tanktbl);

        jButton2.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153), 10));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Tank No");

        tankNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankNoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Fuel Product");

        tankCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankCapActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Capacity");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Critical Level");

        tankPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankPriceActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Retail Price");

        tankLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tankLevelActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tankCat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton10.setFont(new java.awt.Font("Segoe Marker", 1, 12)); // NOI18N
        jButton10.setText("CLEAR");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(47, 47, 47)
                                .addComponent(tankNo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(tankCat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tankPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(43, 43, 43)
                                .addComponent(tankLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(47, 47, 47)
                        .addComponent(tankCap, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(314, 314, 314)
                        .addComponent(jButton1)
                        .addGap(31, 31, 31)
                        .addComponent(jButton10)))
                .addGap(0, 303, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tankNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tankLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel5)
                    .addComponent(tankPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tankCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tankCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton10))
                        .addGap(31, 31, 31))))
        );

        jButton5.setFont(new java.awt.Font("Segoe Marker", 1, 14)); // NOI18N
        jButton5.setText("DELETE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(37, 37, 37)
                        .addComponent(jButton5)
                        .addGap(151, 151, 151))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton5))
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tank", jPanel2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1041, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 76, Short.MAX_VALUE))
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
        String TNo = tankNo.getText();
        String TCat = itemMap.get(tankCat.getSelectedIndex());

        if (validator.isNotEmpty("Tank NO", TNo) && validator.isNotEmpty("Fuel Category", TCat) && validator.isNumber("Critical Level", tankLevel.getText()) && validator.isNotDoubleValue("Tank Capacity", tankCap.getText()) && validator.isPriceValue("Cost price", tankPrice.getText())) {
            try {
                double TCap = Double.parseDouble(tankCap.getText());
                int TCrit = Integer.parseInt(tankLevel.getText());
                double TPrice = Double.parseDouble(tankPrice.getText());
                String v = "select * from tankInfo where tankNo='" + TNo + "'";
                pst = con.prepareStatement(v);
                rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Tank ID already exists.");
                } else {
                    String q8 = "INSERT INTO tankInfo (tankNo,productId,capacity,criticalLevel,retailPrice) VALUES ('" + TNo + "','" + TCat + "','" + TCap + "','" + TCrit + "'," + TPrice + " )";
                    pst = con.prepareStatement(q8);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "You have successfully entered");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //JOptionPane.showMessageDialog(null, "You have successfully entered");
            tableload(tanktbl, "tankInfo");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String PNo = pumpNo.getText();
        String PCat = (String) pumpCat.getSelectedItem();

        if (validator.isNotEmpty("Pump NO", PNo) && validator.isNotEmpty("Fuel Category", PCat) && validator.isNotDoubleValue("Volume", pumpVol.getText()) && validator.isNotDoubleValue("Presure", pPres.getText()) && validator.isPriceValue("Cost price", pumpPrice.getText())) {
            try {
                double PVolume = Double.parseDouble(pumpVol.getText());
                double PPres = Double.parseDouble(pPres.getText());
                double PPrice = Double.parseDouble(pumpPrice.getText());
                String v = "select * from pumpInfo where pumpNo='" + PNo + "'";
                pst = con.prepareStatement(v);
                rs = pst.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Pump ID already exists.");
                } else {
                    String q13 = "INSERT INTO pumpInfo (pumpNo,tankId,totalVolume,presure,costPrice) VALUES ('" + PNo + "','" + PCat + "','" + PVolume + "','" + PPres + "'," + PPrice + " )";
                    pst = con.prepareStatement(q13);
                    pst.execute();

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "You have successfully entered");
            tableload(pumptbl, "pumpInfo");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//int s = JOptionPane.showConfirmDialog(null, "do you want to update");
        //if (s == 0) {
        String ppno = pumpNo.getText();
        String ppcat = (String) pumpCat.getSelectedItem();
        String ppvol = pumpVol.getText();
        String pppres = pPres.getText();
        String ppprice = pumpPrice.getText();

        if (validator.isNotEmpty("Pump NO", ppno) && validator.isNotEmpty("Fuel Category", ppcat) && validator.isNotDoubleValue("Volume", pumpVol.getText()) && validator.isNotDoubleValue("Presure", pPres.getText()) && validator.isPriceValue("Cost price", pumpPrice.getText())) {
            int s = JOptionPane.showConfirmDialog(null, "do you want to update");
            if (s == 0) {
                try {
                    //String q2 = "update employee set Name='"+name+"' and Email='"+Email+"' and Contact='"+contact+"' and Address='"+address+"'and Age='"+age+"' where EmpID='"+empid+"' ";
                    String q12 = "update pumpInfo set tankId='" + ppcat + "', totalVolume='" + ppvol + "', presure=" + pppres + ", costPrice='" + ppprice + "' where pumpNo='" + ppno + "' ";

                    pst = con.prepareStatement(q12);
                    pst.execute();
                    tableload(pumptbl, "pumpInfo");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            pumpNo.setText("");
            pumpCat.setSelectedItem("");
            pumpVol.setText("");
            pPres.setText("");
            pumpPrice.setText("");
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void pumpVolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pumpVolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pumpVolActionPerformed

    private void tankLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankLevelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tankLevelActionPerformed

    private void tankPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tankPriceActionPerformed

    private void tankCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankCapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tankCapActionPerformed

    private void tankNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tankNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tankNoActionPerformed

    private void pumptblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pumptblMouseClicked
        int r = pumptbl.getSelectedRow();

        String pno = pumptbl.getValueAt(r, 0).toString();
        String pcat = pumptbl.getValueAt(r, 1).toString();
        String pvol = pumptbl.getValueAt(r, 2).toString();
        String ppres = pumptbl.getValueAt(r, 3).toString();
        String pprice = pumptbl.getValueAt(r, 4).toString();

        pumpNo.setText(pno);
        pumpCat.setSelectedItem(pcat);
        pumpVol.setText(pvol);
        pPres.setText(ppres);
        pumpPrice.setText(pprice);        // TODO add your handling code here:
    }//GEN-LAST:event_pumptblMouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int s = JOptionPane.showConfirmDialog(null, "do you want to delete");

        if (s == 0) {
            String pdno = pumpNo.getText();

            try {
                String q10 = "DELETE FROM pumpInfo WHERE pumpNo = '" + pdno + "'";
                pst = con.prepareStatement(q10);
                pst.execute();
                tableload(pumptbl, "pumpInfo");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        pumpNo.setText("");
        pumpCat.setSelectedItem("");
        pumpVol.setText("");
        pPres.setText("");
        pumpPrice.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tanktblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tanktblMouseClicked
        int r = tanktbl.getSelectedRow();

        String tno = tanktbl.getValueAt(r, 0).toString();
        String tcat = tanktbl.getValueAt(r, 1).toString();
        String tcap = tanktbl.getValueAt(r, 2).toString();
        String tlevel = tanktbl.getValueAt(r, 3).toString();
        String tprice = tanktbl.getValueAt(r, 4).toString();

        tankNo.setText(tno);
        tankCat.setSelectedItem(tcat);
        tankCap.setText(tcap);
        tankLevel.setText(tlevel);
        tankPrice.setText(tprice);        // TODO add your handling code here:
    }//GEN-LAST:event_tanktblMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        String ttno = tankNo.getText();
        String ttcat = itemMap.get(tankCat.getSelectedIndex());
        String ttcap = tankCap.getText();
        String ttlevel = tankLevel.getText();
        String ttprice = tankPrice.getText();

        if (validator.isNotEmpty("Tank NO", ttno) && validator.isNotEmpty("Fuel Category", ttcat) && validator.isNumber("Critical Level", tankLevel.getText()) && validator.isNotDoubleValue("Tank Capacity", tankCap.getText()) && validator.isPriceValue("Cost price", tankPrice.getText())) {
            int s = JOptionPane.showConfirmDialog(null, "do you want to update");
            if (s == 0) {
                try {
                    //String q2 = "update employee set Name='"+name+"' and Email='"+Email+"' and Contact='"+contact+"' and Address='"+address+"'and Age='"+age+"' where EmpID='"+empid+"' ";
                    String q9 = "update tankInfo set   productId='" + ttcat + "', capacity='" + ttcap + "', criticalLevel=" + ttlevel + ", retailPrice='" + ttprice + "' where tankNo='" + ttno + "'";

                    pst = con.prepareStatement(q9);
                    pst.execute();
                    tableload(tanktbl, "tankInfo");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            tankNo.setText("");
            tankCat.setSelectedItem("");
            tankCap.setText("");
            tankLevel.setText("");
            tankPrice.setText("");
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int s = JOptionPane.showConfirmDialog(null, "do you want to delete");

        if (s == 0) {
            String tdno = tankNo.getText();

            try {
                String q11 = "DELETE FROM tankInfo WHERE tankNo = '" + tdno + "'";
                pst = con.prepareStatement(q11);
                pst.execute();
                tableload(tanktbl, "tankInfo");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        tankNo.setText("");
        tankCat.setSelectedItem("");
        tankCap.setText("");
        tankLevel.setText("");
        tankPrice.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        JButton b = new JButton("CLEAR");
        pumpNo.setText("");
        pumpCat.setSelectedItem("");
        pumpVol.setText("");
        pPres.setText("");
        pumpPrice.setText("");// TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        JButton b = new JButton("CLEAR");
        tankNo.setText("");
        tankCat.setSelectedItem("");
        tankCap.setText("");
        tankLevel.setText("");
        tankPrice.setText("");
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField pPres;
    private javax.swing.JComboBox pumpCat;
    private javax.swing.JTextField pumpNo;
    private javax.swing.JTextField pumpPrice;
    private javax.swing.JTextField pumpVol;
    private javax.swing.JTable pumptbl;
    private javax.swing.JTextField tankCap;
    private javax.swing.JComboBox tankCat;
    private javax.swing.JTextField tankLevel;
    private javax.swing.JTextField tankNo;
    private javax.swing.JTextField tankPrice;
    private javax.swing.JTable tanktbl;
    // End of variables declaration//GEN-END:variables
}
