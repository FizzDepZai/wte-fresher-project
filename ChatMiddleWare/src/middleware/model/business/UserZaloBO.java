/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middleware.model.business;

import com.vng.zalosdk.builder.ZaloServiceBuilder;
import com.vng.zalosdk.entity.ShortProfile;
import com.vng.zalosdk.entity.StdProfile;
import com.vng.zalosdk.exceptions.ZaloSdkException;
import com.vng.zalosdk.model.AuthorizedCode;
import com.vng.zalosdk.model.Token;
import com.vng.zalosdk.oauth.OAuthService;
import com.vng.zalosdk.service.ZaloQueryServiceImpl;
import middleware.utility.ZaloInApp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import middleware.frontend.ConnectionChatHandler;

/**
 *
 * @author tript Create on 9:41:14 PM Dec 20, 2013
 */
public class UserZaloBO {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserZaloBO.class);

    OAuthService service;
    Token accessToken;
    ZaloQueryServiceImpl queryService;

    public UserZaloBO(String oauthCode) {
        try {
            service = new ZaloServiceBuilder().appID(ZaloInApp.appID).appSecret(ZaloInApp.appSecret).callback(ZaloInApp.urlCallBack).build();
            AuthorizedCode ac = new AuthorizedCode(oauthCode);
            accessToken = service.getAccessToken(ac);
            queryService = new ZaloQueryServiceImpl();
        } catch (Exception ex) {
            logger.error("contructor UserZaloBO:" + ex);
        }
    }

    public StdProfile getProfile() {
        StdProfile profile = null;
        try {
            profile = queryService.getProfile(ZaloInApp.appIDNumber, accessToken.getToken());
        } catch (Exception ex) {
            logger.error("getProfile:" + ex);
        }
        return profile;
    }

    public List<ShortProfile> getFriends() {
        List<ShortProfile> friendList = null;
        try {
            friendList = queryService.getFriends(ZaloInApp.appIDNumber, accessToken.getToken(), 0, 1000);
        } catch (Exception ex) {
            logger.error("getFriends:" + ex);
        }
        return friendList;
    }
;
}
