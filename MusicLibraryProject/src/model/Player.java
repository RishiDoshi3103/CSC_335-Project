package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Player {
	private ArrayList<Track> tracks;
	private LinkedList<Track> recent;
	private PriorityQueue<Track> topSongs;
	
	public Player() {
		this.tracks = new ArrayList<Track>();
		this.recent = new LinkedList<Track>();
		this.topSongs = new PriorityQueue<Track>(Comparator.comparingInt(Track::getPlayCount).reversed());
	}
	
	
	/** may be unnecessary w. contains**/
	public boolean checkTrack(Song song) {
		for (Track track : this.tracks) {
			if (track.getSong().equals(song)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addTrack(Song song) {
		for (Track track: this.tracks) {
			if (track.getSong().equals(song)) {
				return false;
			}
		}

		Track newTrack = new Track(song.getTitle(), song.getAlbum(), song.getArtist());
		this.tracks.add(newTrack);
		return true;
	}
	
	public Track getTrack(Song song, boolean play) {
		for (Track track : this.tracks) {
			if (track.getSong().equals(song)) {
				if (play == true) {
					track.incrementPlayCount();
					addToRecent(track);
					checkTop(track);
				}
				return new Track(track.getTitle(), track.getAlbum(), track.getArtist(), track.getPlayCount());
			}
		}
		return null;
	}
	
	public void addToRecent(Track track) {
		for (Track check : this.recent) {
			if (check.getSong().equals(track.getSong())) {
				this.recent.remove(check);
				this.recent.addFirst(check);
				return;
			}
		}
		if (this.recent.size() == 10) {
			this.recent.removeLast();
			this.recent.addFirst(track);
		}
		else {
			this.recent.addFirst(track);
		}
	}
	
	public void checkTop(Track track) {
		if (this.topSongs.size() == 10) {
			this.topSongs.add(track);
			this.topSongs.poll();
		}
		else {
			this.topSongs.add(track);
		}
	}
	
	public ArrayList<Track> getTrackList() {
		ArrayList<Track> replicant = new ArrayList<Track>();
		for (Track track : this.tracks) {
			replicant.add(new Track(track.getTitle(), track.getAlbum(), track.getArtist(), track.getPlayCount()));
		}
		return replicant;
	}
	
	public LinkedList<Track> getRecent() {
		LinkedList<Track> replicant = new LinkedList<Track>();
		for (Track track : this.recent) {
			replicant.add(new Track(track.getTitle(), track.getAlbum(), track.getArtist(), track.getPlayCount()));
		}
		return replicant;
	}
	
	public ArrayList<Track> getTop() {
		ArrayList<Track> replicant = new ArrayList<Track>();
		for (Track track : this.topSongs) {
			replicant.add(new Track(track.getTitle(), track.getAlbum(), track.getArtist(), track.getPlayCount()));
		}
		return replicant;
	}

}
