/**
 *  File: MusicStoreTest.java
 *  Authors: Kyle Becker / Rishi Doshi
 *  
 *  Purpose: This file tests the MusicStore class.
 *  
 *  Please note that these tests utilize the default album.txt configuration
 *  file and associated album files in the resource directory. Or that the
 *  used files are from an Adele fan but, like, only the albums 19 and 21..
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import database.MusicStore;
import model.Album;
import model.Song;


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
	
	@Test
	void testSearchSongsByArtist() {
		MusicStore store = new MusicStore();
		
		ArrayList<Song> list = store.searchSongsByArtist("No_Song");
		assertEquals(list.size(), 0);
		
		ArrayList<Song> list2 = store.searchSongsByArtist("adele");
		assertEquals(list2.size(), 24);
		assertEquals(list2.get(0).toString(), "Daydreamer by Adele | Album: 19");
		
		ArrayList<Song> list3 = store.searchSongsByArtist("doll");
		assertEquals(list3.size(), 10);
	}
	
	@Test
	void testSearchAlbumsByTitle() {
		MusicStore store = new MusicStore();
		
		ArrayList<Album> list = store.searchAlbumsByTitle("No_Title");
		assertEquals(list.size(), 0);
		
		ArrayList<Album> list2 = store.searchAlbumsByTitle("21");
		assertEquals(list2.size(), 1);
		assertEquals(list2.get(0).toString(), "21 by Adele (2011) - Pop\n" +
			"Songs:[Rolling in the Deep, Rumour Has It, Turning Tables, "+
			"Don't You Remember, Set Fire to the Rain, He Won't Go, Take It All, "+
			"I'll Be Waiting, One and Only, Lovesong, Someone Like You, I Found a Boy]");
	}
	
	@Test
	void testSearchAlbumsByArtst() {
		MusicStore store = new MusicStore();
		
		ArrayList<Album> list = store.searchAlbumsByArtist("No_Title");
		assertEquals(list.size(), 0);
		
		ArrayList<Album> list2 = store.searchAlbumsByArtist("adele");
		assertEquals(list2.size(), 2);
		assertEquals(list2.get(0).getArtist(), "Adele");
	}
}
