/*
 *  This program is intended to represent individual songs present
 *  in the database. Each song contains a title, album, and artist
 *  associated with it.
 *  
 *  Per LA1 specs:
 *  - the ratings are 1 to 5
 *	- songs do not have to be rated so there is no default rating
 *	- songs that are rated as 5 should automatically be set to “favorite”
 * 
 */

package model;

public class Song {
	private String title;
	private String album;
	private String artist;
	private Rating rating;
	
	public Song(String title, String album, String artist) {
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.rating = Rating.NOT_RATED;
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
	
	public void setRating(Rating rate) {
		this.rating = rate;
	}
	
	public Rating getRating() {
		return this.rating;
	}
	
	@Override
	public String toString() {
		return title + " by " + artist + " | Album: " + album;
	}
}
