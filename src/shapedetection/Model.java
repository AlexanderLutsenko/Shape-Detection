package shapedetection;

import shapedetection.violajones.*;
import shapedetection.config.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Rectangle;

class Model {

    static void setConfigs(ConfigKeeper configKeeper) {
        
        minNeighbors = configKeeper.getMinNeighbors();
        baseScale = configKeeper.getBaseScale();
        maxScale = configKeeper.getMaxScale();
        scaleMultiplier_inc = configKeeper.getScaleMultiplier_inc();
        increment = configKeeper.getIncrement();
        doCannyPruning = configKeeper.getDoCannyPruning();
        XMLFiles = configKeeper.getXMLFiles();
        
        detector = Detector.create(XMLFiles);
        resArray = new ArrayList<>(XMLFiles.size());
        
    }

    static ArrayList<LinkedList<Rectangle>> doEvents(BufferedImage image) {
        resArray = detector.getShapes(image, baseScale, maxScale, scaleMultiplier_inc, increment, minNeighbors, doCannyPruning);
        return resArray;
    }
       
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
