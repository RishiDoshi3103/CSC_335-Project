# Music Library Project

## Overview

The Music Library Project is a Java-based application that manages a collection of music albums and songs. The project demonstrates a simplified Model-View-Controller (MVC) architecture, where:
- **Model:** Contains the core data classes such as `Album`, `Song`, `PlayList`, `LibraryModel`, and `Rating`.
- **Database:** The `MusicStore` class simulates a pseudo-database by loading album data from text files.
- **View:** The `TextView` class provides a text-based user interface for interacting with the music library.
- **Main:** The entry point of the application.

## Features

- **Album Management:** Load albums from text files, preserving the order of songs.
- **Song Management:** Each song has a title, album, artist, a rating (using an enum), and a favorite flag.
- **Playlist Management:** Create and manage playlists with their own rating and favorite status.
- **Library Model:** Stores albums that the user adds to their personal library.
- **Text-Based User Interface:** Interact with the music library through simple command-line prompts.

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/MusicLibraryProject.git
