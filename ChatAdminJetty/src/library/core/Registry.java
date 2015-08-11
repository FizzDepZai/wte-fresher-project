/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.core;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
/**
 *
 * @author tript
 */
public class Registry{
    private static Properties properties;
    public static synchronized String get(String key){
        return properties.getProperty(key);
    }
    public static void init(String propertyFileName){
        try {
            properties = new Properties();
            properties.load(new FileInputStream(propertyFileName));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}