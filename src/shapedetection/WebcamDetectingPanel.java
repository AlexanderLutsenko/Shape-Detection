package shapedetection;

import com.github.sarxos.webcam.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import shapedetection.config.*;

public class WebcamDetectingPanel extends JPanel implements WebcamListener {

    private static final long serialVersionUID = 5792962512394656227L;
    private static final Logger LOG = LoggerFactory.getLogger(WebcamPanel.class);

    private class Repainter extends Thread {

        public Repainter() {
            resArray = new ArrayList<>();
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

                    resArray = Model.doEvents(image);
                    sleep(delay);
                    
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

        setSize(webcam.getViewSize());

        if (repainter == null) {
            repainter = new Repainter();
            repainter.start();
        }
    }

    public void setConfigs(ConfigKeeper configKeeper) {
        this.webcam = configKeeper.getWebcam();
        this.colors = configKeeper.getColors();
        this.delay = configKeeper.getDelay();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        super.paintComponent(g2);
       
        if (image == null) {
            return;
        }

        g2.drawImage(image, 0, 0, null);

        int i = 0;
        for (LinkedList<Rectangle> rectangles : resArray) {
            g2.setColor(colors.get(i));
            for (Rectangle rect : rectangles) {
                g2.drawOval(rect.x, rect.y, rect.width, rect.height);
            }
            i++;
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
    
    private ArrayList<LinkedList<Rectangle>> resArray;
    private LinkedList<Color> colors;
    
    private int delay;
}