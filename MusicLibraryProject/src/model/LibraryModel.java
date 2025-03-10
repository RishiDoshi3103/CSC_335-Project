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
	 * It uses the checkForSongPresence() helper function to check if the 
	 * song already exists in the library. If not found, it adds a copy
	 * and returns true. If it already exists, returns false.
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
	
	/**
	 * This function adds a deep copy of an album to the library, and adds
	 * each associated song to its' list as a string representation of the 
	 * song's title.
	 * 
	 * It utilizes the checkForAlbumPresence() helper function to check
	 * if the album already exists in the library. If not found, it adds 
	 * a copy of the album and returns true. It it already exists, returns
	 * false.
	 * 
	 * @param   album  Album class to be added
	 * @return  true   Returns true if album successfully added 
	 * 			false  Returns false if song not added
	 */
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
	
	/**
	 * Returns a deep copy list of all songs, adds extra levels of encapsulation
	 * as the Song class was was made immutable during development.
	 * 
	 * @return result  An ArrayList of all songs present in the library
	 */
	public ArrayList<Song> getAllSongs() {
		ArrayList<Song> result = new ArrayList<Song>();
		for (Song target : this.library) {
			Song addition = new Song(target.getTitle(), target.getAlbum(), target.getArtist());
			result.add(addition);
		}
		return result;
	}
	
	/**
	 * Returns a deep copy list of songs with titles that match, or contain,
	 * the input String parameter. Not case sensitive.
	 * 
	 * @param  title	 String representation of a title
	 * @return result    ArrayList of Songs with titles matched to title input
	 */
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
	
	/**
	 * Returns a deep copy list of songs with artists that match, or contain,
	 * the input String parameter. Not case sensitive.
	 * 
	 * @param  artist	String representation of artist
	 * @return result   ArrayList of Songs with artists matched to artist input
	 */
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
	
	/**
	 * This function checks if the target Song has already been rated, and
	 * if not adds the Song-Integer rating pair. If it has already been
	 * rated, it updates the rating to the input num.
	 * 
	 * @param song	Song to be rated
	 * @param num   Rating to be applied
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
	
	
	/**
	 * Returns an ArrayList of Strings that represent all the artists
	 * currently in the library.
	 * 
	 * @return list ArrayList of Strings representing artists
	 */
	public ArrayList<String> getArtists() {
		ArrayList<String> list = new ArrayList<String>();
		for (Song song : this.library) {
			if (!list.contains(song.getArtist())) {
				list.add(song.getArtist());
			}
		}
		return list;
	}
	
	
	/**
	 * Returns a deep copy list of all albums currently in library.
	 * 
	 * @return list ArrayList of Albums present in library
	 */
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
	
	/**
	 * Returns a deep copy list of albums with titles matching 
	 * or containing the input title string.
	 * 
	 * @param  title String representation of a title
	 * @return list  ArrayList of Albums with matching titles
	 */
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
	
	/**
	 * Returns a deep copy list of albums with artists matching
	 * or containing the input artist string.
	 * 
	 * @param  artist String representation of artist
	 * @return list   ArrayList of Albums matching artist
	 */
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
	
	/**
	 * This helper function checks if an album is already present in the current user
	 * library. It returns true if a copy is present, false if not.
	 * 
	 * @param  target Album class object to search for in library
	 * @return true   Returns true if album with same data is in library
	 *         false  Returns false if not found
	 */
	private boolean checkForAlbumPresence(Album target) {
		for (Album album : this.albums) {
			if (album.getTitle().toLowerCase().equals(target.getTitle().toLowerCase()) 
					&& album.getArtist().toLowerCase().equals(target.getArtist().toLowerCase())) {
				return true;
			}
		}
		return false; 
	}
	
	/**
	 * This function creates a playlist named by the input string
	 * 
	 * @param   name  String of the desired playlist name
	 * @return  true  Returns true if a playlist is successfully created
	 *          false Returns false if a playlist with that name already
	 *                exists
	 */
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
	
	/**
	 * This function removes a playlist with a name matching the 
	 * input string 
	 * @param   name  String of the desired playlist to delete
	 * @return  true  Returns true if playlist is successfully deleted
	 *          false Returns false if playlist fails to delete / is 
	 *                not found
	 */
	public boolean removePlaylist(String name) {
		for (PlayList p : playlists) {
			if(p.getName().equalsIgnoreCase(name)) {
				playlists.remove(p);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function adds a song to a playlist with a name matching
	 * the input string.
	 * 
	 * @param  playlistName Name of the desired playlist
	 * @param  s            Song to be added
	 * @return true         Returns true if song added successfully
	 *         false        Returns false if song already in playlist,
	 *                      or failed to add (playlist doesnt exist)
	 */
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
	
	/**
	 * This function removes a song from playlist, determined by playlist name
	 * and song title.
	 * 
	 * @param  playlistName  Playlist to remove song from
	 * @param  songTitle     Song to be removed
	 * @return true          Returns true if song successfully deleted
	 *                       Returns false if song unsuccessfully deleted or
	 *                       playlist with name not found.
	 */
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
	
	/**
	 * Returns a playlist with a matching name to the input string
	 * 
	 * @param  name  Desired playlist name
	 * @return p     Returns the found playlist, or null if not found
	 */
	public PlayList searchPlaylistByName(String name) {
	    for (PlayList p : playlists) {
	        if (p.getName().equalsIgnoreCase(name)) {
	            return p;
	        }
	    }
	    return null; // Not found
	}

	/**
	 * Returns an ArrayList of strings representing current playlists
	 * in library.
	 * 
	 * @return playlistNames  ArrayList of strings representing current
	 *                        playlists in library 
	 */
	public ArrayList<String> getPlaylists() {
		ArrayList<String> playlistNames = new ArrayList<>();
		for(PlayList p : playlists) {
				playlistNames.add(p.getName());
		}
		return playlistNames;
	}
	
	/**
	 * Returns a list of songs that have a rating of 5 (max).
	 * Relies on the immutability of the song class to maintain
	 * encapsulation. Avoids the antipattern of temporary fields
	 * by adding the immutable songs to list, if they're paired
	 * with a 5 rating.
	 * 
	 * @return faves  ArrayList of songs with a 5 rating
	 */
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
