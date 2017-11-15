/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FUEL;

import javax.swing.JProgressBar;

/**
 *
 * @author Lihini
 */
public class Tank {
    
    private String contentName;
    private String contentId;
    private JProgressBar progBarRef;
    private int level;
    private int criticalLevel;
    private int capacity;

    /**
     * @return the contentName
     */
    public String getContentName() {
        return contentName;
    }

    /**
     * @param contentName the contentName to set
     */
    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    /**
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * @return the progBarRef
     */
    public JProgressBar getProgBarRef() {
        return progBarRef;
    }

    /**
     * @param progBarRef the progBarRef to set
     */
    public void setProgBarRef(JProgressBar progBarRef) {
        this.progBarRef = progBarRef;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
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
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
}
