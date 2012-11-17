package shapedetection;

import com.github.sarxos.webcam.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import shapedetection.violajones.Detector;



public class WebcamDetectingPanel extends JPanel implements WebcamListener {

    private static final long serialVersionUID = 5792962512394656227L;
    private static final Logger LOG = LoggerFactory.getLogger(WebcamPanel.class);
    private double frequency = 25;

    private class Repainter extends Thread {

        public Repainter() {
            LinkedList<String> XMLFiles = new LinkedList<String>();
            XMLFiles.add("haarcascades\\frontalface_default.xml");
            detector = Detector.create(XMLFiles);
            resArray = new ArrayList<>(XMLFiles.size());

            setDaemon(true);
        }

        @Override
        public void run() {
            super.run();

            while (webcam.isOpen()) {

                image = webcam.getImage();
                if (image == null) {
                    LOG.error("Image is null");
                }

                try {
                    if (paused) {
                        synchronized (this) {
                            this.wait();
                        }
                    }
                    resArray = detector.getShapes(image, 50, 500, 1.1f, 0.1f, 3, true);
                    //Thread.sleep((long) (1000 / frequency));
                } catch (InterruptedException e) {
                    LOG.error("Nasty interrupted exception");
                }

                repaint();
            }
        }
    }
    private Webcam webcam = null;
    private BufferedImage image = null;
    private Repainter repainter = null;

    public WebcamDetectingPanel(Webcam webcam) {

        this.webcam = webcam;
        this.webcam.addWebcamListener(this);

        if (!webcam.isOpen()) {
            webcam.open();
        }

        setPreferredSize(webcam.getViewSize());

        if (repainter == null) {
            repainter = new Repainter();
            repainter.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (image == null) {
            return;
        }
        g.setColor(Color.green);
        g.drawImage(image, 0, 0, null);
        for (LinkedList<Rectangle> rectangles : resArray) {
            for (Rectangle rect : rectangles) {
                g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    @Override
    public void webcamOpen(WebcamEvent we) {
        if (repainter == null) {
            repainter = new Repainter();
            repainter.start();
        }
        setPreferredSize(webcam.getViewSize());
    }

    @Override
    public void webcamClosed(WebcamEvent we) {
        if (repainter != null) {
            if (repainter.isAlive()) {
                try {
                    repainter.join(1000);
                } catch (InterruptedException e) {
                    throw new WebcamException("Thread interrupted", e);
                }
            }
            repainter = null;
        }
    }
    private volatile boolean paused = false;

    public void pause() {
        if (paused) {
            return;
        }
        paused = true;
    }

    public void resume() {
        if (!paused) {
            return;
        }
        synchronized (repainter) {
            repainter.notifyAll();
        }
        paused = false;
    }


    public double getFrequency() {
        return frequency;
    }
    private static final double MIN_FREQUENCY = 0.016; 
    private static final double MAX_FREQUENCY = 25; 
   
    public void setFPS(double frequency) {
        if (frequency > MAX_FREQUENCY) {
            frequency = MAX_FREQUENCY;
        }
        if (frequency < MIN_FREQUENCY) {
            frequency = MIN_FREQUENCY;
        }
        this.frequency = frequency;
    }
    private Detector detector;
    ArrayList<LinkedList<Rectangle>> resArray;
}