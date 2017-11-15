/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePackage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mycode.DBconnect;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Sanjani
 */
public class SalaryCalculation extends javax.swing.JInternalFrame {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form SalaryCalculation
     */
    
    
    double medical;
     double ot;                          
    double shift;
     double otrate;           
     double bsal;           
    double pytax;
    String s1,add7;
    public SalaryCalculation() {
        initComponents();
        conn=DBconnect.connect();
        fillCombo();
    }

    public void fillCombo(){
   
   String sql="select Employee_ID from empsaldetails ";
   
   try
   {
       pst=conn.prepareStatement(sql);
       rs=pst.executeQuery();
       while(rs.next())
       {
           cmbeid.addItem(rs.getString("Employee_ID"));
       }
   }
   
   catch(Exception e){
   System.out.println(e);
   }
   }
    
     public void getSalDetails(){
         
         
        
       
        
         //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat_payday = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
         s1 =dateFormat_payday.format(date); //2014/08/06 15:59:48
        
        
        DateFormat dateFormat_month = new SimpleDateFormat("MM");
        Date month = new Date();
        String s2 =dateFormat_month.format(month); //2014/08/06 15:59:48
        
       String eid = cmbeid.getSelectedItem().toString();
         try{
          
            String a ="select * from empsaldetails where Employee_ID='"+eid+"'";
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery(a);
             while(rs1.next()==true)
            {
                bsal=rs1.getDouble("Basic_Salary");
                String c1 = Double.toString(bsal);
                txtbsal.setText(c1);
                
                double hra=rs1.getDouble("HRA");
                String c2 = Double.toString(hra);
                txthra.setText(c2);
                
                double lta=rs1.getDouble("LTA");
                String c3 = Double.toString(lta);
                txtlta.setText(c3);
                
                double cea=rs1.getDouble("CEA");
                String c4 = Double.toString(cea);
                txtcea.setText(c4);
                
                double da=rs1.getDouble("DA");
                String c5 = Double.toString(da);
                txtda.setText(c5);
            
            }
            
            String s ="select * from empsaldetails e,categorydetails c where e.Salary_Scheme=c.Category and e.Employee_ID='"+eid+"'";
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery(s);
            
            while(rs2.next()==true)
            {
                medical=rs2.getDouble("Medical_Allowance");
                               
                shift=rs2.getDouble("Shift_Allowance");
                
                
                pytax=rs2.getDouble("Pay_Tax");
                
                
                otrate=rs2.getDouble("Overtime_Rate");
                double b=bsal*(otrate/100);
                double t = new BigDecimal(b).setScale(2, RoundingMode.HALF_UP).doubleValue();
                txtotrate.setText(Double.toString(t));
 
                
            }
            /*
             String g ="select c.Overtime_Rate from empsaldetails e,categorydetails c where e.Salary_Scheme=c.Category and e.Employee_ID='"+eid+"'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(g);
           
            while(rs.next()==true)
            {
                double otrate=rs.getDouble("Overtime_Rate");
                String h1 = Double.toString(otrate);
                txtotrate.setText(h1);
            }
            */
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }        
        
        
       
        }   
        
         public void clear()
   {
        
        txtgross.setText("");;
        txtepfe.setText("");
        txtpaytax.setText("");
        txtot.setText("");
        epfemployer.setText("");
        txtetf.setText("");
        txtnetpay.setText("");
        txtothours.setText("");
        txtshift.setText("");
        txtmedical.setText("");
        txtloan.setText(""); 
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txtbsal = new javax.swing.JTextField();
        txthra = new javax.swing.JTextField();
        txtlta = new javax.swing.JTextField();
        txtcea = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtda = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnsalscheme = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtmedical = new javax.swing.JTextField();
        txtshift = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtgross = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txtloan = new javax.swing.JTextField();
        txtepfe = new javax.swing.JTextField();
        txtpaytax = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        epfemployer = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtetf = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtotrate = new javax.swing.JTextField();
        txtothours = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtot = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtnetpay = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cmbeid = new javax.swing.JComboBox();
        txtname = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cmbmonth = new javax.swing.JComboBox();
        cmbyear = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1030, 1024));
        setMinimumSize(new java.awt.Dimension(1030, 1024));
        setPreferredSize(new java.awt.Dimension(1017, 609));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(1017, 609));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Earnings");

        jLabel3.setText("House Rent Allowance");

        txtda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdaActionPerformed(evt);
            }
        });

        jLabel4.setText("Leave Travel Assistance");

        jLabel1.setText("Basic Salary");

        jLabel5.setText("Chidren's Education Allowance");

        jLabel7.setText("Dearness Allowance");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txthra)
                    .addComponent(txtbsal)
                    .addComponent(txtlta)
                    .addComponent(txtcea)
                    .addComponent(txtda, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtbsal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnsalscheme.setText("Calculate Other Earnings");
        btnsalscheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalschemeActionPerformed(evt);
            }
        });

        jLabel9.setText("Medical Allowance");

        jLabel13.setText("Shift Allowance");

        txtshift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtshiftActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(btnsalscheme)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmedical)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(txtshift, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtmedical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtshift, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnsalscheme)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Gross Pay");

        jButton1.setText("Calculate Gross Pay");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(txtgross, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txtgross, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Deductions");

        jLabel12.setText("EPF");

        jLabel14.setText("Pay Tax");

        jButton2.setText("Calculate Deductions");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel16.setText("Loan");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtepfe, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtloan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                            .addComponent(txtpaytax, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtepfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtpaytax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtloan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Employer's Contribution");

        jLabel18.setText("EPF");

        jLabel19.setText("ETF");

        txtetf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtetfActionPerformed(evt);
            }
        });

        jButton4.setText("Calculate");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(txtetf, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(epfemployer, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(48, 48, 48))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(epfemployer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtetf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(35, 35, 35))
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Overtime");

        jLabel15.setText("OT Amount");

        jButton3.setText("Calculate OT Amount");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("Overtime Rate");

        jLabel6.setText("Overtime Hours");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtotrate, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(txtothours)
                            .addComponent(txtot))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtotrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtothours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(27, 27, 27)
                .addComponent(jButton3)
                .addGap(22, 22, 22))
        );

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Net Pay");

        jButton5.setText("Calculate Net Pay");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Generate Pay Slip");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Save");
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
                .addGap(58, 58, 58)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(txtnetpay, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7)
                        .addGap(32, 32, 32)))
                .addGap(72, 72, 72))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnetpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(26, 26, 26)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel26.setText("Employee ID");

        jLabel27.setText("Name");

        cmbeid.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbeidPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbeid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbeidMouseClicked(evt);
            }
        });
        cmbeid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbeidActionPerformed(evt);
            }
        });

        txtname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnameActionPerformed(evt);
            }
        });

        jLabel28.setText("Month");

        jLabel29.setText("Year");

        cmbmonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        cmbyear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(43, 43, 43)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbmonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbeid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(cmbeid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(cmbmonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cmbyear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Salary Calculator");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/InterfacePackage/12372.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtetfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtetfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtetfActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        String gross=txtgross.getText();
        
        CalculationMethods  cm1=new CalculationMethods();
        double epfe = cm1.calculate_EPF_Employer(gross);
     
        double t = new BigDecimal(epfe).setScale(2, RoundingMode.HALF_UP).doubleValue();
        String g=Double.toString(t);
        epfemployer.setText(g);
        
        CalculationMethods  cm2=new CalculationMethods();
        double etf = cm2.calculate_ETF(gross);
        double r = new BigDecimal(etf).setScale(2, RoundingMode.HALF_UP).doubleValue();
        String v=Double.toString(r);
     
        txtetf.setText(v);
        
        
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String basic=txtbsal.getText();
        String hra=txthra.getText();
        String cea=txtcea.getText();
        String lta=txtlta.getText();
        String da=txtda.getText();
        String med=txtmedical.getText();
        String shift=txtshift.getText();
        
        CalculationMethods  cm1=new CalculationMethods();
        double gross = cm1.calculateGrossPay(basic,hra,cea,lta,da,med,shift);
        double t = new BigDecimal(gross).setScale(2, RoundingMode.HALF_UP).doubleValue();
        String g=Double.toString(t);
        txtgross.setText(g);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtdaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String gross=txtgross.getText();
         String eid=cmbeid.getSelectedItem().toString();
        CalculationMethods  cm1=new CalculationMethods();
        double epfe = cm1.calculate_EPF_Employee(gross);
        double x = new BigDecimal(epfe).setScale(2, RoundingMode.HALF_UP).doubleValue();
    
        String e=Double.toString(x);
        txtepfe.setText(e);
        
         double shifta=Double.parseDouble(gross)*(pytax/100);
        double t = new BigDecimal(shifta).setScale(2, RoundingMode.HALF_UP).doubleValue();
        txtpaytax.setText(Double.toString(t));
        
        try
        {   String c=null,d=null;
            String sql="select * from loandetails where Employee_ID='"+eid+"'";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next())
            {
           c =rs.getString("Remaining_Installments");
           d =rs.getString("Installment");
           
           if(Integer.parseInt(c)>0)
            {
                txtloan.setText(d);
            }
           else if(Integer.parseInt(c)==0)
           {
                txtloan.setText("0");
           }
           
           }
            
            else{
                txtloan.setText("0");
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       
        
        
       
           
            
            String a = txtotrate.getText();
            double otrate= Double.parseDouble(a);
            
            String b =txtothours.getText();
            
            if("".equals(b))
            {
                txtot.setText("0.0");
            }
            
            else{    
            int  othours=Integer.parseInt(b);
         
            double amount=otrate*othours;
             double t = new BigDecimal(amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
        String ins = Double.toString(t);
          
            txtot.setText(ins);
            }
           /* String s ="select Overtime_Hours from empsaldetails e,categorydetails c where e.Salary_Category=c.Category and e.Employee_ID='"+eid+"'";
            Statement st = conn.createStatement();
            rs = st.executeQuery(s);
            
            while(rs.next()==true)
            {
                double otrate=rs.getDouble("Overtime_Rate");
            }*/
            
       
           
         
      
                
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        String gross=txtgross.getText();
        String epfe=txtepfe.getText();
        String tax=txtpaytax.getText();
        String ln=txtloan.getText();
        String ot=txtot.getText();
        
        if("".equals(txtgross.getText())||"".equals(txtepfe.getText())||"".equals(txtpaytax.getText())||"".equals(txtot.getText())||"".equals(txtloan.getText()))
        {
            JOptionPane.showMessageDialog(null, "Fields are having null values cannot calculate net pay ");
        }   
        else{
        CalculationMethods  cm1=new CalculationMethods();
        double net = cm1.calculate_netpay(gross, epfe, tax, ot,ln);
        
        double t = new BigDecimal(net).setScale(2, RoundingMode.HALF_UP).doubleValue();
        String g=Double.toString(t);
        txtnetpay.setText(g);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        String empid = cmbeid.getSelectedItem().toString();
        String name=txtname.getText();
        String month = cmbmonth.getSelectedItem().toString();
        String year=cmbyear.getSelectedItem().toString();
        String payday = s1;
        String grosspay = txtgross.getText();
        String epf = txtepfe.getText();
        String tax = txtpaytax.getText();
        String overtime = txtot.getText();
        String epfe = epfemployer.getText();
        String etf = txtetf.getText();
        String netpay = txtnetpay.getText();
        String ln = txtloan.getText();
        if("".equals(txtgross.getText())||"".equals(txtepfe.getText())||"".equals(txtpaytax.getText())||"".equals(txtot.getText())||"".equals(txtnetpay.getText())||"".equals(txtloan.getText()))
        {
            JOptionPane.showMessageDialog(null, "Fields are having null values cannot save ");
        }  
        else{
        try{
            
            
            String v="select * from salarypayment where Employee_ID='"+empid+"' and Month='"+month+"' and Year='"+year+"'";
            pst=conn.prepareStatement(v);
            rs =pst.executeQuery();
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "You have already payed salary for this employee for this month");
            }
            else{
            String x="INSERT INTO salarypayment (Employee_ID, Name, Month,Year,Pay_Day, Gross_Pay,EPF,Tax,Loan,Overtime,EPFE,ETF,Net_Pay) VALUES ('"+empid+"','"+name+"','"+month+"','"+year+"','"+payday+"','"+grosspay+"','"+epf+"','"+tax+"','"+ln+"','"+overtime+"','"+epfe+"','"+etf+"','"+netpay+"')";
            pst=conn.prepareStatement(x);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Record Successfully Added");
            
            }
            //  String y="INSERT INTO Rooms (cus_nic,deluxe,superior,premium,executive,total_price) values ('"+nic+"','"+Deluxe+"','"+Superior+"','"+Premium+"','"+Executive+"','"+total+"')";
            //  pst=con1.prepareStatement(y);
            //  pst.execute();

        }

        catch(Exception e){

            System.out.println(e);
        }
        
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
if("".equals(txtgross.getText())||"".equals(txtepfe.getText())||"".equals(txtpaytax.getText())||"".equals(txtot.getText())||"".equals(txtnetpay.getText())||"".equals(txtloan.getText()))
        {
            JOptionPane.showMessageDialog(null, "Fields are having null values cannot print the pay slip ");
        }  
else{
        try {
            // TODO add your handling code here:
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream("Report.pdf"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SalaryCalculation.class.getName()).log(Level.SEVERE, null, ex);
            }
            doc.open();
            doc.add(new Paragraph("----------------Salary Slip----------------"));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Date         : "+s1));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Employee ID  : "+cmbeid.getSelectedItem().toString()));
            doc.add(new Paragraph("Name         : "+txtname.getText().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Gross Salary : "+txtgross.getText().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Deductions    "));
            doc.add(new Paragraph("EPF          : "+txtepfe.getText().toString()));
            doc.add(new Paragraph("Pay Tax      : "+txtpaytax.getText().toString()));
            doc.add(new Paragraph("Loan      : "+txtloan.getText().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Additions    "));
            
            doc.add(new Paragraph("Overtime     : "+txtot.getText().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Employer's Contribution    "));
            doc.add(new Paragraph("EPFE         : "+epfemployer.getText().toString()));
            doc.add(new Paragraph("ETF          : "+txtetf.getText().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Net Pay      : "+txtnetpay.getText().toString()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Your Pay Has Been Made Up As Shown Above"));
            doc.close();
            
            String eid=cmbeid.getSelectedItem().toString();
            try{
            
            String sql="select Remaining_Installments,Amount_Paid from loandetails where Employee_ID='"+eid+"'";
   
   String c=null,d=null;
       pst=conn.prepareStatement(sql);
       rs=pst.executeQuery();
       while(rs.next())
       {
           c =rs.getString("Remaining_Installments");
           d =rs.getString("Amount_Paid");
       }
   
       Double ri =Double.parseDouble(c);
       Double am =Double.parseDouble(d); 
       
       if(ri>0){
           ri--;
           am=am+Double.parseDouble(txtloan.getText());
           String q="update loandetails set Remaining_Installments='"+ri+"',Amount_Paid='"+am+"'where Employee_ID='"+eid+"'";
               pst=conn.prepareStatement(q);
               pst.executeUpdate();
              
        }     
            }  
            catch(Exception e){
            System.out.println(e);}
            
            
            
            
            
            
        } catch (DocumentException ex) {
            Logger.getLogger(SalaryCalculation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}  
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtshiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtshiftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtshiftActionPerformed

    private void btnsalschemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalschemeActionPerformed
        // TODO add your handling code here:
        double shifta=bsal*(shift/100);
        double t = new BigDecimal(shifta).setScale(2, RoundingMode.HALF_UP).doubleValue();
        txtshift.setText(Double.toString(t));
        
        double medicala=bsal*(medical/100);
        double q = new BigDecimal(medicala).setScale(2, RoundingMode.HALF_UP).doubleValue();
        txtmedical.setText(Double.toString(q));
    }//GEN-LAST:event_btnsalschemeActionPerformed

    private void cmbeidPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbeidPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        clear();
        String temp= (String) cmbeid.getSelectedItem();

        String sql ="select * from empsaldetails where Employee_ID=?";

        try
        {
            pst=conn.prepareStatement(sql);
            pst.setString(1, temp);
            rs=pst.executeQuery();

            if(rs.next())
            {
                String add1=rs.getString("Name");
                txtname.setText(add1);
                /*String add2=rs.getString("Basic_Salary");
                txtbsal.setText(add2);
                String add3=rs.getString("HRA");
                txthra.setText(add3);
                String add4=rs.getString("LTA");
                txtlta.setText(add4);
                String add5=rs.getString("CEA");
                txtcea.setText(add5);
                String add6=rs.getString("DA");
                txtda.setText(add6);
                add7=rs.getString("Salary_Scheme");
                
                try
        {
            String l ="select * from categorydetails  where Category='"+add7+"'"; 
            pst=conn.prepareStatement(sql);
            
            rs=pst.executeQuery();
            
            if(rs.next())
            {
                ot=Double.parseDouble(rs.getString("Overtime_Rate"));
                shift=Double.parseDouble(rs.getString("Shift_Allowance"));
                medical=Double.parseDouble(rs.getString("Medical_Allowance"));
                pytax=Double.parseDouble(rs.getString("Pay_Tax"));
    
                
     
            }

            
        }
        
        catch(Exception e){
        System.out.println(e);
        }
*/
                getSalDetails();
            }

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        
        /*   txtbsal.setText("");
        txthra.setText("");
        txtlta.setText("");
        txtcea.setText("");
        txtda.setText("");
        tableload();
        */
    }//GEN-LAST:event_cmbeidPopupMenuWillBecomeInvisible

    private void cmbeidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbeidMouseClicked
        // TODO add your handling code here:
        /* try
        {
            String q="select Name from employee where username=?";
            pst=conn.prepareStatement(q);
            pst.setString(1, (String)cmbempid.getSelectedItem());
            rs =pst.executeQuery();

            txtname.setText(rs.getString("Name"));
        }

        catch(Exception e)
        {
            System.out.println(e);
        }*/
    }//GEN-LAST:event_cmbeidMouseClicked

    private void cmbeidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbeidActionPerformed

    }//GEN-LAST:event_cmbeidActionPerformed

    private void txtnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsalscheme;
    private javax.swing.JComboBox cmbeid;
    private javax.swing.JComboBox cmbmonth;
    private javax.swing.JComboBox cmbyear;
    private javax.swing.JTextField epfemployer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField txtbsal;
    private javax.swing.JTextField txtcea;
    private javax.swing.JTextField txtda;
    private javax.swing.JTextField txtepfe;
    private javax.swing.JTextField txtetf;
    private javax.swing.JTextField txtgross;
    private javax.swing.JTextField txthra;
    private javax.swing.JTextField txtloan;
    private javax.swing.JTextField txtlta;
    private javax.swing.JTextField txtmedical;
    private javax.swing.JTextField txtname;
    private javax.swing.JTextField txtnetpay;
    private javax.swing.JTextField txtot;
    private javax.swing.JTextField txtothours;
    private javax.swing.JTextField txtotrate;
    private javax.swing.JTextField txtpaytax;
    private javax.swing.JTextField txtshift;
    // End of variables declaration//GEN-END:variables
}
