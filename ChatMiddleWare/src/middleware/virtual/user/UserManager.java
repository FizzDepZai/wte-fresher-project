/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.virtual.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tript Create on 2:16:18 PM Dec 17, 2013
 */
public class UserManager {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserManager.class.getName());
    //threadsafe
    private static final Map<String, String> mapUser = new ConcurrentHashMap<String, String>();

    public static Map<String, String> getMapUser() {
        return mapUser;
    }


}
