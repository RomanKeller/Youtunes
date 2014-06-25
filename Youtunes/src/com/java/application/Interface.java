package com.java.application;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;




import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Position;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.java.utils.ItunesParser;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Interface {
	public static Youtube youtube;
	public static ArrayList<String> listSong;
	public static ItunesParser it;
	public static JButton compute;
	public static JFrame cadre;
	public static JFrame frame ;
	public static String playlistName;
	public static String path = null;
	public static String htmlLink;
	private JButton buttonOK = new JButton("OK");

	public static void main(String[] arg) throws IOException, ServiceException {	
		Interface i = new Interface();
		i.login();;

	}
	/**
	 * Creating the frame which contains the itunes and youtube songs
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public JFrame showSong() throws MalformedURLException, IOException, ServiceException
	{
		cadre = new JFrame("Main Menu");
		it = new ItunesParser();
		while(path == null)
		{
			path = fileChooser();
		}
		System.out.println("Computing the playlist...");
		htmlLink = youtube.doPlaylist(path,playlistName);
		listSong = youtube.getSongs();
		JPanel panel = new JPanel();
		panel.add(ItunesText(),BorderLayout.WEST);
		panel.add(YoutubeText(),BorderLayout.EAST);
		panel.add(computeButton(),BorderLayout.SOUTH);
		cadre.setPreferredSize(new Dimension(650,450));
		cadre.getContentPane().add(panel);
		cadre.pack();
		cadre.setVisible(true);
		cadre.setLocationRelativeTo(null);
		cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return cadre;
	}

	/**
	 * Creating the FileChooser to get the playlist
	 * @return the playlist's path
	 */
	public String fileChooser()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.showOpenDialog(null);
		File selectedPfile = chooser.getSelectedFile();
		path = selectedPfile.getAbsolutePath();
		String fullName = selectedPfile.getName();
		playlistName = fullName.substring(0,fullName.length()-4);
		String extension = fullName.substring(fullName.length()-4,fullName.length());
		if(extension.equals(".xml"))
		{
			return path;
		}
		else 
		{
			JOptionPane.showMessageDialog(cadre, "Please select a .xml playlist format!");
			return null;
		}
	}

	/**
	 * Creating the scrollPane which shows the Itunes songs
	 * @return a ScrollPane
	 */
	public JScrollPane ItunesText()
	{
		JPanel itunes = new JPanel();
		JTextArea text = new JTextArea(20,20);
		text.setWrapStyleWord(true);
		ArrayList<String> ItunesSongs = it.getItunesSongs();
		for(int i=0;i<ItunesSongs.size();i++)
		{
			text.setText(text.getText()+"\n"+ItunesSongs.get(i));
		}
		text.setEditable(false);
		itunes.add(text);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.add(itunes);
		scroll.setPreferredSize(new Dimension(300,350));
		return scroll;
	}

	/**
	 * Creating the scrollPane which shows the Youtube songs
	 * @return a ScrollPane
	 */
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
		scroll.setPreferredSize(new Dimension(300,350));
		return scroll;
	}

	/**
	 * Creating a button which will allow the computation to get the QR Code
	 * @return the button
	 */
	public JButton computeButton()
	{
		compute = new JButton("Compute!");
		compute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				cadre.dispose();  
				try {
					generateQRCode(playlistName,htmlLink);
					System.out.println("QR code location :  D:/ProjetUploads/Result/"+playlistName+".png");
				} catch (WriterException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});      
		return compute;
	}

	/**
	 * Method which allow the user to his Google account 
	 */
	public void login()
	{
		final JFrame login = new JFrame("Login");
		JPanel pan = new JPanel();
		login.setPreferredSize(new Dimension(250,250));
		final JPasswordField passwordField = new JPasswordField(20);
		final JTextField fieldLogin = new JTextField(20);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.pack();
		login.setLocationRelativeTo(null);
		login.setVisible(true);
		JLabel labelLogin = new JLabel("Your Google Login : ");
		JLabel labelPassword = new JLabel("Enter password:");
		pan.add(labelLogin,BorderLayout.AFTER_LAST_LINE);
		pan.add(fieldLogin,BorderLayout.AFTER_LAST_LINE);
		pan.add(labelPassword,BorderLayout.AFTER_LAST_LINE);
		pan.add(passwordField,BorderLayout.AFTER_LAST_LINE);
		pan.add(buttonOK,BorderLayout.AFTER_LAST_LINE);
		login.getContentPane().add(pan);
		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String user = fieldLogin.getText();
				String pass = passwordField.getText();
				youtube = new Youtube(user,pass);
				if(youtube.isLoginValid())
				{
					login.dispose();
					try {
						showSong();
					} catch (IOException
							| ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(login, "Error during the authentification");
				}
			}
		});
	}

	/**
	 * Method which creates the Qr Code given the name of the playlist and its URL 
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
		final ByteMatrix matrix = QRcode.generateMatrix(link, level);
		QRcode.writeImage(outputFileName, imageFormat, matrix, size,name);
	}
}