/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.memcached;

import java.util.UUID;


/**
 *
 * @author tript
 */
public class SessionClient {
    
    public static String init() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }
    
    public static void set(String sessionId, String key, String value, int expire) {
        Memcached.set(sessionId + "-" + key, expire, value);
    }
    
    public static String get(String sessionId, String key) {
        if (Memcached.get(sessionId + "-" + key) != null) {
            Object value = Memcached.get(sessionId + "-" + key);
            return value.toString();
        }
        return null;
    }
    
    public static void del(String key, String attr) {
        Memcached.delete(key + "-" + attr);
    }
    
  
}
