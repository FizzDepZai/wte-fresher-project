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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.AdminController;
import library.core.Registry;
import library.core.View;
import library.memcached.Memcached;
import org.json.simple.JSONValue;

/**
 *
 * @author tript
 */
public class AddNewEmotion extends
        HttpServlet {

    int groupEmotionId;

    public AddNewEmotion() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");
        groupEmotionId = Integer.parseInt(request.getParameter("groupId"));
        View layout = new View("Index.xtm");
        View view = new View("admin", "AddNewEmotion.xtm");

        TemplateDataDictionary listEmotionView = view.addSection("listEmotion");

        int indexAdd = 0;

        for (int i = 0; i < 10; i++) {
            //show image khong co trong database de add
            TemplateDataDictionary emotionItem = listEmotionView.addSection("emotionItem");
            emotionItem.setVariable("imageIndex", Integer.toString(indexAdd + 1));
            view.setVariable("imageIndex", Integer.toString(indexAdd + 1));
            //sau khi upload image vao folder upload thi tren server da co image 
            indexAdd++;

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
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");
//        ArrayList<EmotionPOJO> arrEmotionAdd = new ArrayList<>();
        String listEmotion = "";
        List listEmotionSONObject = new LinkedList();

        int indexAdd = 0;
        String a = request.getParameter("totalRow");
        int totalRow = Integer.parseInt(request.getParameter("totalRow"));
        for (int i = 0; i < totalRow; i++) {
            HashMap mapEmotionItem = new HashMap();
//            EmotionPOJO emotion = new EmotionPOJO();
//            emotion.linkImage = request.getParameter("style" + (i + 1));
//            emotion.description = request.getParameter("title" + (i + 1));
//            emotion.groupEmotionId = groupEmotionId;
//            emotion.keyInput = request.getParameter("emotionKey" + (i + 1));
//            arrEmotionAdd.add(emotion);
            mapEmotionItem.put("keyInput", request.getParameter("emotionKey" + (i + 1)));
            mapEmotionItem.put("style", request.getParameter("style" + (i + 1)));
            mapEmotionItem.put("src", "http://static.me.zing.vn/v3/images/blank.gif");
            mapEmotionItem.put("title", request.getParameter("title" + (i + 1)));
            mapEmotionItem.put("alt", 0);
            mapEmotionItem.put("group",groupEmotionId);
            listEmotionSONObject.add(mapEmotionItem);
            

        }
        String jsonListEmotionValue  = JSONValue.toJSONString(listEmotionSONObject);
        
//        for (int i = 0; i < arrayLinkImage.length; i++) {
//            //get description input tuong ung
//            if (!arrayLinkImage[i].equals("")) {
//                EmotionPOJO emotion = new EmotionPOJO();
//                //duong dan hien tai chi co x.png ko co link folder
//                emotion.linkImage = arrayLinkImage[i];
//                emotion.description = request.getParameter("description" + (indexAdd + 1));
//                emotion.groupEmotionId = groupEmotionId;
//                arrEmotionAdd.add(emotion);
//                indexAdd++;
//            }
//
//        }

        EmotionBOImpl emotionBoImpl = new EmotionBOImpl();
        if (!emotionBoImpl.addEmotionWithLink(jsonListEmotionValue)) {
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
