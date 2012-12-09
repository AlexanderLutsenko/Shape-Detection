package shapedetection.gui;

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

import shapedetection.model.Model;
import java.awt.Dimension;

public class WebcamDetectingPanel extends JPanel implements WebcamListener {

    private static final long serialVersionUID = 5792962512394656227L;
    private static final Logger LOG = LoggerFactory.getLogger(WebcamPanel.class);

    private class Repainter extends Thread {

        public Repainter() {
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

                    //Cвязываемся с моделью
                    resArray = Model.doEvents(image);
                    //

                    sleep(delay);

                } catch (InterruptedException e) {
                    LOG.error("Nasty interrupted exception");
                }
                if (!paused) {
                    repaint();
                }
            }
        }
    }

    public void Init(Webcam webcam) {
        this.webcam = webcam;
        state = new StateNull();
        
        if (webcam != null) {
            notifyListeners("initBeginned");
            state = new StateNormal();

            this.webcam = webcam;
            this.webcam.addWebcamListener(this);

            webcam.setViewSize(new Dimension(320, 240));
            setSize(webcam.getViewSize());

            if (!webcam.isOpen()) {
                webcam.open();
            }

            if (repainter == null) {
                repainter = new Repainter();
                repainter.start();
            }
            notifyListeners("initEnded");
        } else {      
            notifyListeners("initNull");
        }
    }

    public void setConfigs() {
        ConfigKeeper configKeeper = ConfigManager.getKeeper();

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
        try {
            for (LinkedList<Rectangle> rectangles : resArray) {

                g2.setColor(colors.get(i));
                for (Rectangle rect : rectangles) {
                    g2.drawOval(rect.x, rect.y, rect.width, rect.height);
                }
                i++;

            }
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Override
    public void webcamOpen(WebcamEvent we) {
        state.webcamOpen(we);
    }

    @Override
    public void webcamClosed(WebcamEvent we) {
        state.webcamClosed(we);
    }

    public void pause() {
        state.pause();
    }

    public void resume() {
        state.resume();
    }

    public void dispose() {
        pause();
        if (webcam != null) {
            webcam.close();
        }
        notifyListeners("disposed");
    }

    public void AttachListener(WebcamPanelListener listener) {
        listener.setSubject(this);
        listeners.add(listener);
    }

    public void DetachListener(WebcamPanelListener listener) {
        listeners.remove(listener);
    }

    protected Webcam getWebcam() {
        return webcam;
    }

    protected void notifyListeners(String action) {
        for (WebcamPanelListener listener : listeners) {
            listener.update(action);
        }
    }
    private LinkedList<WebcamPanelListener> listeners = new LinkedList<>();
    private WebcamPanelState state;
    private Webcam webcam = null;
    private BufferedImage image = null;
    private Repainter repainter = null;
    private volatile boolean paused = false;
    private ArrayList<LinkedList<Rectangle>> resArray = new ArrayList<>();
    private LinkedList<Color> colors;
    private int delay;

    protected abstract static class WebcamPanelState {

        protected abstract void webcamOpen(WebcamEvent we);

        protected abstract void webcamClosed(WebcamEvent we);

        protected abstract void pause();

        protected abstract void resume();
    }

    protected class StateNormal extends WebcamPanelState {

        protected void webcamOpen(WebcamEvent we) {
            if (repainter == null) {
                repainter = new WebcamDetectingPanel.Repainter();
                repainter.start();
            }
            setPreferredSize(webcam.getViewSize());
            notifyListeners("opened");
        }

        protected void webcamClosed(WebcamEvent we) {
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
            notifyListeners("closed");
        }

        protected void pause() {
            if (paused) {
                return;
            }
            paused = true;
            notifyListeners("paused");
        }

        protected void resume() {
            if (!paused) {
                return;
            }
            synchronized (repainter) {
                repainter.notifyAll();
            }
            paused = false;
            notifyListeners("resumed");
        }
    }

    protected class StateNull extends WebcamPanelState {

        protected void webcamOpen(WebcamEvent we) {
        }

        protected void webcamClosed(WebcamEvent we) {
        }

        protected void pause() {
        }

        protected void resume() {
        }
    }
}