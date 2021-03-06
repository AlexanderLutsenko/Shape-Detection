package shapedetection.gui;

import com.github.sarxos.webcam.Webcam;
import java.awt.EventQueue;
import java.util.*;
import shapedetection.config.*;

import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Component;

public class OptionsFrame extends javax.swing.JFrame {

    /**
     * Creates new form OptionsFrame
     */
    public OptionsFrame(MainFrame parent) {
        setResizable(false);
        setAlwaysOnTop(true);

        initComponents();
        shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.Y_AXIS));
        this.parent = parent;


    }

    public void getOptions() {
        readOptions(ConfigManager.getKeeper());
    }

    public void getDefaultOptions() {
        readOptions(ConfigManager.getDefaultKeeper());
    }

    private void readOptions(ConfigKeeper keeper) {
        baseScaleSlider.setValue(keeper.getBaseScale());
        maxScaleSlider.setValue(keeper.getMaxScale());
        minNeighborsSpinner.setValue(keeper.getMinNeighbors());
        float v = (keeper.getIncrement() * 100);

        incrementSlider.setValue((int) v);

        scaleMultiplier_incSlider.setValue((int) (keeper.getScaleMultiplier_inc() * 100));
        doCannyPruningCheckBox.setSelected(keeper.getDoCannyPruning());
        delaySpinner.setValue(keeper.getDelay());

        webcamBox.removeAllItems();
        for (Webcam cam : Webcam.getWebcams()) {
            webcamBox.addItem(cam);
        }

        shapePanel.removeAll();

        LinkedList<String> availableXMLs = ConfigManager.getAvailableXMLs();
        LinkedList<String> cascades = keeper.getXMLFiles();
        int k = 0;

        for (String XML : availableXMLs) {
            if (cascades.contains(XML)) {
                Color color = keeper.getColors().get(k);
                CascadePanel cascadePanel = new CascadePanel(XML, color, true);
                shapePanel.add(cascadePanel);
                k++;
            } else {
                CascadePanel cascadePanel = new CascadePanel(XML, Color.GRAY, false);
                shapePanel.add(cascadePanel);
            }
        }
        shapeScrollPane.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ConfigPane = new javax.swing.JTabbedPane();
        AlgorithmPane = new javax.swing.JLayeredPane();
        ScanningWindowPanel = new javax.swing.JPanel();
        baseScaleLabel = new javax.swing.JLabel();
        baseScaleSlider = new javax.swing.JSlider();
        maxScaleLabel = new javax.swing.JLabel();
        maxScaleSlider = new javax.swing.JSlider();
        scaleMultiplier_incSlider = new javax.swing.JSlider();
        scaleMultiplier_incLabel = new javax.swing.JLabel();
        incrementLabel = new javax.swing.JLabel();
        incrementSlider = new javax.swing.JSlider();
        DetectorPanel = new javax.swing.JPanel();
        doCannyPruningLabel = new javax.swing.JLabel();
        doCannyPruningCheckBox = new javax.swing.JCheckBox();
        minNeighborsLabel = new javax.swing.JLabel();
        minNeighborsSpinner = new javax.swing.JSpinner();
        VisualizationPane = new javax.swing.JLayeredPane();
        cameraPanel = new javax.swing.JPanel();
        webcamBox = new javax.swing.JComboBox();
        refreshButton = new javax.swing.JButton();
        delayLabel = new javax.swing.JLabel();
        delaySpinner = new javax.swing.JSpinner();
        shapeScrollPane = new javax.swing.JScrollPane();
        shapePanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        defaultButton = new javax.swing.JButton();

        setTitle("Настройки");

        ConfigPane.setToolTipText("");
        ConfigPane.setName(""); // NOI18N

        ScanningWindowPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Сканирующее окно"));

        baseScaleLabel.setText("Базовый размер");

        baseScaleSlider.setMaximum(240);
        baseScaleSlider.setMinimum(25);
        baseScaleSlider.setMinorTickSpacing(1);
        baseScaleSlider.setPaintLabels(true);

        maxScaleLabel.setText("Максимальный размер");

        maxScaleSlider.setMaximum(240);
        maxScaleSlider.setMinimum(25);
        maxScaleSlider.setMinorTickSpacing(1);

        scaleMultiplier_incSlider.setMaximum(150);
        scaleMultiplier_incSlider.setMinimum(105);
        scaleMultiplier_incSlider.setMinorTickSpacing(5);

        scaleMultiplier_incLabel.setText("Шаг масштабирования");

        incrementLabel.setText("Шаг сдвига");

        incrementSlider.setMaximum(30);
        incrementSlider.setMinimum(5);
        incrementSlider.setMinorTickSpacing(5);

        javax.swing.GroupLayout ScanningWindowPanelLayout = new javax.swing.GroupLayout(ScanningWindowPanel);
        ScanningWindowPanel.setLayout(ScanningWindowPanelLayout);
        ScanningWindowPanelLayout.setHorizontalGroup(
            ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ScanningWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ScanningWindowPanelLayout.createSequentialGroup()
                        .addComponent(baseScaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(baseScaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ScanningWindowPanelLayout.createSequentialGroup()
                        .addComponent(maxScaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(maxScaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ScanningWindowPanelLayout.createSequentialGroup()
                        .addComponent(scaleMultiplier_incLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(scaleMultiplier_incSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ScanningWindowPanelLayout.createSequentialGroup()
                        .addComponent(incrementLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(incrementSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ScanningWindowPanelLayout.setVerticalGroup(
            ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ScanningWindowPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(baseScaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(baseScaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(maxScaleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxScaleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scaleMultiplier_incSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scaleMultiplier_incLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ScanningWindowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(incrementSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(incrementLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        ScanningWindowPanel.setBounds(10, 130, 320, 160);
        AlgorithmPane.add(ScanningWindowPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        DetectorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Детектор"));

        doCannyPruningLabel.setText("Использовать Canny pruning");

        minNeighborsLabel.setText("Минимальное число соседей:");

        minNeighborsSpinner.setModel(new javax.swing.SpinnerNumberModel(5, 1, 10, 1));

        javax.swing.GroupLayout DetectorPanelLayout = new javax.swing.GroupLayout(DetectorPanel);
        DetectorPanel.setLayout(DetectorPanelLayout);
        DetectorPanelLayout.setHorizontalGroup(
            DetectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetectorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DetectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetectorPanelLayout.createSequentialGroup()
                        .addComponent(doCannyPruningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(doCannyPruningCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(DetectorPanelLayout.createSequentialGroup()
                        .addComponent(minNeighborsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minNeighborsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        DetectorPanelLayout.setVerticalGroup(
            DetectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetectorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DetectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(doCannyPruningLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doCannyPruningCheckBox))
                .addGap(7, 7, 7)
                .addGroup(DetectorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minNeighborsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minNeighborsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        DetectorPanel.setBounds(10, 10, 320, 110);
        AlgorithmPane.add(DetectorPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ConfigPane.addTab("Алгоритм", AlgorithmPane);

        cameraPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Камера"));

        webcamBox.setAutoscrolls(true);

        refreshButton.setText("Обновить");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        delayLabel.setText("Задержка захвата, мс:");

        delaySpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 1));

        javax.swing.GroupLayout cameraPanelLayout = new javax.swing.GroupLayout(cameraPanel);
        cameraPanel.setLayout(cameraPanelLayout);
        cameraPanelLayout.setHorizontalGroup(
            cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cameraPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(webcamBox, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshButton)
                    .addGroup(cameraPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(delaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        cameraPanelLayout.setVerticalGroup(
            cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cameraPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(webcamBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delaySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        cameraPanel.setBounds(10, 10, 320, 110);
        VisualizationPane.add(cameraPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        shapeScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Образы"));
        shapeScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout shapePanelLayout = new javax.swing.GroupLayout(shapePanel);
        shapePanel.setLayout(shapePanelLayout);
        shapePanelLayout.setHorizontalGroup(
            shapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );
        shapePanelLayout.setVerticalGroup(
            shapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );

        shapeScrollPane.setViewportView(shapePanel);

        shapeScrollPane.setBounds(10, 130, 320, 160);
        VisualizationPane.add(shapeScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ConfigPane.addTab("Визуализация", VisualizationPane);

        ConfigPane.setSelectedComponent(AlgorithmPane);

        saveButton.setText("OK");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        defaultButton.setText("По умолчанию");
        defaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addGap(18, 18, 18)
                .addComponent(defaultButton)
                .addGap(66, 66, 66))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConfigPane, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {defaultButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ConfigPane, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(defaultButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {defaultButton, saveButton});

        ConfigPane.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                List<Webcam> webcams = Webcam.getWebcams();
                webcamBox.removeAllItems();
                for (Webcam cam : webcams) {
                    webcamBox.addItem(cam);
                }
            }
        });
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void defaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultButtonActionPerformed
        getDefaultOptions();
    }//GEN-LAST:event_defaultButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        ConfigKeeper keeper = ConfigManager.getKeeper();

        int delay = (int) delaySpinner.getValue();
        int baseScale = baseScaleSlider.getValue();
        int maxScale = maxScaleSlider.getValue();
        int minNeighbors = (int) minNeighborsSpinner.getValue();
        float increment = (incrementSlider.getValue()) / 100f;
        float scaleMultiplier_inc = (scaleMultiplier_incSlider.getValue()) / 100f;
        boolean doCannyPruning = doCannyPruningCheckBox.isSelected();

        LinkedList<String> XMLfiles = new LinkedList<>();
        LinkedList<Color> colors = new LinkedList<>();

        Component[] cPanels = shapePanel.getComponents();
        for (Component p : cPanels) {
            CascadePanel panel = (CascadePanel) p;
            if (panel.isActive()) {
                XMLfiles.add(panel.getName());
                colors.add(panel.getColor());
            }
        }

        keeper.setConfigs(null, delay, baseScale, maxScale,
                scaleMultiplier_inc, increment,
                minNeighbors, doCannyPruning, XMLfiles, colors);

        ConfigManager.saveKeeper();

        Webcam webcam = (Webcam) webcamBox.getSelectedItem();
        if (!webcam.isOpen()) {
            parent.initCam(webcam);
        } else {
            parent.setWorkingConfigs();
        }
        
        setVisible(false);
    }//GEN-LAST:event_saveButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane AlgorithmPane;
    private javax.swing.JTabbedPane ConfigPane;
    private javax.swing.JPanel DetectorPanel;
    private javax.swing.JPanel ScanningWindowPanel;
    private javax.swing.JLayeredPane VisualizationPane;
    private javax.swing.JLabel baseScaleLabel;
    private javax.swing.JSlider baseScaleSlider;
    private javax.swing.JPanel cameraPanel;
    private javax.swing.JButton defaultButton;
    private javax.swing.JLabel delayLabel;
    private javax.swing.JSpinner delaySpinner;
    private javax.swing.JCheckBox doCannyPruningCheckBox;
    private javax.swing.JLabel doCannyPruningLabel;
    private javax.swing.JLabel incrementLabel;
    private javax.swing.JSlider incrementSlider;
    private javax.swing.JLabel maxScaleLabel;
    private javax.swing.JSlider maxScaleSlider;
    private javax.swing.JLabel minNeighborsLabel;
    private javax.swing.JSpinner minNeighborsSpinner;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel scaleMultiplier_incLabel;
    private javax.swing.JSlider scaleMultiplier_incSlider;
    private javax.swing.JPanel shapePanel;
    private javax.swing.JScrollPane shapeScrollPane;
    private javax.swing.JComboBox webcamBox;
    // End of variables declaration//GEN-END:variables
    private MainFrame parent;
}
