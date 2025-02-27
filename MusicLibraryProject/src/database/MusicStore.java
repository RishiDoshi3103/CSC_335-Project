/*
 * File: MusicStore.java
 * Author: Kyle Becker & Rishi Doshi
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
 * - searchSongsByTitle( String ): Prints to console all songs with titles
 * that match the parameter string.
 * - searchSongsByArtist( String ): Prints to console all songs by artists
 * that match the parameter string.
 * - searchAlbumsByTitle( String ): Prints to console all albums with titles
 * that match the parameter string.
 * - searchAlbumsByArtist( String ): Prints to console all albums by artists
 * that match the parameter string.
 * 
 * - getSongByTitle( String ): Returns the first found song whose title matches
 * the parameter string. Otherwise, returns null.
 * - getSongsByArtist( String ): Returns an ArrayList object containing songs whose
 * artists match the parameter string. Otherwise, returns an empty ArrayList.
 * - getAlbumByTitle( String ): Returns the first found album whose title matches
 * the parameter string. Otherwise, returns null.
 * - getAlbumsByArtist( String ): Returns an ArrayList object containing albums
 * whose artists match the parameter string. Otherwise, returns an empty ArrayList.
 * 
 * - loadAlbums( String, String ): Helper function that accepts each individual line
 * in the albums.txt file, where the first string parameter represents the tokenized
 * album title and the second represents album artist, and searches the other .txt
 * files for albums matching the associated data. If found, it adds the albums data
 * to the variable list album_stock - representing available albums.
 * - loadSongs(): Helper function called after the album_stock variable has been 
 * fully allocated. It reads in each song attached to each album found in the album_stock
 * variable, and creates an appropriately representative Song class for each one in the 
 * inventory variable.
 * 
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
	
	/**
     * Default constructor that loads album and song data from the resources directory.
     */
	public MusicStore() {
		this.album_stock = new ArrayList<Album>();
		this.inventory = new ArrayList<Song>();
		
        // Locate the resources directory
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
     * Searches for songs by title in the store inventory.
     * Returns a list of copies of matching Song objects.
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
     * Searches for songs by artist in the store inventory.
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
     * Searches for albums by title in the store.
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
     * Searches for albums by artist in the store.
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
     * Returns the names of all files in the resources directory.
     */
	
	public ArrayList<String> getFileNames() {
		ArrayList<String> output = new ArrayList<String>();
		for (File file : this.files) {
			output.add(file.getName());
		}
		return output;
	}
	
	/**
     * Loads an album based on the title and artist, by scanning through all files.
     * If a file matches, it loads album data and its song list.
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
     * Loads all songs from each album in album_stock into the inventory.
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
