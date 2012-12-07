package shapedetection.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class ExceptionPanel extends JPanel{

    public ExceptionPanel(String message) {
        setBounds(55, 75, 210, 90);
        setLayout(null);

        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Ошибка"));
        
        JLabel exceptionLabel = new JLabel(message);
        
        int horBound = (getWidth() - 150) / 2;
        int verBound = (getHeight() - 15) / 2;
        exceptionLabel.setBounds(horBound, verBound, 150, 15);
        add(exceptionLabel);
        repaint();
    }
}
