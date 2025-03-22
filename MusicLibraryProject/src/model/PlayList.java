
/**
 * File: PlayList.java
 * Authors: Rishi Doshi / Kyle Becker
 * 
 * Purpose: The PlayList class represents a user-defined playlist.
 * It stores a name, an ordered list of Song objects, an overall 
 * playlist rating, and a favorite flag.
 */


package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class PlayList implements Iterable<Song>, Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private ArrayList<Song> songs;
	
	
	/**
     * Constructs a new PlayList with the specified name.
     * The initial rating is set to NOT_RATED, and the playlist is not marked as favorite.
     *
     * @param name the name of the playlist.
     */
	
	public PlayList(String name) {
		if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Playlist name must not be empty.");
        }
		this.name = name;
		this.songs = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Song> getSongs(){
		ArrayList<Song> list = new ArrayList<Song>();
		for (Song song : songs) {
			list.add(song);
		}
		return list;
	}
	
	 /**
     * Adds a song to the playlist.
     *
     * @param song the Song to add.
     */
	
	public void addSong(Song song) {
		if(song != null && !songs.contains(song)) {
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
		for (Song target : songs) {
			if (target.getTitle().equalsIgnoreCase(song.getTitle()) 
					&& target.getAlbum().equalsIgnoreCase(song.getAlbum()) 
					&& target.getArtist().equalsIgnoreCase(song.getArtist())) {
						songs.remove(target);
						return true;
					}
		}
		return false;
	}
	
	/**
     * Replaces the current song list with a new list.
     * @param newSongs The new list of songs.
     */
	public void newSetList(ArrayList<Song> newSongs) {
        songs.clear();
        if(newSongs != null) {
            for(Song s : newSongs) {
                addSong(s);
            }
        }
    }
	
	/**
     * Adds a song to the beginning of the list (for "Most Recently Played")
     * and ensures the list does not exceed 10 songs.
     * @param song The Song to add.
     */
    public void addToRecent(Song song) {
        songs.remove(song);
        songs.add(0, song);
        while(songs.size() > 10) {
            songs.remove(songs.size() - 1);
        }
    }
    
    @Override
    public Iterator<Song> iterator() {
        return getSongs().iterator(); // Return iterator over a copy for safety.
    }
	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Playlist: " + name + "\nSongs:\n");
        if(songs.isEmpty()) {
            sb.append("No songs in this playlist.");
        } else {
            int count = 1;
            for(Song s : songs) {
                sb.append(count++).append(". ").append(s).append("\n");
            }
        }
        return sb.toString();
    }
}