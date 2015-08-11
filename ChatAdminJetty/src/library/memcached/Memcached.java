/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.spy.memcached.MemcachedClient;

/**
 *
 * @author tript
 */
public class Memcached {

    public static MemcachedClient mem = null;
    public static int portNum = 11211;
    public static String host = "localhost";

    public static void run() throws IOException {
        mem = new MemcachedClient(new InetSocketAddress(host, portNum));
    }

    public static synchronized void set(String key, int expire, Object obj) {
        mem.set(key, expire, obj);
    }

    public static void delete(String key) {
        mem.delete(key);
    }

    public static Object get(String key) {
        Object obj = null;
        Future<Object> f = mem.asyncGet(key);
        try {
            obj = f.get(20, TimeUnit.SECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException ex) {
            
        }
        return obj;
    }
}
