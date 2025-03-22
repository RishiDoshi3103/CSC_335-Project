package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
	    void testToString() {
	        Song song = new Song("TitleA", "AlbumA", "ArtistA");
	        playlist.addSong(song);

	        String output = playlist.toString();
	        assertTrue(output.contains("My Playlist"), "toString should include playlist name.");
	        assertTrue(output.contains("TitleA"), "toString should include added songs.");
	    }
	    
	    @Test
	    void encapsulationTest() {
	        Song song = new Song("TitleA", "AlbumA", "ArtistA");
	        playlist.addSong(song);
	        
	        ArrayList<Song> playlist1 = playlist.getSongs();
	        Song song2 = new Song("TitleA", "AlbumA", "ArtistA");

	        playlist1.remove(0);
	        playlist.removeSong(song2);
	        
	        assertEquals(playlist.getSongs().size(), 0);

	        assertFalse(playlist.removeSong(song2));

	    }
	    
	    @Test
	    void testNewSetList() {
	    	Song song1 = new Song("1", "1", "1");
	    	Song song2 = new Song("2", "2", "2");
	    	Song song3 = new Song("3", "3", "3");
	    	Song song4 = new Song("4", "4", "4");
	    	
	    	playlist.addSong(song1);
	    	playlist.addSong(song2);
	    	
	    	ArrayList<Song> newSongs = new ArrayList<Song>();
	    	
	    	newSongs.add(song3);
	    	newSongs.add(song4);
	    	playlist.newSetList(newSongs);
	    	
	    	ArrayList<Song> getSongList = playlist.getSongs();
	    	assertEquals(getSongList.size(), 2);
	    	assertEquals(getSongList.get(0).getTitle(), "3");
	    	assertEquals(getSongList.get(1).getTitle(), "4");
	    }
	    
	    @Test
	    void testAddToRecent() {
	    	Song song1 = new Song("1", "1", "1");
	    	Song song2 = new Song("2", "2", "2");
	    	Song song3 = new Song("3", "3", "3");
	    	Song song4 = new Song("4", "4", "4");
	    	
	    	playlist.addSong(song1);
	    	playlist.addSong(song2);
	    	ArrayList<Song> getSongList = playlist.getSongs();
	    	assertEquals(getSongList.get(0).getTitle(), "1");
	    	

	    	playlist.addToRecent(song3);
	    	playlist.addToRecent(song4);
	    	getSongList = playlist.getSongs();
	    	assertEquals(getSongList.get(0).getTitle(), "4");
	    	
	    	playlist.addToRecent(song1);
	    	assertEquals(getSongList.get(0).getTitle(), "4");
	    	assertEquals(getSongList.size(), 4);
	    	

	    }

}
