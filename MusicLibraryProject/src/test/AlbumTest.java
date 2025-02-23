package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Album;
import model.Rating;

class AlbumTest {

	private Album album;

    @BeforeEach
    void setUp() {
        album = new Album("Test Album", "Test Artist", "Rock", 2025);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Test Album", album.getTitle());
        assertEquals("Test Artist", album.getArtist());
        assertEquals("Rock", album.getGenre());
        assertEquals(2025, album.getYear());
        assertTrue(album.getSongs().isEmpty(), "Songs list should be empty initially.");
        assertEquals(Rating.NOT_RATED, album.getRating(), "Default rating should be NOT_RATED.");
        assertFalse(album.isFavortite(), "Album should not be favorite by default.");
    }

    @Test
    void testAddSong() {
        album.addSong("Song 1");
        album.addSong("Song 2");
        assertEquals(2, album.getSongs().size(), "Songs list should have 2 songs.");
        assertEquals("Song 1", album.getSongs().get(0));
        assertEquals("Song 2", album.getSongs().get(1));
    }

    @Test
    void testSetRating() {
        album.setRating(Rating.THREE);
        assertEquals(Rating.THREE, album.getRating());
        album.setRating(Rating.FIVE);
        assertEquals(Rating.FIVE, album.getRating());
    }

    @Test
    void testSetFavorite() {
        album.setFavorite(true);
        assertTrue(album.isFavortite(), "Album should be marked as favorite.");
        album.setFavorite(false);
        assertFalse(album.isFavortite(), "Album should not be favorite after unmarking.");
    }

    @Test
    void testToString() {
        album.addSong("Song A");
        album.setRating(Rating.TWO);
        album.setFavorite(true);
        String output = album.toString();
        assertTrue(output.contains("Test Album"), "toString should include album title.");
        assertTrue(output.contains("Test Artist"), "toString should include artist.");
        assertTrue(output.contains("Song A"), "toString should include song titles.");
        assertTrue(output.contains("2"), "toString should include rating.");
        assertTrue(output.contains("[Favorite]"), "toString should include favorite marker if set to favorite.");
    }

    @Test
    void testSetRatingNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            album.setRating(null);
        }, "Should throw IllegalArgumentException when rating is null.");
    }
}
