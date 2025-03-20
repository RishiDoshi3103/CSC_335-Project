/**
 *  File: LibraryModelTest.java
 *  Authors: Kyle Becker / Rishi Doshi
 *  
 *  Purpose: This file tests the LibraryModel class.
 */

package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.Song;

class LibraryModelTest {
	
	@Test
	void testAddSong() {
		LibraryModel lib = new LibraryModel();
		Song song = new Song("Title", "Album", "Artist");
		
		assertTrue(lib.addSong(song));
		assertFalse(lib.addSong(song));
		assertFalse(lib.addSong(new Song("Title", "Album", "Artist")));
	}
	
	@Test
	void testAddAlbum() {
		LibraryModel lib = new LibraryModel();
		Album album = new Album("Title", "Artist", "Genre", "2025");
		String song = "Song";
		album.addSong(song);
		
		assertTrue(lib.addAlbum(album));
		assertFalse(lib.addAlbum(album));
	}
	
	@Test
	void testGetAllSongs() {
		LibraryModel lib = new LibraryModel();
		Song song1 = new Song("Title", "Album", "Artist");
		Song song2 = new Song("Title1", "Album1", "Artist1");
		Song song3 = new Song("Title2", "Album2", "Artist2");
		
		ArrayList<Song> list = lib.getAllSongs();
		assertEquals(list.size(),0);
		lib.addSong(song1);
		
		list = lib.getAllSongs();
		assertEquals(list.size(), 1);
		
		lib.addSong(song2);
		lib.addSong(song3);
		list = lib.getAllSongs();
		assertEquals(list.size(), 3);
	}
	
	@Test
	void testSearchSongByTitle() {
		LibraryModel lib = new LibraryModel();
		Song song1 = new Song("Title", "Album", "Artist");
		Song song2 = new Song("Title1", "Album1", "Artist1");
		Song song3 = new Song("Title2", "Album2", "Artist2");
		lib.addSong(song1);
		lib.addSong(song2);
		lib.addSong(song3);
		
		ArrayList<Song> list = lib.searchSongByTitle("Bad Title");
		assertEquals(list.size(), 0);
		
		list = lib.searchSongByTitle("Title2");
		assertEquals(list.size(), 1);
		
		list = lib.searchSongByTitle("Title");
		assertEquals(list.size(), 3);
	}
	
	@Test
	void testSearchSongByArtist() {
		LibraryModel lib = new LibraryModel();
		Song song1 = new Song("Title", "Album", "Artist");
		Song song2 = new Song("Title1", "Album1", "Artist1");
		Song song3 = new Song("Title2", "Album2", "Artist2");
		lib.addSong(song1);
		lib.addSong(song2);
		lib.addSong(song3);
		
		ArrayList<Song> list = lib.searchSongByArtist("Bad Artist");
		assertEquals(list.size(), 0);
		
		list = lib.searchSongByArtist("Artist2");
		assertEquals(list.size(), 1);
		
		list = lib.searchSongByArtist("Artist");
		assertEquals(list.size(), 3);
	}
	
	@Test
	void testSetRatingAndGetFavorites() {
		LibraryModel lib = new LibraryModel();
		Song song1 = new Song("Title", "Album", "Artist");
		Song song2 = new Song("Title1", "Album1", "Artist1");
		Song song3 = new Song("Title2", "Album2", "Artist2");
		
		lib.setRating(song1, 1);
		lib.setRating(song2, 1);
		lib.setRating(song3, 1);
		
		ArrayList<Song> list = lib.getFavorites();
		assertEquals(list.size(), 0);
		
		lib.setRating(song1, 5);
		lib.setRating(song2, 5);
		list = lib.getFavorites();
		assertEquals(list.size(), 2);
	}
	
	@Test
	void testGetArtists() {
		LibraryModel lib = new LibraryModel();
		Song song1 = new Song("Title", "Album", "Artist");
		Song song2 = new Song("Title1", "Album1", "Artist1");
		Song song3 = new Song("Title2", "Album2", "Artist2");
		
		
		ArrayList<String> list = lib.getArtists();
		assertEquals(list.size(), 0);
		
		lib.addSong(song1);
		
		list = lib.getArtists();
		assertEquals(list.size(), 1);
		assertEquals(list.get(0).toString(), "Artist");
		
		lib.addSong(song2);
		lib.addSong(song3);
		list = lib.getArtists();
		assertEquals(list.size(), 3);
	}
	
	@Test
	void testGetAllAlbumsInLibrary() {
		LibraryModel lib = new LibraryModel();
		Album album = new Album("Title", "Artist", "Genre", "2025");
		Album album1 = new Album("Title1", "Artist1", "Genre1", "2026");
		String song1 = "song";
		album.addSong(song1);
		
		ArrayList<Album> list = lib.getAllAlbumsInLibrary();
		assertEquals(list.size(), 0); 
		
		lib.addAlbum(album);
		lib.addAlbum(album1);
		list = lib.getAllAlbumsInLibrary();
		assertEquals(list.size(), 2);
		assertEquals(list.get(0).toString(), album.toString());
	}
	
	@Test
	void testSearchAlbumsByTitle() {
		LibraryModel lib = new LibraryModel();
		Album album = new Album("Title", "Artist", "Genre", "2025");
		Album album1 = new Album("Title1", "Artist1", "Genre1", "2026");
		String song1 = "song";
		album.addSong(song1);
		lib.addAlbum(album);
		lib.addAlbum(album1);

		ArrayList<Album> list = lib.searchAlbumsByTitle("Title1");
		assertEquals(list.size(), 1);
		assertEquals(list.get(0).getArtist(), album1.getArtist());
		
		list = lib.searchAlbumsByTitle("Title"); // contains
		assertEquals(list.size(), 2);
		
	}
	
	@Test
	void testSearchAlbumsByArtist() {
		LibraryModel lib = new LibraryModel();
		Album album = new Album("Title", "Artist", "Genre", "2025");
		Album album1 = new Album("Title1", "Artist1", "Genre1", "2026");
		String song1 = "song";
		album.addSong(song1);
		lib.addAlbum(album);
		lib.addAlbum(album1);
		
		ArrayList<Album> list = lib.searchAlbumsByArtist("Artist1");
		assertEquals(list.size(), 1);
		assertEquals(list.get(0).getArtist(), album1.getArtist());
		
		list = lib.searchAlbumsByArtist("Artist");
		assertEquals(list.size(), 2);
	}
	
	@Test
	void testCreateRemoveAndSearchPlayLists() {
		LibraryModel lib = new LibraryModel();
		assertTrue(lib.createPlaylist("playlist1"));
		assertFalse(lib.createPlaylist("playlist1")); // already exists
		
		lib.createPlaylist("playlist2");
		ArrayList<String> list = lib.getPlaylists();
		assertEquals(list.toString(), "[Most Recently Played, Most Frequently Played, playlist1, playlist2]");
		
		assertFalse(lib.removePlaylist("wrong name"));
		assertTrue(lib.removePlaylist("playlist1"));
		
		assertEquals(lib.searchPlaylistByName("playlist2").getName(), "playlist2");
		assertEquals(lib.searchPlaylistByName("playlist1"), null);
	}
	
	@Test
	void testPlaylistAddAndRemoveSongs() {
		LibraryModel lib = new LibraryModel();
		lib.createPlaylist("playlist1");
		Song song1 = new Song("Title", "Album", "Artist");
		Song song2 = new Song("Title1", "Album1", "Artist1");
		
		assertFalse(lib.addSongToPlaylist("playlist3", song1));
		assertTrue(lib.addSongToPlaylist("playlist1", song1));
		assertFalse(lib.addSongToPlaylist("playlist1", song1));
		
		assertFalse(lib.removeSongFromPlaylist("playlist3", song1.getTitle()));
		assertTrue(lib.removeSongFromPlaylist("playlist1", song1.getTitle()));
	}
	
	@Test
	void testPlaySongAndUpdateMostPlayed() {
		LibraryModel lib = new LibraryModel();
	}
}
