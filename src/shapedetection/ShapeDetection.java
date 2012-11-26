
package shapedetection;

import com.github.sarxos.webcam.*;
import javax.swing.JFrame;
import java.awt.Dimension;

import java.awt.event.*;


import shapedetection.config.*;

public class ShapeDetection {

    public static void main(String[] args) {
        
        //Тест
        configKeeper = new ConfigKeeper();
        configKeeper.setDefaultConfigs();
        //
        
        ConfigManager.saveConfigKeeper(configKeeper);
        
        configKeeper = ConfigManager.loadConfigKeeper();

        Webcam cam = Webcam.getDefault();
        Dimension dim = new Dimension(320, 240);
        cam.setViewSize(dim);
        
        configKeeper.setWebcam(cam);
        
        JFrame window = new JFrame("Test webcam panel"); 
        
        Model.setConfigs(configKeeper);
        WebcamDetectingPanel campanel = new WebcamDetectingPanel(cam);       
        campanel.setConfigs(configKeeper);
        
        cam.open();
        window.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                configKeeper.getWebcam().close();
                System.exit(0);
            }
        });
        window.add(campanel);
        window.pack();
        
        window.setVisible(true);
    }
    
    private static ConfigKeeper configKeeper;
}
