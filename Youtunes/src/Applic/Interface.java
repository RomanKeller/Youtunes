package Applic;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;




import javax.swing.ScrollPaneConstants;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import Utils.ItunesParser;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;

public class Interface {
	public static Appli ap;
	public static ArrayList<String> listSong;
    public static void main(String[] arg) throws IOException, ServiceException {	
    Interface i=new Interface();
	JFrame cadre = new javax.swing.JFrame("Main Menu");
	JScrollPane panneau = i.ItunesText();
	ap = new Appli();
	listSong = ap.getSongs();
	ap.doPlaylist("salut2");
	JScrollPane panneau2 = i.YoutubeText();
	GridLayout grid = new GridLayout(2,0);
	cadre.setLayout(grid);
	cadre.setPreferredSize(new Dimension(800,800));
	cadre.add(panneau);
	cadre.add(panneau2);
	cadre.pack();
	cadre.setVisible(true);
	cadre.setLocationRelativeTo(null);
	cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public JScrollPane ItunesText()
    {
    	JPanel itunes = new JPanel();
    	JTextArea text = new JTextArea(20,20);
    	text.setWrapStyleWord(true);
    	ItunesParser it = new ItunesParser();
		it.parsePlay("C:/Users/roman/Desktop/list.xml");
		ArrayList<String> listSong = it.getItunesSong();
		for(int i=0;i<listSong.size();i++)
		{
			text.setText(text.getText()+"\n"+listSong.get(i));
		}
		text.setEditable(false);
		itunes.add(text);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.add(itunes);
		return scroll;
    }
    public JScrollPane YoutubeText() throws IOException, ServiceException
    {
    	
    	JPanel youtube = new JPanel();
    	JTextArea text = new JTextArea(20,20);
    	text.setWrapStyleWord(true);
		for(int i=0;i<listSong.size();i++)
		{
			text.setText(text.getText()+"\n"+listSong.get(i));
		}
		text.setEditable(false);
		youtube.add(text);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.add(youtube);
		return scroll;
    }
}