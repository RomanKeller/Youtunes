package com.java.servlets;
import java.io.IOException;



import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gdata.util.AuthenticationException;


import com.google.gdata.util.ServiceException;




//import com.google.gdata.util.AuthenticationException;
import Applic.Youtube;

public class Connexion  extends HttpServlet{
	public ArrayList<String> playlists = new ArrayList<String>();
	public String login;
	public String password; 

	public void doGet( HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException
			{	

		String actionPath = request.getServletPath();  
		
		String message = "";
		System.out.println(actionPath);
		if(actionPath.equals("/Connexion")){  
			login = request.getParameter( "loginClient" );
			password = request.getParameter( "passClient" );
			//code to process this url  
			try {
				Youtube youtube = new Youtube(login,password);
				 HttpSession session=request.getSession();  
			     session.setAttribute("login",login);  
			     session.setAttribute("password",password);  
				message = "Choisir la playlist que vous voulez importer";
				request.setAttribute( "message", message );
				this.getServletContext().getRequestDispatcher("/WEB-INF/Connexion.jsp" ).forward( request, response );
				request.setAttribute("client",login);
				//this.getServletContext().getRequestDispatcher("/result.jsp" ).forward( request, response );
			} catch (AuthenticationException e) {
				message = "Erreur d'authentification!";
				request.setAttribute( "message", message );
				this.getServletContext().getRequestDispatcher("/WEB-INF/Test.jsp" ).forward( request, response );
				e.printStackTrace();
			}
		}
		else if(actionPath.equals("/Compute")){  
			 HttpSession session=request.getSession(false);  
		     if(session!=null){  
		     String login=(String)session.getAttribute("login");  
		     String password = (String) session.getAttribute("password");
			String name = request.getParameter( "name" );
			String path =  "D:/ProjetUploads/"+name;
			String playlistName = name.substring(0, name.length()-4);
			System.out.println(login+"  "+password);
			System.out.println(playlistName+ "    :   "+path);
			try {
				Youtube youtube = new Youtube(login,password);
				youtube.doPlaylist(path, playlistName);
				request.setAttribute( "okCompute", true );
				request.setAttribute( "compute", "Playlist added on Youtube" );
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.getServletContext().getRequestDispatcher("/compute.jsp" ).forward( request, response );
		}  
			}}
}

/*<iframe id="ytplayer" type="text/html" width="640" height="390"
  src="http://www.youtube.com/embed?listType=playlist&index=1&list=PLFvcoOkuf1jeHXYZorZA0d4qSY3Hj3Zig;autoplay=1"
  frameborder="0"/>*/

