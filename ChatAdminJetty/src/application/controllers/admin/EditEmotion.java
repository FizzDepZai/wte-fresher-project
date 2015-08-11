/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controllers.admin;

import application.model.business.EmotionBOImpl;
import application.model.valueobject.EmotionPOJO;
import application.model.valueobject.GroupEmotionPOJO;
import application.views.assets.UploadConstant;
import hapax.TemplateDataDictionary;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import library.core.Registry;
import library.core.View;
import library.memcached.Memcached;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author root
 */
public class EditEmotion extends HttpServlet {

    EmotionPOJO emotionSelected;

    public EditEmotion() {
    }

    /**
     * lay du lieu truc tiep tu views list emotions de hien thi
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");
        View layout = new View("Index.xtm");
        View view = new View("admin", "EditEmotion.xtm");

        TemplateDataDictionary listEmotionView = view.addSection("listEmotion");
        //get list emotion in group
        String groupId = request.getParameter("groupId");
        List<EmotionPOJO> listEmotionInGroup = (List<EmotionPOJO>) Memcached.get("listEmotionInGroup" + groupId);
        List<GroupEmotionPOJO> listGroupEmotion = (List<GroupEmotionPOJO>) Memcached.get("listGroupEmotion");

        //get id from href location 
        int indexEmotionSelected = Integer.parseInt(request.getParameter("id"));
        emotionSelected = listEmotionInGroup.get(indexEmotionSelected);
        //show image khong co trong database de add
        TemplateDataDictionary emotionItem = listEmotionView.addSection("emotionItem");
        emotionItem.setVariable("imageId", Integer.toString(1));
        emotionItem.setVariable("imageLink", listEmotionInGroup.get(indexEmotionSelected).linkImage);
        emotionItem.setVariable("descriptionEmotion", listEmotionInGroup.get(indexEmotionSelected).description);
        for (int i = 0; i < listGroupEmotion.size(); i++) {
            TemplateDataDictionary emotionSelect = emotionItem.addSection("emotionSelect");
            emotionSelect.setVariable("groupEmotionId", Integer.toString(listGroupEmotion.get(i).id));
            emotionSelect.setVariable("groupEmotionName", listGroupEmotion.get(i).name);

        }
        //Tao view
        String content = view.render();
        layout.setVariable("content", content);
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

        String linkImage = uploadNewImage(request, response);

        EmotionBOImpl emotionBoImpl = new EmotionBOImpl();
        //   emotionSelected.description = description;
        // emotionSelected.groupEmotionId = groupEmotionId;
//        emotionSelected.linkImage = linkImage;
//        emotionSelected.description = request.getParameter("description");
//        emotionSelected.linkImage = request.getParameter("imageURL");
//        emotionSelected.groupEmotionId = Integer.parseInt(request.getParameter("groupEmotion"));

        //edit link image and groupEmotionId
        if (!emotionBoImpl.editEmotion(emotionSelected)) {
            //if error show view error
            View layout = new View("Index.xtm");
            View view = new View("admin", "EditEmotion.xtm");
            view.setVariable("errorExist", "Error when edit emotion");
            String content = view.render();
            layout.setVariable("content", content);
            String mainView = layout.render();
            response.getWriter().write(mainView);
        }

        String newURL = response.encodeRedirectURL(Registry.get("Host") + "/groupEmotion/emotion?groupId=" + emotionSelected.groupEmotionId);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", newURL);
        response.setContentType("text/html");
    }

    private String uploadNewImage(HttpServletRequest request, HttpServletResponse response) {
        String linkImage = emotionSelected.linkImage;
        if (!ServletFileUpload.isMultipartContent(request)) {
            return "";
        }
        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(UploadConstant.THRESHOLD_SIZE);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setFileSizeMax(UploadConstant.MAX_FILE_SIZE);
        upload.setSizeMax(UploadConstant.MAX_REQUEST_SIZE);

        // constructs the directory path to store upload file
        //groupEmotionId chinh la folder chua
        // creates the directory if it does not exist
        String[] arrLink = emotionSelected.linkImage.split("/");
        if (arrLink.length < 3) {
            return "";
        }
        try {
            List formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (item.isFormField()) {
                    //form field
                    if (item.getFieldName().equals("groupEmotion")) {
                        try {
                            if (emotionSelected.groupEmotionId != Integer.parseInt(item.getString())) {
                                //thay doi group id --> chuyen file sang folder tuong ung va thay doi imagelink
                                //chuyen file sang folder tuong ung theo group da chon

                                String sourcePath = Registry.get("imageHost") + emotionSelected.linkImage;
                                //item.getString --> groupEmotion chon
                                String desPath = Registry.get("imageHost")
                                        + "/emotions-image/" + item.getString() + "/" + arrLink[3];

                                File sourceDir = new File(sourcePath);
                                File desDir = new File(desPath);
                                if (sourceDir.exists()) {
                                    FileUtils.copyFile(sourceDir, desDir);
                                    sourceDir.delete();

                                }

                                emotionSelected.groupEmotionId = Integer.parseInt(item.getString());
                                linkImage = "/emotions-image/"
                                        + emotionSelected.groupEmotionId + "/" + arrLink[3];

                            }
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (item.getFieldName().equals("description")) {
                        emotionSelected.description = item.getString();
                    }
                    if (item.getFieldName().equals("imageURL")) {
                        emotionSelected.linkImage = item.getString();

                    }
                } else {

                    String fileName = new File(item.getName()).getName();
                    //file name bang empty khi ko upload new image
                    if ("".equals(fileName)) {
                        continue;
                    }
                    String filePath = Registry.get("imageHost") + emotionSelected.linkImage;
                    File newImage = new File(filePath);
                    File oldImage = new File(filePath);
                    // xoa image cu bi edit
                    if (oldImage.exists()) {
                        oldImage.delete();
                    }
                    // save image moi vao  folder cua group id va cung ten moi image cu
                    item.write(newImage);

                    //save lai file cua image moi
                    linkImage = "/emotions-image/" + emotionSelected.groupEmotionId + "/" + arrLink[3];
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(EditEmotion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linkImage;

    }

}
