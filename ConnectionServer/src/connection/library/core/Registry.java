/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection.library.core;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author tript
 */
public class Registry {

    private static Properties properties;

    public static synchronized String get(String key, String defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static void init(String propertyFileName) {
        try {
            String path = "../properties/" + propertyFileName;
            properties = new Properties();
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getInt(String key, int defaultValue) {
        try {
            String value = properties.getProperty(key);
            if (value == null) {
                return defaultValue;
            }
            int valueInt = Integer.parseInt(value);
            return valueInt;
        } catch (NumberFormatException e) {
            return defaultValue;
        }

    }

}
