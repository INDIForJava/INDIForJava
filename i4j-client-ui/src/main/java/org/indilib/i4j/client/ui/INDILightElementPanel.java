package org.indilib.i4j.client.ui;

/*
 * #%L
 * INDI for Java Client UI Library
 * %%
 * Copyright (C) 2013 - 2014 indiforjava
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.Dimension;

import org.indilib.i4j.Constants.LightStates;
import org.indilib.i4j.Constants.PropertyPermissions;
import org.indilib.i4j.client.INDIElement;
import org.indilib.i4j.client.INDILightElement;

/**
 * A panel to represent a <code>INDILightElement</code>.
 * 
 * @author S. Alonso (Zerjillo) [zerjioi at ugr.es]
 * @version 1.36, November 18, 2013
 * @see INDILightElement
 */
public class INDILightElementPanel extends INDIElementPanel {

    /**
     * serial Version UID.
     */
    private static final long serialVersionUID = -8353803691556329760L;

    /**
     * the light element behind this pannel.
     */
    private INDILightElement le;

    /**
     * the alert icon.
     */
    private javax.swing.ImageIcon iconAlert = new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/light_alert_big.png"));

    /**
     * the idle icon.
     */
    private javax.swing.ImageIcon iconIdle = new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/light_idle_big.png"));

    /**
     * the busy icon.
     */
    private javax.swing.ImageIcon iconBusy = new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/light_busy_big.png"));

    /**
     * the ok icon.
     */
    private javax.swing.ImageIcon iconOk = new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/light_ok_big.png"));

    /**
     * Creates new form INDILightElementPanel.
     * 
     * @param le
     *            the light element behind this pannel.
     */
    public INDILightElementPanel(INDILightElement le) {
        super(PropertyPermissions.RO);

        initComponents();

        this.le = le;

        updateElementData();
    }

    /**
     * update the view according to the backing element.
     */
    private void updateElementData() {
        name.setText(le.getLabel());
        name.setToolTipText(le.getName());

        LightStates s = (LightStates) le.getValue();

        if (s == LightStates.ALERT) {
            currentValue.setText("Alert");
            currentValue.setIcon(iconAlert);
        } else if (s == LightStates.BUSY) {
            currentValue.setText("Busy");
            currentValue.setIcon(iconBusy);
        } else if (s == LightStates.OK) {
            currentValue.setText("Ok");
            currentValue.setIcon(iconOk);
        } else if (s == LightStates.IDLE) {
            currentValue.setText("Idle");
            currentValue.setIcon(iconIdle);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        name = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        currentValue = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setLayout(new java.awt.BorderLayout(5, 0));

        name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        name.setMinimumSize(new java.awt.Dimension(100, 0));
        add(name, java.awt.BorderLayout.WEST);

        mainPanel.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        currentValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/light_idle_big.png"))); // NOI18N
        mainPanel.add(currentValue);

        add(mainPanel, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents
      // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JLabel currentValue;

    private javax.swing.JPanel mainPanel;

    private javax.swing.JLabel name;

    // End of variables declaration//GEN-END:variables

    @Override
    protected Object getDesiredValue() {
        return null; // There is no desired value for a light
    }

    @Override
    protected INDILightElement getElement() {
        return le;
    }

    @Override
    protected void setError(boolean erroneous, String errorMessage) {
        // No thing to do, a light cannot be erroneous
    }

    @Override
    protected boolean isDesiredValueErroneous() {
        return false; // Cannot be erroneous
    }

    @Override
    protected void cleanDesiredValue() {
        // There is no desired value for a light
    }

    @Override
    public void elementChanged(INDIElement element) {
        if (element == le) {
            updateElementData();
        }
    }

    @Override
    protected int getNameSize() {
        return name.getWidth();
    }

    @Override
    protected void setNameSize(int size) {
        name.setPreferredSize(new Dimension(size, (int) (name.getPreferredSize().getHeight())));
    }
}
