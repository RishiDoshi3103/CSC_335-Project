package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import database.MusicStore;
import model.Album;
import model.Song;

class MusicStoreTest {

	@Test
	void testSongSearches() {
		MusicStore store = new MusicStore();
		
		String title_not_found = store.searchSongsByTitle("not_found");
		assertEquals(title_not_found, "0 results for songs with title: not_found.\n" );
		
		String title_found = store.searchSongsByTitle("Set Fire to the Rain");
		assertEquals(title_found, "Set Fire to the Rain by Adele | Album: 21\n");
		
		String artist_not_found = store.searchSongsByArtist("no_artist");
		assertEquals(artist_not_found, "0 results for songs with artist: no_artist.\n");
		
		String artist_found = store.searchSongsByArtist("Adele");
		String adele_list = "Daydreamer by Adele | Album: 19\n" 
						  + "Best for Last by Adele | Album: 19\n" 
						  + "Chasing Pavements by Adele | Album: 19\n" 
						  + "Cold Shoulder by Adele | Album: 19\n" 
						  + "Crazy for You by Adele | Album: 19\n" 
						  + "Melt My Heart to Stone by Adele | Album: 19\n" 
						  + "First Love by Adele | Album: 19\n" 
						  + "Right as Rain by Adele | Album: 19\n" 
						  + "Make You Feel My Love by Adele | Album: 19\n" 
						  + "My Same by Adele | Album: 19\n" 
						  + "Tired by Adele | Album: 19\n" 
						  + "Hometown Glory by Adele | Album: 19\n" 
						  + "Rolling in the Deep by Adele | Album: 21\n" 
						  + "Rumour Has It by Adele | Album: 21\n" 
						  + "Turning Tables by Adele | Album: 21\n" 
						  + "Don't You Remember by Adele | Album: 21\n" 
						  + "Set Fire to the Rain by Adele | Album: 21\n" 
						  + "He Won't Go by Adele | Album: 21\n" 
						  + "Take It All by Adele | Album: 21\n" 
						  + "I'll Be Waiting by Adele | Album: 21\n" 
						  + "One and Only by Adele | Album: 21\n" 
						  + "Lovesong by Adele | Album: 21\n" 
						  + "Someone Like You by Adele | Album: 21\n" 
						  + "I Found a Boy by Adele | Album: 21\n";
		assertEquals(artist_found, adele_list);
	}
	
	@Test
	void testAlbumSearches() {
		MusicStore store = new MusicStore();
		
		String title_not_found = store.searchAlbumsByTitle("not_found");
		assertEquals(title_not_found, "0 results for albums with title: not_found.\n");
		
		String title_found = store.searchAlbumsByTitle("Waking Up");
		assertEquals(title_found, "Waking Up by OneRepublic (2009) - Rock\n"
				+ "Rating: NOT_RATED\n"
				+ "Songs:[Made for You, All the Right Moves, Secrets, Everybody Loves Me, " + 
				"Missing Persons 1 & 2, Good Life, All This Time, Fear, Waking Up, Marchin On, Lullaby]\n");
		
		String artist_not_found = store.searchAlbumsByArtist("not_found");
		assertEquals(artist_not_found, "0 results for albums with artist: not_found.\n");
		
		
		String artist_found = store.searchAlbumsByArtist("Adele");
		assertEquals(artist_found,"19 by Adele (2008) - Pop\n"
				+ "Rating: NOT_RATED\n"
				+ "Songs:[Daydreamer, Best for Last, Chasing Pavements, Cold Shoulder, Crazy for You, Melt My "
				+ "Heart to Stone, First Love, Right as Rain, Make You Feel My Love, My Same, Tired, Hometown Glory]\n"
				+ "21 by Adele (2011) - Pop\n"
				+ "Rating: NOT_RATED\n"
				+ "Songs:[Rolling in the Deep, Rumour Has It, Turning Tables, Don't You Remember, Set Fire to the Rain,"
				+" He Won't Go, Take It All, I'll Be Waiting, One and Only, Lovesong, Someone Like You, I Found a Boy]\n" );
		
	}

}
