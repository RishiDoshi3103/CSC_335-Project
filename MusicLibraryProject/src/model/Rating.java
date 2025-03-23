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

import java.io.Serializable;

public class Rating implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Song song;
	private int rating;
	
	public Rating(Song target, int score) {
		this.song = target;
		this.rating = score;
	}
	
	public void setRating(int num) {
//		 if(rating < 1 || rating > 5) {
//	            throw new IllegalArgumentException("Rating must be between 1 and 5.");
//	        }
		this.rating = num;
	}
	public Song getSong() {
		return this.song;
	}
	
	public int getRating() {
		return this.rating;
	}
}

