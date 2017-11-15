/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author HP
 */
public class CategoryDetails extends javax.swing.JInternalFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs=null;
    /**
     * Creates new form CategoryDetails
     */
    public CategoryDetails() {
        initComponents();
        conn=DBconnect.connect();
        tableload();
    }

    public void tableload()
    {
       try {
           
           String sql1 = "SELECT Category,Medical_Allowance, Shift_Allowance, Pay_Tax, Overtime_Rate FROM categorydetails";
            pst = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            
            
            tblCategory.setModel(DbUtils.resultSetToTableModel(rs));
            
            
            
         
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     public void clear_search()
   {
       txtCat.setText("");
       tableload();
       
   }
   
   public void clear()
   {
        txtcat.setText("");
        txtmedical.setText("");
        txtShiftAllowance.setText("");
        txtpaytax.setText("");
        txtotrate.setText("");
        
        
        tableload();
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        lblCategory = new javax.swing.JLabel();
        lblShiftAllowance = new javax.swing.JLabel();
        lblRentSubsidy = new javax.swing.JLabel();
        lblMedical = new javax.swing.JLabel();
        lblSupplementary = new javax.swing.JLabel();
        txtShiftAllowance = new javax.swing.JTextField();
        txtpaytax = new javax.swing.JTextField();
        txtotrate = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        txtmedical = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblEmployeeID = new javax.swing.JLabel();
        txtCat = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtcat = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1017, 609));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1030, 576));

        lblCategory.setText("Medical Allowance");

        lblShiftAllowance.setText("Shift Allowance");

        lblRentSubsidy.setText("Pay Tax");

        lblMedical.setText("Overtime Rate");

        lblSupplementary.setText("Category");

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Category ID", "Category", "Shift Allowance", "Rent Subsidy", "Medical Allowance"
            }
        ));
        tblCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoryMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCategory);

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Employee Category Wise Salary Details");

        lblEmployeeID.setText("Category");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnRefresh.setText("Clear");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblEmployeeID)
                        .addGap(18, 18, 18)
                        .addComponent(txtCat, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmployeeID)
                    .addComponent(txtCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(btnSearch)
                .addGap(18, 18, 18)
                .addComponent(btnRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("%");

        jLabel4.setText("%");

        jLabel5.setText("%");

        jLabel6.setText("%");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/InterfacePackage/payrollsolution.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCategory)
                            .addComponent(lblShiftAllowance)
                            .addComponent(lblRentSubsidy)
                            .addComponent(lblSupplementary)
                            .addComponent(lblMedical))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtmedical, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                    .addComponent(txtShiftAllowance)
                                    .addComponent(txtpaytax)
                                    .addComponent(txtotrate))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)))
                            .addComponent(txtcat, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAdd)
                                    .addComponent(btnUpdate)
                                    .addComponent(btnDelete)
                                    .addComponent(jButton1)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblSupplementary)
                                    .addComponent(txtcat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCategory)
                                    .addComponent(txtmedical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblShiftAllowance)
                                    .addComponent(txtShiftAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblRentSubsidy)
                                    .addComponent(txtpaytax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMedical)
                                    .addComponent(jLabel6)
                                    .addComponent(txtotrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(42, 42, 42)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(120, 120, 120))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        
        String category = txtcat.getText();
        String medical = txtmedical.getText();
        String shiftAllowance = txtShiftAllowance.getText();
        String paytax = txtpaytax.getText();
        String otrate = txtotrate.getText();
        

        try{
            if("".equals(medical)||"".equals(shiftAllowance)||"".equals(paytax)||"".equals(otrate)||"".equals(category))
        {
            JOptionPane.showMessageDialog(null, "Fields cannot have null values");
        }
        
        
         /*   
        String t="select * from empsaldetails where Employee_ID='"+employeeID+"'";  
        pst=conn.prepareStatement(t);
        rs =pst.executeQuery();
        if(rs.next())
        {
            JOptionPane.showMessageDialog(null, "Not A registered Employee. Please check again");
        }
                 
        */
            
       
        
        String l="select * from categorydetails where Category='"+category+"'";  
        pst=conn.prepareStatement(l);
        rs =pst.executeQuery();
        
        if(rs.next())
        {
            JOptionPane.showMessageDialog(null, "Category already exists.");
        }
       
        else{
            String x="INSERT INTO categorydetails (Category,Medical_Allowance, Shift_Allowance, Pay_Tax, Overtime_Rate) VALUES ('"+category+"','"+medical+"','"+shiftAllowance+"','"+paytax+"','"+otrate+"')";
            pst=conn.prepareStatement(x);
            pst.execute();
            
            tableload();
            JOptionPane.showMessageDialog(null, "Record Successfully Added");
        }
            //  String y="INSERT INTO Rooms (cus_nic,deluxe,superior,premium,executive,total_price) values ('"+nic+"','"+Deluxe+"','"+Superior+"','"+Premium+"','"+Exe,cutive+"','"+total+"')";
            //  pst=con1.prepareStatement(y);
            //  pst.execute();

        }

        catch(Exception e){

             JOptionPane.showMessageDialog(null, "Please enter numbers only");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
          int s1= JOptionPane.showConfirmDialog(null, "do you want to Update ?");
         
        if( s1==0){
            
        
        String category = txtcat.getText();
        String medical = txtmedical.getText();
        String shiftAllowance = txtShiftAllowance.getText();
        String paytax = txtpaytax.getText();
        String otrate = txtotrate.getText();
        
        try{
               String b="update categorydetails set Medical_Allowance='"+medical+"',Shift_Allowance='"+shiftAllowance+"',Pay_Tax='"+paytax+"',Overtime_Rate='"+otrate+"' WHERE Category='"+category+"'";
               pst=conn.prepareStatement(b);
               pst.executeUpdate();
               tableload();
            
            }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please enter numbers only");
        }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            // TODO add your handling code here:
            
            String cat = txtcat.getText();
            String v="select * from empsaldetails where Salary_Scheme='"+cat+"'";
            pst=conn.prepareStatement(v);
            rs =pst.executeQuery();
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "Cannot Delete Category. There are Employees under This Category");
            }
            else{
            int s1= JOptionPane.showConfirmDialog(null, "do you want to delete");
            
            if( s1==0)
            {
                String catid = txtcat.getText();
                
                try {
                    
                    String q1 = "DELETE FROM categorydetails WHERE Category = '"+catid+"'";
                    pst = conn.prepareStatement(q1);
                    pst.execute();
                    tableload();
                    
                    
                    // tableload1();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } 
        }catch (SQLException ex) {
            Logger.getLogger(CategoryDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoryMouseClicked
        // TODO add your handling code here:
        int row = tblCategory.getSelectedRow();
        
        
        String cat= tblCategory.getValueAt(row, 0).toString();
        String medical = tblCategory.getValueAt(row, 1).toString();
        String shift = tblCategory.getValueAt(row, 2).toString();
        String paytax = tblCategory.getValueAt(row, 3).toString();
        String otrate = tblCategory.getValueAt(row, 4).toString();
        
        
        txtcat.setText(cat);
        txtmedical.setText(medical);
        txtShiftAllowance.setText(shift);
        txtpaytax.setText(paytax);
       
        txtotrate.setText(otrate);
        
    }//GEN-LAST:event_tblCategoryMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String cat = txtCat.getText();

        try{
            String b="Select Category,Medical_Allowance, Shift_Allowance, Pay_Tax, Overtime_Rate from categorydetails WHERE Category LIKE '%"+cat+"%'";
            pst=conn.prepareStatement(b);
            rs = pst.executeQuery();

            tblCategory.setModel(DbUtils.resultSetToTableModel(rs));

        }
        catch(Exception  e){

            System.out.println(e);
        }

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        clear_search();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblEmployeeID;
    private javax.swing.JLabel lblMedical;
    private javax.swing.JLabel lblRentSubsidy;
    private javax.swing.JLabel lblShiftAllowance;
    private javax.swing.JLabel lblSupplementary;
    private javax.swing.JTable tblCategory;
    private javax.swing.JTextField txtCat;
    private javax.swing.JTextField txtShiftAllowance;
    private javax.swing.JTextField txtcat;
    private javax.swing.JTextField txtmedical;
    private javax.swing.JTextField txtotrate;
    private javax.swing.JTextField txtpaytax;
    // End of variables declaration//GEN-END:variables
}
