package com.java.utils;

import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class ItunesParser {
	private static ArrayList<String> listSong = new ArrayList<String>();
	static Document document;
	static Element racine;
	
	public void parse(String file)
	{
		SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         document = sxb.build(file);
	      }
	      catch(Exception e){}

	      racine = document.getRootElement();
	      
	      getAll();
	   }

	static void getAll()
	{
		listSong.clear();
	   List listDict = racine.getChildren("dict");
	   List dico =  ((Element) listDict.get(0)).getChild("dict").getChildren();
	   for(int i=1;i<dico.size();i=i+2)
	   {
		   Element current = (Element) dico.get(i);
		   String name = current.getChildren().get(3).getText();
		   String artist = current.getChildren().get(5).getText();
		   String searchKey =  (name+" "+artist);
		   searchKey = searchKey.replaceAll("[^\\p{ASCII}]", "");
		   listSong.add(searchKey);
	   }
	}
	
	public ArrayList<String> getItunesSongs()
	{
		return listSong;
	}
	
}
