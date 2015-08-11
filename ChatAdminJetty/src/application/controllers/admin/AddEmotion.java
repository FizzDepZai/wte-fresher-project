/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers.admin;

import application.model.business.EmotionBOImpl;
import application.model.valueobject.EmotionPOJO;
import application.views.assets.UploadConstant;
import hapax.TemplateDataDictionary;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.AdminController;
import library.core.Registry;
import library.core.View;
import library.memcached.Memcached;

/**
 *
 * @author root
 */
public class AddEmotion extends AdminController {

    String[] arrayLinkImage;
    int groupEmotionId;

    public AddEmotion() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");
        View layout = new View("Index.xtm");
        View view = new View("admin", "AddEmotion.xtm");
        String sessionId = request.getAttribute("sessionIdAdmin").toString();
        arrayLinkImage = (String[]) Memcached.get("arrLinkImage-" + sessionId);
        EmotionBOImpl emotionBoImpl = new EmotionBOImpl();
        try {
            groupEmotionId = Integer.parseInt(request.getParameter("groupId"));
            //  groupEmotionId = Integer.parseInt((String) session.getAttribute("groupEmotionId"));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        //check new image exist in db
        String linkImageExist = emotionBoImpl.checkImageEmotionExist(arrayLinkImage, groupEmotionId);

        TemplateDataDictionary listEmotionView = view.addSection("listEmotion");
        if (!"".equals(linkImageExist)) {
            view.setVariable("errorExist", linkImageExist + " was exist in database");
        }
        int indexAdd = 0;

        for (int i = 0; i < arrayLinkImage.length; i++) {
            if (!"".equals(arrayLinkImage[i])) {
                //show image khong co trong database de add
                TemplateDataDictionary emotionItem = listEmotionView.addSection("emotionItem");
                emotionItem.setVariable("imageId", Integer.toString(indexAdd + 1));
                //sau khi upload image vao folder upload thi tren server da co image 
                emotionItem.setVariable("imageLink", Registry.get("Host")
                        + "/resources/emotions-image/" + UploadConstant.UPLOAD_DIRECTORY + "/" + arrayLinkImage[i]);
                indexAdd++;
            }
        }
        //Tao view
        String resourceHost = "";
        resourceHost += Registry.get("Host");
        resourceHost = resourceHost + "/resources";
        layout.setVariable("hostResource", resourceHost);

        //Tao view
        String content = view.render();
        layout.setVariable("content", content);
        String mainView = layout.render();
        response.getWriter().write(mainView);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");
        ArrayList<EmotionPOJO> arrEmotionAdd = new ArrayList<>();
        int indexAdd = 0;
       
        
        for (int i = 0; i < arrayLinkImage.length; i++) {
            //get description input tuong ung
            if (!arrayLinkImage[i].equals("")) {
                EmotionPOJO emotion = new EmotionPOJO();
                //duong dan hien tai chi co x.png ko co link folder
                emotion.linkImage = arrayLinkImage[i];
                emotion.description = request.getParameter("description" + (indexAdd + 1));
                emotion.groupEmotionId = groupEmotionId;
                arrEmotionAdd.add(emotion);
                indexAdd++;
            }

        }
        EmotionBOImpl emotionBoImpl = new EmotionBOImpl();
        if (!emotionBoImpl.addEmotion(arrEmotionAdd)) {
            //if error show view error
            View layout = new View("Index.xtm");
            View view = new View("admin", "AddEmotion.xtm");
            view.setVariable("errorExist", "Error when add emotion");
            String content = view.render();
            layout.setVariable("content", content);
            String mainView = layout.render();
            response.getWriter().write(mainView);
        }
        String newURL = response.encodeRedirectURL(Registry.get("Host") + "/groupEmotion/emotion?groupId=" + groupEmotionId);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", newURL);
        response.setContentType("text/html");
    }

}
