
package shapedetection;

import com.github.sarxos.webcam.*;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.image.*;

public class ShapeDetection {

    public static void main(String[] args) {
        JFrame window = new JFrame("Test webcam panel");
        Webcam cam = Webcam.getDefault();
        Dimension dim = new Dimension(320, 240);
        cam.setViewSize(dim);

        WebcamDetectingPanel campanel = new WebcamDetectingPanel(cam);
        campanel.setFPS(25);
        cam.open();
 
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(campanel);
        window.pack();
        
        window.setVisible(true);
    }
}

