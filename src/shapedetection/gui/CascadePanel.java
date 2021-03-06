package shapedetection.gui;

import java.awt.Color;
import javax.swing.JColorChooser;
import shapedetection.config.ConfigManager;

public class CascadePanel extends javax.swing.JPanel {

    /**
     * Creates new form cascadePanel
     */
    public CascadePanel(String cascadeName,Color color, boolean isActive) {
        initComponents();
        this.cascadeName = cascadeName;
        String shortName = cascadeName.replace(ConfigManager.CASCADE_PATH, "").replace(".xml", "");
        nameLabel.setText(shortName);  
        this.color = color;
        this.isActive = true;
        isActiveCheckBox.setSelected(isActive);
        this.setSize(320, 50);
        repaintButton();
    }
    
    public String getName(){
        return cascadeName;
    }
    
    public Color getColor(){
        return color;
    }
    
    public boolean isActive(){
        return isActiveCheckBox.isSelected();
    }
    
    private void repaintButton(){
        colorButton.setForeground(color);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorChooser = new javax.swing.JColorChooser();
        nameLabel = new javax.swing.JLabel();
        colorButton = new javax.swing.JButton();
        isActiveCheckBox = new javax.swing.JCheckBox();

        nameLabel.setText("Cascade name");

        colorButton.setForeground(new java.awt.Color(60, 60, 60));
        colorButton.setText("Цвет");
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseColorAction(evt);
            }
        });

        isActiveCheckBox.setText("Активен");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(isActiveCheckBox)
                .addGap(18, 18, 18)
                .addComponent(colorButton)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(isActiveCheckBox))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chooseColorAction(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseColorAction
        color = JColorChooser.showDialog(this,"Выберите цвет",color);
        repaintButton();
    }//GEN-LAST:event_chooseColorAction

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton colorButton;
    private javax.swing.JColorChooser colorChooser;
    private javax.swing.JCheckBox isActiveCheckBox;
    private javax.swing.JLabel nameLabel;
    // End of variables declaration//GEN-END:variables
    private String cascadeName;
    private Color color;
    private boolean isActive;
}
