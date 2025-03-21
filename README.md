# Music Library Project

**Authors**: Kyle Becker & Rishi Doshi

## Overview

This project is a Music Library system that supports multiple users, secure login/registration, and comprehensive library management. It demonstrates:

- **Multiple Users**: Each user has their own personal library (`LibraryModel`).
- **Secure Login & Registration**: Passwords are salted and hashed.
- **Music Store Integration**: A `MusicStore` class loads album and song data from resource files.
- **Library Management**: Users can search for songs and albums, create/manage playlists, rate songs, and track play counts.
- **Automatic Playlists**: 
  - *Most Recently Played* (up to 10 songs)
  - *Most Frequently Played* (top 10 by play count)
  - *Favorite Songs* (songs rated 5)

## Directory Structure
MusicLibraryProject/ 
├── src/ 
│    ├── model/ # Contains model classes (LibraryModel, Song, Album, Rating, PlayList, UserAccount) 
│    ├── database/ # Contains MusicStore and resource-loading classes 
│    ├── view/ # Contains TextView (user interface) 
│    └── PasswordStorage/ # Contains user data files (e.g., users.dat) 
├── test/ # JUnit tests for the project 
│    ├── AlbumTest.java 
│    ├── LibraryModelTest.java 
│    ├── MusicStoreTest.java 
│    ├── PlayListTest.java 
│    ├── RatingTest.java 
│    ├── SongTest.java 
│    └── UserManagerTest.java 
├── resources/ # Resource files (albums.txt and associated album files) 
└── ...


## Requirements

- **Java Version**: 11+ (verify using `java -version`)
- **Executable JAR**: Ensure `MusicLibraryProject.jar` is in the project folder.
- **Resource Files**: The `resources/` folder must be intact for the `MusicStore` to load album data properly.

## How to Run

1. **Open a Terminal** and navigate to the project directory containing `MusicLibraryProject.jar`.

2. **Run the Application**:
   ```bash
   java -jar MusicLibraryProject.jar
   
3. **Follow the On-Screen Prompts:**
   - Login or Register
   - Navigate the menu to search the store, manage your library, create playlists, and play songs.

## How to Compile (Optional)
   ```bash
   javac -d out -sourcepath src src/view/TextView.java

   jar cfe MusicLibraryProject.jar view.TextView -C out
