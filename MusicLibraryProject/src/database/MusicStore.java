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
		
		// If albums.txt file found, get each associated album data
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
			loadSongs();
		}
	}
	
	public void searchSongsByTitle(String title) {
		boolean found = false;
		for (Song song : this.inventory) {
			if (song.getTitle().equals(title)) {
				System.out.println(song.toString());
				found = true;
			}
		}
		if (!found) {
			System.out.println("0 results for songs with title: " + title + ".");
		}
		
	}
	
	public void searchSongsByArtist(String artist) {
		boolean found = false;
		for (Song song : this.inventory) {
			if (song.getArtist().equals(artist)) {
				System.out.println(song.toString());
				found = true;
			}
		}
		if (!found) {
			System.out.println("0 results for songs with artist: " + artist + ".");
		}
	}
	
	public void searchAlbumsByTitle(String title) {
		boolean found = false;
		for (Album album : this.album_stock) {
			if (album.getTitle().equals(title)) {
				System.out.println(album.toString());
				found = true;
			}
		}
		if (!found) {
			System.out.println("0 results for albums with title: " + title + ".");
		}
	}
	
	public void searchAlbumsByArtist(String artist) {
		boolean found = false;
		for (Album album : this.album_stock) {
			System.out.println(album.toString());
			found = true;
		}
		if (!found) {
			System.out.println("0 results for albums with artist: " + artist + ".");
		}
	}
	
	public Song getSongByTitle(String title) {
		for (Song song : this.inventory) {
			if (song.getTitle().equals(title)) {
				Song target = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				return target;
			}
		}
		return null;
	}
	
	public ArrayList<Song> getSongsByArtist(String artist) {
		ArrayList<Song> target = new ArrayList<Song>();
		for (Song song : this.inventory) {
			if (song.getArtist().equals(artist)) {
				Song add_song = new Song(song.getTitle(), song.getAlbum(), song.getArtist());
				target.add(add_song);
			}
		}
		return target;
	}
	
	public Album getAlbumByTitle(String title) {
		for (Album album : this.album_stock) {
			if (album.getTitle().equals(title)) {
				Album target = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				for (String song : album.getSongs()) {
					target.addSong(song);
				}
				return target;
			}
		}
		return null;
	}
	
	public ArrayList<Album> getAlbumsByArtist(String artist) {
		ArrayList<Album> target = new ArrayList<Album>();
		for (Album album : this.album_stock) {
			if (album.getArtist().equals(artist)) {
				Album add_album = new Album(album.getTitle(), album.getArtist(), album.getGenre(), album.getYear());
				for (String song : album.getSongs()) {
					add_album.addSong(song);
				}
			}
		}
		return target;
	}
	
	public ArrayList<String> getFileNames() {
		ArrayList<String> output = new ArrayList<String>();
		for (File file : this.files) {
			output.add(file.getName());
		}
		return output;
	}
	
	
	private void loadAlbum(String title, String artist) {
		for (File file : this.files) {
			try {
				if (!file.getName().equals("albums.txt")) {
					String[] album_data = file.getName().split("_");
					String target_title = album_data[0];
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
	
	private void loadSongs() {
		for (Album album : this.album_stock) {
			for (String song : album.getSongs()) {
				Song add_song = new Song(song, album.getTitle(), album.getArtist());
				this.inventory.add(add_song);
			}
		}
	}
	
	
	public ArrayList<Album> getAlbumsForTest() {
		return this.album_stock;
	}
	
	public ArrayList<Song> getSongsForTest() {
		return this.inventory;
	}

}
