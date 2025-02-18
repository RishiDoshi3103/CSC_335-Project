package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
	private String title;
	private String artist;
	private String genre;
	private int year;
	private List<String> songs;
	
	public Album(String title, String artist, String genre, int year) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
		this.year = year;
		
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

	public int getYear() {
		return year;
	}

	public List<String> getSongs() {
		return songs;
	}
	
	@Override
	public String toString () {
		return title + " by " + artist + " (" + year + ") " + genre + "\nSongs:" + songs;
	}
}
