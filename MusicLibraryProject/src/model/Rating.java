package model;

/**
 * The Rating enum represents possible rating values for songs and albums.
 */

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
     */
	
	public int getValue() {
		return value;
	}
	
	/**
     * Returns a Rating enum for a given integer value.
     *
     * Value the integer value (0 to 5)
     * The corresponding Rating, or NOT_RATED if value is 0.
     * IllegalArgumentException if the value is outside the range 0-5.
     */
	
	public static Rating fromInt(int value) {
		for(Rating r : Rating.values()) {
			if(r.getValue() == value) {
				return r;
			}
			throw new IllegalArgumentException("Invalid Rating Value: " + value);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return (this == NOT_RATED) ? "NOT_RATED" : String.valueOf(value);
	}
}
