package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.PlayList;
import model.Song;
import model.Rating;

class PlayListTest {

	 private PlayList playlist;

	    @BeforeEach
	    void setUp() {
	        playlist = new PlayList("My Playlist");
	    }

	    @Test
	    void testConstructorAndGetters() {
	        assertEquals("My Playlist", playlist.getName());
	        assertNotNull(playlist.getSongs(), "Songs list should not be null.");
	        assertTrue(playlist.getSongs().isEmpty(), "Songs list should be empty initially.");
	        assertEquals(Rating.NOT_RATED, playlist.getRating(), "Default rating should be NOT_RATED.");
	        assertFalse(playlist.isFavorite(), "Playlist should not be favorite by default.");
	    }

	    @Test
	    void testAddSong() {
	        Song song1 = new Song("Title1", "Album1", "Artist1");
	        Song song2 = new Song("Title2", "Album2", "Artist2");
	        playlist.addSong(song1);
	        playlist.addSong(song2);

	        assertEquals(2, playlist.getSongs().size());
	        assertSame(song1, playlist.getSongs().get(0));
	        assertSame(song2, playlist.getSongs().get(1));
	    }

	    @Test
	    void testRemoveSong() {
	        Song song1 = new Song("Title1", "Album1", "Artist1");
	        Song song2 = new Song("Title2", "Album2", "Artist2");
	        playlist.addSong(song1);
	        playlist.addSong(song2);

	        boolean removed = playlist.removeSong(song1);
	        assertTrue(removed, "Song1 should be removed successfully.");
	        assertEquals(1, playlist.getSongs().size());
	        assertFalse(playlist.getSongs().contains(song1));
	    }

	    @Test
	    void testSetRating() {
	        playlist.setRating(Rating.FOUR);
	        assertEquals(Rating.FOUR, playlist.getRating());
	        assertFalse(playlist.isFavorite(), "Should not be favorite if rating is not FIVE.");

	        playlist.setRating(Rating.FIVE);
	        assertEquals(Rating.FIVE, playlist.getRating());
	        assertTrue(playlist.isFavorite(), "Should be favorite when rating is FIVE.");
	    }

	    @Test
	    void testSetFavorite() {
	        playlist.setFavorite(true);
	        assertTrue(playlist.isFavorite());
	        playlist.setFavorite(false);
	        assertFalse(playlist.isFavorite());
	    }

	    @Test
	    void testToString() {
	        Song song = new Song("TitleA", "AlbumA", "ArtistA");
	        playlist.addSong(song);
	        playlist.setRating(Rating.THREE);

	        String output = playlist.toString();
	        assertTrue(output.contains("My Playlist"), "toString should include playlist name.");
	        assertTrue(output.contains("3"), "toString should include rating if not NOT_RATED.");
	        assertTrue(output.contains("TitleA"), "toString should include added songs.");
	    }

	    @Test
	    void testSetRatingNull() {
	        assertThrows(IllegalArgumentException.class, () -> {
	            playlist.setRating(null);
	        }, "Should throw IllegalArgumentException when rating is null.");
	    }

}
