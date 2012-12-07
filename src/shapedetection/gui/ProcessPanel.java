package shapedetection.gui;

import javax.swing.*;

public class ProcessPanel extends JPanel {

    public ProcessPanel(String message) {
        setBounds(55,75,210,90);
        setLayout(null);

        setLayout(null);
        setBorder(BorderFactory.createTitledBorder(message));

        JProgressBar initBar = new JProgressBar(0, 0);

        initBar.setIndeterminate(true);
        int horBound = (getWidth() - 150) / 2;
        int verBound = (getHeight() - 15) / 2;
        initBar.setBounds(horBound, verBound, 150, 15);
        add(initBar);
        repaint();
    }
}
