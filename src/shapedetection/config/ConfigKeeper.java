package shapedetection.config;

import com.github.sarxos.webcam.Webcam;
import java.io.Serializable;
import java.util.LinkedList;
import java.io.File;
import java.awt.Color;

public class ConfigKeeper implements Serializable{

    public Webcam getWebcam() {
        return webcam;
    }

    public int getMinNeighbors() {
        return minNeighbors;
    }

    public int getBaseScale() {
        return baseScale;
    }

    public int getMaxScale() {
        return maxScale;
    }

    public float getScaleMultiplier_inc() {
        return scaleMultiplier_inc;
    }

    public float getIncrement() {
        return increment;
    }

    public boolean getDoCannyPruning() {
        return doCannyPruning;
    }

    public LinkedList<String> getXMLFiles() {
        return XMLFiles;
    }
    
    public LinkedList<Color> getColors() {
        return colors;
    }
    
    public int getDelay() {
        return delay;
    }
    
    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    //Тестовый
    public void setDefaultConfigs() {
        webcam = null;
        minNeighbors = 3;
        baseScale = 50;
        maxScale = 240;
        scaleMultiplier_inc = 1.1f;
        increment = 0.1f;
        doCannyPruning = true;        
        XMLFiles = new LinkedList<>();             
        colors = new LinkedList<>();
        delay = 1;
    }
    
    public void setConfigs(Webcam webcam, int delay, int baseScale, int maxScale, float scaleMultiplier_inc,
            float increment, int minNeighbors, boolean doCannyPruning, LinkedList<String> XMLFiles, 
            LinkedList<Color> Colors) {
        
        this.webcam = webcam;
        this.delay = delay;
        this.baseScale = baseScale;
        this.maxScale = maxScale;
        this.scaleMultiplier_inc = scaleMultiplier_inc;
        this.increment = increment;
        this.minNeighbors = minNeighbors;
        this.doCannyPruning = doCannyPruning;
        
        this.XMLFiles = XMLFiles;     
        this.colors = Colors;
    }
    
    private Webcam webcam;
    private int baseScale;
    private int maxScale;
    private float scaleMultiplier_inc;
    private float increment;
    private int minNeighbors;
    private boolean doCannyPruning;
    
    private volatile LinkedList<String> XMLFiles;
    private volatile LinkedList<Color> colors;
    
    private int delay;
}
