package com.bytemeagain.picasa;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


public class PicasaAPI {

	public static void main(String[] args)  {
		//This code is to test the Picasa Web Service API! It works :)
		
		PicasawebService myService = new PicasawebService("kailarhomes");
		try {
			myService.setUserCredentials("hb.shashidhar@gmail.com", "*******");
			URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/hb.shashidhar?kind=album");

			UserFeed myUserFeed = myService.getFeed(feedUrl, UserFeed.class);

			int count=0;
			for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
				System.out.println(myAlbum.getGphotoId());
			    System.out.println(myAlbum.getTitle().getPlainText());
			    System.out.println("Links size: "+myAlbum.getLinks().size());
			    count++;
			}
			
			System.out.println("Total: "+ count);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
