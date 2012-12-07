package shapedetection.gui;

import javax.swing.JToggleButton;
import javax.swing.JPanel;

public class WebcamPanelListener {

    public WebcamPanelListener(JToggleButton startButton, JPanel actionPanel) {
        this.startButton = startButton;
        this.actionPanel = actionPanel;
    }

    public void update(String action) {

        //По какой-то причине switch не работает
        if (action == "opened") {
            startButton.setEnabled(true);
        } else if (action == "closed") {
            startButton.setEnabled(false);
        } else if (action == "resumed") {
            startButton.setText("Стоп");
            startButton.setSelected(true);
        } else if (action == "paused") {
            startButton.setText("Старт");
            startButton.setSelected(false);
        } else if (action == "initBeginned") {
            procPanel = new ProcessPanel("Инициализация камеры...");
            actionPanel.add(procPanel);
            actionPanel.repaint();
        } else if (action == "initEnded") {
            actionPanel.remove(procPanel);
            actionPanel.add(subject);
        } else if (action == "initNull") {
            ExceptionPanel exPanel = new ExceptionPanel("Нет камеры для захвата");
            actionPanel.add(exPanel);
            actionPanel.repaint();
            startButton.setEnabled(false);
        } else if (action == "disposed") {
            subject.pause();
            actionPanel.remove(subject);
            actionPanel.repaint();
        } 
    }

    public void setSubject(WebcamDetectingPanel subject) {
        this.subject = subject;
    }
    JToggleButton startButton;
    WebcamDetectingPanel subject;
    ProcessPanel procPanel;
    JPanel actionPanel;
}
