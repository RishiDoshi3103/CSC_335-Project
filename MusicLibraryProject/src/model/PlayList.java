package model;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
	private String name;
	private List<Song> songs;
	private Rating rating;
	private boolean favorite;
	
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
	
	public void addSong(Song song) {
	    if (song != null) {
	        songs.add(song);
	    }
	}

	
	public boolean removeSong(Song song) {
		return songs.remove(song);
	}
	
	public Rating getRating() {
		return rating;
	}
	
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