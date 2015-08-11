/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers.admin;

import application.model.business.EmotionBOImpl;
import application.model.valueobject.EmotionPOJO;
import application.model.valueobject.GroupEmotionPOJO;
import hapax.TemplateDataDictionary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import library.core.AdminController;
import library.core.Registry;
import library.core.View;
import library.memcached.Memcached;

/**
 *
 * @author tript
 */
public class Emotion extends HttpServlet {

    public static final int TOTAL_IMAGE_ROWS = 10;

    public Emotion() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");

        View view = new View("admin", "Emotion.xtm");
        View layout = new View("Index.xtm");
        View codeJSView = new View("assets", "JSEmotion.xtm");

        view.setVariable("imageHost", Registry.get("imageHost"));

        //get list emotions from database
        EmotionBOImpl emotionImpl = new EmotionBOImpl();
        List<GroupEmotionPOJO> listGroupEmotion = emotionImpl.getGroupEmotion();

        List<EmotionPOJO> listEmotion = emotionImpl.getAllEmotion();
        //send list group emotion to session
        Memcached.set("listGroupEmotion", 3600, listGroupEmotion);

        TemplateDataDictionary listEmotionView = view.addSection("listEmotion");

        for (int i = 0; i < listGroupEmotion.size(); i++) {
            TemplateDataDictionary emotionItem = listEmotionView.addSection("emotionItem");
            //add name cho box
            emotionItem.setVariable("groupEmotionName", listGroupEmotion.get(i).name);
            //add id cho box
            emotionItem.setVariable("groupId", Integer.toString(listGroupEmotion.get(i).id));

            int imageIndex = 0;
            //image index cho moi box bat dau bang 1
            for (int j = 0; j < listEmotion.size();) {
                if (listEmotion.get(j).groupEmotionId == listGroupEmotion.get(i).id) {
                    //add image complete then delete in list
//                    if (imageIndex == 0) {
//                        //hinh lon thi add vao variable imageLink0                           
//                        emotionItem.addSection("emotionImageItem").setVariable("imageLink" + imageIndex, listEmotion.get(j).linkImage);
//                    } else {

                    //hinh nho thi vao section row tuong ung de add
                    if (imageIndex < TOTAL_IMAGE_ROWS) {
                        TemplateDataDictionary imageItemRow1 = emotionItem.addSection("imageItemRow1");
                        imageItemRow1.setVariable("imageLink", listEmotion.get(j).linkImage);
                    } else {
                        TemplateDataDictionary imageItemRow2 = emotionItem.addSection("imageItemRow2");
//                            imageItemRow2.setVariable("imageLink", Registry.get("Host")+"/"+listEmotion.get(j).linkImage);
                        imageItemRow2.setVariable("imageLink", listEmotion.get(j).linkImage);
                    }
//                    }
                    listEmotion.remove(j);
                    imageIndex++;

                } else {
                    j++;
                }
                if (imageIndex >= TOTAL_IMAGE_ROWS * 2) {
                    // if totalImage > 9  then break;
                    break;
                }
            }
            //tao row moi khi co 3 box
            if (i % 2 == 0 && i > 0) {
                emotionItem.setVariable("newRowsView", "<div class=\"space-24\"></div>");
            }
        }

        //Tao view
        String content = view.render();
        String codeJS = codeJSView.render();

        layout.setVariable("content", content);
        layout.setVariable("codejs", codeJS);
        //Tao view
        String resourceHost = "";
        resourceHost += Registry.get("Host");
        resourceHost = resourceHost + "/resources";
        layout.setVariable("hostResource", resourceHost);

        String mainView = layout.render();
        response.getWriter().write(mainView);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
