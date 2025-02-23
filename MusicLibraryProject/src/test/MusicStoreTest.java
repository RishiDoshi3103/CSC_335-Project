package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;

class MusicStoreTest {

	@Test
	void testGetFiles() {
		MusicStore store = new MusicStore();
		//System.out.println(store.getFileNames());
		System.out.println(store.getNumAlbums());
		for (Album album : store.getAlbumsForTest()) {
			System.out.println(album.toString());
		}
	}

}
