/*
 * File: Rating.java
 * Authors: Kyle Becker / Rishi Doshi
 * 
 * Purpose: This file is intended to represent an isolated pairing
 * of songs and their ratings, added when a user rates a song in 
 * their library.
 *
 * Note: This class anticipates objects of the Song class to be
 * immutable, so it will return them directly.
 */

package model;

public class Rating {
	
	private Song song;
	private int rating;
	
	public Rating(Song target, int score) {
		this.song = target;
		this.rating = score;
	}
	
	public void setRating(int num) {
		this.rating = num;
	}
	public Song getSong() {
		return this.song;
	}
	
	public int getRating() {
		return this.rating;
	}
}

