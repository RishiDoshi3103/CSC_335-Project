package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Rating;
import model.Song;

class SongTest {

	@Test
	void testSong() {
		Song song1 = new Song("test_title", "test_album","test_artist");
		assertEquals(song1.getTitle(), "test_title");
		assertEquals(song1.getAlbum(), "test_album");
		assertEquals(song1.getArtist(), "test_artist");

		assertEquals(song1.toString(), "test_title by test_artist | Album: test_album");
	}

}
