package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Album class represents a music album.
 * It stores album metadata (title, artist, genre, year), an ordered list of song titles,
 * an overall album rating, and a favorite flag.
 */

public class Album {
	private String title;
	private String artist;
	private String genre;
	private String year;
	private List<String> songs;		// List of song titles
	
	
  /**
   * Constructs a new Album with the given details.
   * The album's initial rating is set to NOT_RATED and it is not marked as favorite.
   *
   * @param title  the album title
   * @param artist the album artist
   * @param genre  the genre of the album
   * @param year   the release year
   */
	public Album(String title, String artist, String genre, String year) {

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
		return songs;
	}
	
	
	@Override
	public String toString () {
		return title + " by " + artist + " (" + year + ") - " + genre + "\nSongs:" + songs;
	}
}
