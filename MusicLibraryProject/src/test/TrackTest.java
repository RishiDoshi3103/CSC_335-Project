package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Song;
import model.Track;

class TrackTest {

	@Test
	void testTracks() {
		Track track = new Track("test1", "test2", "test3");
		
		assertEquals(track.getPlayCount(), 0);
		assertEquals(track.getTitle(), "test1");
		assertEquals(track.getAlbum(), "test2");
		assertEquals(track.getArtist(), "test3");
		
		track.incrementPlayCount();
		
		assertEquals(track.getPlayCount(), 1);
		
		Song song = new Song("test1", "test2", "test3");
		
		assertTrue(track.getSong().equals(song));
		
		track.incrementPlayCount();
		
		assertEquals(track.getPlayCount(), 2);
		
		Track track2 = new Track("testA", "testB", "testC", 5);
		
		assertEquals(track2.getPlayCount(), 5);
		track2.incrementPlayCount();
		assertEquals(track2.getPlayCount(), 6);
	}

}
