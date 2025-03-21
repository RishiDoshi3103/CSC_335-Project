/**
 * File: TextView.java
 * Authors: Kyle Becker / Rishi Doshi / AI
 * 
 * This class implements the text-based user interface (View) for the Music Library Project.
 * It interacts only via public methods of the MusicStore and LibraryModel, thereby maintaining encapsulation.
 * 
 * Functionalities include:
 * - Searching the Music Store for songs (by title or artist) and albums (by title or artist) and adding them to the library.
 * - Accessing the user library to list/search songs, list artists, albums, and playlists.
 * - Managing playlists (creating, removing, adding/removing songs).
 * - Rating songs and marking songs as favorite.
 * 
 * AI (chatgpt) was used to augment this file (and this file only), to assist with user friendly
 * interaction and functionality.
 */

package view;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.PlayList;
import model.Rating;
import model.Song;
import model.UserAccount;
import model.UserManager;

public class TextView {

	private static MusicStore store = new MusicStore();
    private static LibraryModel library = new LibraryModel();
    private static Scanner scanner = new Scanner(System.in);
    private static Song current; // Song currently playing
    
 // UserManager for handling login/registration.
    private static UserManager um = new UserManager();
    
    public static void main(String[] args) {
    	// First, perform login/registration.
        UserAccount user = displayLoginMenu();
        library = user.getLibrary();
        System.out.println("User's library loaded.");
        // Then launch the main menu.
        mainMenu();
    }
    
    // ---------------------------
    // LOGIN/REGISTRATION METHODS
    // ---------------------------
    
    /**
     * Displays a login/registration menu until a valid user is logged in.
     * @return The logged-in UserAccount.
     */
    private static UserAccount displayLoginMenu() {
        UserAccount loggedInUser = null;
        while (loggedInUser == null) {
            System.out.println("Welcome to the Music Library System (LA2)!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                loggedInUser = login();
            } else if (choice.equals("2")) {
                register();
            } else if (choice.equals("3")) {
                System.out.println("--- Exiting ---");
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        return loggedInUser;
    }
    
    private static String readPassword(String prompt) {
        // Try to get the system console (will be null in some IDEs)
        Console console = System.console();
        if (console != null) {
            char[] pwd = console.readPassword(prompt);
            return new String(pwd);
        } else {
            // Fallback: password will be visible
            System.out.print(prompt);
            return scanner.nextLine().trim();
        }
    }

    
    private static UserAccount login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        String password = readPassword("Enter Password: ");
        UserAccount user = um.login(username, password);
        
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            return user;
        } else {
            System.out.println("Login failed. Please check your credentials.");
            return null;
        }
    }

    private static void register() {
        System.out.print("Enter desired username: ");
        String username = scanner.nextLine().trim();
        String password = readPassword("Enter desired password: ");
        if (um.registerUser(username, password)) {
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Registration failed. Username may already be in use.");
        }
    }
    
    /**
     * Logs out the current user.
     * Resets session-specific data, then returns to the login/registration screen.
     */
    private static void logout() {
        System.out.println("Logging out...");
        // Here you might want to persist any changes before logging out.
        current = null;
        library = null;
        // Return to login/registration screen.
        UserAccount user = displayLoginMenu();
        library = user.getLibrary();
        System.out.println(user.getUsername() + "'s library loaded.");
    }

    
    // ---------------------------
    // MAIN MENU METHODS
    // ---------------------------
    
    private static void mainMenu() {
    	boolean menuRunning = true;
    	while (menuRunning) {
            System.out.println("\n--- Rishi & Kyle's Music Library ---");
            if (current != null) {
            	System.out.println("Currently Playing: " + current + "\n");
            }
            System.out.println("- Store:");
            System.out.println("    1. Search Store\n");
            System.out.println("- Library:");
            System.out.println("    2. Songs");
            System.out.println("    3. Artists");
            System.out.println("    4. Albums");
            System.out.println("    5. Playlists");
            System.out.println("    6. Favorite Songs");
            System.out.println("    7. Music Player\n");
            System.out.println("    8. Logout");
            System.out.println("9. Exit Music Player\n");
            System.out.print("Enter Choice: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    searchStore();
                    break;
                case "2":
                    librarySongsMenu();
                    break;
                case "3":
                    libraryArtistsMenu();
                    break;
                case "4":
                    libraryAlbumsMenu();
                    break;
                case "5":
                    libraryPlaylistsMenu();
                    break;
                case "6":
                	ArrayList<Song> faves = library.getFavorites();
                	if (faves.size() > 0) {
                		for (Song song : faves) {
                			System.out.println(song.toString());
                		}
                	}
                	else {
                		System.out.println("No Songs Rated 5, yet!");
                	}
                	break;
                case "7":
                	libraryPlayer();
                	break;
                case "8":
                    logout();
                    break;
                case "9":
                    System.out.println("--- Exiting ---");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter the number associated with your selection.");
            }
        }
    }
    
    private static void libraryPlayer() {
    	while(true) {
    		System.out.println("\n--- Player ---");
    		ArrayList<Song> songs = library.getAllSongs();
            printSongList(songs);
            System.out.println("Choose a Song to Play - Enter index, or type exit to go back:");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
     
                int index = Integer.parseInt(input);
                if (index >= 0 && index < songs.size()) {
                    Song selected = songs.get(index);
                    current = library.playSong(selected);
                    break;
                } else {
                    System.out.println("Invalid index. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number or 'exit'.");
            }
        }
    }
    // ========================
    // STORE MENU FUNCTIONS
    // ========================
    private static void searchStore() {
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
            switch (choice) {
                case "1":
                    storeSearchSongByTitle();
                    break;
                case "2":
                    storeSearchSongByArtist();
                    break;
                case "3":
                    storeSearchAlbumByTitle();
                    break;
                case "4":
                    storeSearchAlbumByArtist();
                    break;
                case "5":
                    storeRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void storeSearchSongByTitle() {
        System.out.print("Enter Song title: ");
        String title = scanner.nextLine().trim();
        ArrayList<Song> results = store.searchSongsByTitle(title);
        if (results.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }
        printSongList(results);
        promptAddSongToLibrary(results);
    }
    
    private static void storeSearchSongByArtist() {
        System.out.print("Enter Song artist: ");
        String artist = scanner.nextLine().trim();
        ArrayList<Song> results = store.searchSongsByArtist(artist);
        if (results.isEmpty()) {
            System.out.println("No songs found.");
            return;
        }
        printSongList(results);
        promptAddSongToLibrary(results);
    }
    
    private static void storeSearchAlbumByTitle() {
        System.out.print("Enter Album title: ");
        String title = scanner.nextLine().trim();
        ArrayList<Album> results = store.searchAlbumsByTitle(title);
        if (results.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }
        printAlbumList(results);
        promptAddAlbumToLibrary(results);
    }
    
    private static void storeSearchAlbumByArtist() {
        System.out.print("Enter Album artist: ");
        String artist = scanner.nextLine().trim();
        ArrayList<Album> results = store.searchAlbumsByArtist(artist);
        if (results.isEmpty()) {
            System.out.println("No albums found.");
            return;
        }
        printAlbumList(results);
        promptAddAlbumToLibrary(results);
    }
    
    // Helper methods for store search
    private static void printSongList(ArrayList<Song> songs) {
        for (int i = 0; i < songs.size(); i++) {
            System.out.println(i + ". " + songs.get(i));
        }
    }
    
    private static void printAlbumList(ArrayList<Album> albums) {
        for (int i = 0; i < albums.size(); i++) {
            System.out.println(i + ". " + albums.get(i));
        }
    }
    
    private static void promptAddSongToLibrary(ArrayList<Song> songs) {
        while (true) {
            System.out.println("\nAdd Song to library? Enter index, or type 'exit' to return:");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                int index = Integer.parseInt(input);
                if (index >= 0 && index < songs.size()) {
                    Song selected = songs.get(index);
                    if (library.addSong(selected)) {
                        System.out.println("Song added: " + selected);
                    } else {
                        System.out.println("Song already in library.");
                    }
                } else {
                    System.out.println("Invalid index. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number or 'exit'.");
            }
        }
    }
    
    private static void promptAddAlbumToLibrary(ArrayList<Album> albums) {
        while (true) {
            System.out.println("\nAdd all songs from an album to library? Enter index, or type 'exit' to return:");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                int index = Integer.parseInt(input);
                if (index >= 0 && index < albums.size()) {
                    Album selected = albums.get(index);
                    int addedCount = 0;
                    for (String track : selected.getSongs()) {
                        Song albumSong = new Song(track, selected.getTitle(), selected.getArtist());
                        if (library.addSong(albumSong)) {
                            addedCount++;
                        }
                    }
                    System.out.println(addedCount + " song(s) added from: " + selected.getTitle());
                    if (library.addAlbum(selected)) {
                    	System.out.println("Successfully added Album: " + selected.getTitle());
                    }
                    else {
                    	System.out.println("Error: Album already in library, or failed to add.");
                    }
                } else {
                    System.out.println("Invalid index. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid number or 'exit'.");
            }
        }
    }
    
    // ========================
    // LIBRARY MENU FUNCTIONS
    // ========================
    private static void libraryMenu() {
        boolean libRunning = true;
        while (libRunning) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Songs");
            System.out.println("2. Artists");
            System.out.println("3. Albums");
            System.out.println("4. Playlists");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    librarySongsMenu();
                    break;
                case "2":
                    libraryArtistsMenu();
                    break;
                case "3":
                    libraryAlbumsMenu();
                    break;
                case "4":
                    libraryPlaylistsMenu();
                    break;
                case "5":
                    libRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    // ---- Library > Songs Submenu ----
    private static void librarySongsMenu() {
        boolean songsRunning = true;
        while (songsRunning) {
            System.out.println("\n--- Library > Songs ---");
            System.out.println("1. List all songs");
            System.out.println("2. Search for a song by title");
            System.out.println("3. Search for a song by artist");
            System.out.println("4. Rate a song");
            System.out.println("5. Mark a song as favorite");
            System.out.println("6. Back to Library Menu");
            System.out.print("Enter choice: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    listAllSongs();
                    break;
                case "2":
                    searchSongInLibrary();
                    break;
                case "3":
                	searchSongInLibraryArtist();
                	break;
                case "4":
                    rateSongInLibrary();
                    break;
                case "5":
                    markSongAsFavorite();
                    break;
                case "6":
                    songsRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void listAllSongs() {
        ArrayList<Song> list = library.getAllSongs();
        if (list.isEmpty()) {
            System.out.println("No songs in your library.");
        } else {
            System.out.println("\n--- Songs in Your Library ---");
            for (Song song : list) {
                System.out.println(" - " + song.toString());
            }
        }
    }
    
    private static void searchSongInLibrary() {
        System.out.print("Enter song title to search in library: ");
        String title = scanner.nextLine().trim();
        ArrayList<Song> found = library.searchSongByTitle(title);
        if (found.size() > 0) {
          int i = 1;
	        for (Song song : found) {
	        	System.out.println(Integer.toString(i) + ". " + song.toString());
	        	i++;
	        }

        } else {
            System.out.println("Song not found in library.");
        }
    }
    
    private static void searchSongInLibraryArtist() {
    	System.out.print("Enter song artist to search in library: ");
    	String artist = scanner.nextLine().trim();
    	ArrayList<Song> found = library.searchSongByArtist(artist);
    	if (found.size() > 0) {
    		int i = 1;
    		for (Song song : found) {
    			System.out.println(Integer.toString(i) + ". " + song.toString());
    			i++;
    		}
    	} else {
    		System.out.println("Song not found in library.");
    	}
    }
    
    private static void rateSongInLibrary() {
        System.out.print("Enter song title to rate: ");
        String title = scanner.nextLine().trim();
        ArrayList<Song> song = library.searchSongByTitle(title);
        if (song.size() == 0) {
            System.out.println("Song not found in library.");
            return;
        }
        if (song.size() > 1) {
        	System.out.println("Multiple Songs found with title: " + title);
        	return;
        }
        System.out.print("Enter rating (1-5): ");
        String ratingStr = scanner.nextLine().trim();
        try {
            int ratingVal = Integer.parseInt(ratingStr);
            if (ratingVal < 1 || ratingVal > 5) {
                System.out.println("Rating must be between 1 and 5.");
                return;
            }
            library.setRating(song.get(0), ratingVal); 
            System.out.println("Song successfully rated " + Integer.toString(ratingVal) + ": " + song.get(0).toString());
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating input.");
        }
    }
    
    private static void markSongAsFavorite() {
        System.out.print("Enter song title to mark as favorite: ");
        String title = scanner.nextLine().trim();
        ArrayList<Song> songs = library.searchSongByTitle(title);
        if (songs.size() == 0) {
            System.out.println("Song not found in library.");
            return;
        }
        if (songs.size() > 1) {
        	System.out.println("Multiple Songs found with title: " + title);
        	return;
        }
        library.setRating(songs.get(0), 5);
        System.out.println("Song successfully marked as favorite: " + songs.get(0).toString());
    }
    
    // ---- Library > Artists Submenu ----
    private static void libraryArtistsMenu() {
        ArrayList<String> artists = library.getArtists();
        if (artists.isEmpty()) {
            System.out.println("No artists in your library.");
        } else {
            System.out.println("\n--- Artists in Your Library ---");
            for (String artist : artists) {
                System.out.println(" - " + artist);
            }
        }
        System.out.println("Press Enter to return to Library Menu.");
        scanner.nextLine();
    }
    
    // ---- Library > Albums Submenu ----
    private static void libraryAlbumsMenu() {
        boolean albumsRunning = true;
        while (albumsRunning) {
            System.out.println("\n--- Library > Albums ---");
            System.out.println("1. List all albums");
            System.out.println("2. Search for an album by title");
            System.out.println("3. Search for an album by artist");
            System.out.println("4. Back to Library Menu");
            System.out.print("Enter choice: ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    listAllAlbums();
                    break;
                case "2":
                    searchAlbumInLibraryByTitle();
                    break;
                case "3":
                    searchAlbumInLibraryByArtist();
                    break;
                case "4":
                    albumsRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void listAllAlbums() {
        ArrayList<Album> albums = library.getAllAlbumsInLibrary();
        if (albums.isEmpty()) {
            System.out.println("No albums in your library.");
        } else {
            System.out.println("\n--- Albums in Your Library ---");
            for (Album album : albums) {
                System.out.println(album);
                System.out.println("---------------------");
            }
        }
        System.out.println("Press Enter to return to Albums Menu.");
        scanner.nextLine();
    }

    private static void searchAlbumInLibraryByTitle() {
        System.out.print("Enter album title to search for: ");
        String title = scanner.nextLine().trim();
        ArrayList<Album> albums = library.searchAlbumsByTitle(title);
        if (albums.isEmpty()) {
            System.out.println("No albums found with that title.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Album album : albums) {
                System.out.println(album);
                System.out.println("---------------------");
            }
        }
        System.out.println("Press Enter to return to Albums Menu.");
        scanner.nextLine();
    }

    private static void searchAlbumInLibraryByArtist() {
        System.out.print("Enter album artist to search for: ");
        String artist = scanner.nextLine().trim();
        ArrayList<Album> albums = library.searchAlbumsByArtist(artist);
        if (albums.isEmpty()) {
            System.out.println("No albums found by that artist.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Album album : albums) {
                System.out.println(album);
                System.out.println("---------------------");
            }
        }
        System.out.println("Press Enter to return to Albums Menu.");
        scanner.nextLine();
    }

    // ---- Library > Playlists Submenu ----
    private static void libraryPlaylistsMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Library > Playlists ---");
            System.out.println("1. Create a playlist");
            System.out.println("2. Remove a playlist");
            System.out.println("3. Add a song to a playlist");
            System.out.println("4. Remove a song from a playlist");
            System.out.println("5. Search for a playlist");
            System.out.println("6. List all playlists");
            System.out.println("7. Back to Library Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    createPlaylist();
                    break;
                case "2":
                    removePlaylist();
                    break;
                case "3":
                    addSongToPlaylist();
                    break;
                case "4":
                    removeSongFromPlaylist();
                    break;
                case "5":
                    viewPlaylist();
                    break;
                case "6":
                    listAllPlaylists();
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void createPlaylist() {
        System.out.print("Enter new playlist name: ");
        String name = scanner.nextLine().trim();
        if (library.createPlaylist(name)) {
            System.out.println("Playlist '" + name + "' created.");
        } else {
            System.out.println("Playlist could not be created (perhaps it already exists).");
        }
    }
    
    private static void removePlaylist() {
        ArrayList<String> playlists = library.getPlaylists();  // Fetch the list of playlists
        if (playlists.isEmpty()) {
            System.out.println("No playlists available.");
            return;
        }

        // List all playlists with indices
        System.out.println("\n--- Available Playlists ---");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println(i + ". " + playlists.get(i));
        }

        System.out.print("Enter the index of the playlist to remove or type 'exit' to return: ");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("exit")) {
            return;
        }

        try {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < playlists.size()) {
                String playlistName = playlists.get(index);
                if (library.removePlaylist(playlistName)) {
                    System.out.println("Playlist '" + playlistName + "' removed.");
                } else {
                    System.out.println("Playlist could not be removed.");
                }
            } else {
                System.out.println("Invalid index. Try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid index or 'exit'.");
        }
    }

    private static void addSongToPlaylist() {
        // Display the list of playlists first
        ArrayList<String> playlists = library.getPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No playlists available in your library.");
            return;
        }

        // Display available playlists for user to choose
        System.out.println("\n--- Available Playlists ---");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println(i + ". " + playlists.get(i));
        }

        // Prompt until a valid playlist index is entered or user types "exit"
        int playlistIndex = -1;
        while (playlistIndex < 0 || playlistIndex >= playlists.size()) {
            System.out.print("Enter playlist number to add song to (or type 'exit' to cancel): ");
            String input = scanner.nextLine().trim();

            // Check if user wants to exit
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting add song to playlist.");
                return;  // Exit the method and return to previous state (e.g., main menu)
            }

            try {
                playlistIndex = Integer.parseInt(input);
                if (playlistIndex < 0 || playlistIndex >= playlists.size()) {
                    System.out.println("Invalid playlist index. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit' to cancel.");
            }
        }

        String playlistName = playlists.get(playlistIndex);

        // Prompt the user for the song they want to add
        System.out.print("Enter song title to add: ");
        String songTitle = scanner.nextLine().trim();
        ArrayList<Song> songs = library.searchSongByTitle(songTitle);

        if (songs.isEmpty()) {
            System.out.println("Song not found in library. Add it to the library first.");
            return;
        }

        for (Song song : songs) {
            if (library.addSongToPlaylist(playlistName, song)) {
                System.out.println("Song added to playlist: " + song.toString());
            } else {
                System.out.println("Failed to add song: " + song.toString());
                System.out.println("Playlist might not exist or the song is already in there.");
            }
        }
    }


    private static void removeSongFromPlaylist() {
        // List all available playlists
        ArrayList<String> playlists = library.getPlaylists();
        if (playlists.isEmpty()) {
            System.out.println("No playlists available in your library.");
            return;
        }

        // Display the list of playlists for the user to choose from
        System.out.println("\n--- Available Playlists ---");
        for (int i = 0; i < playlists.size(); i++) {
            System.out.println(i + ". " + playlists.get(i));
        }

        // Prompt until a valid playlist index is entered or user types "exit"
        int playlistIndex = -1;
        while (playlistIndex < 0 || playlistIndex >= playlists.size()) {
            System.out.print("Enter playlist number to remove song from (or type 'exit' to cancel): ");
            String input = scanner.nextLine().trim();

            // Check if the user wants to exit
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting remove song from playlist.");
                return;  // Exit the method and return to previous state
            }

            try {
                playlistIndex = Integer.parseInt(input);
                if (playlistIndex < 0 || playlistIndex >= playlists.size()) {
                    System.out.println("Invalid playlist index. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit' to cancel.");
            }
        }

        String playlistName = playlists.get(playlistIndex);
        PlayList playlist = library.searchPlaylistByName(playlistName);

        // List songs in the selected playlist
        ArrayList<Song> playlistSongs = playlist.getSongs();
        if (playlistSongs.isEmpty()) {
            System.out.println("No songs in this playlist.");
            return;
        }

        // Display the songs in the playlist
        System.out.println("\n--- Songs in Playlist: " + playlistName + " ---");
        for (int i = 0; i < playlistSongs.size(); i++) {
            System.out.println(i + ". " + playlistSongs.get(i));
        }

        // Prompt the user to select the index of the song they want to remove
        int songIndex = -1;
        while (songIndex < 0 || songIndex >= playlistSongs.size()) {
            System.out.print("Enter song number to remove (or type 'exit' to cancel): ");
            String input = scanner.nextLine().trim();

            // Check if the user wants to exit
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting remove song from playlist.");
                return;  // Exit the method and return to previous state
            }

            try {
                songIndex = Integer.parseInt(input);
                if (songIndex < 0 || songIndex >= playlistSongs.size()) {
                    System.out.println("Invalid song index. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'exit' to cancel.");
            }
        }

        Song songToRemove = playlistSongs.get(songIndex);

        // Attempt to remove the song from the playlist
        if (library.removeSongFromPlaylist(playlistName, songToRemove.getTitle())) {
            System.out.println("Song removed from playlist: " + songToRemove.toString());
        } else {
            System.out.println("Failed to remove song from playlist. It may not be in the playlist.");
        }
    }

    private static void viewPlaylist() {
        System.out.print("Enter playlist name to view: ");
        String playlistName = scanner.nextLine().trim();
        PlayList p = library.searchPlaylistByName(playlistName);
        if (p != null) {
            //System.out.println("\nPlaylist: " + p.getName());
            System.out.println(p);
        } else {
            System.out.println("Playlist not found.");
        }
        System.out.println("Press Enter to return.");
        scanner.nextLine();
    }
    
    private static void listAllPlaylists() {
        ArrayList<String> lists = library.getPlaylists();
        if (lists.isEmpty()) {
            System.out.println("No playlists in your library.");
        } else {
            System.out.println("\n--- Playlists in Your Library ---");
            for (String name : lists) {
                System.out.println(" - " + name);
            }
        }
        System.out.println("Press Enter to return.");
        scanner.nextLine();
    }
}
