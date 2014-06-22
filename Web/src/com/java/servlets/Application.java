package com.java.servlets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.java.application.QRcode;
import com.java.application.Youtube;

//import com.google.gdata.util.AuthenticationException;

public class Application  extends HttpServlet{
	public ArrayList<String> playlists = new ArrayList<String>();
	public String login;
	public String password; 
	public String htmlLink;


	/**
	 * Depending on the active servlet, the program will compute the request of the users
	 */
	public void doGet( HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException
			{	

		String actionPath = request.getServletPath();  
		String message = "";
		System.out.println(actionPath);
		if(actionPath.equals("/Connexion")){  
			login = request.getParameter( "loginClient" );
			password = request.getParameter( "passClient" );
			try {
				Youtube youtube = new Youtube(login,password);
				HttpSession session=request.getSession();  
				session.setAttribute("login",login);  
				session.setAttribute("password",password);  
				message = "Choisir la playlist que vous voulez importer";
				request.setAttribute( "message", message );
				this.getServletContext().getRequestDispatcher("/WEB-INF/Connexion.jsp" ).forward( request, response );
				request.setAttribute("client",login);
			} catch (AuthenticationException e) {
				message = "Erreur d'authentification!";
				request.setAttribute( "message", message );
				this.getServletContext().getRequestDispatcher("/WEB-INF/Index.jsp" ).forward( request, response );
				e.printStackTrace();
			}
		}
		else if(actionPath.equals("/Compute")){  
			HttpSession session=request.getSession(false);  
			if(session!=null){  
				String login=(String)session.getAttribute("login");  
				String password = (String) session.getAttribute("password");
				String name = request.getParameter("name");
				String path =  "D:/ProjetUploads/"+name;
				String playlistName = name.substring(0, name.length()-4);
				try {
					Youtube youtube = new Youtube(login,password);
					htmlLink = youtube.doPlaylist(path, playlistName);
					request.setAttribute( "okCompute", true );
					request.setAttribute( "compute", "Playlist added on Youtube" );
					request.setAttribute( "hmtlLink", htmlLink );
					generateQRCode(playlistName, htmlLink);
					request.setAttribute("path",path.substring(0,path.length()-4)+".png");

				} catch (ServiceException | WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				this.getServletContext().getRequestDispatcher("/WEB-INF/compute.jsp" ).forward( request, response );
			}  
		}
			}

	/**
	 * Method which creates the QR Code given the name of the playlist and its URL
	 * @param name
	 * @param link
	 * @throws WriterException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void generateQRCode(String name,String link) throws WriterException, FileNotFoundException, IOException
	{
		final String imageFormat = "png";
		final String outputFileName = "D:/ProjetUploads/Result/"+ name +'.'+ imageFormat;
		final int size = 400;
		final ErrorCorrectionLevel level = ErrorCorrectionLevel.Q;
		// encode
		final ByteMatrix matrix = QRcode.generateMatrix(link, level);
		// write in a file
		QRcode.writeImage(outputFileName, imageFormat, matrix, size,name);

	}

}
