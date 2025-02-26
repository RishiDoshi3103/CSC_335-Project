package view;

import java.util.ArrayList;
import java.util.Scanner;

import database.MusicStore;
import model.Album;
import model.LibraryModel;
import model.PlayList;
import model.Rating;
import model.Song;

/**
 * TextView.java
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
 * AI-assisted code was used to structure the UI into small, testable pieces.
 */

public class TextView {
	// Static references for simplicity
    private static MusicStore store = new MusicStore();
    private static LibraryModel library = new LibraryModel();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Optionally, load store data here:
        // store.loadAlbums("resources/albums.txt", "resources");
        
        while (true) {
            System.out.println("\n--- Rishi & Kyle's Music Library ---");
            System.out.println("- Store:");
            System.out.println("    1. Search Store");
            System.out.println("- Library:");
            System.out.println("    2. Songs");
            System.out.println("    3. Artists");
            System.out.println("    4. Albums");
            System.out.println("    5. Playlists");
            System.out.println("6. Exit Music Player");
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
                    System.out.println("--- Exiting ---");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter the number associated with your selection.");
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
            System.out.println("3. Rate a song");
            System.out.println("4. Mark a song as favorite");
            System.out.println("5. Back to Library Menu");
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
                    rateSongInLibrary();
                    break;
                case "4":
                    markSongAsFavorite();
                    break;
                case "5":
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
           for (Song song : found) {
        	   System.out.println()
           }
        } else {
            System.out.println("Song not found in library.");
        }
    }
    
    private static void rateSongInLibrary() {
        System.out.print("Enter song title to rate: ");
        String title = scanner.nextLine().trim();
        Song song = library.searchSongByTitle(title);
        if (song == null) {
            System.out.println("Song not found in library.");
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
            song.setRating(Rating.fromInt(ratingVal));
            System.out.println("Song updated: " + song);
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating input.");
        }
    }
    
    private static void markSongAsFavorite() {
        System.out.print("Enter song title to mark as favorite: ");
        String title = scanner.nextLine().trim();
        Song song = library.searchSongByTitle(title);
        if (song == null) {
            System.out.println("Song not found in library.");
            return;
        }
        song.setRating(Rating.FIVE);  // If setting rating to FIVE automatically marks as favorite.
        System.out.println("Song marked as favorite: " + song);
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
        System.out.println("Press Enter to return to Library Menu.");
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
            System.out.println("5. View a playlist");
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
        System.out.print("Enter playlist name to remove: ");
        String name = scanner.nextLine().trim();
        if (library.removePlaylist(name)) {
            System.out.println("Playlist '" + name + "' removed.");
        } else {
            System.out.println("Playlist not found or could not be removed.");
        }
    }
    
    private static void addSongToPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();
        System.out.print("Enter song title to add: ");
        String songTitle = scanner.nextLine().trim();
        Song song = library.searchSongByTitle(songTitle);
        if (song == null) {
            System.out.println("Song not found in library. Add it to library first.");
            return;
        }
        if (library.addSongToPlaylist(playlistName, song)) {
            System.out.println("Song added to playlist '" + playlistName + "'.");
        } else {
            System.out.println("Failed to add song. Check if the playlist exists.");
        }
    }
    
    private static void removeSongFromPlaylist() {
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine().trim();
        System.out.print("Enter song title to remove: ");
        String songTitle = scanner.nextLine().trim();
        if (library.removeSongFromPlaylist(playlistName, songTitle)) {
            System.out.println("Song removed from playlist '" + playlistName + "'.");
        } else {
            System.out.println("Failed to remove song. Check the playlist and song title.");
        }
    }
    
    private static void viewPlaylist() {
        System.out.print("Enter playlist name to view: ");
        String playlistName = scanner.nextLine().trim();
        PlayList p = library.searchPlaylistByName(playlistName);
        if (p != null) {
            System.out.println("\nPlaylist: " + p.getName());
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
