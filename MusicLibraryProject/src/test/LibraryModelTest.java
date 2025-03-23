/**
 *  File: LibraryModelTest.java
 *  Authors: Kyle Becker / Rishi Doshi
 *  
 *  Purpose: This file tests the LibraryModel class.
 */

package test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.Rating;
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
		
		assertTrue(lib.addAlbumWithAllSongs(album));
		assertFalse(lib.addAlbumWithAllSongs(album));
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
		
		lib.addAlbumWithAllSongs(album);
		lib.addAlbumWithAllSongs(album1);
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
		lib.addAlbumWithAllSongs(album);
		lib.addAlbumWithAllSongs(album1);

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
		lib.addAlbumWithAllSongs(album);
		lib.addAlbumWithAllSongs(album1);
		
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
		assertEquals(list.toString(), "[Most Recently Played, Most Frequently Played, Favorite Songs, Top Rated, playlist1, playlist2]");
		
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
		
		Song song1 = new Song("1", "1", "1");
    	Song song2 = new Song("2", "2", "2");
    	Song song3 = new Song("3", "3", "3");
    	Song song4 = new Song("4", "4", "4");
    	Song song5 = new Song("5", "5", "5");
    	
    	lib.addSong(song1);
    	lib.addSong(song2);
    	lib.addSong(song3);
    	lib.addSong(song4);
    	lib.addSong(song5);
    	
    	lib.playSong(song1);
    	lib.playSong(song1);
    	lib.playSong(song2);
    	lib.playSong(song4);
    	lib.playSong(song2);
    	lib.playSong(song2);
    	lib.playSong(song2);
    	lib.playSong(song3);
    	
    	ArrayList<Song> recent = lib.searchPlaylistByName("Most Recently Played").getSongs();
    	assertEquals(recent.size(), 4);
    	assertEquals(recent.get(0).getTitle(), "3");
    	assertEquals(recent.get(1).getTitle(), "2");
    	assertEquals(recent.get(2).getTitle(), "4");
    	assertEquals(recent.get(3).getTitle(), "1");
    	
    	ArrayList<Song> freq = lib.searchPlaylistByName("Most Frequently Played").getSongs();
    	assertEquals(freq.size(), 4);
    	assertEquals(freq.get(0).getTitle(), "2");
    	assertEquals(freq.get(1).getTitle(), "1");
    	assertEquals(freq.get(2).getTitle(), "4");
    	assertEquals(freq.get(3).getTitle(), "3");
	}
	
	@Test
	void testGetRatedSongs() {
		LibraryModel lib = new LibraryModel();
		
		Song song1 = new Song("1", "1", "1");
    	Song song2 = new Song("2", "2", "2");
    	Song song3 = new Song("3", "3", "3");
    	Song song4 = new Song("4", "4", "4");
    	Song song5 = new Song("5", "5", "5");
    	
    	lib.addSong(song1);
    	lib.addSong(song2);
    	lib.addSong(song3);
    	lib.addSong(song4);
    	lib.addSong(song5);
    	
    	lib.setRating(song3, 1);
    	lib.setRating(song2, 2);
    	lib.setRating(song5, 3);
    	lib.setRating(song1, 4);
    	lib.setRating(song4, 5);
    	
    	ArrayList<Rating> rates = lib.getRatedSongs();
    	Collections.sort(rates, (rate1, rate2) -> Integer.compare(rate1.getRating(), rate2.getRating()));
    	
    	assertEquals(rates.get(0).getSong(), song3); 
    	assertEquals(rates.get(1).getSong(), song2); 
    	assertEquals(rates.get(2).getSong(), song5); 
    	assertEquals(rates.get(3).getSong(), song1);
    	assertEquals(rates.get(4).getSong(), song4);
    	
		
	}
	
	@Test
	void testRemoveSong() {
		LibraryModel lib = new LibraryModel();
		
		Song song1 = new Song("1", "1", "1");
    	Song song2 = new Song("2", "2", "2");
    	Song song3 = new Song("3", "3", "3");
    	Song song4 = new Song("4", "4", "4");
    	Song song5 = new Song("5", "5", "5");
    	
    	lib.addSong(song1);
    	lib.addSong(song2);
    	lib.addSong(song3);
    	lib.addSong(song4);
    	lib.addSong(song5);
    	
    	lib.removeSong(song3);
    	
    	assertEquals(lib.getAllSongs().size(), 4);
    	assertFalse(lib.getAllSongs().contains(song3));
	}
	
	@Test
	void testRemoveAlbum() {
		LibraryModel lib = new LibraryModel();
		
		Album album = new Album("Title", "Artist", "Genre", "2025");
		Album album1 = new Album("1", "1", "1", "1");
		Album album2 = new Album("2", "2", "2", "2");
		
		lib.addAlbumWithAllSongs(album);
		lib.addAlbumWithAllSongs(album1);
		lib.addAlbumWithAllSongs(album2);
		
		ArrayList<Album> records = lib.getAllAlbumsInLibrary();
		assertEquals(records.size(), 3);
		
		assertTrue(lib.removeAlbum(new Album("1", "1", "1", "1")));
		assertFalse(lib.checkForAlbumPresence(album1));

	}
}
