/*
 * File: MusicStore.java
 * Authors: Kyle Becker / Rishi Doshi
 * 
 * Purpose: This file is meant to replicate a working inventory for songs
 * and albums based on appropriately formatted .txt files, representing
 * available albums found in the resources directory. This program will
 * only recognize files (albums) if their data is present in the resources
 * directory's albums.txt file.
 * 
 * Functions:
 * - Default Constructor: Reads the project's resources directory's associated 
 * albums.txt file, and populates the internal inventory and album stock variables 
 * with appropriately indicated songs and albums. If an album data is referenced
 * in albums.txt, but does not have an associated .txt file, the constructor will 
 * ignore it and move on to the next.
 * - searchSongsByTitle(String title): Returns a list of songs whose titles match 
 * 	 the input string.
 * - searchSongsByArtist(String artist): Returns a list of songs whose artists match
 *   the input string. 
 * - searchAlbumsByTitle(String title): Returns a list of albums whose titles match
 *   the input string.
 * - searchAlbumsByArtist(String artist): Returns a list of albums whose titles match
 *   the input string.
 *   
 * - loadAlbum(String title, String artist): Helper function that loads albums files 
 *   that match the configuration album.txt file contents.
 * - loadSongs(): Helper function that loads songs from all the loaded albums via 
 *   loadAlbum.
 */

package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import model.Album;
import model.Song;

public class MusicStore {
	private ArrayList<Song> inventory;
	private ArrayList<Album> album_stock;
	private File[] files;
	
	public MusicStore() {
		this.album_stock = new ArrayList<Album>();
		this.inventory = new ArrayList<Song>();
		
		Path path = Paths.get("resources");
		File catalogue = new File(path.toAbsolutePath().toString());
		
		
		File[] file_list = catalogue.listFiles();
		this.files = file_list;
		
		// Find albums.txt file
		File album_indexes = null; 
		for (File file : this.files) {
			if (file.getName().equals("albums.txt")) {
				album_indexes = file;
			}
		}
		
		// If albums.txt file found, get each associated album data by line
		// and use tokenized data to populate album_stock with loadAlbum.
		if (album_indexes != null) {
			try {
				Scanner index_scanner = new Scanner(album_indexes);
				while(index_scanner.hasNextLine()) {
					String[] next_album = index_scanner.nextLine().split(",");
					loadAlbum(next_album[0], next_album[1]);
				}
				index_scanner.close();
			} catch (Exception e) {
				System.out.println("Error With albums.txt Read | " + e);
			}
			// Load inventory with songs from found albums (now in album_stock)
			loadSongs();
		}
	}
	
	/**
	 * This function creates a deep copy list of songs with titles that match
	 * the input parameter, based on ones currently in the inventory.
	 * 
	 * @param  title	String representation of a song title
	 * @return list		ArrayList of songs with titles that match the input
	 */
	public ArrayList<Song> searchSongsByTitle(String title) {
		ArrayList<Song> list = new ArrayList<Song>();
		for (Song song : this.inventory) {
			if (song.getTitle().toLowerCase().equals(title.toLowerCase()) ||
					song.getTitle().toLowerCase().contains(title.toLowerCase())) {
				Song jingle = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				list.add(jingle);
			}
		}
		return list;
	}
	
	/**
	 * This function creates a deep copy list of songs with artists that match
	 * the input parameter, based on ones currently in the inventory.
	 * @param  artist	String representation of an artist
	 * @return list		ArrayList of songs with artists that match the input
	 */
	public ArrayList<Song> searchSongsByArtist(String artist) {
		ArrayList<Song> list = new ArrayList<Song>();
		for (Song song : this.inventory) {
			if (song.getArtist().toLowerCase().equals(artist.toLowerCase()) ||
					song.getArtist().toLowerCase().contains(artist.toLowerCase())) {
				Song jingle = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				list.add(jingle);
			}
		}
		return list;
	}
	
	/**
	 * This function creates a deep copy list of albums with titles that match 
	 * the input parameter, based on ones currently in the album stock.
	 * 
	 * @param  title	String representation of an album title
	 * @return list	    ArrayList of albums with titles that match the input
	 */
	public ArrayList<Album> searchAlbumsByTitle(String title) {
		ArrayList<Album> list = new ArrayList<Album>();
		for (Album album : this.album_stock) {
			if(album.getTitle().toLowerCase().equals(title.toLowerCase()) ||
					album.getTitle().toLowerCase().contains(title.toLowerCase())) {
				Album target = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				for (String song : album.getSongs()) {
					target.addSong(song);
				}
				list.add(target);
			}
		}
		return list;
	}
	
	/**
	 * This function creates a deep copy list of albums with artists that match
	 * the input parameter, based on ones currently in the album stock. 
	 * 
	 * @param  artist	String representation of an album artist
	 * @return list		ArrayList of albums with artists that match the input
	 */
	public ArrayList<Album> searchAlbumsByArtist(String artist) {
		ArrayList<Album> list = new ArrayList<Album>();
		for (Album album : this.album_stock) {
			if(album.getArtist().toLowerCase().equals(artist.toLowerCase()) ||
					album.getArtist().toLowerCase().contains(artist.toLowerCase())) {
				Album target = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				for (String song : album.getSongs()) {
					target.addSong(song);
				}
				list.add(target);
			}
		}
		return list;
	}
	
	
	/**
	 * This helper function takes in a title and artist for each line in the album.txt
	 * file, and searches the other files in the resources directory with a matching 
	 * title / artist. If found, it converts the inner data into an equivalent Album
	 * class object and saves it into the internal album_stock variable.
	 * 
	 * @param title		String representing album title (from album.txt)
	 * @param artist	String representing album artist (from album.txt)
	 */
	private void loadAlbum(String title, String artist) {
		for (File file : this.files) {
			try {
				if (!file.getName().equals("albums.txt")) {
					String[] album_data = file.getName().split("_");
					String target_title = album_data[0];
					
					// trim off ".txt"
					String target_artist = album_data[1].substring(0, album_data[1].length() - 4);
					
					if (target_title.equals(title) && target_artist.equals(artist)) {
						try {
							Scanner scanner = new Scanner(file);
							String[] header = scanner.nextLine().split(",");
							Album album = new Album(header[0], header[1], header[2], header[3]);
							while(scanner.hasNextLine()) {
								String name = scanner.nextLine();
								album.addSong(name);
							}
							this.album_stock.add(album);
							scanner.close();
						} catch (Exception e) {
							System.out.println("Error: " + e);
						}
					}
				}
			}
			catch (Exception e) {
				System.out.println("Album-Load Function Error: " + file.getName() + " | " + e);
			}
		}
	}
	
	/**
	 * This helper function is meant to run after all the albums have been parsed, identified,
	 * and loaded into album_stock - then uses the data to create all the individual songs'
	 * equivalent Song classes, storing them into the inventory variable.
	 */
	private void loadSongs() {
		for (Album album : this.album_stock) {
			for (String song : album.getSongs()) {
				Song add_song = new Song(song, album.getTitle(), album.getArtist());
				this.inventory.add(add_song);
			}
		}
	}

}
