package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The PlayList class represents a user-defined playlist.
 * It stores a name, an ordered list of Song objects, and a rating.
 * If the rating is set to FIVE, the playlist is automatically marked as favorite.
 */
public class PlayList {
	private String name;
	private List<Song> songs;
	private Rating rating;
	private boolean favorite;
	
	/**
     * Constructs a new PlayList with the specified name.
     * The initial rating is set to NOT_RATED, and the playlist is not marked as favorite.
     *
     * @param name the name of the playlist.
     */
	
	public PlayList(String name) {
		this.name = name;
		this.songs = new ArrayList<>();
		this.rating = Rating.NOT_RATED;
		this.favorite = false;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Song> getSongs(){
		return songs;
	}
	
	 /**
     * Adds a song to the playlist.
     *
     * @param song the Song to add.
     */
	
	public void addSong(Song song) {
	    if (song != null) {
	        songs.add(song);
	    }
	}

	/**
     * Removes a song from the playlist.
     *
     * @param song the Song to remove.
     * @return true if the song was removed successfully; false otherwise.
     */
	
	public boolean removeSong(Song song) {
		return songs.remove(song);
	}
	
	public Rating getRating() {
		return rating;
	}
	
	/**
     * Sets the playlist's rating.
     * If the rating is set to FIVE, the playlist is automatically marked as favorite.
     *
     * @param rating the Rating to set (must not be null).
     * @throws IllegalArgumentException if rating is null.
     */
	
	public void setRating(Rating rating) {
		if(rating == null) {
			throw new IllegalArgumentException("Rating cannot be null.");
		}
		this.rating = rating;
		this.favorite = (rating == Rating.FIVE);
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	/**
     * Returns a formatted string representing the playlist and its songs in order.
     */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Playlist: ").append(name).append("\n");
		sb.append("Rating: ").append(rating);
		if(favorite) {
			sb.append(" [Favorite] ");
		}
		sb.append("\nSongs:\n");
		if(songs.isEmpty()) {
			sb.append("No songs in this playlist.");
		}
		else {
			int count = 1;
			for(Song song : songs) {
				sb.append(count++).append(". ").append(song).append("\n");
			}
		}
		return sb.toString();
	}
}