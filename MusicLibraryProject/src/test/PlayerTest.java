package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Player;
import model.Song;
import model.Track;

class PlayerTest {

	@Test
	void testAddTrackAndCheckTrack() {
		Player player = new Player();
		
		Song song1 = new Song("test1", "test2", "test3");
		Song song2 = new Song("testA", "testB", "testC");
		
		player.addTrack(song1);
		player.addTrack(song2);
		
		ArrayList<Track> tracks = player.getTrackList(); // Deep Copy
		assertTrue(tracks.get(0).getSong().equals(song1));
		assertEquals(tracks.get(0).getPlayCount(), 0);
		assertTrue(tracks.get(1).getSong().equals(song2));
		assertEquals(tracks.get(1).getPlayCount(), 0);
		
		
		player.getTrack(song1, true);
		tracks = player.getTrackList(); // Deep Copy
		assertTrue(tracks.get(0).getSong().equals(song1));
		assertEquals(tracks.get(0).getPlayCount(), 1);
		assertEquals(tracks.size(), 2);
		
		Track track1 = player.getTrack(song1, true);
		player.getTrack(song2, false);
		Track track2 = player.getTrack(song2, true);
		tracks = player.getTrackList();
		assertEquals(tracks.size(), 2);
		assertEquals(tracks.get(0).getPlayCount(), 2);
		assertEquals(tracks.get(0).getPlayCount(), track1.getPlayCount());
		assertEquals(tracks.get(1).getPlayCount(), 1);
		assertEquals(tracks.get(1).getPlayCount(), track2.getPlayCount());
		

	}

}
