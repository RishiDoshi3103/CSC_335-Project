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
	
	 /**
     * Constructs a new Song with the given title, album, and artist.
     * Default rating is NOT_RATED and it is not marked as favorite.
     *
     * @param title  the song title.
     * @param album  the album the song belongs to.
     * @param artist the artist of the song.
     */
	
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
	
	/**
     * Sets the rating for the song.
     * If the rating is set to FIVE, it automatically marks the song as favorite.
     *
     * @param rate the new rating (must not be null).
     * @throws IllegalArgumentException if the rating is null.
     */
	
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
	
	/**
     * Returns the current rating of the song.
     */
	
	public Rating getRating() {
		return this.rating;
	}
	
	/**
     * Returns whether the song is marked as favorite.
     */
	
  public boolean isFavorite() {
		return favorite;
  	}
  
  /**
   * Returns a formatted string representing the song.
   */
  
	@Override
	public String toString() {
		return title + " by " + artist + " | Album: " + album;

	}
}
