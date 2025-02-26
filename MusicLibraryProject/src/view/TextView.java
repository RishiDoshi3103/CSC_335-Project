package view;

import java.util.ArrayList;
import java.util.Scanner;

import database.MusicStore;
import model.LibraryModel;
import model.Song;
import model.Album;

public class TextView {
	private static MusicStore store = new MusicStore();
	private static LibraryModel library = new LibraryModel(store);
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;
		
		while(!exit) {
			System.out.println("\n--- Rishi & Kyle's Music Library ---");
			System.out.println("1. Search Store");
			System.out.println("2. Access Library");
			System.out.println("3. Exit");
			System.out.print("Enter Choice: ");
			
			String choice = scanner.nextLine().trim();
			
			switch (choice) {
				case "1":
					searchStore(scanner);
					break;
				case "2":
					//accessLibrary(scanner);
					break;
				case "3":
					System.out.println("--- Exiting ---");
					exit = true; // Needed? Needs review.
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Please enter number associated with desired selection.");
			}
		}
		scanner.close();
	}
	
	private static void searchStore(Scanner scanner) {
		boolean storeRunning = true;
		
		while (storeRunning) {
			System.out.println("\n--- Store ---");
			System.out.println("1. Search songs by title");
			System.out.println("2. Search songs by artist");
			System.out.println("3. Search albums by title");
			System.out.println("4. Search albums by artist");
			System.out.println("5. Back to Main Menu");
			
			System.out.print("Enter choice: ");
			String choice = scanner.nextLine().trim();
			
			switch(choice) {
				case "1":
					System.out.print("Enter Song title: ");
					String title = scanner.nextLine().trim();
					ArrayList<Song> title_list = store.searchSongsByTitle(title);
					if (title_list.size() > 0) {
						for (int i=0; i < title_list.size(); i++) {
							System.out.print("\n" + Integer.toString(i) + ". " + title_list.get(i).toString());
						}
					}
					else {
						System.out.println("No songs found.");
					}
					
					boolean validPick = false;
					while (!validPick) {
						System.out.println("\nAdd Song to library? Enter number, or type exit to return: ");
						String pick = scanner.nextLine().trim();
						
						if (pick.toLowerCase().equals("exit")) {
							validPick = true;
							break;
						}
						
						try {
							int songIndex = Integer.parseInt(pick);
							if (songIndex >= 0 && songIndex < title_list.size()) {
								Song selectedSong = title_list.get(songIndex);
								if (library.addSong(selectedSong)) {
									System.out.println("Song successfully added: " + selectedSong.toString());
									validPick = true;
								}
								else {
									System.out.println("Song already in library");
								}
							}
							else {
								System.out.println("Invalid number. Please choose a valid option.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a valid number or type 'exit' to return");
						}
					}
					break;
					
				case "2":
					System.out.print("Enter Song artist: ");
					String artist = scanner.nextLine().trim();
					ArrayList<Song> artist_list = store.searchSongsByArtist(artist); // create by artist
					if (artist_list.size() > 0) {
						for (int i=0; i < artist_list.size(); i++) {
							System.out.print("\n" + Integer.toString(i) + ". " + artist_list.get(i).toString());
						}
					}
					else {
						System.out.println("No songs found.");
					}
					
					validPick = false;
					while (!validPick) {
						System.out.println("\nAdd Song to library? Enter number, or type exit to return: ");
						String pick = scanner.nextLine().trim();
						
						if (pick.toLowerCase().equals("exit")) {
							validPick = true;
							break;
						}
						
						try {
							int songIndex = Integer.parseInt(pick);
							if (songIndex >= 0 && songIndex < artist_list.size()) {
								Song selectedSong = artist_list.get(songIndex);
								if (library.addSong(selectedSong)) {
									System.out.println("Song successfully added: " + selectedSong.toString());
									validPick = true;
								}
								else {
									System.out.println("Song already in library");
								}
							}
							else {
								System.out.println("Invalid number. Please choose a valid option.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a valid number or type 'exit' to return");
						}
					}
					break;
					
				case "3":
					System.out.print("Enter Album title: ");
					title = scanner.nextLine().trim();
					ArrayList<Album> album_titles = store.searchAlbumsByTitle(title);
					if (album_titles.size() > 0) {
						for (int i=0; i < album_titles.size(); i++) {
							System.out.print("\n" + Integer.toString(i) + ". " + album_titles.get(i).toString() + "\n");
						}
					}
					else {
						System.out.println("No Albums found.");
					}
					
					validPick = false;
					while (!validPick) {
						System.out.println("\nAdd Album Songs to library? Enter number, or type exit to return: ");
						String pick = scanner.nextLine().trim();
						
						if (pick.toLowerCase().equals("exit")) {
							validPick = true;
							break;
						}
						
						try {
							int albumIndex = Integer.parseInt(pick);
							if (albumIndex >= 0 && albumIndex < album_titles.size()) {
								Album selectedAlbum = album_titles.get(albumIndex);
								// Create songs to add based on Album List, and track
								// number added
								int added = 0;
								for (String song : selectedAlbum.getSongs()) {
									Song album_song = new Song(song, selectedAlbum.getTitle(), selectedAlbum.getArtist());
									if (library.addSong(album_song)) {
										added++;
									}
								}
								if (added > 0) {
									System.out.println(Integer.toString(added) + " Songs successfully added from: " + selectedAlbum.getTitle());
									validPick = true;
								}
								else {
									System.out.println("All Album Songs already in library");
								}
							}
							else {
								System.out.println("Invalid number. Please choose a valid option.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a valid number or type 'exit' to return");
						}
					}
					break;
					
				case "4":
					System.out.print("Enter Album Artist: ");
					artist = scanner.nextLine().trim();
					ArrayList<Album> album_artists = store.searchAlbumsByArtist(artist);
					if (album_artists.size() > 0) {
						for (int i=0; i < album_artists.size(); i++) {
							System.out.print("\n" + Integer.toString(i) + ". " + album_artists.get(i).toString() + "\n");
						}
					}
					else {
						System.out.println("No Albums found.");
					}
					
					validPick = false;
					while (!validPick) {
						System.out.println("\nAdd Album Songs to library? Enter number, or type exit to return: ");
						String pick = scanner.nextLine().trim();
						
						if (pick.toLowerCase().equals("exit")) {
							validPick = true;
							break;
						}
						
						try {
							int albumIndex = Integer.parseInt(pick);
							if (albumIndex >= 0 && albumIndex < album_artists.size()) {
								Album selectedAlbum = album_artists.get(albumIndex);
								// Create songs to add based on Album List, and track
								// number added
								int added = 0;
								for (String song : selectedAlbum.getSongs()) {
									Song album_song = new Song(song, selectedAlbum.getTitle(), selectedAlbum.getArtist());
									if (library.addSong(album_song)) {
										added++;
									}
								}
								if (added > 0) {
									System.out.println(Integer.toString(added) + " Songs successfully added from: " + selectedAlbum.getTitle());
									validPick = true;
								}
								else {
									System.out.println("All Album Songs already in library");
								}
							}
							else {
								System.out.println("Invalid number. Please choose a valid option.");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid input. Please enter a valid number or type 'exit' to return");
						}
					}
					break;
					
				case "5":
					storeRunning = false;
					break;
					
				default:
					System.out.println("Invalid choice. Please enter number associated with desired selection.");

			}
		}
	}
}
