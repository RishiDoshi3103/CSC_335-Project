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
	
	private ArrayList<Song> library;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playlists;
	
	public LibraryModel() {
		this.library = new ArrayList<Song>();
		this.playlists = new ArrayList<PlayList>();
		this.albums = new ArrayList<Album>();
	}
	
	public boolean addSong(Song song) {
		if (!checkForSongPresence(song)) {
			Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
			this.library.add(target);
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
	
	public boolean setRating( String title, Rating rate) {
		for (Song song : this.library) {
			if (song.getTitle().equals(title)) {
				song.setRating(rate);
				return true;
			}
		}
		return false;
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
		PlayList targetPlaylist = findPlayListByTitle(playlistName);
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
	
	// Helper method to find a playlist by name
	private PlayList findPlayListByTitle(String name) {
		for(PlayList p : playlists) {
			if(p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null; // Not Found
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
		for (Song song : this.library) {
			if (song.isFavorite()) {
				Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				target.setRating(song.getRating());
			}
		}
		return faves;
	}
}
