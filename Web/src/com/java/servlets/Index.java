package com.java.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {
	public void doGet( HttpServletRequest request, HttpServletResponse
			response ) throws ServletException, IOException{
		this.getServletContext().getRequestDispatcher("/WEB-INF/Index.jsp").forward( request, response );
	}
}
