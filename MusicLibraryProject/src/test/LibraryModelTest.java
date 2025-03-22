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
import model.PlayList;
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
    	
    	lib.removeSong(new Song("3", "3", "3"));
    	
    	assertEquals(lib.getAllSongs().size(), 4);
    	assertFalse(lib.getAllSongs().contains(song3));
    	
    	assertFalse(lib.removeSong(new Song("3", "3", "3")));
    	assertEquals(lib.getAllSongs().size(), 4);
    	
    	Album album = new Album("Test", "Test", "Test", "2025");
    	album.addSong("Song1");
    	album.addSong("Song2");
    	
    	lib.addAlbumWithAllSongs(album);
    	// due to success handling in viewer, we add the songs manually.
    	lib.addSong(new Song("Song1", album.getTitle(), album.getArtist()));
    	lib.addSong(new Song("Song1", album.getTitle(), album.getArtist()));

    	assertEquals(lib.getAllSongs().size(), 6);

    	lib.removeSong(new Song("Song1", "Test", "Test"));
    	
		ArrayList<Album> records = lib.getAllAlbumsInLibrary();
		assertEquals(records.get(0).getSongs().size(), 1);
		assertEquals(records.get(0).getSongs().get(0), "Song2");
    	assertEquals(lib.getAllSongs().size(), 5);
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
	
	@Test
	void testSearchSongsByGenre() {
		LibraryModel lib = new LibraryModel();
		
		Album album1 = new Album("1", "1", "1", "1");
		Album album2 = new Album("2", "2", "2", "2");
		
		album1.addSong("Song1A");
		album1.addSong("Song1B");
		
		album2.addSong("Song2A");
		album2.addSong("Song2B");
		album2.addSong("Song2C");
		
		lib.addAlbumWithAllSongs(album1);
		lib.addAlbumWithAllSongs(album2);
		
		ArrayList<Song> songs1 = lib.searchSongsByGenre("1");
		ArrayList<Song> songs2 = lib.searchSongsByGenre("2");
		assertEquals(songs1.size(), 2);
		assertEquals(songs1.get(0), new Song("Song1A", "1", "1"));
		assertEquals(songs1.get(1), new Song("Song1B", "1", "1"));
		
		assertEquals(songs2.size(), 3);
		assertEquals(songs2.get(0), new Song("Song2A", "2", "2"));
		assertEquals(songs2.get(1), new Song("Song2B", "2", "2"));
		assertEquals(songs2.get(2), new Song("Song2C", "2", "2"));
		
	}
	
	@Test
	void testAddAlbumOneSongAddAlbumSong() {
		LibraryModel lib = new LibraryModel();

		Album album1 = new Album("1", "1", "1", "1");
		
		Song song1 = new Song("A", "1", "1");
    	Song song2 = new Song("B", "2", "2");
    	Song song3 = new Song("C", "1", "1");
    	
    	assertFalse(lib.addAlbumOneSong(album1, song2));
    	assertEquals(lib.getAllAlbumsInLibrary().size(), 0);
    	
    	assertTrue(lib.addAlbumOneSong(album1, song1));
    	assertEquals(lib.getAllAlbumsInLibrary().size(), 1);
    	
    	assertFalse(lib.addAlbumOneSong(album1, song3));
    	assertEquals(lib.getAllAlbumsInLibrary().size(), 1);

    	assertFalse(lib.addAlbumSong(song2));
    	assertTrue(lib.addAlbumSong(song3));

    	
    	ArrayList<Album> albs = lib.searchAlbumsByTitle("1");
    	assertEquals(albs.get(0).getSongs().size(), 2);
    	assertEquals(albs.get(0).getSongs().get(0), "A");
    	assertEquals(albs.get(0).getSongs().get(1), "C");

	}
	
	@Test
	void testGenrePlaylists() {
		// For Video Proof
		// Our implementation checks our genre playlists every time
		// a song/album is added, cross-referencing the two, and if
		// an album-song combo contains a combined song count, with
		// other album-song combinations of the same genre, it adds
		// the total of those songs to a created/updated Genre playlist.
		
		// Because our user's libraries are mediated to the store by the 
		// viewer - we get returned information over what was successfully added
		// and not. This means we must add things a bit tediously in this test,
		// but results are more accurately communicated and verifiable.
		LibraryModel lib = new LibraryModel();
		
		Album album1 = new Album("1", "1", "Genre1", "1");
		Song song1 = new Song("1", "1", "1");
    	Song song2 = new Song("2", "1", "1");
    	Song song3 = new Song("3", "1", "1");
    	Song song4 = new Song("4", "1", "1");
    	Song song5 = new Song("5", "1", "1");
    	Song song6 = new Song("6", "1", "1");
    	Song song7 = new Song("7", "1", "1");
    	Song song8 = new Song("8", "1", "1");
    	Song song9 = new Song("9", "1", "1");
    	Song song10 = new Song("10", "1", "1");
    	for (int i =1; i < 11; i++) {
    		album1.addSong(Integer.toString(i));
    	}
    	lib.addSong(song1);
    	lib.addSong(song2);
    	lib.addSong(song3);
    	lib.addSong(song4);
    	lib.addSong(song5);
    	lib.addSong(song6);
    	lib.addSong(song7);
    	lib.addSong(song8);
    	lib.addSong(song9);
    	lib.addSong(song10);
    	
    	lib.addAlbumWithAllSongs(album1);
    	
    	Album albumA = new Album("A", "A", "GenreA", "A");
    	Song songA = new Song("A", "A", "A");
    	Song songB = new Song("B", "A", "A");
    	Song songC = new Song("C", "A", "A");
    	albumA.addSong("A");
    	albumA.addSong("B");
    	albumA.addSong("C");
    	lib.addSong(songA);
    	lib.addSong(songB);
    	lib.addSong(songC);
    	
    	PlayList p1 = lib.searchPlaylistByName("Genre1");
    	PlayList pA = lib.searchPlaylistByName("GenreA");
    	
    	assertEquals(pA, null);
    	assertEquals(p1.getSongs().size(), 10);

    	
    	lib.removeSong(new Song("10", "1", "1"));
    	p1 = lib.searchPlaylistByName("Genre1");
    	
    	assertEquals(p1, null);
	}
}
