package shapedetection.gui;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import shapedetection.*;
import shapedetection.config.*;

import javax.swing.UIManager;

public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
               
        initComponents();
        startButton.setSelected(true);
        startButton.setEnabled(false);

        //Тест
        configKeeper = new ConfigKeeper();
        configKeeper.setDefaultConfigs();
        //

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                configKeeper.getWebcam().close();
                System.exit(0);
            }
        });


        ConfigManager.saveConfigKeeper(configKeeper);

        configKeeper = ConfigManager.loadConfigKeeper();
    }

    public void init() {

        ProcessPanel procPanel = new ProcessPanel("Поиск камер...");
        desktopPane.add(procPanel);
        
        Webcam cam = Webcam.getDefault();
        configKeeper.setWebcam(cam);     
        Model.setConfigs(configKeeper);
        
        desktopPane.remove(procPanel);
        
        initCam(cam);
        campanel.setConfigs(configKeeper);

        
    }

    public void initCam(Webcam webcam) {
        startButton.setEnabled(false);
        
        if (webcam != null) {
            ProcessPanel procPanel = new ProcessPanel("Инициализация камеры...");
            desktopPane.add(procPanel);

            Dimension dim = new Dimension(320, 240);
            webcam.setViewSize(dim);
            webcam.open();

            campanel = new WebcamDetectingPanel(webcam);

            desktopPane.remove(procPanel);
            desktopPane.add(campanel);

            startButton.setEnabled(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButton = new javax.swing.JToggleButton();
        desktopPane = new javax.swing.JDesktopPane();
        Menu = new javax.swing.JMenuBar();
        Options = new javax.swing.JMenu();
        About = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MainFrame");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        startButton.setText("Стоп");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        Options.setLabel("Настройки");
        Menu.add(Options);

        About.setLabel("О программе");
        Menu.add(About);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        startButton.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (startButton.isSelected()) {
            startButton.setText("Стоп");
            campanel.resume();
        } else {
            startButton.setText("Старт");
            campanel.pause();
        }
    }//GEN-LAST:event_startButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu About;
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenu Options;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JToggleButton startButton;
    // End of variables declaration//GEN-END:variables
    private ConfigKeeper configKeeper;
    private WebcamDetectingPanel campanel;
}
