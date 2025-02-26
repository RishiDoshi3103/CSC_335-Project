package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.Song;

/**
 * These tests are ran assuming default album.txt file, and associated
 * album files and their songs.
 */
class MusicStoreTest {
	
	@Test
	void testSearchSongsByTitle() {
		MusicStore store = new MusicStore();
		
		ArrayList<Song> list = store.searchSongsByTitle("No_Songs");
		assertEquals(list.size(), 0);
		
		ArrayList<Song> list2 = store.searchSongsByTitle("Set Fire to the Rain");
		assertEquals(list2.size(), 1);
		assertEquals(list2.get(0).toString(), "Set Fire to the Rain by Adele | Album: 21");
		
		ArrayList<Song> list3 = store.searchSongsByTitle("The");
		assertEquals(list3.size(), 21);
	}
}
