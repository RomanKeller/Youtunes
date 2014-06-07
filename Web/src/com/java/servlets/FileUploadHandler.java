package com.java.servlets;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gdata.util.ServiceException;


public class FileUploadHandler extends HttpServlet {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "D:/ProjetUploads";
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        //process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                                         new DiskFileItemFactory()).parseRequest(request);
              
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                        request.setAttribute("name",name);
                    }
                }
           
               //File uploaded successfully
              request.setAttribute("upResult", true);         
              request.setAttribute("message", "File Uploaded Successfully");

            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
               //request.setAttribute("upResult", false);
            }          
         
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
           // request.setAttribute("upResult", false);
        }
        	this.getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
    }
    
   
    }
  
