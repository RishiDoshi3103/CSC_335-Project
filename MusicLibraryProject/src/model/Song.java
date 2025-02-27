/*
 *  File: Song.java
 *  Authors: Kyle Becker / Rishi Doshi
 *  
 *  Purpose: This program is intended to represent individual songs 
 *  present in the database. Each song contains a title, album, and 
 *  artist associated with it.
 */

package model;

public class Song {
	private final String title;
	private final String album;
	private final String artist;
	
	public Song(String title, String album, String artist) {
		this.title = title;
		this.album = album;
		this.artist = artist;
	}
	
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public String getArtist() {
		return this.artist;
	}
  	
	@Override
	public String toString() {
		return title + " by " + artist + " | Album: " + album;

	}
}
