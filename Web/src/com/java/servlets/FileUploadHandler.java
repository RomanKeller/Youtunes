package com.java.servlets;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 
Remarque : ce code s'inspire d'un tutoriel disponible à l'adresse suivante : http://javarevisited.blogspot.fr/2013/07/ile-upload-example-in-servlet-and-jsp-java-web-tutorial-example.html
 *
 */
public class FileUploadHandler extends HttpServlet {

	/**
	 * Servlet which allow the user to upload a file to the server
	 */
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "D:/ProjetUploads";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(ServletFileUpload.isMultipartContent(request)){
			try {
				List<FileItem> multiparts = new ServletFileUpload(
						new DiskFileItemFactory()).parseRequest(request);

				for(FileItem item : multiparts){
					if(!item.isFormField()){
						String name = new File(item.getName()).getName();
						if(name.substring(name.length()-4, name.length()).equals(".xml"))
						{
							item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
							request.setAttribute("name",name);
							request.setAttribute("upResult", true);         
						}
						else 
						{
							request.setAttribute("upResult",false);
							request.setAttribute("message", "Wrong extension");
							this.getServletContext().getRequestDispatcher("/WEB-INF/Connexion.jsp").forward(request, response);
						}
					}
				}
				request.setAttribute("upResult", true);         
				request.setAttribute("message", "File Successfully uploaded");

			} catch (Exception ex) {
				request.setAttribute("message", "Fail to upload :" + ex);
			}          

		}else{
			request.setAttribute("message",
					"Erreur");
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/result.jsp").forward(request, response);
	}


}

