/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.core;

import java.util.UUID;
import javax.servlet.http.HttpSession;

/**
 *
 * @author biendltb
 */
public class SessionManager {

    public static String init() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    private final HttpSession mySession;
    int session_exp = Integer.parseInt(Registry.get("session_exp"));

    /**
     * Init session manager
     *
     * @param session current session
     */
    public SessionManager(HttpSession session) {
        mySession = session;
    }

    /**
     * Check does user login
     *
     * @return true/false
     */
    public Boolean isLogin() {
        String userId = (String) mySession.getAttribute("userId");
        if (userId == null) {
            return false;
        }
        return true;
    }

    /**
     * Set attribute to session
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        // Set expire time
        mySession.setMaxInactiveInterval(session_exp);
        mySession.setAttribute(key, value);
    }

    /**
     * Set login in session
     *
     * @param userId
     * @param oAuthCode
     */
    public void setLogin(String userId, String oAuthCode) {
        mySession.setAttribute("userId", userId);
        mySession.setAttribute("oauthCode", oAuthCode);

        // Set expire time
        mySession.setMaxInactiveInterval(session_exp);
    }

    /**
     * Get attribute from session
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return mySession.getAttribute(key);
    }

    /**
     * Logout of page
     */
    public void logout() {
        mySession.removeAttribute("userId");
        mySession.removeAttribute("oauthCode");
    }

    public void remove(String key) {
        mySession.removeAttribute(key);
    }
}
