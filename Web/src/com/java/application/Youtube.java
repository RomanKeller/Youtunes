package com.java.application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.PlaylistEntry;
import com.google.gdata.data.youtube.PlaylistFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.java.utils.Distance;
import com.java.utils.ItunesParser;

public class Youtube {
	private YouTubeService service;
	private Distance d;
	private ArrayList<String> YoutubeSongs = new ArrayList<>();

	/**
	 * Authenticates the user
	 * @param login
	 * @param password
	 */
	public Youtube(String login,String password) throws AuthenticationException
	{
		service = new YouTubeService("179467727333.apps.googleusercontent.com", "AIzaSyDvAx-Sfz307fkgFSHoyP4f9RivYldVVRA");
		service.setUserCredentials(login, password);
		d = new Distance();
	}

	/**
	 * Get Song name 
	 * @param video
	 * @return The name of the Song
	 */
	public String getSongName(VideoEntry video)
	{
		return video.getTitle().getPlainText();
	}

	/**
	 * Add a specific song to a specific playlist
	 */
	private void addSongToPlaylist(String songName,String playListName) throws IOException, ServiceException
	{
		PlaylistLinkEntry play = getPlaylist(playListName);
		String playlistUrl = play.getFeedUrl();
		PlaylistEntry playlistEntry = new PlaylistEntry(searchSong(songName));
		service.insert(new URL(playlistUrl), playlistEntry);
	}

	/**
	 * Search song with a levenshtein distance 
	 * @param name
	 * @return the video 
	 * @throws IOException
	 * @throws ServiceException
	 */
	public VideoEntry searchSong(String name) throws IOException, ServiceException
	{
		int distance = 50 ; // On prend une distance de 50 pour �tre sur 
		VideoEntry video = null;
		YouTubeQuery query = new YouTubeQuery(new URL("http://gdata.youtube.com/feeds/api/videos"));
		query.setFullTextQuery(name);
		VideoFeed videoFeed = service.query(query, VideoFeed.class);
		for (int i=0;i<videoFeed.getEntries().size()/2;i++)
		{
			VideoEntry vid = videoFeed.getEntries().get(i);
			int distancetmp = d.computeLevenshteinDistance(name, getSongName(vid));
			if(distancetmp<distance)
			{
				distance = distancetmp;
				video = vid; 
			}
		}
		if(video == null)
		{
			return null;
		}
		else 
		{
			return video;
		}
	}

	/**
	 * Get the "name" playlist
	 * @param name
	 * @return the playlist
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	private PlaylistLinkEntry getPlaylist(String name) throws MalformedURLException, IOException, ServiceException
	{
		PlaylistLinkEntry playlist = null;
		String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/playlists";
		PlaylistLinkFeed feed = service.getFeed(new URL(feedUrl), PlaylistLinkFeed.class);
		int i = 0;
		PlaylistLinkEntry tmp =null;
		while(playlist==null)
		{
			if(i==feed.getEntries().size())
				break; 
			tmp = feed.getEntries().get(i);
			if(getPlaylistName(tmp).equals(name))
				playlist=tmp;
			i++;
		}
		return playlist;
	}

	/**
	 * Create a new playlist
	 * @param name
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	private void createPlaylist(String name) throws MalformedURLException, IOException, ServiceException
	{
		String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/playlists";
		PlaylistLinkEntry newEntry = new PlaylistLinkEntry();
		newEntry.setTitle(new PlainTextConstruct(name));
		service.insert(new URL(feedUrl), newEntry);
	}

	/**
	 * Get the playlist name
	 * @param list
	 * @return
	 */
	private String getPlaylistName(PlaylistLinkEntry list)
	{
		return list.getTitle().getPlainText();
	}

	/**
	 * Create the URL of the playlist
	 * @param playlist
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public String createUrl(String playlist) throws MalformedURLException, IOException, ServiceException
	{
		String adress = null;
		PlaylistLinkEntry play = getPlaylist(playlist);
		URL url = new URL(play.getFeedUrl());

		String playlistId = service.getFeed(url, PlaylistFeed.class).getEntries().get(0).getSelf().getId().split("playlist:")[1].split(":")[0];
		adress = "http://www.youtube.com/embed?listType=playlist&index=0&list="+playlistId+";autoplay=1";
		return adress;
	}

	/**
	 * Fill the playlist with the songs located in the specific file
	 * @param path
	 * @param playlistName
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public String doPlaylist(String path,String playlistName) throws MalformedURLException, IOException, ServiceException
	{
		createPlaylist(playlistName);
		ItunesParser it = new ItunesParser();
		it.parse(path);
		ArrayList<String> listSong =  it.getItunesSongs();
		for (int i = 0; i < listSong.size(); i++)
		{
			VideoEntry video = null;
			video = searchSong(listSong.get(i));
			if(video!=null)
			{
				String videoName = getSongName(video);
				YoutubeSongs.add(videoName);
				System.out.println(YoutubeSongs);
				addSongToPlaylist(videoName,playlistName);
			}
		}
		return createUrl(playlistName);
	}

	/**
	 * Return the Youtube Songs
	 * @return
	 */
	public ArrayList<String> getSongs()
	{
		return YoutubeSongs;
	}
}
