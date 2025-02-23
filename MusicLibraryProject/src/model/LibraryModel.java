/*
 * This program replicates a users library, and contains the
 * following functionality:
 * - Search for a song by title √
 * - Search for a song by artist √
 * - Search for an album by title √
 * - Search for an album by artist √
 * 	- How should we handle multiple albums by same artist?
 * - Search for a playlist by name (prints songs' title/artists) √
 * - Add a song to the library (from the store) √
 * - Add a whole album to the library (from the store) √
 * - Get a list of song titles √
 * - Get a list of artists √
 * - Get a list of playlists √
 * - Get a list of favorite songs (songs with 5 rating) √
 * 
 * Please adjust functions to return whatever types/designs are
 * needed for functionality in other classes. Or let me know! :)
 * 
 */

package model;

import java.util.ArrayList;

import database.MusicStore;

public class LibraryModel {
	private MusicStore store;
	private ArrayList<Song> library;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playlists;
	
	public LibraryModel(MusicStore database) {
		this.store = database;
		this.library = new ArrayList<Song>();
		this.playlists = new ArrayList<PlayList>();
		// This for the tempAddAlbum()
		// By Adding this want call the NullPointerException 
		this.albums = new ArrayList<Album>();
	}
	
	
	public Song getSongTitle(String title) {
		for (Song song : this.library) {
			if (song.getTitle().equals(title)) {
				Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				target.setRating(song.getRating());
				return target;
			}
		}
		return null;
	}
	
	public Song getSongByArtist(String artist) {
		for (Song song : this.library) {
			if (song.getArtist().equals(artist)) {
				Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				target.setRating(song.getRating());
				return target;
			}
		}
		return null;
	}
	
	
	public Album getAlbumByTitle(String title) {
		for (Album album : this.albums) {
			if (album.getTitle().equals(title)) {
				Album target = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				// Adds songs to album that are in user's song library
				for (Song song : this.library) {
					if (song.getAlbum().equals(title)) {
						target.addSong(song.getTitle());
					}
				}
				return target;
			}
		}
		return null;
	}
	
	public Album getAlbumByArtist(String artist) {
		for (Album album : this.albums) {
			if (album.getArtist().equals(artist)) {
				Album target = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				// Adds songs to album that are in user's song library
				for (Song song : this.library) {
					if (song.getAlbum().equals(album.getTitle())) {
						target.addSong(song.getTitle());
					}
				}
				return target;
			}
		}
		return null;
	}
	
	public void getPlaylist(String name) {
		// Tag to determine if playlist present, found
		int foundTag = 0;
		for (PlayList list : this.playlists) {
			if (list.getName().equals(name)) {
				foundTag = 1;
				System.out.println(list.toString());
			}
		}
		if (foundTag == 0) {
			System.out.println("Playlist not found.");
		}
	}
	
	// Temporary, for testing purposes
	public void tempAddSong(Song song) {
		this.library.add(song);
	}
	
	public void addSong(Song song) {
		// Need MusicStore song getter to add
		//this.library.add(store.getSong(song.getTitle())); // By song title?
		
	}
	
	// Temporary, for testing purposes
	public void tempAddAlbum(Album album) {
		this.albums.add(album);
	}
	
	public void addAlbum(String album) {
		// Need MusicStore album getter
		//this.albums.add(store.getAlbum(album));
		//for (Song song : store.getAlbum(album).getSongs()) {
		//	this.addSong(song);
		//} //(may need getSongs() to return copies?) 
	}
	
	public ArrayList<String> getSongTitles() {
		ArrayList<String> titles = new ArrayList<String>();
		for (Song song : this.library) {
			titles.add(song.getTitle());
		}
		return titles;
	}
	
	public ArrayList<String> getArtists() {
		ArrayList<String> artists = new ArrayList<String>();
		for (Song song : this.library) {
			if (!artists.contains(song.getArtist())) {
				artists.add(song.getArtist());
			}
		}
		return artists;
	}
	
	public ArrayList<String> getPlaylists() {
		ArrayList<String> lists = new ArrayList<String>();
		for (PlayList list : this.playlists) {
			lists.add(list.getName());
		}
		return lists;
	}
	
	public ArrayList<String> getFavorites() {
		ArrayList<String> faves = new ArrayList<String>();
		for (Song song : this.library) {
			if (song.getRating() == Rating.FIVE) {
				faves.add(song.getTitle());
			}
		}
		return faves;
	}

}
