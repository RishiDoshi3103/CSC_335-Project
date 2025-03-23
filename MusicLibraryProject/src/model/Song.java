/*
 *  File: Song.java
 *  Authors: Kyle Becker / Rishi Doshi
 *  
 *  Purpose: This program is intended to represent individual songs 
 *  present in the database. Each song contains a title, album, and 
 *  artist associated with it.
 */

package model;

import java.util.Objects;
import java.io.Serializable;

public class Song implements Serializable {
	
	private static final long serialVersionUID = 1L;
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
	
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (this.getClass() != object.getClass()) {
			return false;
		}
		Song song = (Song) object;
		return this.title.equals(song.title) 
				&& this.artist.equals(song.artist)
				&& this.album.equals(song.album);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(title, album, artist);
	}
}
