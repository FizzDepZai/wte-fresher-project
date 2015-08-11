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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import library.core.AdminController;
import library.core.Registry;
import library.core.View;
import library.memcached.Memcached;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author tript
 */
@MultipartConfig
public class EmotionList extends HttpServlet {

    public EmotionList() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Type", "text/html");
        String groupId = request.getParameter("groupId");

        View view = new View("admin", "EmotionList.xtm");
        View codeJSView = new View("assets", "JSEmotionList.xtm");

        //set groupId cho code js emotion
        codeJSView.setVariable("groupIdJS", groupId);
        View layout = new View("Index.xtm");
        view.setVariable("host", Registry.get("Host"));
        view.setVariable("groupId", groupId);
        EmotionBOImpl emotionImpl = new EmotionBOImpl();
        List<EmotionPOJO> listEmotionInGroup = emotionImpl.getEmotionWithGroup(groupId);
        Memcached.set("listEmotionInGroup" + groupId, 3600, listEmotionInGroup);

        //set  table list emotion
        TemplateDataDictionary listEmotionView = view.addSection("listEmotion");
        for (int i = 0; i < listEmotionInGroup.size(); i++) {
            TemplateDataDictionary emotionItem = listEmotionView.addSection("emotionItem");
//            emotionItem.setVariable("imageId", Integer.toString(i + 1));
            emotionItem.setVariable("imageId", Integer.toString(listEmotionInGroup.get(i).emotionId));
            //lay link trong database set cho image 
//            emotionItem.setVariable("imageLink", Registry.get("Host") + "/" + listEmotionInGroup.get(i).linkImage);
            emotionItem.setVariable("imageLink", listEmotionInGroup.get(i).linkImage);
            emotionItem.setVariable("description", listEmotionInGroup.get(i).description);
            emotionItem.setVariable("emotionId", Integer.toString(listEmotionInGroup.get(i).emotionId));
            emotionItem.setVariable("emotionIndexInGroup", Integer.toString(i));
            emotionItem.setVariable("groupId", groupId);

        }
        String resourceHost = "";
        resourceHost += Registry.get("Host");
        resourceHost = resourceHost + "/resources";
        layout.setVariable("hostResource", resourceHost);

        //show view
        String content = view.render();
        String codeJS = codeJSView.render();
        layout.setVariable("content", content);
        layout.setVariable("codejs", codeJS);
          //Tao view

        String mainView = layout.render();
        response.getWriter().write(mainView);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //upload image to browser for add emotions
        //upload image to browser for add emotions
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
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
        // String uploadPath = Registry.get("Host") +"/emotions-image/"+ UPLOAD_DIRECTORY;
        String uploadPath = Registry.get("imageHost") + "/emotions-image/" + UploadConstant.UPLOAD_DIRECTORY;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        //list image user upload
        String[] arrLinkImage = null;
        try {
            int indexImage = 0;
            // parses the request's content to extract file data
            List formItems = upload.parseRequest(request);
            arrLinkImage = new String[formItems.size()];
            Iterator iter = formItems.iterator();

            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String extensionImage = "";
                    for(int i =fileName.length()-1;i>=0;i--){
                        if(fileName.charAt(i) == '.'){
                            break;
                            
                        }else{
                            extensionImage = fileName.charAt(i)+extensionImage;
                        }
                    }
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    String contentType = item.getContentType();
                    boolean isInMemory = item.isInMemory();
                    long sizeInBytes = item.getSize();
                    String fieldName = item.getFieldName();

                    // saves the file on disk
                    item.write(storeFile);
                    arrLinkImage[indexImage++] = item.getName();
                }
            }

        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
        }

        //   request.setAttribute("arrLinkImage", arrLinkImage);
        //  RequestDispatcher rd = request.getRequestDispatcher("/groupEmotion/emotion/add");
        //   rd.forward(request, response);
//        String sessionId = request.getAttribute("sessionIdAdmin").toString();
        String sessionId = request.getSession().toString();
        String groupId = request.getParameter("groupId");
        Memcached.set("arrLinkImage-" + sessionId, 3600, arrLinkImage);

        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", Registry.get("Host") + "/groupEmotion/emotion/add?groupId=" + groupId);
        response.setContentType("text/html");

    }
}
