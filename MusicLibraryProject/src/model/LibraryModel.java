/*
 * File: LibraryModel.java
 * Authors: Kyle Becker / Rishi Doshi
 * 
 * Purpose: This file is meant to replicate a user's working Music Library.
 * Songs and albums are loaded from the MusicStore, and stored in respective
 * structures. Library holds songs, albums hold albums, playlists obviously
 * hold playlists, and ratings holds pairs of songs and their respective ratings
 * (when appropriate, as songs are not required, nor inherently possess, ratings.
 * Given the assignment parameters, ranking allows for 1-5, but offers very
 * little functionality aside from those being marked 5 being included and
 * referrable as 'favorites'.
 * 
 */

package model;

import java.util.ArrayList;

import database.MusicStore;

public class LibraryModel {
	
	private ArrayList<Song> library;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playlists;
	private ArrayList<Rating> ratings;
	
	public LibraryModel() {
		this.library = new ArrayList<Song>();
		this.playlists = new ArrayList<PlayList>();
		this.albums = new ArrayList<Album>();
		this.ratings = new ArrayList<Rating>();
	}
	
	/**
	 * Adds a deep copy of a song to library, though slightly unnecessary
	 * as the song class was restructured immutable due to rating changes.
	 * 
	 * @param  song	 Song class to be added 
	 * @return true  Returns true if song successfully added
	 * 		   false Returns false if song not added
	 */
	public boolean addSong(Song song) {
		if (!checkForSongPresence(song)) {
			Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
			this.library.add(target);
			return true;
		}
		return false;
	}
	
	public boolean addAlbum(Album album) {
		if (!checkForAlbumPresence(album)) {
			Album target = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
			for (String song : album.getSongs()) {
				target.addSong(song);
			}
			this.albums.add(target);
			return true;
		}
		return false;
	}
	
	public ArrayList<Song> getAllSongs() {
		ArrayList<Song> result = new ArrayList<Song>();
		for (Song target : this.library) {
			Song addition = new Song(target.getTitle(), target.getAlbum(), target.getArtist());
			result.add(addition);
		}
		return result;
	}
	
	public ArrayList<Song> searchSongByTitle(String title) {
		ArrayList<Song> result = new ArrayList<Song>();
		for (Song target : this.library) {
			if (target.getTitle().toLowerCase().equals(title.toLowerCase()) ||
					target.getTitle().toLowerCase().contains(title.toLowerCase())) {
				Song addition = new Song(target.getTitle(), target.getAlbum(), target.getArtist());
				result.add(addition);
			}
		}
		return result;
	}
	
	public ArrayList<Song> searchSongByArtist(String artist) {
		ArrayList<Song> result = new ArrayList<Song>();
		for (Song target : this.library) {
			if (target.getArtist().equalsIgnoreCase(artist) ||
					target.getArtist().toLowerCase().contains(artist.toLowerCase())) {
				Song addition = new Song(target.getTitle(), target.getAlbum(), target.getArtist());
				result.add(addition);
			}
		}
		return result;
	}
	/*
	public boolean setRating( String title, Rating rate) {
		for (Song song : this.library) {
			if (song.getTitle().equals(title)) {
				song.setRating(rate);
				return true;
			}
		}
		return false;
	}
	*/
	
	public void setRating(Song song, int num) {
		for (Rating r : this.ratings) {
			if (r.getSong().getTitle().equalsIgnoreCase(song.getTitle())
					&& r.getSong().getAlbum().equalsIgnoreCase(song.getAlbum())) {
				r.setRating(num);
				return;
			}
		}
		Rating rating = new Rating(song, num);
		ratings.add(rating);
	}
	
	public ArrayList<String> getArtists() {
		ArrayList<String> list = new ArrayList<String>();
		for (Song song : this.library) {
			if (!list.contains(song.getArtist())) {
				list.add(song.getArtist());
			}
		}
		return list;
	}
	
	public ArrayList<Album> getAllAlbumsInLibrary() {
		ArrayList<Album> list = new ArrayList<Album>();
		for (Album album : this.albums) {
			Album addition = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
			for (String song : album.getSongs()) {
				addition.addSong(song);
			}
			list.add(addition);
		}
		return list;
	}
	
	public ArrayList<Album> searchAlbumsByTitle(String title) {
		ArrayList<Album> list = new ArrayList<Album>();
		for (Album album : this.albums) {
			if (album.getTitle().equalsIgnoreCase(title) ||
					album.getTitle().toLowerCase().contains(title.toLowerCase())) {
				Album addition = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				for (String song : album.getSongs()) {
					addition.addSong(song);
				}
				list.add(addition);
			}
		}
		return list;
	}
	
	public ArrayList<Album> searchAlbumsByArtist(String artist) {
		ArrayList<Album> list = new ArrayList<Album>();
		for (Album album : this.albums) {
			if (album.getArtist().equalsIgnoreCase(artist) ||
					album.getArtist().toLowerCase().contains(artist.toLowerCase())) {
				Album addition = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				for (String song : album.getSongs()) {
					addition.addSong(song);
				}
				list.add(addition);
			}
		}
		return list;
	}
	/**
	 * This helper function checks if a song is present in the current user library.
	 * It returns true if a copy is present, false if not.
	 * 
	 * @param target	Song class object to search for in library
	 * @return true		Returns true if song with same title, album, and artist is 
	 * 					 in library
	 * 		   false    Returns false is song is not currently in library
	 */
	private boolean checkForSongPresence(Song target) {
		for (Song song : this.library) {
			if (song.getTitle().toLowerCase().equals(target.getTitle().toLowerCase())
			  && song.getAlbum().toLowerCase().equals(target.getAlbum().toLowerCase())
			  && song.getArtist().toLowerCase().equals(target.getArtist().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkForAlbumPresence(Album target) {
		for (Album album : this.albums) {
			if (album.getTitle().toLowerCase().equals(target.getTitle().toLowerCase()) 
					&& album.getArtist().toLowerCase().equals(target.getArtist().toLowerCase())) {
				return true;
			}
		}
		return false; 
	}
	
	public boolean createPlaylist(String name) {
	    // Check if a playlist with this name already exists
	    for (PlayList p : playlists) {
	        if (p.getName().equalsIgnoreCase(name)) {
	            return false; // Already exists
	        }
	    }
	    PlayList newPlaylist = new PlayList(name);
	    playlists.add(newPlaylist);
	    return true;
	}

	public boolean removePlaylist(String name) {
		for (PlayList p : playlists) {
			if(p.getName().equalsIgnoreCase(name)) {
				playlists.remove(p);
				return true;
			}
		}
		return false;
	}

	public boolean addSongToPlaylist(String playlistName, Song s) {
		PlayList targetPlaylist = searchPlaylistByName(playlistName);
		if(targetPlaylist == null) {
			return false;
		}
		
		for(Song existing : targetPlaylist.getSongs()) {
			if(existing.getTitle().equalsIgnoreCase(s.getTitle())){
				return false; // Song is Already is in the Playlist
			}
		}
		
		//If the song is not found, then add the song
		targetPlaylist.addSong(s);
		return true;
	}
	
	
	public boolean removeSongFromPlaylist(String playlistName, String songTitle) {
	    for (PlayList p : playlists) {
	        if (p.getName().equalsIgnoreCase(playlistName)) {
	            // find the Song object in p’s list
	            for (Song track : p.getSongs()) {
	                if (track.getTitle().equalsIgnoreCase(songTitle)) {
	                    p.removeSong(track);
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
	
	public PlayList searchPlaylistByName(String name) {
	    for (PlayList p : playlists) {
	        if (p.getName().equalsIgnoreCase(name)) {
	            return p;
	        }
	    }
	    return null; // Not found
	}

	public ArrayList<String> getPlaylists() {
		ArrayList<String> playlistNames = new ArrayList<>();
		for(PlayList p : playlists) {
				playlistNames.add(p.getName());
		}
		return playlistNames;
	}
	
	public ArrayList<Song> getFavorites() {
		ArrayList<Song> faves = new ArrayList<Song>();
		for (Rating pair : this.ratings) {
			if (pair.getRating() == 5) {
				faves.add(pair.getSong());
			}
		}
		return faves;
	}
}
