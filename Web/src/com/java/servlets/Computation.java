package com.java.servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gdata.util.ServiceException;

public class Computation extends Connexion{
	public void doGet( HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException
			{
	/*	String name = "list.xml";//request.getParameter("playlist");
		HttpSession session = request.getSession(true);
		Enumeration<String> l = session.getAttributeNames();
		System.out.println(l.hasMoreElements());
		//String client = request.getParameter("loginClient");
		//System.out.println(client);
		//System.out.println(name);
		String path =  "D:/ProjetUploads/"+name;
		String playlistName = name.substring(0, name.length()-4);
		try {
		//	System.out.println(path);
		//	System.out.println(playlistName);
			youtube.doPlaylist(path, playlistName);
			request.setAttribute( "okCompute", true );
			request.setAttribute( "compute", "Playlist added on Youtube" );
			this.getServletContext().getRequestDispatcher("/compute.jsp" ).forward( request, response );
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
}
}

