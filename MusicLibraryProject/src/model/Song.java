/*
 *  File: Song.java
 *  Authors: Kyle Becker / Rishi Doshi
 *  
 *  Purpose: This program is intended to represent individual songs 
 *  present in the database. Each song contains a title, album, and 
 *  artist associated with it.
 */

package model;

import java.io.Serializable;
import java.util.Objects;

public class Song implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String title;
	private final String album;
	private final String artist;
	//private final String genre;
	
	public Song(String title, String album, String artist) {
		
		if(title == null || album == null || artist == null) {
            throw new IllegalArgumentException("Song fields must not be null.");
        }
		
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
	
	/**
	 * public String getGenre() {
	 * 		return this.genre;
	 * }
	 */
  	
	@Override
	public String toString() {
		return title + " by " + artist + " | Album: " + album;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return title.equals(song.title) &&
               album.equals(song.album) &&
               artist.equals(song.artist);
    }
	
	@Override
	public int hashCode() {
		return Objects.hash(title, album, artist);
	}
}
