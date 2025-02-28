
/**
 * File: PlayList.java
 * Authors: Rishi Doshi / Kyle Becker
 * 
 * Purpose: The PlayList class represents a user-defined playlist.
 * It stores a name, an ordered list of Song objects, an overall 
 * playlist rating, and a favorite flag.
 */


package model;

import java.util.ArrayList;
import java.util.List;


public class PlayList {
	private String name;
	private ArrayList<Song> songs;
	
	
	/**
     * Constructs a new PlayList with the specified name.
     * The initial rating is set to NOT_RATED, and the playlist is not marked as favorite.
     *
     * @param name the name of the playlist.
     */
	
	public PlayList(String name) {
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
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Playlist: ").append(name).append("\n");
		sb.append("Songs:\n");
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