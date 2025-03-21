/*
 * File: Album.java
 * Authors: Rishi Doshi / Kyle Becker
 * 
 * Purpose: The purpose of this file is to represent an album that 
 * contains data for the album name, the artist, genre, and year.
 * It also holds a string list representation of the songs that
 * are associated with that album, originally loaded in the MusicStore.
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Album implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String artist;
	private String genre;
	private String year;
	private List<String> songs;	// List of song titles
	
	
  /**
   * Constructs a new Album with the given details.
   *
   * @param title  the album title
   * @param artist the album artist
   * @param genre  the genre of the album
   * @param year   the release year
   */
	public Album(String title, String artist, String genre, String year) {

		if(title == null || artist == null || genre == null || year == null) {
            throw new IllegalArgumentException("Album fields must not be null.");
        }
		
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		
		// Order for the Songs and the Artist are maintain hear.
		this.songs = new ArrayList<>();
	}
	
   /**
    * Adds a song to the album while preserving the order.
    *
    * @param song the song title to add.
    */
	public void addSong(String song) {
		songs.add(song);
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artist;
	}

	public String getGenre() {
		return genre;
	}

	public String getYear() {
		return year;
	}

	public List<String> getSongs() {
		List<String> list = new ArrayList<String>();
		for (String song : songs) {
			list.add(song);
		}
		return list;
	}
	
	
	@Override
	public String toString () {
		return title + " by " + artist + " (" + year + ") - " + genre + "\nSongs:" + songs;
	}
}
