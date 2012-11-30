package shapedetection.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.*;

public class ProcessPanel extends JPanel {

    public ProcessPanel(String message) {
        setLayout(null);
        setSize(320, 240);

        JPanel borderPanel = new JPanel();
        
        int horBound = (this.getWidth() - 210) / 2;
        int verBound = (this.getHeight() - 90) / 2;
        
        borderPanel.setBounds(horBound, verBound, 210,90);
        borderPanel.setLayout(null);
        borderPanel.setBorder(BorderFactory.createTitledBorder(message));
        
        
        JProgressBar initBar = new JProgressBar(0, 0);
        
        initBar.setIndeterminate(true);
        horBound = (borderPanel.getWidth() - 150) / 2;
        verBound = (borderPanel.getHeight() - 15) / 2;
        initBar.setBounds(horBound, verBound, 150, 15);
        
        
        borderPanel.add(initBar);
        add(borderPanel);
    }
}
