package shapedetection.config;

import java.io.*;

public class ConfigManager {

    public static ConfigKeeper loadConfigKeeper() {

        FileInputStream configFile = null;
        ConfigKeeper configKeeper = null;

        try {
            configFile = new FileInputStream(userConfigPath);
        } catch (FileNotFoundException e1) {
            try {
                configFile = new FileInputStream(defaultConfigPath);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
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

    public static void saveConfigKeeper(ConfigKeeper configKeeper) {

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
    private static String root = File.listRoots()[0].toString();
    private static String sep = File.separator;
    private static String defaultConfigPath = "configs" + sep + "default.conf";
    
    private static String userConfigDir = root + sep + "Shape-Detection";
    private static String userConfigPath = userConfigDir + sep + "user.conf";
}
