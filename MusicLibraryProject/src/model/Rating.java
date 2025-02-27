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
/**
public enum Rating {
	NOT_RATED(0),
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5);
	
	private final int value;
	
	Rating(int value){
		this.value = value;
	}
	
	/**
     * Returns the numeric value of this rating.
     *
     * @return the rating value.
     
	
	public int getValue() {
		return value;
	}
	
	/**
     * Returns a Rating enum for a given integer value.
     *
     * @param value the integer value (0 to 5)
     * @return the corresponding Rating, or throws an exception if not found.
     * @throws IllegalArgumentException if the value is outside the range 0-5.
     
	
	public static Rating fromInt(int value) {
		for(Rating r : Rating.values()) {
			if(r.getValue() == value) {
				return r;
			}
		}
		throw new IllegalArgumentException("Invalid Rating Value: " + value);
	}
	
	@Override
	public String toString() {
		return (this == NOT_RATED) ? "NOT_RATED" : String.valueOf(value);
	}
	*/

