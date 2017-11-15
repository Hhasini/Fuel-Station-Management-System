/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FUEL;

import java.awt.Color;

/**
 *
 * @author Lihini
 */
public class LevelCellRenderer extends javax.swing.table.DefaultTableCellRenderer {

    private int criticalLevel = 0;
    private int columnIndex = 0;
    
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		//stocktbl.setDefaultRenderer(Object.class, new LevelCellRenderer());
        setColumnIndex(6);
        setCriticalLevel(1000);
        
        Object val = table.getValueAt(row, getColumnIndex());
        String sval = val.toString();       
        
        try {
            int ival = Integer.parseInt(sval);
            if (ival <= getCriticalLevel()) {
                cellComponent.setForeground(Color.black);
                cellComponent.setBackground(Color.red);
            } else {
                cellComponent.setBackground(Color.white);
                cellComponent.setForeground(Color.black);
            }
            if (isSelected) {
                cellComponent.setForeground(table.getSelectionForeground());
                cellComponent.setBackground(table.getSelectionBackground());
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
        return cellComponent;
    }

    /**
     * @return the criticalLevel
     */
    public int getCriticalLevel() {
        return criticalLevel;
    }

    /**
     * @param criticalLevel the criticalLevel to set
     */
    public void setCriticalLevel(int criticalLevel) {
        this.criticalLevel = criticalLevel;
    }

    /**
     * @return the columnIndex
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * @param columnIndex the columnIndex to set
     */
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

}
