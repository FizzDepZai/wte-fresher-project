/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.memcached;

/**
 * A singleton memcached
 *
 * @author biendltb
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.library.core.Registry;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MyMemcached {

    // Memcached instance
    private static MyMemcached instance = null;
    // Array of memcached client
    private static MemcachedClient[] m = null;
    private final int maxThread;

    /**
     * Memcached constructor: load config and initialize memcached client
     *
     * @throws IOException
     */
    private MyMemcached() throws IOException {
        // Load configuration
//        Properties pro = new Properties();
//        pro.load(new FileInputStream("../properties/chat.properties"));

        maxThread = Registry.getInt("maxthread", 20);
        String host = Registry.get("host_memcache", "localhost");
        int port = Registry.getInt("port_memcache",11211);

      
            m = new MemcachedClient[maxThread];

            for (int i = 0; i < maxThread; i++) {

                MemcachedClient c = new MemcachedClient(new BinaryConnectionFactory(),
                        AddrUtil.getAddresses(host + ":" + port));

                m[i] = c;
            }
        
    }

    /**
     * Singleton pattern: check and create an instance
     *
     * @return a memcached instance
     * @throws IOException
     */
    public static synchronized MyMemcached getInstance() {
        if (instance == null) {
            System.out.println("Creating a new instance");
            try {
                instance = new MyMemcached();
            } catch (IOException ex) {
                Logger.getLogger(MyMemcached.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    /**
     * Set a pair key - value in memcached
     *
     * @param key the key under which this object should be added
     * @param exp expiration of object
     * @param o the object to store
     */
    public void set(String key, int exp, final Object o) {
        getMemcachedClient().set(key, exp, o);
    }

    /**
     * Get an object from the memcached by key
     * Safe get: time out of a get operation is 5 seconds
     * @param key
     * @return object
     */
    public Object get(String key){
        // Try to get a value, for up to 5 seconds, and cancel if it doesn't return
        Object myObj = null;
        Future<Object> f = getMemcachedClient().asyncGet(key);
        try {
            myObj = f.get(5, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            // Since we don't need this, go ahead and cancel the operation.  This
            // is not strictly necessary, but it'll save some work on the server.
            f.cancel(false);
            // Do other timeout related stuff
        } catch (InterruptedException  ex) {
            Logger.getLogger(MyMemcached.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(MyMemcached.class.getName()).log(Level.SEVERE, null, ex);
        }
        return myObj;
    }

    /**
     * Delete an object in memcached by key
     *
     * @param key
     * @return
     */
    public Object delete(String key) {
        return getMemcachedClient().delete(key);
    }

    /**
     * Get a randomize memcached client form array of memcached clients
     *
     * @return a memcached client
     */
    public MemcachedClient getMemcachedClient() {

        MemcachedClient c = null;
        try {
            int i = (int) (Math.random() % maxThread);
            c = m[i];
        } catch (Exception e) {
        }

        return c;
    }
}
