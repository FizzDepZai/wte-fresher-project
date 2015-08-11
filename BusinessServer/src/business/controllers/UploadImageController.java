/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.controllers;

import business.library.core.Registry;
import business.middleware.BusinessChatHandler;
import business.utility.ManagerImageId;
import business.utility.UploadConstant;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.json.simple.JSONValue;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tript
 */
public class UploadImageController extends HttpServlet {

    Logger logger = Logger.getLogger(UploadImageController.class);

    ManagerImageId managerImageId = new ManagerImageId();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }
        String oauthCode = "";
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
        List formItems = new ArrayList();
        try {
            // parses the request's content to extract file data
            formItems = upload.parseRequest(request);
            Iterator iter = formItems.iterator();
            // iterates over form's fields
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                // processes only fields that are not form fields
                if (item.getFieldName().equals("oauthcode")) {
                    oauthCode = item.getString();
                }

            }

        } catch (FileUploadException ex) {
            logger.error("Write image:" + ex);
        }

        //checkLogin
        BusinessChatHandler handler = new BusinessChatHandler();
        try {
            if(handler.isLogin(oauthCode)){
                uploadImage(request, response, formItems);
            }
        } catch (TException ex) {
            java.util.logging.Logger.getLogger(UploadImageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadImage(HttpServletRequest request, HttpServletResponse response, List formItems) throws IOException {
        BusinessChatHandler handler = new BusinessChatHandler();
        Long imageId;
        try {
            imageId = (Long) handler.getImageId();
        } catch (TException ex) {
            imageId = 0L;
            logger.error("get image id:" + ex);
        }
        String filePath = "";
        String uploadPath = "../resources/" + UploadConstant.UPLOAD_DIRECTORY;

        // parses the request's content to extract file data
        Iterator iter = formItems.iterator();
        String userId = "";
        String fileName = "";
        File storeFile = null;
        // iterates over form's fields
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // processes only fields that are not form fields
            if (!item.isFormField()) {
                try {
                    fileName = new File(item.getName()).getName();
                    String extensionImage = "";
                    for (int i = fileName.length() - 1; i >= 0; i--) {
                        if (fileName.charAt(i) == '.') {
                            break;

                        } else {
                            extensionImage = fileName.charAt(i) + extensionImage;
                        }
                    }
                    //tao file name moi
//                    fileName = imageId + "." + extensionImage;
//                    fileName = imageId + "_" + fileName;
                    filePath = uploadPath + File.separator + fileName;
                    storeFile = new File(filePath);
                    // saves the file on disk
                    item.write(storeFile);
                } catch (Exception ex) {
                    logger.error("error when write image");
                }
            } else if (item.getFieldName().equals("userId")) {
                userId = item.getString();
            }
        }
        if (storeFile != null) {
            //userID_imageGen_fileNameofUser.jpg
            fileName = userId + "_" + imageId + "_" + fileName;
            filePath = uploadPath + File.separator + fileName;
            File newNameImage = new File(filePath);
            storeFile.renameTo(newNameImage);
        }

        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setContentType("text/html");
        response.getWriter().println(filePath);

    }
}