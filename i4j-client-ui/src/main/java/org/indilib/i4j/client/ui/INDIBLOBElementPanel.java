/*
 *  This file is part of INDI for Java Client UI.
 * 
 *  INDI for Java Client UI is free software: you can redistribute it
 *  and/or modify it under the terms of the GNU General Public License 
 *  as published by the Free Software Foundation, either version 3 of 
 *  the License, or (at your option) any later version.
 * 
 *  INDI for Java Client UI is distributed in the hope that it will blobElement
 *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with INDI for Java Client UI.  If not, see 
 *  <http://www.gnu.org/licenses/>.
 */
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.indilib.i4j.Constants.PropertyPermissions;
import org.indilib.i4j.FileUtils;
import org.indilib.i4j.INDIBLOBValue;
import org.indilib.i4j.client.INDIBLOBElement;
import org.indilib.i4j.client.INDIElement;

/**
 * A panel to represent a <code>INDIBLOBElement</code>.
 * 
 * @author S. Alonso (Zerjillo) [zerjioi at ugr.es]
 * @version 1.36, November 18, 2013
 * @see INDIBLOBElement
 */
public class INDIBLOBElementPanel extends INDIElementPanel {

    /**
     * The BLOB Element for which this panel is a UI of.
     */
    private INDIBLOBElement blobElement;

    /**
     * The desired value for the BLOB element.
     */
    private INDIBLOBValue desiredValue;

    /**
     * Creates new form INDITextElementPanel.
     * 
     * @param be
     *            The BLOB element for which this panel is a UI of.
     * @param perm
     *            The permissions of the property for the element.
     */
    public INDIBLOBElementPanel(INDIBLOBElement be, PropertyPermissions perm) {
        super(perm);

        initComponents();

        if (!isWritable()) {
            sendPanel.setVisible(false);
            mainPanel.remove(sendPanel);
        }

        this.blobElement = be;

        desiredValue = null;

        updateElementData();
    }

    /**
     * Updates the visual data for the element.
     */
    private void updateElementData() {
        name.setText(blobElement.getLabel());
        name.setToolTipText(blobElement.getName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        mainPanel = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        saveBLOBButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        formatText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lengthText = new javax.swing.JTextField();
        sendPanel = new javax.swing.JPanel();
        loadBLOBButton = new javax.swing.JButton();
        messagePanel = new javax.swing.JPanel();
        messageText = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setLayout(new java.awt.BorderLayout());

        name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        name.setMinimumSize(new java.awt.Dimension(100, 0));
        mainPanel.add(name);

        saveBLOBButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/disk.png"))); // NOI18N
        saveBLOBButton.setText("Save");
        saveBLOBButton.setEnabled(false);
        saveBLOBButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBLOBButtonActionPerformed(evt);
            }
        });
        jPanel1.add(saveBLOBButton);

        jPanel2.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Format:");
        jPanel2.add(jLabel1);

        formatText.setEditable(false);
        jPanel2.add(formatText);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Length:");
        jPanel2.add(jLabel2);

        lengthText.setEditable(false);
        lengthText.setColumns(6);
        lengthText.setToolTipText("bytes");
        jPanel2.add(lengthText);

        jPanel1.add(jPanel2);

        mainPanel.add(jPanel1);

        loadBLOBButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/indilib/i4j/client/ui/images/attach.png"))); // NOI18N
        loadBLOBButton.setText("Load...");
        loadBLOBButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBLOBButtonActionPerformed(evt);
            }
        });
        sendPanel.add(loadBLOBButton);

        mainPanel.add(sendPanel);

        messageText.setEditable(false);
        messageText.setColumns(6);
        messagePanel.add(messageText);

        mainPanel.add(messagePanel);

        add(mainPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Saves the BLOB data to a file.
     * 
     * @param evt
     *            The event that lauches the saving process (click on the
     *            appropriate button)
     */
    private void saveBLOBButtonActionPerformed(java.awt.event.ActionEvent evt) {
        FileFilterByExtension filter = new FileFilterByExtension();

        String format = blobElement.getValue().getFormat();

        if (format.startsWith(".")) {
            format = format.substring(1);
        }

        filter.addExtension(format);

        filter.setDescription(format + " format");

        fileChooser.setFileFilter(filter);

        int res = fileChooser.showSaveDialog(this);
        fileChooser.removeChoosableFileFilter(filter);

        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();

            try {
                blobElement.getValue().saveBLOBData(f);
            } catch (IOException e) {
                JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Problem saving BLOB data in " + f.getAbsolutePath(), "Saving problem",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        messageText.setText("Saved");
    }

    /**
     * Loads the BLOB data from a file.
     * 
     * @param evt
     *            The event that lauches the loading process (click on the
     *            appropriate button)
     */
    private void loadBLOBButtonActionPerformed(java.awt.event.ActionEvent evt) {
        byte[] bytes = null;

        int res = fileChooser.showSaveDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();

            int size = (int) f.length();

            bytes = new byte[size];
            int pos = 0;

            try {
                FileInputStream fis = new FileInputStream(f);

                int breaded = fis.read(bytes);
                pos = breaded;

                while ((breaded != -1) && (size < pos)) {
                    breaded = fis.read(bytes, pos, size - pos);

                    if (breaded != -1) {
                        pos += breaded;
                    }
                }

                fis.close();

                desiredValue = new INDIBLOBValue(bytes, FileUtils.getExtensionOfFile(f));

                setChanged(true);

                messageText.setText("Loaded");
            } catch (IOException e) {
                JOptionPane.showMessageDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Problem loading BLOB data from " + f.getAbsolutePath(), "Loading problem",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        checkSetButton();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;

    private javax.swing.JTextField formatText;

    private javax.swing.JLabel jLabel1;

    private javax.swing.JLabel jLabel2;

    private javax.swing.JPanel jPanel1;

    private javax.swing.JPanel jPanel2;

    private javax.swing.JTextField lengthText;

    private javax.swing.JButton loadBLOBButton;

    private javax.swing.JPanel mainPanel;

    private javax.swing.JPanel messagePanel;

    private javax.swing.JTextField messageText;

    private javax.swing.JLabel name;

    private javax.swing.JButton saveBLOBButton;

    private javax.swing.JPanel sendPanel;

    // End of variables declaration//GEN-END:variables

    @Override
    protected Object getDesiredValue() {
        return desiredValue;
    }

    @Override
    protected INDIBLOBElement getElement() {
        return blobElement;
    }

    @Override
    protected void setError(boolean erroneous, String errorMessage) {
    }

    @Override
    protected boolean isDesiredValueErroneous() {
        return false;
    }

    @Override
    protected void cleanDesiredValue() {
        desiredValue = null;
        messageText.setText("");
    }

    @Override
    public void elementChanged(INDIElement element) {
        if (element == blobElement) {
            saveBLOBButton.setEnabled(true);

            formatText.setText(blobElement.getValue().getFormat());
            lengthText.setText(blobElement.getValue().getSize() + "");
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
