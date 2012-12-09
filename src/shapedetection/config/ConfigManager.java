package shapedetection.config;

import java.awt.Color;
import java.io.*;
import java.util.LinkedList;

public class ConfigManager {

    //Тест
    public static void createDefault() {
        configKeeper = new ConfigKeeper();
        configKeeper.setDefaultConfigs();
        saveKeeper();
    }
    //

    public static ConfigKeeper getKeeper() {
        if (configKeeper == null) {
            configKeeper = loadKeeper();
        }
        return configKeeper;
    }

    public static ConfigKeeper getDefaultKeeper() {
        if (defaultKeeper == null) {
            defaultKeeper = loadDefaultKeeper();
        }
        return defaultKeeper;
    }
    
    public static void saveKeeper() {

        try {
            File dirOut = new File(userConfigDir);
            dirOut.mkdir();

            File fileOut = new File(userConfigPath);
            fileOut.createNewFile();

            FileOutputStream userConfigFile = new FileOutputStream(userConfigPath);

            ObjectOutputStream out = new ObjectOutputStream(userConfigFile);
            out.writeObject(configKeeper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<String> getAvailableXMLs() {
        if (availableXMLs == null) {
            availableXMLs = new LinkedList<>();
            File XMLdir = new File(CASCADE_PATH);
            File[] files = XMLdir.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getPath().endsWith(".xml")) {
                    availableXMLs.add(file.getPath());
                }
            }
        }
        return availableXMLs;
    }

    public static String getCascadePath() {
        return CASCADE_PATH;
    }

    private static ConfigKeeper loadKeeper() {

        FileInputStream configFile = null;
        ConfigKeeper configKeeper = null;

        try {
            configFile = new FileInputStream(userConfigPath);
        } catch (FileNotFoundException e) {
            return loadDefaultKeeper();
        }

        try {
            ObjectInputStream in = new ObjectInputStream(configFile);
            configKeeper = (ConfigKeeper) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return configKeeper;
    }

    private static ConfigKeeper loadDefaultKeeper() {

        FileInputStream configFile = null;
        ConfigKeeper configKeeper = null;

        try {
            configFile = new FileInputStream(defaultConfigPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream in = new ObjectInputStream(configFile);
            configKeeper = (ConfigKeeper) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return configKeeper;
    }
    private static final String root = File.listRoots()[0].toString();
    private static final String sep = File.separator;
    private static final String defaultConfigPath = "configs" + sep + "default.conf";
    private static final String userConfigDir = root + sep + "Shape-Detection";
    private static final String userConfigPath = userConfigDir + sep + "user.conf";
    public static final String CASCADE_PATH = "haarcascades" + sep;
    private static ConfigKeeper configKeeper;
    private static ConfigKeeper defaultKeeper;
    private static LinkedList<String> availableXMLs;
}