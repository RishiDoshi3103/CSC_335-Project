package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
	private String title;
	private String artist;
	private String genre;
	private String year;
	private List<String> songs;
	private Rating rating;
	private boolean favorite;
	
	public Album(String title, String artist, String genre, String year) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		this.rating = Rating.NOT_RATED;
		this.favorite = false;
		
		// Order for the Songs and the Artist are maintain hear.
		this.songs = new ArrayList<>();
	}
	
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
	
	public Rating getRating() {
		return rating;
	}
	
	public void setRating(Rating rating) {
		if(rating == null) {
			throw new IllegalArgumentException("Rating Cannot Be Null.");
		}
		this.rating = rating;
	}
	
	public boolean isFavortite() {
		return favorite;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	@Override
	public String toString () {
		return title + " by " + artist + " (" + year + ") - " + genre + "\nRating: "+ rating + (favorite ? "[Favorite]" : "") +"\nSongs:" + songs;
	}
}
