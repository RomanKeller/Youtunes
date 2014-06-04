package Applic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Utils.Distance;
import Utils.ItunesParser;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.youtube.PlaylistEntry;
import com.google.gdata.data.youtube.PlaylistFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YtOccupation;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Youtube {
	private YouTubeService service;
	private Distance d;
	private ArrayList<String> YoutubeSongs = new ArrayList<>();

	public Youtube() throws AuthenticationException{
		 service = new YouTubeService("179467727333.apps.googleusercontent.com", "AIzaSyDvAx-Sfz307fkgFSHoyP4f9RivYldVVRA");
		 service.setUserCredentials("keller.projet380@gmail.com", "projet380");
		 d = new Distance();
	}
	
	public Youtube(String login,String password) throws AuthenticationException
	{
		service = new YouTubeService("179467727333.apps.googleusercontent.com", "AIzaSyDvAx-Sfz307fkgFSHoyP4f9RivYldVVRA");
		service.setUserCredentials(login, password);
		d = new Distance();
	}
	
	private String getSongId(VideoEntry video)
	{
		return video.getId().substring(27, 38);
	}
	
	public String getSongName(VideoEntry video)
	{
		return video.getTitle().getPlainText();
	}
	
	private void addSongToPlaylist(String songName,String playListName) throws IOException, ServiceException
	{
		PlaylistLinkEntry play = getPlaylist(playListName);
		String playlistUrl = play.getFeedUrl();
		PlaylistEntry playlistEntry = new PlaylistEntry(searchSong(songName));
		service.insert(new URL(playlistUrl), playlistEntry);
	}
	
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
	
	private void createPlaylist(String name) throws MalformedURLException, IOException, ServiceException
	{
		String feedUrl = "http://gdata.youtube.com/feeds/api/users/default/playlists";
		PlaylistLinkEntry newEntry = new PlaylistLinkEntry();
	    newEntry.setTitle(new PlainTextConstruct(name));
	    service.insert(new URL(feedUrl), newEntry);
	}
	
	private String getPlaylistName(PlaylistLinkEntry list)
	{
		return list.getTitle().getPlainText();
	}
	
	private String getPlaylistName(PlaylistEntry list)
	{
		return list.getTitle().getPlainText();
	}
	
	
	public String createUrl(String playlist) throws MalformedURLException, IOException, ServiceException
	{
		String adress = null;
		PlaylistLinkEntry play = getPlaylist(playlist);
		URL url = new URL(play.getFeedUrl());
		adress = service.getFeed(url, PlaylistFeed.class).getEntries().get(0).getSelf().getHtmlLink().getHref().substring(0, 43);
		adress= adress+"list="+play.getPlaylistId();
		return adress;
	}
	
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
				addSongToPlaylist(videoName,playlistName);
			}
		}
		return createUrl(playlistName);
	}
	
	public ArrayList<String> test()
	{
		return YoutubeSongs;
	}
	
	public static void main(String[] args) throws IOException, ServiceException
	{
		Youtube youtube = new Youtube();
		youtube.doPlaylist("C:/Users/roman/Desktop/list.xml","salut");
		youtube.test();
	}
}