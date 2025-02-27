package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Rating;
import model.Song;

class RatingTest {

	@Test
	void testRating() {
		Song song = new Song("Test1", "Test2", "Test3");
		Rating rate = new Rating(song, 3);
		
		assertEquals(rate.getSong(), song);
		assertEquals(rate.getRating(), 3);
		
		rate.setRating(5);
		assertEquals(rate.getRating(), 5);
	}

}
