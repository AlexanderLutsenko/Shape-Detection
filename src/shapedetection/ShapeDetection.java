
package shapedetection;

import com.github.sarxos.webcam.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.image.*;

import shapedetection.config.*;

import java.awt.Color;
public class ShapeDetection {

    public static void main(String[] args) {
        
        //Тест
        ConfigKeeper configKeeper = new ConfigKeeper();
        configKeeper.setDefaultConfigs();
        //
        
        ConfigManager.saveConfigKeeper(configKeeper);
        
        configKeeper = ConfigManager.loadConfigKeeper();

        Webcam cam = Webcam.getDefault();
        Dimension dim = new Dimension(320, 240);
        cam.setViewSize(dim);
        
        configKeeper.setWebcam(cam);
        
        Model.getConfigs(configKeeper);
        
        JFrame window = new JFrame("Test webcam panel"); 
        
        WebcamDetectingPanel campanel = new WebcamDetectingPanel(cam);       
        
        campanel.getConfigs(configKeeper);
        
        cam.open();
 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(campanel);
        window.pack();
        
        window.setVisible(true);
    }
}

