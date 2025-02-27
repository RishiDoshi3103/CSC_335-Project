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

public class LibraryModel {
	
	private ArrayList<Song> library;
	private ArrayList<Album> albums;
	private ArrayList<PlayList> playlists;
	
	/**
     * Constructor: initializes empty lists for songs, albums, and playlists.
     */
	
	public LibraryModel() {
		this.library = new ArrayList<Song>();
		this.playlists = new ArrayList<PlayList>();
		this.albums = new ArrayList<Album>();
	}
	
	// ---------------------------
    // SONG-RELATED METHODS
    // ---------------------------
    
    /**
     * Attempts to add a Song to the library.
     * It creates a copy of the song if not already present.
     *
     * @param song the Song object to add
     * @return true if the song was added; false if it already exists
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
     * Returns a defensive copy of all songs in the library.
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
	*/
	
	/**
     * Searches for songs by title (case-insensitive).
     * Returns the actual Song objects from the library.
     */
	
	public ArrayList<Song> searchSongByTitle(String title) {
	    ArrayList<Song> result = new ArrayList<Song>();
	    for (Song target : this.library) {
	        if (target.getTitle().toLowerCase().equals(title.toLowerCase()) ||
	                target.getTitle().toLowerCase().contains(title.toLowerCase())) {
	            result.add(target);  // Use the existing Song object instead of creating a new one.
	        }
	    }
	    return result;
	}

	
//	public boolean setRating(String title, Rating rate) {
//	    for (Song song : this.library) {
//	        if (song.getTitle().equals(title)) {
//	            song.setRating(rate);
//	            return true;
//	        }
//	    }
//	    return false;
//	}
	
	/**
     * Sets the rating of all songs matching the given title.
     * Returns true if at least one song was updated.
     */
	
	public boolean setRating(String title, Rating rate) {
	    boolean found = false;
	    for (Song song : this.library) {
	        if (song.getTitle().equalsIgnoreCase(title)) {
	            song.setRating(rate);
	            found = true;
	        }
	    }
	    return found;
	}

	/**
     * Returns a list of distinct artist names from the songs in the library.
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
	
	// ---------------------------
    // ALBUM-RELATED METHODS
    // ---------------------------
    
    /**
     * Returns a defensive copy of all albums in the library.
     */
	
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
     * Adds an album from the store to the library.
     * Also adds each song from the album to the song library.
     *
     * @param album the Album to add
     */
	
	public void addAlbumFromStore(Album album) {
	    if (album != null) {
	        // Add the album to the album list
	        this.albumsInLibrary.add(album);
	        // And add each song from the album to the song list (if needed)
	        for (String track : album.getSongs()) {
	            Song newSong = new Song(track, album.getTitle(), album.getArtist());
	            this.addSong(newSong);
	        }
	    }
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
	
	// ---------------------------
    // PLAYLIST-RELATED METHODS
    // ---------------------------
    
	/**
     * Creates a new playlist with the given name if it doesn't already exist.
     * Returns true if created, false otherwise.
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
     * Removes a playlist by name.
     * Returns true if the playlist was removed, false if not found.
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
     * Adds a Song to an existing playlist if the song is not already present.
     * Returns true if the song is added, false if the playlist doesn't exist or song already exists.
     */

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
	
	/**
     * Private helper method to find a playlist by name.
     */
	
	// Helper method to find a playlist by name
	private PlayList findPlayListByTitle(String name) {
		for(PlayList p : playlists) {
			if(p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null; // Not Found
	}
	
	 /**
     * Removes a song (by title) from a specified playlist.
     * Returns true if the song is removed, false otherwise.
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
     * Searches for a playlist by name.
     * Returns the PlayList object if found; null otherwise.
     */
	
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
	
	// ---------------------------
    // FAVORITES
    // ---------------------------
    
    /**
     * Returns a list of songs from the library that are marked as favorite (rating == FIVE).
     * Returns copies of the Song objects to maintain encapsulation.
     */
	
	public ArrayList<Song> getFavorites() {
	    ArrayList<Song> faves = new ArrayList<Song>();
	    for (Song song : this.library) {
	        if (song.isFavorite()) {
	            Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
	            target.setRating(song.getRating());
	            faves.add(target);  // Add the copy to the list.
	        }
	    }
	    return faves;
	}

}
