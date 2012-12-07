package shapedetection.model;

import shapedetection.violajones.*;
import shapedetection.config.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Rectangle;

public class Model {

    public static void setConfigs() {
        
        ConfigKeeper configKeeper = ConfigManager.getKeeper();
        XMLFiles = configKeeper.getXMLFiles();
        resArray = new ArrayList<>();
        if (XMLFiles != null && XMLFiles.size() != 0) {
            minNeighbors = configKeeper.getMinNeighbors();
            baseScale = configKeeper.getBaseScale();
            maxScale = configKeeper.getMaxScale();
            scaleMultiplier_inc = configKeeper.getScaleMultiplier_inc();
            increment = configKeeper.getIncrement();
            doCannyPruning = configKeeper.getDoCannyPruning();

            resArray.ensureCapacity(XMLFiles.size());
            detector = Detector.create(XMLFiles);       
            
            isInactive = false;
        } else {
            isInactive = true;
        }
    }

    public static ArrayList<LinkedList<Rectangle>> doEvents(BufferedImage image) {
        if (!isInactive) {
            resArray = detector.getShapes(image, baseScale, maxScale, scaleMultiplier_inc, increment, minNeighbors, doCannyPruning);
        }
        return resArray;
    }
    
    private static boolean isInactive;
    
    private static int baseScale;
    private static int maxScale;
    private static float scaleMultiplier_inc;
    private static float increment;
    private static int minNeighbors;
    private static boolean doCannyPruning;
    private static ArrayList<LinkedList<Rectangle>> resArray;
    private static Detector detector;
    private static LinkedList<String> XMLFiles;
}
