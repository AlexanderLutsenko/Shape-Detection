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
        return Colors;
    }
    
    
    public void setWebcam(Webcam webcam) {
        this.webcam = webcam;
    }

    void setMinNeighbors(int minNeighbors) {
        this.minNeighbors = minNeighbors;
    }

    void setBaseScale(int baseScale) {
        this.baseScale = baseScale;
    }

    void setMaxScale(int maxScale) {
        this.maxScale = maxScale;
    }

    void setScaleMultiplier_inc(float scaleMultiplier_inc) {
        this.scaleMultiplier_inc = scaleMultiplier_inc;
    }

    void setIncrement(float increment) {
        this.increment = increment;
    }

    void setDoCannyPruning(boolean doCannyPruning) {
        this.doCannyPruning = doCannyPruning;
    }
    
    //Тестовый
    public void setDefaultConfigs() {
        webcam = null;
        minNeighbors = 3;
        baseScale = 50;
        maxScale = 0;
        scaleMultiplier_inc = 1.1f;
        increment = 0.1f;
        doCannyPruning = true;
        
        XMLFiles = new LinkedList<>();
        
        XMLFiles.add("haarcascades"+File.separator+"frontalface_alt2.xml"); 
        XMLFiles.add("haarcascades"+File.separator+"profileface.xml");
        
        Colors = new LinkedList<>();
        
        Colors.add(Color.red);
        Colors.add(Color.orange);
        
    }
    
    public void setConfigs(Webcam webcam, int baseScale, int maxScale, float scaleMultiplier_inc,
            float increment, int minNeighbors, boolean doCannyPruning, LinkedList<String> XMLFiles, 
            LinkedList<Color> Colors) {
        
        this.webcam = webcam;
        this.baseScale = baseScale;
        this.maxScale = maxScale;
        this.scaleMultiplier_inc = scaleMultiplier_inc;
        this.increment = increment;
        this.minNeighbors = minNeighbors;
        this.doCannyPruning = doCannyPruning;
        
        this.XMLFiles = XMLFiles;     
        this.Colors = Colors;
    }
    
    private Webcam webcam;
    private int baseScale;
    private int maxScale;
    private float scaleMultiplier_inc;
    private float increment;
    private int minNeighbors;
    private boolean doCannyPruning;
    
    private LinkedList<String> XMLFiles;
    private LinkedList<Color> Colors;
}
