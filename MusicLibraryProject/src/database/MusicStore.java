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
		
		Path path = Paths.get("resources");
		//System.out.println(path.toAbsolutePath());
		File catalogue = new File(path.toAbsolutePath().toString());
		//System.out.println(catalogue.exists());
		
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
					//System.out.println("Title: " + next_album[0] + " | Artist: " + next_album[1]);
					loadAlbum(next_album[0], next_album[1]);
				}
				index_scanner.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error With albums.txt Read | " + e);
			}
		}
		/**
		for (File file : this.files) {
			try {
				Scanner scanner = new Scanner(file);
				String[] header = scanner.nextLine().split(",");
				System.out.println(Arrays.toString(header));
				Album album = new Album(header[0], header[1], header[2], header[3]);
				while(scanner.hasNextLine()) {
					String name = scanner.nextLine();
					//System.out.println(name);
					album.addSong(name);
				}
				this.album_stock.add(album);
				scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
	
	public ArrayList<String> getFileNames() {
		ArrayList<String> output = new ArrayList<String>();
		for (File file : this.files) {
			output.add(file.getName());
		}
		return output;
	}
	
	public int getNumAlbums() {
		return this.album_stock.size();
	}
	
	private void loadAlbum(String title, String artist) {
		for (File file : this.files) {
			try {
				if (!file.getName().equals("albums.txt")) {
					String[] album_data = file.getName().split("_");
					String target_title = album_data[0];
					String target_artist = album_data[1].substring(0, album_data[1].length() - 4);
					//System.out.println(target_artist);
					if (target_title.equals(title) && target_artist.equals(artist)) {
						try {
							Scanner scanner = new Scanner(file);
							String[] header = scanner.nextLine().split(",");
							//System.out.println(Arrays.toString(header));
							Album album = new Album(header[0], header[1], header[2], header[3]);
							while(scanner.hasNextLine()) {
								String name = scanner.nextLine();
								//System.out.println(name);
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
				System.out.println("Album Load Error: " + file.getName() + " | " + e);
			}
		}
	}
	
	public ArrayList<Album> getAlbumsForTest() {
		return this.album_stock;
	}

}
