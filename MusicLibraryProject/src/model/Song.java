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
	private boolean favorite;
	
	public Song(String title, String album, String artist) {
		this.title = title;
		this.album = album;
		this.artist = artist;
		this.rating = Rating.NOT_RATED;
		this.favorite = false;
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
		if(rate == null)
			throw new IllegalArgumentException("Rating cannot be null.");
		this.rating = rate;
		/**
		 * This way, if someone calls song.setRating(Rating.FIVE), 
		 * it will automatically sets favorite = true.
		 */
		this.favorite = (rate == Rating.FIVE);
	}
	
	public Rating getRating() {
		return this.rating;
	}
	
  public boolean isFavorite() {
		return favorite;
  
	@Override
	public String toString() {
		return title + " by " + artist + " | Album: " + album;

	}
}
