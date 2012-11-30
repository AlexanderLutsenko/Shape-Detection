package shapedetection;

import javax.swing.*;
import shapedetection.gui.MainFrame;

public class ShapeDetection {

    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        MainFrame frame = new MainFrame();
        frame.setVisible(true);

        frame.init();
    }
}
