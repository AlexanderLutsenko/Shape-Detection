package shapedetection.facedetectiontest;

import shapedetection.violajones.Detector;
import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;
import java.awt.*;
import java.awt.event.*;

import java.util.LinkedList;
import java.util.ArrayList;
import shapedetection.violajones.Detector;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/* Тест обнаружения образов.
 * Пока захват веб-камеры осуществляется библиотекой OpenCV */

public class FaceDetectionTest {

    public static void main(String[] args) throws Exception {

        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();
        grabber.delayedGrab(1);

        IplImage grabbedImage = grabber.grab();
        int width = grabbedImage.width();
        int height = grabbedImage.height();
        IplImage integralImage = IplImage.create(width, height, IPL_DEPTH_8U, 1);
        BufferedImage bImage = grabbedImage.getBufferedImage();
        CvMemStorage storage = CvMemStorage.create();


        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());


        while (frame.isVisible() && (grabbedImage = grabber.grab()) != null) {
            cvClearMemStorage(storage);


            LinkedList<String> XMLFiles = new LinkedList<>();
            XMLFiles.add("haarcascades\\frontalface_default.xml");
            //XMLFiles.add("haarcascades\\frontalface_alt2.xml");

            Detector detector = Detector.create(XMLFiles);

            ArrayList<LinkedList<Rectangle>> resArray = new ArrayList<>(XMLFiles.size());
                resArray = detector.getShapes(bImage, 50, 500, 1.1f, 0.1f, 3 , true);
            
            for (LinkedList<Rectangle> rectangles : resArray) {
                for (Rectangle rect : rectangles) {
                    
                    int x = rect.x, y = rect.y, w = rect.width, h = rect.height;
                    cvRectangle(grabbedImage, cvPoint(x, y), cvPoint(x + w, y + h), CvScalar.GREEN, 1, CV_AA, 0);
                }
            }

            frame.showImage(grabbedImage);



        }

        frame.dispose();

        grabber.stop();
    }
}